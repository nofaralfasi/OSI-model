package il.ac.afeka.comm.ip;

import il.ac.afeka.util.Injection;
import il.ac.afeka.comm.physical.Host;
import il.ac.afeka.comm.physical.Switch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class IPHost extends Host {

    private BlockingQueue<ICMPPacket> receivedICMP; // queue of received icmp packets

    private Map<String, IPAddress> gateways; // associates a network il.ac.afeka.comm.ip address with the gateway for that address
    private IPAddress defaultGatewayAddr; // the il.ac.afeka.comm.ip address of the default gateway

    private Injection<IPAddress, String> arpCache; // associates il.ac.afeka.comm.ip addresses to mac addresses. It must always include
    // at least the mac addresses and il.ac.afeka.comm.ip addresses of the host itself.

    /* Configuration API */

    public IPHost(String name) {
        super(name);
        arpCache = new Injection<IPAddress, String>();
        gateways = new HashMap<String, IPAddress>();
        receivedICMP = new LinkedBlockingDeque<>();
    }

    public void install(Switch lan, String macAddr, IPAddress ipAddr) {
        install(lan, macAddr);
        arpCache.put(ipAddr, macAddr);
    }

    public void addGateway(String netAddr, IPAddress gatewayAddr) {
        gateways.put(netAddr, gatewayAddr);
    }

    public void setDefaultGateway(IPAddress defaultGatewayAddr) {
        this.defaultGatewayAddr = defaultGatewayAddr;
    }

    /* IP I/O API */

    public ICMPPacket receiveICMP(Integer timeout) {

        ICMPPacket result = null;
            try {
                result = receivedICMP.poll(timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) { }

            return result;
    }

    public int ipsend(IPPacket ippacket) {

        // if the packet is for a host on one of my networks, find out the mac address of that host and send the packet directly.

        String dstMacAddr = null;

        String dstNetId = ippacket.getDstAddr().getNetworkId();

        String srcMacAddr = findIfaceForIPNet(dstNetId);

        if (srcMacAddr != null) { // (local transfer) we have found that we have a mac interface for the destination's il.ac.afeka.comm.ip network

            dstMacAddr = arpCache.getByFirst(ippacket.getDstAddr());

        } else { // (remote transfer through gateways) otherwise, find the gateway that accepts packets to this host and send it to the gateway.

            IPAddress gateway = gateways.get(ippacket.getDstAddr().getNetworkId());

            if (gateway == null) // otherwise, send it to the default gateway.
                gateway = defaultGatewayAddr;

            if (gateway == null) { // oops, no default gateway, this is probably because the host is a gateway
                // so we don't know how to route this packet.

                ipsend( new ICMPDestinationUnreachable(pickAnIPAddress(), ippacket.getSrcAddr(), ippacket.toString()));

                return -1;
            }

            // find the mac address for the gateway's lan

            srcMacAddr = findIfaceForIPNet(gateway.getNetworkId());

            assert srcMacAddr != null; // the current host must be a member of each of its gateways lans.

            dstMacAddr = arpCache.getByFirst(gateway);

            if (dstMacAddr == null) { // the gateway's mac is not in the arp cache

                broadcast(srcMacAddr, new ArpRequest(gateway, srcMacAddr, arpCache.getBySecond(srcMacAddr)));

                return -1;
            }
        }

        return sendaux(srcMacAddr, dstMacAddr, ippacket);
    }

    public void arrivedICMP(ICMPPacket packet) {
        receivedICMP.add(packet);
    }

    // returns one of this host's il.ac.afeka.comm.ip addresses

    private IPAddress pickAnIPAddress() {
        String someMacAddress = lans.keySet().iterator().next();
        return arpCache.getBySecond(someMacAddress);
    }

    private int sendaux(String srcMacAddr, String dstMacAddr, IPPacket ippacket) {

        if (dstMacAddr == null) {

            broadcast(srcMacAddr, new ArpRequest(ippacket.getDstAddr(), srcMacAddr, arpCache.getBySecond(srcMacAddr)));

            return -1;

        } else { // we have all the information we need, send the packet directly to the destination host

            send(srcMacAddr, dstMacAddr, ippacket);

            return 0;
        }
    }

    /* ARP & ICMP helper methods */

    String resolve(IPAddress ipAddr) { return arpCache.getByFirst(ipAddr); }

    void update(IPAddress srcIpAddr, String srcMacAddr) {
        arpCache.put(srcIpAddr, srcMacAddr);
    }

    private String findIfaceForIPNet(String ipNetId) {
        for(String macAddr : lans.keySet()) {
            if (arpCache.getBySecond(macAddr).getNetworkId().equals(ipNetId))
                return macAddr;
        }
        return null;
    }

    boolean hasIpAddress(IPAddress dstAddr) {
        return lans.containsKey(arpCache.getByFirst(dstAddr));
    }
}

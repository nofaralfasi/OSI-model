package il.ac.afeka.comm.ip;

import il.ac.afeka.comm.physical.PhysicalPayload;
import il.ac.afeka.comm.physical.Host;

class ArpRequest implements PhysicalPayload {

    private IPAddress queryIpAddr;
    private String srcMacAddr;
    private IPAddress srcIpAddr;

    public ArpRequest(IPAddress qip, String srcmac, IPAddress srcip) {
        queryIpAddr = qip;
        srcMacAddr = srcmac;
        srcIpAddr = srcip;
    }

    @Override
    public void process(Host h) {

        IPHost host = (IPHost)h;

        host.update(srcIpAddr, srcMacAddr); // use the opportunity to update the arp table with the details of the asking host

        String macAddr = host.resolve(queryIpAddr);

        if (host.hasMacAddr(macAddr)) { // answer only if it is your mac

            host.send(macAddr, srcMacAddr, new ArpReply(queryIpAddr, macAddr));
        }
    }

    @Override
    public String toString() {
        return "ARP-REQ from " + srcMacAddr + " " + queryIpAddr + " => ?" + "\n";
    }
}

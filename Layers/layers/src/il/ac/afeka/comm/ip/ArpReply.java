package il.ac.afeka.comm.ip;

import il.ac.afeka.comm.physical.PhysicalPayload;
import il.ac.afeka.comm.physical.Host;

class ArpReply implements PhysicalPayload {

    private final IPAddress queryIpAddr;
    private final String replyMacAddr;

    public ArpReply(IPAddress queryIpAddr, String replyMacAddr) {
        this.queryIpAddr = queryIpAddr;
        this.replyMacAddr = replyMacAddr;
    }

    @Override
    public void process(Host h) {

        IPHost host = (IPHost)h;

        host.update(queryIpAddr, replyMacAddr);
    }

    @Override
    public String toString() {
        return "ARP-REPLY " + queryIpAddr + " => " + replyMacAddr + "\n";
    }
}

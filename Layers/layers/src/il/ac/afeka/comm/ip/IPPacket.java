package il.ac.afeka.comm.ip;

import il.ac.afeka.comm.physical.PhysicalPayload;
import il.ac.afeka.comm.physical.Host;

import java.io.PrintStream;

public abstract class IPPacket implements PhysicalPayload {

    protected IPAddress srcAddr;
    protected IPAddress dstAddr;

    public IPAddress getSrcAddr() { return srcAddr; }
    public IPAddress getDstAddr() { return dstAddr; }

    public void process(Host s) {

        IPHost host = (IPHost)s;

        // if this packet is for me, actually process it, otherwise send it towards its destination
        if (host.hasIpAddress(dstAddr))
            processLocally(host);
        else
            host.ipsend(this);
    }

    public abstract void processLocally(IPHost host);

    public void print(PrintStream out) {
        out.print("IP Packet from " + srcAddr.toString() + " to " + dstAddr.toString() + " " + toString());
    }

    public abstract String toString();

}

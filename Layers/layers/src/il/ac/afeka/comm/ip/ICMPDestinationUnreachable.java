package il.ac.afeka.comm.ip;

public class ICMPDestinationUnreachable extends ICMPPacket {

    public ICMPDestinationUnreachable(IPAddress srcAddr, IPAddress dstAddr, String data) {
        super(srcAddr, dstAddr, 0, 0, data);
    }

    @Override
    public void processLocally(IPHost host) {
        host.arrivedICMP(this);
    }

    @Override
    public String toString() {
        return "ICMP Destination unreachable for packet " +  data + "\n";
    }
}

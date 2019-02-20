package il.ac.afeka.comm.ip;

public class ICMPEchoReply extends ICMPPacket {

    public ICMPEchoReply(IPAddress srcAddr, IPAddress dstAddr, Integer id, Integer seq, String data) {
        super(srcAddr, dstAddr, id, seq, data);
    }

    @Override
    public void processLocally(IPHost s) {
        s.arrivedICMP(this);
    }

    @Override
    public String toString() {
        return "ICMP Echo Reply. id = " + id + " seq = " + seq + " data = " + data + "\n";
    }
}

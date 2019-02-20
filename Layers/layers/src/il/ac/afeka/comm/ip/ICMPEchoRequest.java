package il.ac.afeka.comm.ip;

import java.util.Date;

public class ICMPEchoRequest extends ICMPPacket {

    public ICMPEchoRequest(IPAddress srcAddr, IPAddress dstAddr, Integer id, Integer seq, String data) {
        super(srcAddr, dstAddr, id, seq, data);
    }

    @Override
    public void processLocally(IPHost host) {

        host.ipsend(new ICMPEchoReply(dstAddr, srcAddr, id, seq, new Date().toString()));
    }

    @Override
    public String toString() {
        return "ICMP Echo Request. id = " + id + " seq = " + seq + " data = " + data + "\n";
    }
}

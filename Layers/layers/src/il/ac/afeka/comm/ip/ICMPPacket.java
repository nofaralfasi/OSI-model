package il.ac.afeka.comm.ip;

public abstract class ICMPPacket extends IPPacket {

    protected Integer id;
    protected Integer seq;
    protected String data;

    public ICMPPacket(IPAddress srcAddr, IPAddress dstAddr, Integer id, Integer seq, String data) {
        this.srcAddr = srcAddr;
        this.dstAddr = dstAddr;
        this.id = id;
        this.seq = seq;
        this.data = data;
    }
}

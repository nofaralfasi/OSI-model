package il.ac.afeka.comm.physical;

import java.io.PrintStream;

class EtherUnicast extends EtherFrame {

    private String srcMac;
    private String dstMac;
    private PhysicalPayload payload;

    public EtherUnicast(String src, String dst, PhysicalPayload p) {
        this.srcMac = src;
        this.dstMac = dst;
        this.payload = p;
    }
    @Override
    public String getSrcMac() {
        return srcMac;
    }

    @Override
    public String getDstMac() {
        return dstMac;
    }

    @Override
    public PhysicalPayload getPayload() {
        return payload;
    }

    @Override
    public void process(Switch sw) {
        sw.deliver(this, dstMac);
    }

    @Override
    public void print(PrintStream out) {
        out.print(toString());
    }

    @Override
    public String toString() {
        return "Ether frame from: " + srcMac + " to: " + dstMac + " " + payload.toString() + "\n";
    }
}

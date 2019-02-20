package il.ac.afeka.comm.physical;

import java.io.PrintStream;

class EtherBroadcast extends EtherFrame {

    private String srcMac;
    private PhysicalPayload payload;

    public EtherBroadcast(String srcMac, PhysicalPayload payload) {
        this.srcMac = srcMac;
        this.payload = payload;
    }

    @Override
    public String getSrcMac() {
        return srcMac;
    }

    @Override
    public String getDstMac() {
        return null;
    }

    @Override
    public PhysicalPayload getPayload() {
        return payload;
    }

    @Override
    public void process(Switch s) {
        s.broadcast(this);
    }

    @Override
    public void print(PrintStream out) {
        out.print(toString());
    }

    @Override
    public String toString() {
        return "Ether frame (BROADCAST) from: " + srcMac + " " + payload.toString() + "\n";
    }
}

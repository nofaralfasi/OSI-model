package il.ac.afeka.comm.physical;

import java.io.PrintStream;

abstract class EtherFrame {

    abstract String getSrcMac();
    abstract String getDstMac();
    abstract PhysicalPayload getPayload();
    abstract void process(Switch s);

    abstract void print(PrintStream out);

    public abstract String toString();
}

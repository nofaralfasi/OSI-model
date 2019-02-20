package il.ac.afeka.comm.physical;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Host implements Runnable {

    protected Map<String, Switch> lans; // the lans in which this host participates, keyed by the mac address it has in each lan

    protected BlockingDeque<EtherFrame> incoming; // frames that were sent to this host but not yet processed by it

    private String name; // not part of tcp/il.ac.afeka.comm.ip, for purposes of illustration in diagrams

    public String getName() { return name; }

    public Host(String name) {
        this.name = name;
        lans = new HashMap<String, Switch>();
        incoming = new LinkedBlockingDeque<EtherFrame>();
    }

    // host network configuration api

    public void install(Switch lan, String macAddr) {
        lan.addHost(macAddr, this);
        lans.put(macAddr, lan);
    }

    public boolean hasMacAddr(String macAddr) { return macAddr != null && lans.get(macAddr) != null; }

    // sending messages from this host

    public void send(String srcMacAddr, String dstMacAddr, PhysicalPayload payload) {

        // ensure that srcMacAddr is one of my interfaces, find its lan and ask it to send the payload (wrapped in the frame)

        Switch lan = lans.get(srcMacAddr);

        if (lan != null) {
            lan.send(new EtherUnicast(srcMacAddr, dstMacAddr, payload));
        }
    }

    public void broadcast(String srcMacAddr, PhysicalPayload payload) {
        Switch lan = lans.get(srcMacAddr);

        if (lan != null) {
            lan.send(new EtherBroadcast(srcMacAddr, payload));
        }
    }

    // delivering messages to this host

    protected void enqueue(EtherFrame frame) {

        frame.print(System.out);

        while (true) {
            try {
                incoming.put(frame);
                return;
            } catch (InterruptedException e) {
                // TODO: emit a warning somewhere
            }
        }
    }

    // processing messages by this host

    public void run() {
        while(true) {
            try {
                incoming.take().getPayload().process(this);
            } catch (InterruptedException e) {
                // TODO: emit a warning to the log
            }
        }
    }


}

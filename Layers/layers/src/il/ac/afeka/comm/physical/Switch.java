package il.ac.afeka.comm.physical;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Switch implements Runnable {

    private BlockingDeque<EtherFrame> incoming;

    private Map<String, Host> hosts;

    private String name; // not part of tcp/ip, for diagram illustration

    private Float reliability; // not part of tcp/ip, for simulation purposes determines the probability of losing a frame

    private Random reliabilityGenerator;

    public Switch(String name) {

        this.name = name;

        incoming = new LinkedBlockingDeque<EtherFrame>();

        hosts = new HashMap<String, Host>();

        reliabilityGenerator = new Random();

        reliability = 1.0f;
    }

    public void addHost(String macAddr, Host host) {
        hosts.put(macAddr, host);
    }

    // send is called by hosts that ask the switch to send the message of their behalf

    void send(EtherFrame frame) {

        if (reliabilityGenerator.nextFloat() <= reliability)
            incoming.add(frame);
    }

    // deliver and broadcast are called by incoming ethernet frames

    void deliver(EtherFrame frame, String hostAddr) {
        Host host = hosts.get(hostAddr);

        if (host != null) {
            host.enqueue(frame);
        }
    }

    void broadcast(EtherFrame frame) {

        for(Host host : hosts.values()) {
            host.enqueue(frame);
        }
    }

    public void run() {
        while(true) {
            take().process(this);
        }
    }

    private EtherFrame take() {

        while (true) {
            try {
                return incoming.take();
            } catch (InterruptedException e) {
                // TODO: emit a warning to the log
            }
        }
    }

    public void printDot(PrintStream out) {
        out.println(name + " [ shape = circle]; ");

        for (Map.Entry<String, Host> p : hosts.entrySet()) {
            out.println(name + " -- " + p.getValue().getName() + " [ label = \"" + p.getKey() + "\"];");
        }
    }

    public String getName() {
        return name;
    }

    public void setReliability(Float f) {
        reliability = f;
    }
}

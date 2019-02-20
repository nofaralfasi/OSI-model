package il.ac.afeka.comm;

import il.ac.afeka.comm.physical.Host;
import il.ac.afeka.comm.physical.Switch;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class World {

    Set<Host> hosts;
    Set<Switch> lans;

    Float reliability; // 1.0 = perfect, 0.5 = network loses half the frames

    public World() {
        hosts = new HashSet<Host>();
        lans = new HashSet<Switch>();
        reliability = 1.0f;
    }

    public void addHost(Host host) {
        hosts.add(host);
    }

    public void addLan(Switch s) {
        lans.add(s);
    }

    public void setReliability(Float f) {

        assert 0 <= f && f <= 1.0;

        reliability = f;

        for(Switch lan : lans)
            lan.setReliability(f);
    }

    public void start() {
        for(Switch lan : lans) {
            Thread thread = new Thread(lan);
            thread.setName("switch " + lan.getName());
            thread.start();
        }
        for(Host host : hosts) {
            Thread thread = new Thread(host);
            thread.setName("host " + host.getName());
            thread.start();
        }
    }

    public void printDot(PrintStream out) {
        out.println("graph {");

        for (Host host : hosts) {
            out.println(host.getName() + "[shape = rectangle];");
        }
        for(Switch lan : lans) {
            lan.printDot(out);
        }
        out.println("}");
    }
}

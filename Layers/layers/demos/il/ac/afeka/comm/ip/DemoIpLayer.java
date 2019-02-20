package il.ac.afeka.comm.ip;

import java.util.Date;

import il.ac.afeka.comm.World;
import il.ac.afeka.comm.physical.Switch;

public class DemoIpLayer {
		
	public static void main(String[] args) {
		
    Switch lan1 = new Switch("S1");

    IPHost hostA = new IPHost("A");

    hostA.install(lan1, "0.0.0.1", new IPAddress("192.168.0.0", "1"));

    IPHost hostB = new IPHost("B");

    hostB.install(lan1, "0.0.0.2", new IPAddress("192.168.0.0", "2"));

    IPHost hostC = new IPHost("C");

    hostC.install(lan1, "0.0.0.3", new IPAddress("192.168.0.0", "3"));

    Switch lan2 = new Switch("S2");

    IPHost hostX = new IPHost("X");

    hostX.install(lan1, "0.0.0.4", new IPAddress("192.168.0.0", "4"));

    hostX.install(lan2, "0.0.1.1", new IPAddress("192.165.0.0", "1"));

    IPHost hostY = new IPHost("Y");

    hostY.install(lan2, "0.0.1.2", new IPAddress("192.165.0.0", "2"));

    IPAddress hostXAddressOnLan1 = new IPAddress("192.168.0.0", "4");
    IPAddress hostXAddressOnLan2 = new IPAddress("192.165.0.0", "1");

    hostA.setDefaultGateway(hostXAddressOnLan1);
    hostB.setDefaultGateway(hostXAddressOnLan1);
    hostC.setDefaultGateway(hostXAddressOnLan1);

    hostY.setDefaultGateway(hostXAddressOnLan2);

    World world = new World();

    world.addHost(hostA);
    world.addHost(hostB);
    world.addHost(hostC);
    world.addHost(hostX);
    world.addHost(hostY);
    world.addLan(lan1);
    world.addLan(lan2);

    world.printDot(System.out);

    world.setReliability(1.0f);
    world.start();

    IPAddress hostAIp = new IPAddress("192.168.0.0", "1");
    IPAddress hostBIp = new IPAddress("192.168.0.0", "2");
    IPAddress hostYIp = new IPAddress("192.165.0.0", "2");

    hostA.ipsend(new ICMPEchoRequest(hostAIp, hostYIp, 1, 13, new Date().toString()));

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    hostA.ipsend(new ICMPEchoRequest(hostAIp, hostYIp, 2, 13, new Date().toString()));

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    hostA.ipsend(new ICMPEchoRequest(hostAIp, hostYIp, 3, 13, new Date().toString()));

    IPAddress NoSuchHostIp = new IPAddress("192.171.0.0", "15");

    hostA.ipsend(new ICMPEchoRequest(hostAIp, NoSuchHostIp, 3, 13, new Date().toString()));

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
	}
}

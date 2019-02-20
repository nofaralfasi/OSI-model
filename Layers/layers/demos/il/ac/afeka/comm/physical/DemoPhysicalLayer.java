package il.ac.afeka.comm.physical;

import il.ac.afeka.comm.World;

public class DemoPhysicalLayer {
	
	public static void main(String[] args) {
		
    Switch lan1 = new Switch("S1");

    Host hostA = new Host("A");

    hostA.install(lan1, "0.0.0.1");

    Host hostB = new Host("B");

    hostB.install(lan1, "0.0.0.2");

    Host hostC = new Host("C");

    hostC.install(lan1, "0.0.0.3");

    Switch lan2 = new Switch("S2");

    Host hostX = new Host("X");

    hostX.install(lan1, "0.0.0.4");

    hostX.install(lan2, "0.0.1.1");

    Host hostY = new Host("Y");

    hostY.install(lan2, "0.0.1.2");

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

    hostA.send("0.0.0.1", "0.0.0.3", new PhysicalPayload() {

		@Override
		public void process(Host host) {
			System.out.println("A new message from 0.0.0.1 to 0.0.0.3 was received");
			
		}});

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    hostX.send("0.0.0.4", "0.0.0.1", new PhysicalPayload() {

		@Override
		public void process(Host host) {
			System.out.println("A new message from 0.0.0.4 to 0.0.0.1 was received");
			
		}});

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    hostX.send("0.0.1.1", "0.0.0.1", new PhysicalPayload() {

		@Override
		public void process(Host host) {
			System.out.println("A new message from 0.0.1.1 to 0.0.0.1 was received");
			
		}});

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

  }

}

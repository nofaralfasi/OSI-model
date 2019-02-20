package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.World;
import il.ac.afeka.comm.ip.ICMPEchoRequest;
import il.ac.afeka.comm.ip.IPAddress;
import il.ac.afeka.comm.ip.IPHost;
import il.ac.afeka.comm.physical.Switch;

import java.util.Date;

public class DemoTcpLayer {

    public static void main(String[] args) throws ChannelException {

        Switch lan1 = new Switch("S1");

        ChannelHost hostA = new ChannelHost("A");

        hostA.install(lan1, "0.0.0.1", new IPAddress("192.168.0.0", "1"));

        ChannelHost hostB = new ChannelHost("B");

        hostB.install(lan1, "0.0.0.2", new IPAddress("192.168.0.0", "2"));

        ChannelHost hostC = new ChannelHost("C");

        hostC.install(lan1, "0.0.0.3", new IPAddress("192.168.0.0", "3"));

        Switch lan2 = new Switch("S2");

        ChannelHost hostX = new ChannelHost("X");

        hostX.install(lan1, "0.0.0.4", new IPAddress("192.168.0.0", "4"));

        hostX.install(lan2, "0.0.1.1", new IPAddress("192.165.0.0", "1"));

        ChannelHost hostY = new ChannelHost("Y");

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

        ChannelReceiver receiver = new ChannelReceiver();

        receiver.listen(hostY);

        ChannelSender sender = new ChannelSender();

        sender.open(hostA, hostAIp, hostYIp, 2000);

        sender.send(new ChannelPacketPayload(1), 2000);

        sender.send(new ChannelPacketPayload(2), 2000);

        sender.close();

        ChannelPacketPayload payload = receiver.receive(1000);
        System.out.println(payload.toString());

        payload = receiver.receive(1000);
        System.out.println(payload.toString());

        receiver.close();

        receiver.listen(hostY);

        sender.open(hostA, hostAIp, hostYIp, 1000);

        sender.send(new ChannelPacketPayload(3), 1000);

        sender.close();

    }
}

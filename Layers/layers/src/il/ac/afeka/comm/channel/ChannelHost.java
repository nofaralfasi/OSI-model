package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPHost;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ChannelHost extends IPHost {

    private BlockingQueue<ChannelPacket> incomingChannelPackets;

    public ChannelHost(String name) {
        super(name);

        incomingChannelPackets = new LinkedBlockingDeque<>();
    }

    public void incomingChannelPacket(ChannelPacket packet) {
        incomingChannelPackets.add(packet);
    }

    public ChannelPacket receiveChannelPacket(Integer timeout) {

        ChannelPacket result = null;
        try {
            result = incomingChannelPackets.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { }

        return result;
    }

}

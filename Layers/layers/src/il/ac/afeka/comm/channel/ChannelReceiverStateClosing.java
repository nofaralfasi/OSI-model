package il.ac.afeka.comm.channel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ChannelReceiverStateClosing implements ChannelReceiverState {

    ChannelHost host;
    ChannelPacket packet;
    BlockingQueue<ChannelPacketData> incomingData;

    public ChannelReceiverStateClosing(ChannelHost host, BlockingQueue<ChannelPacketData> incomingData, ChannelPacket packet) {

        this.incomingData = incomingData;
        this.host = host;
        this.packet = packet;
    }

    public ChannelReceiverState incomingChannelPacket(ChannelPacket newPacket) {

        if (!newPacket.getToken().equals(packet.getToken()))
            return this; // drop packets from older conversations

        if (newPacket.getBit() == packet.getBit()) {
            host.ipsend(new ChannelPacketAck(packet.getDstAddr(), packet.getSrcAddr(), packet.getToken(), packet.getBit()));
        }

        if (incomingData.isEmpty())
            return new ChannelReceiverStateClosed();
        else
            return this;
    }

    @Override
    public ChannelReceiverState listen(ChannelHost host) throws ChannelException {
        throw new ChannelException("error: connection active");
    }

    @Override
    public ChannelReceiverState close() throws ChannelException {
        return new ChannelReceiverStateClosed();
    }

    @Override
    public ChannelPacketPayload receive(Integer timeout) throws ChannelException {
        try {
            return incomingData.poll(timeout, TimeUnit.MILLISECONDS).getPayload();
        } catch (InterruptedException e) {  }
        return null;
    }
}

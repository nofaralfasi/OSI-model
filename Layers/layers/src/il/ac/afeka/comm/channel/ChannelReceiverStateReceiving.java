package il.ac.afeka.comm.channel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ChannelReceiverStateReceiving implements ChannelReceiverState {

    ChannelHost host;
    ChannelPacket packet;
    BlockingQueue<ChannelPacketData> incomingData;

    public ChannelReceiverStateReceiving(BlockingQueue<ChannelPacketData> incomingData, ChannelHost host, ChannelPacket packet) {
        this.incomingData = incomingData;
        this.host = host;
        this.packet = packet;
    }

    public ChannelReceiverState incomingChannelPacket(ChannelPacket newPacket) {

        if (!newPacket.getToken().equals(packet.getToken()))
            return this; // drop packets from older conversations

        if (newPacket.getBit() == packet.getBit()) { // drop and ack repeated copies of the current packet
            host.ipsend(new ChannelPacketAck(packet.getDstAddr(), packet.getSrcAddr(), packet.getToken(), packet.getBit()));
        }
        else {
            if (newPacket instanceof ChannelPacketFin) {
                return new ChannelReceiverStateClosing(host, incomingData, newPacket);
            } else {
                incomingData.add((ChannelPacketData) newPacket);
                packet = newPacket;
            }
        }
        return this;
    }

    @Override
    public ChannelReceiverState listen(ChannelHost host) throws ChannelException {
        throw new ChannelException("error: connection active");
    }

    @Override
    public ChannelReceiverState close() throws ChannelException {
        throw new ChannelException("error: connection active");
    }

    public ChannelPacketPayload receive(Integer timeout) throws ChannelException {
        try {
            return incomingData.poll(timeout, TimeUnit.MILLISECONDS).getPayload();
        } catch (InterruptedException e) {  }
        return null;
    }
}

package il.ac.afeka.comm.channel;

import java.util.concurrent.LinkedBlockingDeque;

public class ChannelReceiverStateListening implements ChannelReceiverState {

    ChannelHost host;

    public ChannelReceiverStateListening(ChannelHost host) {
        this.host = host;
    }

    public ChannelReceiverState incomingChannelPacket(ChannelPacket packet) {

     if (packet instanceof ChannelPacketOpen) {
         host.ipsend(new ChannelPacketAck(packet.getDstAddr(), packet.getSrcAddr(), packet.getToken(), packet.getBit()));
         return new ChannelReceiverStateReceiving(new LinkedBlockingDeque<>(), host, packet);
     }
     else
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

    @Override
    public ChannelPacketPayload receive(Integer timeout) throws ChannelException {
        throw new ChannelException("error: no channel");
    }
}

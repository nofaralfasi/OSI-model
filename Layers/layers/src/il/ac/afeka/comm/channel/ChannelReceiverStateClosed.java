package il.ac.afeka.comm.channel;

public class ChannelReceiverStateClosed implements ChannelReceiverState {

    @Override
    public ChannelReceiverState listen(ChannelHost host) throws ChannelException {

        return new ChannelReceiverStateListening(host);
    }

    @Override
    public ChannelPacketPayload receive(Integer timeout) throws ChannelException {
        throw new ChannelException("error: connection closed");
    }

    @Override
    public ChannelReceiverState incomingChannelPacket(ChannelPacket newPacket) {
        ; // do nothing, just drops the packet
        return this;
    }

    @Override
    public ChannelReceiverState close() throws ChannelException {
        return this;
    }
}

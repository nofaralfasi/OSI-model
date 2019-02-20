package il.ac.afeka.comm.channel;

interface ChannelReceiverState {

    // Channel control

    public abstract ChannelReceiverState listen(ChannelHost host) throws ChannelException;
    public abstract ChannelReceiverState close() throws ChannelException;

    // Channel I/O

    public abstract ChannelPacketPayload receive(Integer timeout) throws ChannelException;

    // Network events

    public abstract ChannelReceiverState incomingChannelPacket(ChannelPacket newPacket);

}
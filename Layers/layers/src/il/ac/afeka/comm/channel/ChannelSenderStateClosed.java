package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPAddress;

import java.nio.channels.Channel;

public class ChannelSenderStateClosed extends ChannelSenderState {

    private final ChannelException reason;

    public ChannelSenderStateClosed(ChannelException reason) {
        this.reason = reason;
    }

    public ChannelException getReason() { return reason; }

    @Override
    public ChannelSenderState open(ChannelHost host, IPAddress source, IPAddress destination, Integer timeout) throws ChannelException {

        String token = ChannelToken.nextUniqueToken();

        ChannelPacket openReq = new ChannelPacketOpen(source, destination, token, 0);

        send(host, openReq, timeout);

        return new ChannelSenderStateOpen(host, source, destination, token, timeout);
    }

    @Override
    public ChannelSenderState close() throws ChannelException {
        return this;
    }

    @Override
    public ChannelSenderState send(ChannelPacketPayload payload, Integer timeout) throws ChannelException {
        throw new ChannelException("error: channel closed");
    }
}

package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPAddress;

public class ChannelSenderStateOpen extends ChannelSenderState {

    ChannelHost host;
    IPAddress source;
    IPAddress destination;
    String token;
    Integer timeout;
    Integer bit;

    public ChannelSenderStateOpen(ChannelHost host, IPAddress source, IPAddress destination, String token, Integer timeout) {

        this.host = host;
        this.source = source;
        this.destination = destination;
        this.token = token;
        this.timeout = timeout;
        this.bit = 1;
    }

    @Override
    public ChannelSenderState open(ChannelHost host, IPAddress source, IPAddress destination, Integer timeout) throws ChannelException {
        throw new ChannelException("error: channel already open");
    }

    @Override
    public ChannelSenderState close() throws ChannelException {

        try {
            send(host, new ChannelPacketFin(source, destination, token, bit), timeout);

        } catch(ChannelException err) { }

        return new ChannelSenderStateClosed(new ChannelException("user request"));
    }

    @Override
    public ChannelSenderState send(ChannelPacketPayload payload, Integer timeout) throws ChannelException {

        try {

            send(host, new ChannelPacketData(source, destination, token, bit, payload), timeout);
            bit = 1 - bit;
            return this;

        } catch(ChannelException err) {
            return new ChannelSenderStateClosed(err);
        }

    }
}

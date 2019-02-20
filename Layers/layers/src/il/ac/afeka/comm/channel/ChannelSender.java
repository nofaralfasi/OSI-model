package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPAddress;

public class ChannelSender {

    ChannelSenderState state;

    public ChannelSender() {

        state = new ChannelSenderStateClosed(new ChannelException("new sender"));
    }

    public void open(ChannelHost host, IPAddress source, IPAddress destination, Integer timeout) throws ChannelException {

        state = state.open(host, source, destination, timeout);
    }

    public void close() throws ChannelException {

        state = state.close();
    }

    public void send(ChannelPacketPayload payload, Integer timeout) throws ChannelException {

        state = state.send(payload, timeout);

        if (state instanceof ChannelSenderStateClosed)
            throw ((ChannelSenderStateClosed)state).getReason();
    }

}

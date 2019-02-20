package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPAddress;
import il.ac.afeka.comm.ip.IPPacket;

public abstract class ChannelSenderState {

    public abstract ChannelSenderState open(ChannelHost host, IPAddress source, IPAddress destination, Integer timeout) throws ChannelException;

    public abstract ChannelSenderState close() throws ChannelException;

    public abstract ChannelSenderState send(ChannelPacketPayload payload, Integer timeout) throws ChannelException;

    protected void send(ChannelHost host, ChannelPacket packet, Integer timeout) throws ChannelException {

            Long startTime = System.currentTimeMillis();

            host.ipsend(packet);

            IPPacket reply = host.receiveChannelPacket(100);

            while ( (System.currentTimeMillis() < startTime + timeout) &&
                    (reply == null || !(reply instanceof ChannelPacketAck) || !packet.matches((ChannelPacket)reply))) {
                host.ipsend(packet);

                reply = host.receiveChannelPacket(100);
            }

            if (System.currentTimeMillis() >= startTime + timeout)
                throw new ChannelException("error: send aborted due to user timeout");

        }
}

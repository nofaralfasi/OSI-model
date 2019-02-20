package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPAddress;

public class ChannelPacketAck extends ChannelPacket {

    public ChannelPacketAck(IPAddress src, IPAddress dst, String token, Integer bit) {
        super(src, dst, token, bit);
    }

    @Override
    public String toString() {
        return "<Channel Packet: Ack; token = " + token + "; bit = " + bit + ">";
    }
}

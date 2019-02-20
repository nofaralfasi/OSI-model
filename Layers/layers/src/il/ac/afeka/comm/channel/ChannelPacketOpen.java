package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPAddress;

public class ChannelPacketOpen extends ChannelPacket {

    public ChannelPacketOpen(IPAddress src, IPAddress dst, String token, Integer bit) {
        super(src, dst, token, bit);
    }

    @Override
    public String toString() {
        return "<Channel Packet: Open; token = " + token + ">";
    }
}

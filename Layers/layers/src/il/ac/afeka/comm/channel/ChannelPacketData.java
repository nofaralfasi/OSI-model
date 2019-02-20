package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPAddress;

public class ChannelPacketData extends ChannelPacket {

    private ChannelPacketPayload payload;

    public ChannelPacketData(IPAddress src, IPAddress dest, String token, Integer bit, ChannelPacketPayload payload) {
        super(src, dest, token, bit);
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "<Channel Packet: Data; token = " + token + "; bit = " + bit + ">";
    }

    public ChannelPacketPayload getPayload() {
        return payload;
    }
}

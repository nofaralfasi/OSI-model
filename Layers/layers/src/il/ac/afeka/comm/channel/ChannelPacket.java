package il.ac.afeka.comm.channel;

import il.ac.afeka.comm.ip.IPAddress;
import il.ac.afeka.comm.ip.IPHost;
import il.ac.afeka.comm.ip.IPPacket;

public abstract class ChannelPacket extends IPPacket {

    String token;
    Integer bit;

    public ChannelPacket(IPAddress src, IPAddress dest, String token, Integer bit) {
        this.srcAddr = src;
        this.dstAddr = dest;
        this.token = token;
        this.bit = bit;
    }

    @Override
    public void processLocally(IPHost h) {

        ChannelHost host = (ChannelHost)h;

        host.incomingChannelPacket(this);
    }

    boolean matches(ChannelPacket other) {
        return this.bit == other.bit && this.token.equals(other.token);
    }

    public String getToken() { return token; }

    public Integer getBit() { return bit; }
}

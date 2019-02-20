package il.ac.afeka.comm.channel;

public class ChannelPacketPayload {

    private Integer i;

    public ChannelPacketPayload(Integer i) { this.i = i; }

    public String toString() { return "<"+i+">"; }
}

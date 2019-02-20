package il.ac.afeka.comm.channel;

import java.util.Date;
import java.util.Random;

public class ChannelToken {

    Random seq = new Random();

    String next() {

        return "" + new Date().getTime() + "" + seq.nextLong();
    }

    private static ChannelToken tokenSeq;

    public static String nextUniqueToken() {

        if (tokenSeq == null)
            tokenSeq = new ChannelToken();

        return tokenSeq.next();
    }
}

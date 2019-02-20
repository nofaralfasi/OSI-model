package il.ac.afeka.comm.channel;

public class ChannelReceiver {

    private ChannelReceiverState state;

    private Thread incomingPacketsProcessor;

    private boolean stopIncomingPacketsThread;

    public ChannelReceiver() {

        state = new ChannelReceiverStateClosed();

        incomingPacketsProcessor = new Thread();

        incomingPacketsProcessor.setName("ChannelReceiver");
    }

    public void listen(ChannelHost host) throws ChannelException {

        assert incomingPacketsProcessor.isAlive() == false;

        state = state.listen(host);

        incomingPacketsProcessor = new Thread(new Runnable() {
            @Override
            public void run() {

                ChannelPacket packet = null;

                stopIncomingPacketsThread = false;

                while(!stopIncomingPacketsThread) {

                    packet = host.receiveChannelPacket(1000);

                    if (packet != null)
                        state = state.incomingChannelPacket(packet);
                }
            }
        });

        incomingPacketsProcessor.setName("ChannelReceiver");

        incomingPacketsProcessor.start();
    }

    public ChannelPacketPayload receive(Integer timeout) throws ChannelException {

        assert incomingPacketsProcessor.isAlive() == true;

        return state.receive(timeout);
    }

    // close should be called only if the state of the receiver is closing

    public void close() throws ChannelException {

        state = state.close();

        stopIncomingPacketsThread = true;
        incomingPacketsProcessor.interrupt();

        try {
            incomingPacketsProcessor.join();
        } catch (InterruptedException e) {
        }
    }
}

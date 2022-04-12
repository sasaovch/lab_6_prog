package lab.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.logging.Logger;

import lab.common.util.Message;

public class ReceiveManager {
    private final int defaultBufferSize = 256;
    private final int defaultSleepTime = 500;
    private DatagramChannel channel;
    private SocketAddress client;
    private Logger logger;

    public ReceiveManager(DatagramChannel channel, SocketAddress client, Logger logger) {
        this.channel = channel;
        this.client = client;
        this.logger = logger;
    }

    public Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        Serializable mess = (Serializable) is.readObject();
        in.close();
        is.close();
        return mess;
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException, InterruptedException {
        byte[] bufReceiveSize = new byte[defaultBufferSize];
        ByteBuffer receiveBufferSize = ByteBuffer.wrap(bufReceiveSize);
        client = channel.receive(receiveBufferSize);
        Message mess;
        if (Objects.nonNull(client)) {
            Serializable receiveMess = deserialize(bufReceiveSize);
            if (receiveMess.getClass().equals(Integer.class)) {
                int size = (int) receiveMess;
                Thread.sleep(defaultSleepTime);
                byte[] bufr = new byte[size];
                ByteBuffer receiveBuffer = ByteBuffer.wrap(bufr);
                channel.receive(receiveBuffer);
                receiveMess = deserialize(bufr);
                mess = (Message) receiveMess;
                logger.info("Received message: " + mess.getCommand());
            } else {
                mess = (Message) receiveMess;
            }
            return mess;
        } else {
            return null;
        }
    }

    public SocketAddress getClient() {
        return client;
    }
}

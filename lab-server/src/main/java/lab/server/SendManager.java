package lab.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

import lab.common.commands.CommandResult;

public class SendManager {
    private DatagramChannel channel;
    private SocketAddress client;
    private Logger logger;
    private final int limitSend = 100;

    public SendManager(DatagramChannel channel, SocketAddress client, Logger logger) {
        this.channel = channel;
        this.client = client;
        this.logger = logger;
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        byte[] outMess = out.toByteArray();
        out.close();
        os.close();
        return outMess;
    }

    public void sendCommResult(CommandResult result) throws IOException {
        byte[] bufs = serialize(result);
        byte[] bufSendSize = serialize(bufs.length);
        int sendSize = bufSendSize.length;
        ByteBuffer sendBufferSize = ByteBuffer.wrap(bufSendSize);
        int limit = limitSend;
        while (channel.send(sendBufferSize, client) < sendSize) {
            limit -= 1;
            if (limit == 0) {
                logger.info("Server couldn't send message");
                return;
            }
        }
        sendBufferSize.clear();
        ByteBuffer sendBuffer = ByteBuffer.wrap(bufs);
        sendSize = bufs.length;
        limit = limitSend;
        while (channel.send(sendBuffer, client) < sendSize) {
            limit -= 1;
            logger.info("could not sent a package, re-trying");
            if (limit == 0) {
                logger.info("Server couldn't send message");
                return;
            }
        }
        sendBuffer.clear();
        logger.info("Send result of command to client: " + result.getData());
    }

    public void setClient(SocketAddress client) {
        this.client = client;
    }
}

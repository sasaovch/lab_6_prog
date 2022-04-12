package lab.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import lab.common.util.Message;

public class SendManager {
    private final InetAddress address;
    private final DatagramSocket socket;
    private final Integer port;
    private byte[] buff;
    private byte[] buffsize;
    private DatagramPacket outPacket;

    public SendManager(InetAddress address, DatagramSocket socket, Integer port) {
        this.address = address;
        this.socket = socket;
        this.port = port;
    }

    public void sendMessage(Message mess) throws IOException, ClassNotFoundException {
        buff = serialize(mess);
        outPacket = new DatagramPacket(buff, buff.length, address, port);
        buffsize = serialize(buff.length);
        socket.send(new DatagramPacket(serialize(buff.length), buffsize.length, address, port)); //81
        socket.send(outPacket);
    }

    public byte[] serialize(Serializable mess) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(mess);
        return out.toByteArray();
    }
}

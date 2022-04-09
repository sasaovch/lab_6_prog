package lab.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import lab.common.commands.CommandResult;
import lab.common.util.Message;

public class ServerWork {
    private final InetAddress address;
    private final DatagramSocket socket;
    private final Integer port;
    private DatagramPacket outPacket;
    private DatagramPacket inPacket;
    private byte[] buff;
    private final int defaultSizeBuffer = 2048;

    public ServerWork(InetAddress address, DatagramSocket socket, Integer port) {
        this.address = address;
        this.socket = socket;
        this.port = port;
    }

    public void sendMessage(Message mess) throws IOException, ClassNotFoundException {
        buff = serialize(mess);
        outPacket = new DatagramPacket(buff, buff.length, address, port);
        socket.send(outPacket);
    }

    public CommandResult reciveMessage() throws IOException, ClassNotFoundException {
        try {
            buff = new byte[defaultSizeBuffer];
            inPacket = new DatagramPacket(buff, defaultSizeBuffer);
            socket.receive(inPacket);
            return (CommandResult) deserialize(inPacket.getData());
        } catch (SocketTimeoutException e) {
            return null;
        }
    }

    public byte[] serialize(Message mess) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(mess);
        return out.toByteArray();
    }
    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public void setTimeout(int milisek) throws SocketException {
        socket.setSoTimeout(milisek);
    }
}

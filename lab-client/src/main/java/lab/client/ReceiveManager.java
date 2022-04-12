package lab.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import lab.common.commands.CommandResult;

public class ReceiveManager {
    private final DatagramSocket socket;
    private DatagramPacket inPacket;
    private byte[] buff;
    private byte[] bufreceive;
    private final int defaultSizeBuffer = 256;

    public ReceiveManager(DatagramSocket socket) {
        this.socket = socket;
    }

    public CommandResult reciveMessage() throws IOException, ClassNotFoundException {
        try {
            bufreceive = new byte[defaultSizeBuffer];
            inPacket = new DatagramPacket(bufreceive, defaultSizeBuffer);
            socket.receive(inPacket);
            int size = (int) deserialize(bufreceive);
            buff = new byte[size];
            inPacket = new DatagramPacket(buff, size);
            socket.receive(inPacket);
            return (CommandResult) deserialize(inPacket.getData());
        } catch (SocketTimeoutException e) {
            return null;
        }
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

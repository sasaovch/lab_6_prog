package lab_6.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Objects;

import lab_6.common.exception.IncorrectData;
import lab_6.common.exception.IncorrectDataOfFileException;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException, IncorrectDataOfFileException, IncorrectData, ClassNotFoundException, InterruptedException {
        Integer port;
        String portString = System.getenv("port");
        if (Objects.equals(portString, null)){
            port = 8713;
        } else {
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                port = 8713;
            }
        }
        HashSet<String> commands = new HashSet<>();
        commands.add("help");
        commands.add("info");
        commands.add("show");
        commands.add("clear");
        commands.add("exit");
        commands.add("group_counting_by_name");
        commands.add("print_descending");
        commands.add("add");
        commands.add("add_if_min");
        commands.add("remove_greater");
        commands.add("remove_lower");
        commands.add("update");
        commands.add("remove_by_id");
        commands.add("count_by_loyal");
        commands.add("execute_script");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out, true);
        IOManager ioManager = new IOManager(reader, writer, "$");
        AskMarine asker = new AskMarine(ioManager);
        ServerWork server = new ServerWork(InetAddress.getLocalHost(), new DatagramSocket(), port);
        server.setTimeout(1000);
        Console console = new Console(commands, ioManager, asker, server);
        console.run();
    }
}

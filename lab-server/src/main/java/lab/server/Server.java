package lab.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import lab.common.commands.*;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetAddress addrr;
        Integer port;
        String address = System.getenv("address");
        String portString = System.getenv("port");
        if (Objects.equals(address, null)){
            addrr = InetAddress.getLocalHost();
        } else {
            try {
                addrr = InetAddress.getByName(address);
            } catch (UnknownHostException e) {
                addrr = InetAddress.getLocalHost();
            }
        }
        if (Objects.equals(portString, null)){
            port = 8713;
        } else {
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                port = 8713;
            }
        }
        CommandManager commands = new CommandManager();
        commands.addCommand("help", new HelpCommand());
        commands.addCommand("info", new InfoCommand());
        commands.addCommand("show", new ShowCommand());
        commands.addCommand("add", new AddCommand());
        commands.addCommand("update", new UpdateCommand());
        commands.addCommand("remove_by_id", new RemoveByIdCommand());
        commands.addCommand("clear", new ClearCommand());
        commands.addCommand("add_if_min", new AddIfMinCommand());
        commands.addCommand("remove_greater", new RemoveGreaterCommand());
        commands.addCommand("remove_lower", new RemoveLowerCommand());
        commands.addCommand("group_counting_by_name", new GroupCountingByNameCommand());
        commands.addCommand("count_by_loyal", new CountByLoyalCommand());
        commands.addCommand("print_descending", new PrintDescendingCommand());
        ServerApp app = new ServerApp(commands, addrr, port);
        app.start();
    }
}

package lab.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import lab.common.exception.IncorrectData;
import lab.common.exception.IncorrectDataOfFileException;
import lab.common.util.Message;

public final class Client {
    private  static final Integer DEFAULT_PORT = 8713;
    private  static final Integer DEFAULT_TIME_OUT = 1000;

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException, IncorrectDataOfFileException, IncorrectData, ClassNotFoundException, InterruptedException {
        Integer port;
        String portString = System.getenv("port");
        if (Objects.equals(portString, null)) {
            port = DEFAULT_PORT;
        } else {
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                port = DEFAULT_PORT;
            }
        }
        Thread.sleep(2000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out, true);
        IOManager ioManager = new IOManager(reader, writer, "$");
        AskMarine asker = new AskMarine(ioManager);
        ServerWork server = new ServerWork(InetAddress.getLocalHost(), new DatagramSocket(), port);
        server.setTimeout(DEFAULT_TIME_OUT);
        ParsCommand parsingComm = new ParsCommand(ioManager, asker);
        Map<String, Function<String, Message>> parsingList = new HashMap<>();
        parsingList.put("help", parsingComm::helpComm);
        parsingList.put("info", parsingComm::infoComm);
        parsingList.put("show", parsingComm::showComm);
        parsingList.put("clear", parsingComm::clearComm);
        parsingList.put("exit", parsingComm::exitComm);
        parsingList.put("group_counting_by_name", parsingComm::groupCountingByNameComm);
        parsingList.put("print_descending", parsingComm::printDescendingComm);
        parsingList.put("add", parsingComm::addComm);
        parsingList.put("add_if_min", parsingComm::addIfMinComm);
        parsingList.put("remove_greater", parsingComm::removeGreaterComm);
        parsingList.put("remove_lower", parsingComm::removeLowerComm);
        parsingList.put("update", parsingComm::updateComm);
        parsingList.put("remove_by_id", parsingComm::removeByIdComm);
        parsingList.put("count_by_loyal", parsingComm::countByLoyalComm);
        parsingList.put("execute_script", parsingComm::executeScriptComm);
        Console console = new Console(parsingList, ioManager, server);
        console.run();
    }
}

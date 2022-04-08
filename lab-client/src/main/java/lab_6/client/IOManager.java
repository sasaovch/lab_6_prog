package lab_6.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

/**
 * Operates input and output.
 */
public class IOManager {
    private BufferedReader reader;
    private PrintWriter writer;
    private final String prompter;
    private Boolean fileMode = false;
    private final Stack<BufferedReader> previosReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();

    public IOManager(BufferedReader reader, PrintWriter writer, String promter){
        this.reader = reader;
        this.writer = writer;
        this.prompter = promter;
    }

    /**
     * Set BufferedReader.
     * @param buf BufferedReader.
     */
    public  void setBufferReader(BufferedReader buf) {
        reader = buf;
    }

    /**
     * Set PrintWriter.
     * @param wr PrintWriter.
     */
    public  void setPrintWriter(PrintWriter wr) {
        writer = wr;
    }

    /**
     * @return BufferedReader.
     */
    public  BufferedReader getBufferedReader() {
        return reader;
    }

    /**
     * @return PrintWriter.
     */
    public  PrintWriter getPrintWriter() {
        return writer;
    }

    public void turnOnFileMode(String filename) throws FileNotFoundException {
        try {
            File file = new File(filename);
            if (file.exists() && !currentFiles.contains(file)) {
                    BufferedReader newReader = new BufferedReader(new FileReader(file)); 
                    println("Started to execute script: " + file.getName());
                    println("------------------------------------------");
                    currentFiles.push(file);
                    previosReaders.push(getBufferedReader());
                    setBufferReader(newReader);
                    fileMode = true;
            } else if (!file.exists()) {
                    printerr("File doesn't exist.");
            } else if(currentFiles.contains(file)) {
                    printerr("The file was not executed due to recursion.");
                    turnOffFileMode();
                }
        } catch (FileNotFoundException e) {
            printerr("Invalid file access rights.");
        }
    }

    public void turnOffFileMode() {
        File file = currentFiles.pop();
        setBufferReader(previosReaders.pop());
        if (currentFiles.isEmpty()) {
            fileMode = false;
        }
        println("------------------------------------------");
        println("Finished to execute script: " + file.getName());
    }

    public Boolean getFileMode() {
        return fileMode;
    }

    /**
     * Read new line from input.
     * @return line.
     * @throws IOException If something goes wrong with file.
     */
    public  String readLine() throws IOException {
        return reader.readLine();
    }

    /**
     * Read file.
     * @return lines of file.
     * @throws IOException If something goes wrong with file.
     */
    public String readfile(File file) throws FileNotFoundException, IOException{
        StringBuilder strData = new StringBuilder();
        String line;
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                while ((line = bufferedReader.readLine()) != null) {
                    strData.append(line);
                }
            }
        }
        return strData.toString();
    }

    /**
     * Close input.
     * @throws IOException If something goes wrong with file.
     */
    public  void close() throws IOException {
        reader.close();
        writer.close();
    }

    /**
     * Print prompt for input.
     */
    public void prompt(){
        writer.printf("%s", prompter);
    }

    /**
     * Print in output.
     * @param o Object to print.
     */
    public  void print(Object o) {
        writer.printf("%s", o);
    }

    /**
     * Print with new line in output.
     * @param o Object to print.
     */
    public  void println(Object o) {
        writer.println(o);
    }

    /**
     * Print errors in output.
     * @param o Object to print.
     */
    public  void printerr(Object o) {
        writer.println("Error! " + o);
    }

    /**
     * Print elements as table.
     * @param el1 element of the first column.
     * @param el2 element of the second column.
     */
    public  void printcolon(Object el1, Object el2) {
        writer.printf("%-25s%-1s%n", el1, el2);
    }
}
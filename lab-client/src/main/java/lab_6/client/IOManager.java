package lab_6.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

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

    public  void setBufferReader(BufferedReader buf) {
        reader = buf;
    }

    public  void setPrintWriter(PrintWriter wr) {
        writer = wr;
    }

    public  BufferedReader getBufferedReader() {
        return reader;
    }

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

    public  String readLine() throws IOException {
        return reader.readLine();
    }

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

    public  void close() throws IOException {
        reader.close();
        writer.close();
    }

    public void prompt(){
        writer.printf("%s", prompter);
    }

    public  void print(Object o) {
        writer.printf("%s", o);
    }

    public  void println(Object o) {
        writer.println(o);
    }

    public  void printerr(Object o) {
        writer.println("Error! " + o);
    }
}
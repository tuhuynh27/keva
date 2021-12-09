package dev.keva.server.command.aof;

import dev.keva.ioc.annotation.Component;
import dev.keva.protocol.resp.Command;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AOFOperations {
    public void write(Command command) throws IOException {
        FileOutputStream fos = new FileOutputStream("keva.aof", true);
        ObjectOutputStream output = new ObjectOutputStream(fos);
        output.writeObject(command);
        output.flush();
        output.close();
    }

    public List<Command> read() throws IOException {
        try {
            List<Command> commands = new ArrayList<>(100);
            FileInputStream fis = new FileInputStream("keva.aof");
            while (true) {
                try {
                    ObjectInputStream input = new ObjectInputStream(fis);
                    Command command = (Command) input.readObject();
                    commands.add(command);
                } catch (EOFException e) {
                    return commands;
                } catch (ClassNotFoundException e) {
                    log.error("Error reading AOF file", e);
                    return commands;
                }
            }
        } catch (FileNotFoundException ignored) {
            throw new FileNotFoundException("AOF file not found");
        }
    }
}

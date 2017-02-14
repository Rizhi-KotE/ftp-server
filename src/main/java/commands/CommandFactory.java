package commands;

import core.FtpSession;
import exceptions.FTPError502Exception;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static java.util.Optional.ofNullable;

public class CommandFactory {
    private Map<String, BiFunction<FtpSession, String[], Command>> commands = new HashMap<>();

    public CommandFactory() {
        commands.put("LIST", (session, args) -> new ListCommandTask(session, args));
        commands.put("FEAT", (session, args) -> new FEATCommand(session, args, this));
        commands.put("PASS", (session, args) -> new PASSCommand(session, args));
        commands.put("PORT", (session, args) -> new PORTCommand(session, args));
        commands.put("PWD", (session, args) -> new PWDCommand(session, args));
        commands.put("USER", (session, args) -> new USERCommand(session, args));
        commands.put("TYPE", (session, args) -> new TYPECommand(session, args));
        commands.put("CWD", (session, args) -> new CWDCommand(session, args));
        commands.put("STOR", (session, args) -> new STORCommand(session, args));
        commands.put("RETR", (session, args) -> new RETRCommand(session, args));
        commands.put("EPRT", (session, args) -> new EPRTCommand(session, args));
        commands.put("OPTS", (session, args) -> new OPTSCommand(session, args));
    }

    public static CommandFactory getInstance() {
        return Singleton.instance.getFactory();
    }

    public Collection<String> getCommands() {
        return commands.keySet();
    }

    public Command get(String command, String[] args, FtpSession session) throws FTPError502Exception {
        return ofNullable(commands.get(command))
                .orElseThrow(() -> new FTPError502Exception(command))
                .apply(session, args);
    }

    private enum Singleton {
        instance;
        private CommandFactory factory;

        Singleton() {
            this.factory = new CommandFactory();
        }

        public CommandFactory getFactory() {
            return factory;
        }
    }
}

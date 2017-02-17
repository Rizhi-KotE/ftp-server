package rk.commands;

import org.apache.log4j.Logger;
import rk.core.FtpSession;
import rk.exceptions.FTPError502Exception;

import java.util.function.BiFunction;

/**
 * Used to create command object
 */
public enum FTPCommands {
    LIST((session, args) -> new LISTCommand(session, args)),
    FEAT((session, args) -> new FEATCommand(session, args)),
    PASS((session, args) -> new PASSCommand(session, args)),
    PORT((session, args) -> new PORTCommand(session, args)),
    PWD((session, args) -> new PWDCommand(session, args)),
    USER((session, args) -> new USERCommand(session, args)),
    TYPE((session, args) -> new TYPECommand(session, args)),
    CWD((session, args) -> new CWDCommand(session, args)),
    STOR((session, args) -> new STORCommand(session, args)),
    RETR((session, args) -> new RETRCommand(session, args)),
    EPRT((session, args) -> new EPRTCommand(session, args)),
    SYST((session, args) -> new SYSTCommand(session, args)),
    QUIT((session, args) -> new QUITCommand(session, args));

    private BiFunction<FtpSession, String[], Command> creator;

    FTPCommands(BiFunction<FtpSession, String[], Command> creator) {
        this.creator = creator;
    }

    public Command createCommand(FtpSession session, String[] args) {
        return creator.apply(session, args);
    }

    static final Logger log = Logger.getLogger(FTPCommands.class);

    /**
     *
     * @param command - FTP command name
     * @param session - FTP session
     * @param args - command args
     * @return - command instance
     * @throws FTPError502Exception when command is not implemented
     */
    public static Command createCommand(String command, FtpSession session, String[] args) throws FTPError502Exception {
        try {
            return valueOf(command).creator.apply(session, args);
        } catch (IllegalArgumentException e) {
            log.debug("No such command", e);
            throw new FTPError502Exception(command);
        }
    }
}

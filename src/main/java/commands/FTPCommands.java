package commands;

import core.FtpSession;

import java.util.function.BiFunction;

public enum FTPCommands {
    LIST((session, args) -> new ListCommandTask(session, args)),
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
    OPTS((session, args) -> new OPTSCommand(session, args)),
    QUIT((session, args)->new QUITCommand(session, args));

    private BiFunction<FtpSession, String[], Command> creator;

    FTPCommands(BiFunction<FtpSession, String[], Command> creator) {
        this.creator = creator;
    }

    public Command createCommand(FtpSession session, String[] args) {
        return creator.apply(session, args);
    }
}

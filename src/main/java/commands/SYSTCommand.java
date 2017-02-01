package commands;

public class SYSTCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public SYSTCommand(FtpSession session, String[] args) {


        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() {
        //TODO ??
//        writeMessage("215 UNIX\n");
    }
}

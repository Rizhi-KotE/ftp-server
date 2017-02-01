package commands;

public class PWDCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PWDCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() {
//        writeMessage(String.format("257 %s\n", new File("/").toString()));
    }

}

package commands;

public class FEATCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public FEATCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() {
        //TODO send all implemented commands list??
//        writeMessage("211-Features\n");
//        writeMessage("PASV\n");
//        writeMessage("LIST\n");
//        writeMessage("211 End\n");
    }
}

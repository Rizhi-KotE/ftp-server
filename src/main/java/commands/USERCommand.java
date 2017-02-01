package commands;

public class USERCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public USERCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() {
//
//            if (tokens.length <= 1) {
//                writeMessage("504 \n");
//            } else {
//                String param = tokens[1];
//                writeMessage("331 \n");
//            }

    }

}

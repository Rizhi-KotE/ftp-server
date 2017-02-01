package commands;

import commands.Command;

import static java.lang.Integer.parseInt;

public class PORTCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PORTCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() {
//        if (tokens.length < 2) {
//            return;
//        }
//        String[] numbers = tokens[1].split(",");
//        String host = String.join(".", numbers[0], numbers[1], numbers[2], numbers[3]);
//        session.put("host", host);
//        session.put("port", Integer.toString(parseInt(numbers[4]) * 256 + parseInt(numbers[5])));
//        writeMessage("200 \n");
    }
}

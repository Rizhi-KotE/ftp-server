package commands;

import core.FtpSession;
import exceptions.NotImplementedFunctionException;
import exceptions.NotLoggedException;
import exceptions.SyntaxErrorInArgumentsException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class PASVCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PASVCommand(FtpSession session, String[] args) {
        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, SyntaxErrorInArgumentsException, NotLoggedException, NotImplementedFunctionException {
        throw new NotImplementedFunctionException("PASV");
//        if (args.length > 1) throw new SyntaxErrorInArgumentsException("PASV", Arrays.toString(args));
//        if (!session.isLogged()) throw new NotLoggedException("");
//        Socket socket = new Socket();
//        socket.bind(null);
//        Connection connection = new Connection(socket);
//        session.putDataConnection(connection);
//        String[] host = socket.getInetAddress().getHostAddress().split(".");
//        int[] port = new int[]{socket.getPort() / 256, socket.getPort() % 256};
//        session.getControlConnection().writeSequence(String.format("227 %s,%s,%s,%s,%d,%d\n",
//                host[0], host[1], host[2], host[3], port[0], port[1]));
    }
}

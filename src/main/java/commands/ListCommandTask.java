package commands;

import core.FtpSession;

public class ListCommandTask implements Command {

    private final FtpSession ftpSession;
    private final String[] args;

    public ListCommandTask(FtpSession ftpSession, String[] args) {
        this.ftpSession = ftpSession;
        this.args = args;
    }


    @Override
    public void execute() {
        try {
//            Socket socket = new Socket(ftpSession.getDataHost(), ftpSession.getDataPort());
//            ftpSession.setDataSocket(socket);
//            new Thread(() -> {
//                BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
//                for (File f : new File(".").listFiles()) {
//                    os.write(f.toString().getBytes());
//                }
//                os.flush();
//                socket.close();
//                writeMessage("250 \n");
//            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

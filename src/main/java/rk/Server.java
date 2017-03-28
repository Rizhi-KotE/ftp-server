package rk;

import org.apache.ftpserver.filesystem.nativefs.NativeFileSystemFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.AuthorizationRequest;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.log4j.Logger;

import java.net.ServerSocket;

import static java.util.Arrays.asList;


public class Server implements Runnable {
    static final Logger log = Logger.getLogger(Server.class);

    private final int port;
    private final String userDir;

    public Server(int port, String userDir) {
        this.port = port;
        this.userDir = userDir;
    }

    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(port)) {
            NativeFileSystemFactory nativeFileSystemFactory = new NativeFileSystemFactory();
            log.info(String.format("listen port [%d]", ss.getLocalPort()));
            while (!Thread.interrupted()) {
                BaseUser baseUser = new BaseUser();
                baseUser.setHomeDirectory(userDir);
                Authority authority = new Authority() {

                    @Override
                    public boolean canAuthorize(AuthorizationRequest request) {
                        return true;
                    }

                    @Override
                    public AuthorizationRequest authorize(AuthorizationRequest request) {
                        return request;
                    }
                };
                baseUser.setAuthorities(asList(authority));
                ConnectionExecutor executor = new ConnectionExecutor(ss.accept(),
                        nativeFileSystemFactory.createFileSystemView(baseUser));
                new Thread(executor::run).start();
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            log.info(String.format("server stopped"));
        }
    }
}

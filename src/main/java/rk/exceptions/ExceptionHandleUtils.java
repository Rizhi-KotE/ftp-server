package rk.exceptions;

import org.apache.log4j.Logger;
import rk.core.FtpSession;

import java.io.IOException;
import java.net.SocketException;

import static rk.utils.Messages.MESSAGE_452;

public class ExceptionHandleUtils {
    public static void handle(Logger log, FtpSession session, Exception ex) {
        try {

            try {
                throw ex;
            } catch (FtpErrorReplyException e) {
                log.trace("", e);
                session.getControlConnection().write(e.getReplyMessage());
                log.info(e.getReplyMessage());
            } catch (Exception e) {
                log.error("", e);
                session.getControlConnection().write(MESSAGE_452);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }
}

package utils;

import exceptions.NoSuchMessageException;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

import static java.util.Optional.ofNullable;

public class MessageFactory {
    static MessageFactory factory = new MessageFactory();
    private final Properties replies;

    public MessageFactory() {
        try {
            InputStream replies = getClass().getClassLoader().getResourceAsStream("replies.properties");
            this.replies = new Properties();
            this.replies.load(replies);
        } catch (IOException e) {
            throw new Error();
        }
    }

    public static String getMessage(String type) throws NoSuchMessageException {
        return (String) ofNullable(factory.replies.get("message_" + type))
                .orElseThrow(() -> new NoSuchMessageException(type));
    }
}

package rk;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] a) throws InterruptedException, IOException {
        InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream("log4j.properties");
        StringBuilder string = new StringBuilder();

        byte[] bytes = new byte[0xff];

        for(int i; (i =resourceAsStream.read(bytes))!=-1;){
            string.append(bytes);
        }

        System.out.println(string.toString());

        int port = a.length >= 1 ? Integer.parseInt(a[0]) : 21;
        String userDir = a.length >=2 ? a[1] : ".";
        new Server(port, userDir).run();
    }
}

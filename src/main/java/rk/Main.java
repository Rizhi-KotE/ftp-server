package rk;

public class Main {
    public static void main(String[] a) throws InterruptedException {
        int port = a.length >= 1 ? Integer.parseInt(a[0]) : 21;
        String userDir = a.length >=2 ? a[1] : ".";
        new Server(port, userDir).run();
    }
}

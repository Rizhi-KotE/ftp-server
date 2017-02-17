package rk;

public class Main {
    public static void main(String[] a) throws InterruptedException {
        int port = a.length >= 1 ? Integer.parseInt(a[0]) : 21;
        new Server(port).run();
    }
}

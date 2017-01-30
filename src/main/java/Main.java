public class Main {
    public static void main(String[] a) {
        int port = a.length >= 1 ? Integer.parseInt(a[0]) : 21;
        new Server(port).start();
    }
}

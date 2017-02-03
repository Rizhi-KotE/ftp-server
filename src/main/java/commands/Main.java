package commands;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(String.format("ls -l /"));
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        inputStream.close();
        bufferedReader.close();
    }
}

package br.com.italomded.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final String ERROR_MESSAGE = "ERROR: %s";
        try {
            var socket = new Socket("localhost", 3000);

            PrintStream send = new PrintStream(socket.getOutputStream());
            Scanner receive = new Scanner(socket.getInputStream());
            Scanner input = new Scanner(System.in);

            new Thread(() -> {
                boolean run = true;
                while (run) {
                    String command = input.nextLine();
                    send.println(command);
                    if (command.equals("EXIT")) run = false;
                }
            }).start();

            Thread thread = new Thread(() -> {
                while (receive.hasNextLine()) {
                    System.out.println(receive.nextLine());
                }
            });
            thread.start();
            thread.join();

            input.close();
            send.close();
            receive.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(String.format(ERROR_MESSAGE, e.getMessage()));
        }
    }
}
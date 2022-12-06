package br.com.italomded.chat;

import br.com.italomded.chat.chat.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    private ServerSocket server;
    private ExecutorService threadPool;
    private AtomicBoolean running;

    private Chat chat;

    public Server(int port) {
        while (true) {
            try {
                server = new ServerSocket(port);
                threadPool = Executors.newCachedThreadPool();
                running = new AtomicBoolean(false);
                chat = new Chat();
                break;
            } catch (IOException | IllegalArgumentException | InputMismatchException e) {
                System.out.println(String.format("MESSAGE: %s", e.getMessage()));
                System.out.print("SET NEW PORT: ");
                port = new Scanner(System.in).nextInt();
            }
        }
    }

    public synchronized void run() {
        if (!running.get()) {
            running.set(true);
            System.out.println("SERVER IS RUNNING...");
        }
        while (running.get()) {
            try {
                Socket client = server.accept();
                client.setSoTimeout(30000);
                System.out.println(String.format("CLIENT ACCEPTED: %s", client.toString()));
                threadPool.execute(new CommandDispatcher(client));
            } catch (IOException e) {
                System.out.println(String.format("MESSAGE: %s", e.getMessage()));
                stop();
            }
        }
    }

    public synchronized void stop() {
        if (running.get()) {
            try {
                System.out.println("CLOSING SERVER...");
                running.set(false);
                server.close();
                System.out.println("SERVER CLOSED...");
            } catch (IOException e) {
                System.out.println(String.format("MESSAGE: %s", e.getMessage()));
            }
        }
    }
}

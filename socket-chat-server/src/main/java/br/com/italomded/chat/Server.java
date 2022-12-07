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
    private final String SET_PORT_MESSAGE = "SET NEW PORT: ";
    private final String SERVER_RUNNING_LOG = "SERVER IS RUNNING...";
    private final String CLIENT_ACCEPTED_LOG = "CLIENT ACCEPTED: %s";
    private final String EXCEPTION_MESSAGE_LOG = "MESSAGE: %s";
    private final String SERVER_CLOSED_LOG = "SERVER CLOSED...";
    private final String SERVER_CLOSING_LOG = "CLOSING SERVER...";

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
                System.out.println(String.format(EXCEPTION_MESSAGE_LOG, e.getMessage()));
                System.out.print(SET_PORT_MESSAGE);
                port = new Scanner(System.in).nextInt();
            }
        }
    }

    public synchronized void run() {
        if (!running.get()) {
            running.set(true);
            System.out.println(SERVER_RUNNING_LOG);
        }
        while (running.get()) {
            try {
                Socket client = server.accept();
                client.setSoTimeout(100000);
                System.out.println(String.format(CLIENT_ACCEPTED_LOG, client.toString()));
                threadPool.execute(new CommandDispatcher(client));
            } catch (IOException e) {
                System.out.println(String.format(EXCEPTION_MESSAGE_LOG, e.getMessage()));
                stop();
            }
        }
    }

    public synchronized void stop() {
        if (running.get()) {
            try {
                System.out.println(SERVER_CLOSING_LOG);
                running.set(false);
                server.close();
                System.out.println(SERVER_CLOSED_LOG);
            } catch (IOException e) {
                System.out.println(String.format(EXCEPTION_MESSAGE_LOG, e.getMessage()));
            }
        }
    }
}

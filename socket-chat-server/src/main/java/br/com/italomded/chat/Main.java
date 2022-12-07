package br.com.italomded.chat;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(3000);
        server.run();
        //server.stop();
    }
}

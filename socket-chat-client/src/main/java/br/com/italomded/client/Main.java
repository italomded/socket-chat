package br.com.italomded.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 3000;
        var socket = new Socket("localhost", port);
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        Scanner sc = new Scanner(System.in);
        while (true) {
            printStream.println(sc.nextLine());
        }
    }
}
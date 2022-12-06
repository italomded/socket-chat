package br.com.italomded.chat.contact;

import br.com.italomded.chat.chat.Chat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@EqualsAndHashCode(of = "userName")
public class Contact {
    private final PrintStream responseToClient;
    private final Scanner clientResponse;

    @Getter
    private String userName;
    @Getter
    private AtomicBoolean online;
    @Getter
    private AtomicBoolean isChatting;

    public Contact(Socket socketClient) throws ContactCreationFailedException {
        userName = Integer.toString(socketClient.hashCode());
        online = new AtomicBoolean(true);
        isChatting = new AtomicBoolean(false);
        try {
            responseToClient = new PrintStream(socketClient.getOutputStream());
            clientResponse = new Scanner(socketClient.getInputStream());
        } catch (IOException e) {
            throw new ContactCreationFailedException(e);
        }
    }

    public void send(String content) {
        responseToClient.println(content);
    }

    public String receive() {
        if (clientResponse.hasNextLine()) return clientResponse.nextLine();
        return null;
    }

    public boolean isReceiving() {
        return clientResponse.hasNextLine();
    }

    public boolean isDisponible() {
        if (online.get()) return false;
        if (isChatting.get()) return false;
        return true;
    }

    public boolean changeUserName(String userName) {
        if (Chat.getContact(userName).isPresent()) return false;
        this.userName = userName;
        return true;
    }
}

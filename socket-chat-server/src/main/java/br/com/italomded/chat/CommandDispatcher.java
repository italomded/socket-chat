package br.com.italomded.chat;

import br.com.italomded.chat.chat.Chat;
import br.com.italomded.chat.command.Command;
import br.com.italomded.chat.command.InvalidCommandException;
import br.com.italomded.chat.command.listeners.*;
import br.com.italomded.chat.contact.Contact;
import br.com.italomded.chat.contact.ContactCreationFailedException;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class CommandDispatcher implements Runnable {
    private final String CLIENT_CLOSED_LOG = "CLIENT CLOSED: %s";
    private final String UNEXPECTED_ERROR_LOG = "UNEXPECTED ERROR, MESSAGE: %s";
    private final String CREDITS_MESSAGE = "HELLO. CREATED BY <https://github.com/italomded/socket-chat>.";
    private final String USERNAME_MESSAGAE = "YOUR CURRENT USERNAME IS %s";
    private final String INVALID_COMMAND_MESSAGE = "INVALID COMMAND";
    private final String COMMAND_LOG_MESSAGE = "CLIENT USERNAME: %s | COMMAND: %s | SOCKET: %s";
    private static final List<Listener> commandListeners = Arrays.asList(
            new RenameListener(),
            new ConnectListener(),
            new MessageListener(),
            new RespondListener(),
            new ExitConnectionListener(),
            new ExitListener()
    );

    private Socket clientSocket;
    private Contact clientContact;

    public CommandDispatcher(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            clientContact = new Contact(clientSocket);
            Chat.addContact(clientContact);
        } catch (ContactCreationFailedException e) {
            System.out.println(String.format(UNEXPECTED_ERROR_LOG, e.getMessage()));
        }
    }

    @Override
    public void run() {
        clientContact.send(CREDITS_MESSAGE);
        clientContact.send(String.format(USERNAME_MESSAGAE, clientContact.getUserName()));
        while (clientContact.getOnline().get() && clientContact.isReceiving()) {
            try {
                String commandValue = clientContact.receive();
                Command command = new Command(commandValue);
                commandListeners.forEach(cL -> cL.doAction(command, clientContact));
                System.out.println(String.format(
                        COMMAND_LOG_MESSAGE,
                        clientContact.getUserName(),
                        command.getVerb().toString(),
                        clientSocket.toString()
                ));
            } catch (InvalidCommandException e) {
                clientContact.send(INVALID_COMMAND_MESSAGE);
            }
        }
        Chat.removeContact(clientContact);
        System.out.println(String.format(CLIENT_CLOSED_LOG, clientSocket.toString()));
    }

}

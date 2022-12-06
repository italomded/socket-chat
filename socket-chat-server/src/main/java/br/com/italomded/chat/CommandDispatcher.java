package br.com.italomded.chat;

import br.com.italomded.chat.command.Command;
import br.com.italomded.chat.command.InvalidCommandException;
import br.com.italomded.chat.command.listeners.Listener;
import br.com.italomded.chat.command.listeners.RenameListener;
import br.com.italomded.chat.contact.Contact;
import br.com.italomded.chat.contact.ContactCreationFailedException;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandDispatcher implements Runnable {
    private static final List<Listener> commandListeners = Arrays.asList(
            new RenameListener()
    );

    private AtomicBoolean stopServeClient;
    private Contact clientContact;

    public CommandDispatcher(Socket clientSocket) {
        try {
            clientContact = new Contact(clientSocket);
            stopServeClient = new AtomicBoolean(false);
        } catch (ContactCreationFailedException e) {
            stopServeClient.set(true);
        }
    }

    @Override
    public void run() {
        if (stopServeClient.get()) return;
        while (!stopServeClient.get() || clientContact.isReceiving()) {
            String commandValue = clientContact.receive();
            try {
                Command command = new Command(commandValue);
                commandListeners.forEach(cL -> cL.doAction(command, clientContact));
                System.out.println(clientContact.getUserName());
            } catch (InvalidCommandException e) {
                clientContact.send("INVALID COMMAND");
            }
        }
    }

}

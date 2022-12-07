package br.com.italomded.chat.command.listeners;

import br.com.italomded.chat.chat.Chat;
import br.com.italomded.chat.command.Verb;
import br.com.italomded.chat.command.listeners.conditions.connect.GuestIsEmptyCondition;
import br.com.italomded.chat.command.listeners.conditions.connect.IsDisponibleCondition;
import br.com.italomded.chat.command.listeners.conditions.connect.IsTheSameContactCondition;
import br.com.italomded.chat.command.listeners.conditions.connect.ListenerConnectCondition;
import br.com.italomded.chat.contact.Connect;
import br.com.italomded.chat.contact.Contact;

import java.util.Optional;

public class ConnectListener extends Listener {
    private final String INVITED_MESSAGE = "YOU HAVE BEEN INVITED TO CONNECT WITH %s. ACCEPT?";
    private final String INVITE_SENT_MESSAGE = "YOUR INVITATION HAS BEEN SENT...";
    private ListenerConnectCondition conditions;

    public ConnectListener() {
        conditions = new GuestIsEmptyCondition().setNext(
                new IsTheSameContactCondition().setNext(
                        new IsDisponibleCondition()));
    }

    @Override
    void whatDo(String value, Contact commandAuthor) {
        Optional<Contact> optionalContact = Chat.getContact(value);

        conditions.setArgs(optionalContact, commandAuthor);
        boolean passed = conditions.go();
        if (!passed) return;

        Contact contact = optionalContact.get();
        contact.send(String.format(INVITED_MESSAGE, commandAuthor.getUserName()));
        commandAuthor.send(INVITE_SENT_MESSAGE);

        Connect connect = new Connect(commandAuthor, contact);
        commandAuthor.setActualConnection(connect);
        contact.setActualConnection(connect);
    }

    @Override
    Verb getAttendVerb() {
        return Verb.CONNECT;
    }
}

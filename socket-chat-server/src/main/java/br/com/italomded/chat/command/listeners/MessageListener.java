package br.com.italomded.chat.command.listeners;

import br.com.italomded.chat.command.Param;
import br.com.italomded.chat.command.Verb;
import br.com.italomded.chat.contact.Contact;

public class MessageListener extends Listener {
    private final String NOT_CONNECTED_MESSAGE = "YOU ARE NOT CONNECTED...";
    private final String NOT_SENT_MESSAGE = "UNABLE TO SEND MESSAGE...";
    @Override
    void whatDo(String value, Contact commandAuthor) {
        if (commandAuthor.isDisponible()) {
            commandAuthor.send(NOT_CONNECTED_MESSAGE);
            return;
        }
        boolean sent = commandAuthor.sendMessage(value);
        if (!sent) commandAuthor.send(NOT_SENT_MESSAGE);
    }

    @Override
    Verb getAttendVerb() {
        return Verb.MESSAGE;
    }
}

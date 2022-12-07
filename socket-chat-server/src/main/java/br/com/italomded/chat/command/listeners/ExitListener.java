package br.com.italomded.chat.command.listeners;

import br.com.italomded.chat.chat.Chat;
import br.com.italomded.chat.command.Verb;
import br.com.italomded.chat.contact.Contact;

public class ExitListener extends Listener {
    private final String EXIT_MESSAGE = "LEAVING...";

    @Override
    void whatDo(String value, Contact commandAuthor) {
        commandAuthor.getOnline().set(false);
        Chat.removeContact(commandAuthor);
        commandAuthor.send(EXIT_MESSAGE);
    }

    @Override
    Verb getAttendVerb() {
        return Verb.EXIT;
    }
}

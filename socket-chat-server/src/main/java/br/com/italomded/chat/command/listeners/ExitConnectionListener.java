package br.com.italomded.chat.command.listeners;

import br.com.italomded.chat.command.Param;
import br.com.italomded.chat.command.Verb;
import br.com.italomded.chat.contact.Contact;

public class ExitConnectionListener extends Listener {
    private final String NO_CONNECTION_MESSAGE = "NO EXISTING CONNECTION...";

    @Override
    void whatDo(String value, Contact commandAuthor) {
        boolean success = true;
        if (commandAuthor.getActualConnection() != null) {
            success = commandAuthor.getActualConnection().exit();
        }
        if (!success) commandAuthor.send(NO_CONNECTION_MESSAGE);
    }

    @Override
    Verb getAttendVerb() {
        return Verb.EXIT_CONNECTION;
    }
}

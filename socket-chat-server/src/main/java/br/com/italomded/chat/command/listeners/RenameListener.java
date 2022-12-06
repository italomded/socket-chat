package br.com.italomded.chat.command.listeners;

import br.com.italomded.chat.command.Command;
import br.com.italomded.chat.command.Verb;
import br.com.italomded.chat.contact.Contact;

public class RenameListener extends Listener {
    @Override
    void whatDo(String value, Contact commandAuthor) {
        commandAuthor.changeUserName(value);
    }

    @Override
    Verb getAttendVerb() {
        return Verb.RENAME;
    }
}

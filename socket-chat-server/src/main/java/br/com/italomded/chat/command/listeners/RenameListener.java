package br.com.italomded.chat.command.listeners;

import br.com.italomded.chat.command.Command;
import br.com.italomded.chat.command.Verb;
import br.com.italomded.chat.contact.Contact;

public class RenameListener extends Listener {
    private final String SUCCESS_MESSAGE = "RENAMED WITH SUCCESS...";
    private final String FAIL_MESSAGE = "USERNAME ALREADY EXISTS OR IS INVALID...";
    @Override
    void whatDo(String value, Contact commandAuthor) {
        boolean renamed = commandAuthor.changeUserName(value);
        if (renamed) commandAuthor.send(SUCCESS_MESSAGE);
        else commandAuthor.send(FAIL_MESSAGE);
    }

    @Override
    Verb getAttendVerb() {
        return Verb.RENAME;
    }
}

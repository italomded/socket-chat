package br.com.italomded.chat.command.listeners;

import br.com.italomded.chat.command.Command;
import br.com.italomded.chat.command.Verb;
import br.com.italomded.chat.contact.Contact;

public abstract class Listener {
    public void doAction(Command command, Contact commandAuthor) {
        if (getAttendVerb() == command.getVerb()) {
            whatDo(command.getValue(), commandAuthor);
        }
    }
    abstract void whatDo(String value, Contact commandAuthor);
    abstract Verb getAttendVerb();
}

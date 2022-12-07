package br.com.italomded.chat.command.listeners.conditions.connect;

import br.com.italomded.chat.contact.Contact;

import java.util.Optional;

public abstract class ListenerConnectCondition {
    protected Optional<Contact> optionalContact;
    protected Contact commandAuthor;
    protected ListenerConnectCondition next;

    public ListenerConnectCondition() {}

    public boolean go() {
        if (this.check()) {
            if (next == null) return true;
            next.optionalContact = optionalContact;
            next.commandAuthor = commandAuthor;
            return next.go();
        }
        return false;
    }

    public ListenerConnectCondition setNext(ListenerConnectCondition next) {
        this.next = next;
        return this;
    }

    public void setArgs(Optional<Contact> optionalContact, Contact commandAuthor) {
        this.optionalContact = optionalContact;
        this.commandAuthor = commandAuthor;
    }

    protected abstract boolean check();
}

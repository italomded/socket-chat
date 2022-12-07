package br.com.italomded.chat.command.listeners.conditions.connect;

import br.com.italomded.chat.contact.Contact;

public class IsTheSameContactCondition extends ListenerConnectCondition {
    private final String FAIL_MESSAGE = "YOU CANNOT CHAT ALONE...";
    @Override
    protected boolean check() {
        Contact contact = super.optionalContact.get();
        System.out.println("helooo i am here");
        if (contact.equals(super.commandAuthor)) {
            commandAuthor.send(FAIL_MESSAGE);
            return false;
        }
        return true;
    }
}

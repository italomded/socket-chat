package br.com.italomded.chat.command.listeners.conditions.connect;

public class GuestIsEmptyCondition extends ListenerConnectCondition {
    private final String FAIL_MESSAGE = "CONTACT DOES NOT EXISTS...";
    @Override
    protected boolean check() {
        if (super.optionalContact.isEmpty()) {
            commandAuthor.send(FAIL_MESSAGE);
            return false;
        }
        return true;
    }
}

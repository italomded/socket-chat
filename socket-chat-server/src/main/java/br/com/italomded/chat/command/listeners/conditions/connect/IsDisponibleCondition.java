package br.com.italomded.chat.command.listeners.conditions.connect;

public class IsDisponibleCondition extends ListenerConnectCondition {
    private final String FAIL_MESSAGE = "YOU HAVE A PENDING INVITATION OR THE USER IS UNAVAILABLE...";
    @Override
    protected boolean check() {
        if (!super.commandAuthor.isDisponible() || !super.optionalContact.get().isDisponible()) {
            super.commandAuthor.send(FAIL_MESSAGE);
            return false;
        }
        return true;
    }
}

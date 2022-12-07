package br.com.italomded.chat.command.listeners;

import br.com.italomded.chat.command.Param;
import br.com.italomded.chat.command.Verb;
import br.com.italomded.chat.contact.Contact;

public class RespondListener extends Listener {
    private final String INVALID_PARAM_MESSAGE = "INVALID PARAMETER...";
    private final String UNABLE_TO_RESPOND_MESSAGE = "NOT ELIGIBLE TO ANSWER...";
    private final String FAIL_TO_ANSWER_MESSAGE = "FAIL TO ANSWER...";

    @Override
    void whatDo(String value, Contact commandAuthor) {
        try {
            Param param = Param.valueOf(value);
            if (commandAuthor.isDisponible()) {
                commandAuthor.send(UNABLE_TO_RESPOND_MESSAGE);
                return;
            }
            boolean success = commandAuthor.getActualConnection().respond(commandAuthor, param.getValue());
            if (!success) commandAuthor.send(FAIL_TO_ANSWER_MESSAGE);
        } catch (IllegalArgumentException e) {
            commandAuthor.send(INVALID_PARAM_MESSAGE);
        }
    }

    @Override
    Verb getAttendVerb() {
        return Verb.RESPOND;
    }
}

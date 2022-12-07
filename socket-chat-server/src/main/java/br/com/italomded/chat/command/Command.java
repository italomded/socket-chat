package br.com.italomded.chat.command;

import lombok.Getter;

public class Command {
    @Getter
    private final Verb verb;
    @Getter
    private final String value;

    public Command(String commandValue) throws InvalidCommandException {
        try {
            String[] commandValueSplit = commandValue.split(" ", 2);
            verb = Verb.valueOf(commandValueSplit[0]);
            if (commandValueSplit.length == 1) value = "";
            else value = commandValueSplit[1];
        } catch (Exception e) {
            throw new InvalidCommandException(e);
        }
    }
}

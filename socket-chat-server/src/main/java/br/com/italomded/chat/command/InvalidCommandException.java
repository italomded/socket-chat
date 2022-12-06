package br.com.italomded.chat.command;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(Exception e) {
        super(e);
    }
}

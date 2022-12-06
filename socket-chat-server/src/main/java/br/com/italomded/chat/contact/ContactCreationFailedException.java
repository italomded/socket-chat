package br.com.italomded.chat.contact;

import java.io.IOException;

public class ContactCreationFailedException extends Exception {
    public ContactCreationFailedException(IOException e) {
        super(e);
    }
}

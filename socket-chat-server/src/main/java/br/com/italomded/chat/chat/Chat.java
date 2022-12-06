package br.com.italomded.chat.chat;

import br.com.italomded.chat.contact.Contact;
import br.com.italomded.chat.contact.ContactCreationFailedException;

import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class Chat {
    public static List<Contact> contactList = new Vector<Contact>();

    public static Optional<Contact> getContact(String userName) {
        return contactList.stream().filter(con -> con.getUserName().equals(userName)).findFirst();
    }

    public static synchronized boolean createContact(Socket clientSocket) {
        try {
            Contact contact = new Contact(clientSocket);
            contactList.add(contact);
            return true;
        } catch (ContactCreationFailedException e) {
            return false;
        }
    }
}

package br.com.italomded.chat.chat;

import br.com.italomded.chat.contact.Connect;
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

    public static boolean addContact(Contact clientContact) {
        return contactList.add(clientContact);
    }

    public static boolean removeContact(Contact contact) {
        return contactList.remove(contact);
    }
}

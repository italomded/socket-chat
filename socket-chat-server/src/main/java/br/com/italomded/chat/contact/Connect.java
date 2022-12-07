package br.com.italomded.chat.contact;

import br.com.italomded.chat.chat.Chat;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

@EqualsAndHashCode
public class Connect {
    private final String MESSAGE_ON_EXIT = "EXITED CONNECTION...";
    private final String MESSAGE_ON_START = "STARTED CONNECTION...";

    private Contact contactHost;
    private Contact contactGuest;
    private AtomicBoolean accepted;

    public Connect(Contact contactHost, Contact contactGuest) {
        this.contactHost = contactHost;
        this.contactGuest = contactGuest;
    }

    private boolean isPending() {
        if (accepted == null) return true;
        return false;
    }

    private void destroy() {
        contactGuest.send(MESSAGE_ON_EXIT);
        contactHost.send(MESSAGE_ON_EXIT);
        contactHost.setActualConnection(null);
        contactGuest.setActualConnection(null);
        contactHost.getIsChatting().set(false);
        contactGuest.getIsChatting().set(false);
    }

    private void startConnection() {
        contactHost.getIsChatting().set(true);
        contactGuest.getIsChatting().set(true);
        contactGuest.send(MESSAGE_ON_START);
        contactHost.send(MESSAGE_ON_START);
    }

    public synchronized boolean respond(Contact caller, boolean answer) {
        if (isPending() && caller.equals(contactGuest)) {
            accepted = new AtomicBoolean(answer);
            if (accepted.get()) startConnection();
            else destroy();
            return true;
        }
        return false;
    }

    public synchronized boolean exit() {
        if (isPending()) return false;
        if (!accepted.get()) return false;
        destroy();
        return true;
    }

    public boolean sendToOther(Contact caller, String messageContent) {
        if (isPending()) return false;
        if (!accepted.get()) return false;

        LocalTime time = LocalTime.now();
        String fullMessage = String.format("%s [%s] %s", time.toString(), caller.getUserName(), messageContent);

        contactGuest.send(fullMessage);
        contactHost.send(fullMessage);
        return true;
    }
}

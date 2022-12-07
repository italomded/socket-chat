package br.com.italomded.chat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServerTest {
    private Server server;
    private final int port = 3000;

    @BeforeEach
    public void runServer() {
        server = new Server(port);
        server.run();
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

    @Test
    public void shouldRun() {
        Assertions.assertTrue(true);
    }
}

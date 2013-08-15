package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConnectionToOpenFire {

    private Chat currentChat;

    @Test(timeout = 30000)
    public void
    I_should_be_able_to_connection_to_openfire() throws Exception {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    XMPPConnection connection = new XMPPConnection("localhost");
                    connection.connect();
                    connection.login("sniper", "sniper");
                    connection.getChatManager().addChatListener(new ChatManagerListener() {
                        @Override
                        public void chatCreated(Chat chat, boolean b) {
                            currentChat = chat;
                        }
                    });

                    Thread.sleep(25000);

                } catch (XMPPException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        thread.join();

        assertThat(currentChat, is(notNullValue()));
    }
}

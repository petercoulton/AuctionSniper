package com.petercoulton.gosgt.auctionsniper;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FakeAuctionServer {
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String AUCTION_LOGIN_FORMAT = "auction-%s";
    public static final String AUCTION_PASSWORD = "auction";

    public static final String XMPP_HOSTNAME = "localhost";

    private final MessageListener messageListener = new SingleMessageListener();

    private final String itemID;
    private final XMPPConnection connection;
    private Chat currentChat;

    public FakeAuctionServer(String itemID) {
        this.itemID = itemID;
        this.connection = new XMPPConnection(XMPP_HOSTNAME);
    }

    public void startSellingItem() throws XMPPException {
        connection.connect();
        connection.login(format(AUCTION_LOGIN_FORMAT, itemID), AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener(
                new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean b) {
                        currentChat = chat;
                        currentChat.addMessageListener(messageListener);
                    }
                }
        );
    }

    public String getItemID() {
        return itemID;
    }

    public void hasReceivedJoinRequestFromSniper() {
    }

    public void announceClosed() throws XMPPException {
        currentChat.sendMessage(new Message());
    }

    public void stop() {
        connection.disconnect();
    }

    public class SingleMessageListener implements MessageListener {
        private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

        @Override
        public void processMessage(Chat chat, Message message) {
            messages.add(message);
        }

        public void receivesAMessage() throws InterruptedException {
            assertThat("Message", messages.poll(5, SECONDS), is(notNullValue()));
        }
    }
}

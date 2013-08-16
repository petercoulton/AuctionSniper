package com.petercoulton.gosgt.auctionsniper;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FakeAuctionServer {
    public static final String XMPP_HOSTNAME = "localhost";

    public static final String AUCTION_LOGIN_FORMAT = "auction-%s";
    public static final String AUCTION_PASSWORD = "auction";
    public static final String AUCTION_RESOURCE = "Auction";

    private final SingleMessageListener messageListener = new SingleMessageListener();

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
                    public void chatCreated(Chat chat, boolean createdLocally) {
                        currentChat = chat;
                        currentChat.addMessageListener(messageListener);
                    }
                }
        );
    }

    public String getItemID() {
        return itemID;
    }

    public void hasReceivedJoinRequestFrom(String bidder) throws InterruptedException {
        messageListener.receivesAMessage(equalTo(format(Main.JOIN_COMMAND_FORMAT, bidder)));
    }

    public void announceClosed() throws XMPPException {
        currentChat.sendMessage("SOLVersion: 1.1; Event: CLOSE;");
    }

    public void stop() {
        connection.disconnect();
    }

    public void reportPrice(int price, int increment, String bidder) throws XMPPException {
        currentChat.sendMessage(
                format("SOLVersion: 1.1; Event: PRICE; " +
                        "CurrentPrice: %d; Increment: %d; Bidder: %s",
                        price, increment, bidder));
    }

    public void hasReceivedBid(int bid, String bidder) throws InterruptedException {
        assertThat(currentChat.getParticipant(), equalTo(bidder));
        messageListener.receivesAMessage(
                equalTo(format(Main.BID_COMMAND_FORMAT, bid, bidder)));
    }


    public class SingleMessageListener implements MessageListener {
        public static final int TIMEOUT = 5;
        private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

        @Override
        public void processMessage(Chat chat, Message message) {
            messages.add(message);
        }

        public void receivesAMessage() throws InterruptedException {
            assertThat("Message", messages.poll(TIMEOUT, SECONDS), is(notNullValue()));
        }

        public void receivesAMessage(Matcher<? super String> messageMatcher) throws InterruptedException {
            final Message message = messages.poll(TIMEOUT, SECONDS);
            assertThat("Message", message, is(notNullValue()));
            assertThat("Message", message.getBody(), messageMatcher);
        }
    }
}

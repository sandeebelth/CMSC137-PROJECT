package game.network;

import chat.Message;

/**
 * Created by joseph on 11/15/16.
 */
public interface MessageListener {
    void newMessage(Message message);
}

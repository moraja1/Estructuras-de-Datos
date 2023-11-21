package chatClient.logic;

import chatProtocol.Message;

public interface ITarget {
    public void deliver(Message message);
}

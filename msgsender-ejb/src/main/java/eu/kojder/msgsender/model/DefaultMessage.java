package eu.kojder.msgsender.model;

import java.io.Serializable;

/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 18:03
 */
public abstract class DefaultMessage implements Serializable {

    private final String recipient;
    private final String message;
    private final MessageType messageType;

    protected DefaultMessage(final String recipient, final String message, final MessageType messageType) {
        this.recipient = recipient;
        this.message = message;
        this.messageType = messageType;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "DefaultMessage{" +
                "recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}

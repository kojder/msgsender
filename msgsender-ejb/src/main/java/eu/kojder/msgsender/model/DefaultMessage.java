package eu.kojder.msgsender.model;

import java.io.Serializable;

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

    public static DefaultMessage valueOf(final String messageTypeCaption, final String recipient, final String message) {
        switch (MessageType.getByCaption(messageTypeCaption)) {
            case SMS:
                return new SmsMessage(recipient, message);
            case EMAIL:
                return new EmailMessage(recipient, message);
            default:
                throw new IllegalStateException();
        }
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

package eu.kojder.msgsender.model;

public class EmailMessage extends DefaultMessage {

    public EmailMessage(final String recipient, final String message) {
        super(recipient, message, MessageType.EMAIL);
    }
}

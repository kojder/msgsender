package eu.kojder.msgsender.model;

public class SmsMessage extends DefaultMessage {

    public SmsMessage(final String recipient, final String message) {
        super(recipient, message, MessageType.SMS);
    }
}

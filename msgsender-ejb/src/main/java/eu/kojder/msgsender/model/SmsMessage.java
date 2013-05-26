package eu.kojder.msgsender.model;

/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 18:16
 */
public class SmsMessage extends DefaultMessage {

    public SmsMessage(final String recipient, final String message) {
        super(recipient, message, MessageType.SMS);
    }
}

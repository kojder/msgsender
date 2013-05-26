package eu.kojder.msgsender.model;

/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 18:16
 */
public class EmailMessage extends DefaultMessage {

    public EmailMessage(final String recipient, final String message) {
        super(recipient, message, MessageType.EMAIL);
    }
}

package eu.kojder.msgsender.service;

import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.model.MessageType;

import javax.ejb.Remote;
import java.util.List;

/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 18:29
 */
@Remote
public interface MessageService {
    void createMessage(String messageTypeValue, String recipient, String message);
    List<DefaultMessage> getAllMessages();
    List<String> getMessageTypes();
    MessageType getMessageType(String value);
}

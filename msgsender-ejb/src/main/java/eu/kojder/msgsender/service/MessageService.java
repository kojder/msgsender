package eu.kojder.msgsender.service;

import eu.kojder.msgsender.model.DefaultMessage;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface MessageService {
    void createMessage(String messageTypeValue, String recipient, String message);
    List<DefaultMessage> getAllMessages();
}

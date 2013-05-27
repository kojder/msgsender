package eu.kojder.msgsender.service;

import eu.kojder.msgsender.model.DefaultMessage;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface MessageAccessService {
    void addMessage(DefaultMessage message);
    List<DefaultMessage> getAllMessages();
    void initMessages(List<DefaultMessage> messages);
}

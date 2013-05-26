package eu.kojder.msgsender.service;

import eu.kojder.msgsender.model.DefaultMessage;

import javax.ejb.Remote;
import java.util.List;

/**
 * User: Andrzej
 * Date: 26.05.13
 * Time: 13:03
 */
@Remote
public interface MessageAccessService {
    void addMessage(DefaultMessage message);
    List<DefaultMessage> getAllMessages();
    void initMessages(List<DefaultMessage> messages);
}

package eu.kojder.msgsender.service.impl;

import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.service.MessageAccessService;
import eu.kojder.msgsender.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 18:30
 */
@Stateless
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private List<DefaultMessage> allMessagesList;

    @EJB
    MessageAccessService messageAccessService;


    @Override
    public void createMessage(String messageTypeValue, String recipient, String message) {
        messageAccessService.addMessage(DefaultMessage.valueOf(messageTypeValue, recipient, message));
    }

    @Override
    public List<DefaultMessage> getAllMessages() {
        return messageAccessService.getAllMessages();
    }

}
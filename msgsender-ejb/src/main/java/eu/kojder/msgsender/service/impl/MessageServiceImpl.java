package eu.kojder.msgsender.service.impl;

import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.model.EmailMessage;
import eu.kojder.msgsender.model.MessageType;
import eu.kojder.msgsender.model.SmsMessage;
import eu.kojder.msgsender.service.MessageAccessService;
import eu.kojder.msgsender.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
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
        final MessageType messageType = getMessageType(messageTypeValue);

        DefaultMessage messageToSave;
        switch (messageType) {
            case EMAIL:
                messageToSave = new EmailMessage(recipient,message);
                break;
            case SMS:
                messageToSave = new SmsMessage(recipient,message);
                break;
            default:
                throw new IllegalStateException();
        }

        messageAccessService.addMessage(messageToSave);
    }

    @Override
    public List<DefaultMessage> getAllMessages() {
        return messageAccessService.getAllMessages();
    }


    @Override
    public List<String> getMessageTypes() {
        final ArrayList<String> result = new ArrayList<String>();
        final MessageType[] messageTypes = MessageType.values();
        for (MessageType messageType : messageTypes) {
            result.add(messageType.getCaption());
        }
        return result;
    }

    @Override
    public MessageType getMessageType(String value) {
        MessageType result = null;
        final MessageType[] messageTypes = MessageType.values();
        for (MessageType messageType : messageTypes) {
            if (messageType.getCaption().equals(value)) {
                return messageType;
            }
        }

        final String errorMessage = "Cannot find " + MessageType.class.getSimpleName() + " for given value: " + value;
        logger.error(errorMessage);
        throw new IllegalStateException(errorMessage);

    }
}
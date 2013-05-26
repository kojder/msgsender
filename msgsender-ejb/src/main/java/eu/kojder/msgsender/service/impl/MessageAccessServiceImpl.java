package eu.kojder.msgsender.service.impl;

import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.service.MessageAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: Andrzej
 * Date: 26.05.13
 * Time: 13:13
 */
@Lock(LockType.WRITE)
@Singleton
public class MessageAccessServiceImpl implements MessageAccessService {

    private static final Logger logger = LoggerFactory.getLogger(MessageAccessServiceImpl.class);

    private ConcurrentLinkedQueue<DefaultMessage> messages = new ConcurrentLinkedQueue<DefaultMessage>();

    @Override
    public void addMessage(DefaultMessage message) {
        messages.add(message);
        logger.info("message added: " + message.toString());
    }

    @Lock(LockType.READ)
    @Override
    public List<DefaultMessage> getAllMessages() {
        return new ArrayList<DefaultMessage>(messages);
    }

    @Override
    public void initMessages(List<DefaultMessage> incommingMessages) {
        if (this.messages.isEmpty()) {
            this.messages = new ConcurrentLinkedQueue<DefaultMessage>(incommingMessages);
        } else if (incommingMessages.containsAll(this.messages)) {
            //TODO: may be wrong, messages sequence can be duplicated
            incommingMessages.retainAll(this.messages);
            this.messages.addAll(incommingMessages);
        } else {
            // TODO: web server restart, multiple web servers, on prod JPA sync/persistence
        }
    }

    @Schedule(second = "*/10", minute = "*", hour = "*", info="Sync to DB every 5 seconds")
    void dbSync() {
         //TODO:
    }

    @PreDestroy
    public void cleanup() {
        logger.warn(this.getClass().getSimpleName() + " destroyed");
        dbSync();
    }
}

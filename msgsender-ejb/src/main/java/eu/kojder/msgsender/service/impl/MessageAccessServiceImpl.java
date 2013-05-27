package eu.kojder.msgsender.service.impl;

import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.service.MessageAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Lock(LockType.WRITE)
@Singleton
public class MessageAccessServiceImpl implements MessageAccessService {

    private static final Logger logger = LoggerFactory.getLogger(MessageAccessServiceImpl.class);

    private ArrayList<DefaultMessage> messages = new ArrayList<DefaultMessage>();

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
            this.messages = new ArrayList<DefaultMessage>(incommingMessages);
        }
    }

    @Schedule(second = "*/10", minute = "*", hour = "*", info="Sync to DB every 5 seconds")
    void dbSync() {
         // db sync. on production
    }

    @PreDestroy
    public void cleanup() {
        logger.warn(this.getClass().getSimpleName() + " destroyed");
        dbSync();
    }
}

package eu.kojder.msgsender.util;

import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.service.MessageAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class MsgSenderContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(MsgSenderContextListener.class);

    @EJB
    MessageAccessService messageAccessService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("start reading messages from file and sending to " + MessageAccessService.class.getSimpleName());
        final List<DefaultMessage> messages = MsgSenderFileUtils.readMessagesFromFile();
        messageAccessService.initMessages(messages);
        logger.info("reading messages from file and sending to " + MessageAccessService.class.getSimpleName() + " finished.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("start reading messages " + MessageAccessService.class.getSimpleName() + " and saving to file");
        final List<DefaultMessage> messages = messageAccessService.getAllMessages();
        MsgSenderFileUtils.writeMessagesToFile(messages);
        logger.info("reading messages " + MessageAccessService.class.getSimpleName() + " and saving to file finished");
    }

}

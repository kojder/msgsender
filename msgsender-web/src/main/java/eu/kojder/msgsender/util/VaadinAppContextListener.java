package eu.kojder.msgsender.util;

import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.model.EmailMessage;
import eu.kojder.msgsender.model.SmsMessage;
import eu.kojder.msgsender.service.MessageAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrzej
 * Date: 26.05.13
 * Time: 14:16
 */

@WebListener
public class VaadinAppContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(VaadinAppContextListener.class);


    @EJB
    MessageAccessService messageAccessService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("start reading messages from file and sending to " + MessageAccessService.class.getSimpleName());
        List<DefaultMessage> messages = new ArrayList<DefaultMessage>();
        messages.add(new SmsMessage("Piotrek", "komunikat SMS"));
        messages.add(new EmailMessage("Rysiek", "komunikat E-mail"));
        messages.add(new SmsMessage("Mirek", "komunikat SMS"));
        messageAccessService.initMessages(messages);
        logger.info("reading messages from file and sending to " + MessageAccessService.class.getSimpleName() + " finished.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("start reading messages " + MessageAccessService.class.getSimpleName() + " and saving to file");
        final List<DefaultMessage> messages = messageAccessService.getAllMessages();
        logger.info("reading messages " + MessageAccessService.class.getSimpleName() + " and saving to file finished");
    }

}

package eu.kojder.msgsender.util;

import eu.kojder.msgsender.model.DefaultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrzej
 * Date: 26.05.13
 * Time: 22:14
 */
public class VaadinAppFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(VaadinAppFileUtils.class);

    //TODO: file name + path set by property
    public static final String MESSAGES_FILE_NAME = "messages.ser";


    public static void writeMessagesToFile(List<DefaultMessage> messages) {

        try {
            final File file = new File(MESSAGES_FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(messages);
            oos.close();
            logger.info("messages were saved to a file {} successfully", MESSAGES_FILE_NAME);

        } catch (Exception ex) {
            logger.error("error at saved messages to file {}", MESSAGES_FILE_NAME, ex);
        }
    }

    public static List<DefaultMessage> readMessagesFromFile() {
        List<DefaultMessage> messages = new ArrayList<DefaultMessage>();
        try{

            FileInputStream fin = new FileInputStream("messages.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            messages = (List<DefaultMessage>) ois.readObject();
            ois.close();
            logger.info("messages were read from a file {} successfully", MESSAGES_FILE_NAME);

        }catch(Exception ex){
            logger.error("error at saved messages to file {}", MESSAGES_FILE_NAME,  ex);
        }
        return messages;
    }
}

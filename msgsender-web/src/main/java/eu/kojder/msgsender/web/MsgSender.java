package eu.kojder.msgsender.web;

import com.vaadin.Application;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;
import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.model.MessageType;
import eu.kojder.msgsender.service.MessageService;
import eu.kojder.msgsender.util.MobileValidator;
import eu.kojder.msgsender.util.MsgSenderFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import java.util.Collections;
import java.util.List;


/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 13:43
 */
@Stateful
public class MsgSender extends Application {

    private static final Logger logger = LoggerFactory.getLogger(MsgSender.class);

    // main views
    private VerticalLayout inputFieldsArea = new VerticalLayout();
    private Table messagesTable = new Table();

    // left view components
    private TextField recipientTextField;
    private TextArea messageTextArea;
    private OptionGroup messageTypeRadios;

    private static final int INPUT_FIELDS_AREA_WIDTH_PERCENT = 50;

    @EJB
    MessageService messageService;


    @Override
    public void init() {
        logger.info("init");
        initView();
        initInputFields();
        initMessagesTable();
    }

    private void initView() {
        final AbstractSplitPanel mainPanel = new HorizontalSplitPanel();
        mainPanel.setFirstComponent(inputFieldsArea);
        mainPanel.setSecondComponent(messagesTable);
        Window mainWindow = new Window("Message Sender", mainPanel);
        setMainWindow(mainWindow);
    }

    private void initInputFields() {
        inputFieldsArea.removeAllComponents();
        constructFields();
        setDefaultStyling();
        addComponentsToFiedsArea();
        addRequiredValidation();
        inputFieldsArea.addComponent(new Button("Send", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                manageSendClick();
            }
        }));
    }

    private void initMessagesTable() {
        messagesTable.addContainerProperty("Message Type", String.class, null);
        messagesTable.addContainerProperty("Recipient", String.class, null);
        messagesTable.addContainerProperty("Message", String.class, null);
        messagesTable.setSizeFull();
        populateMessagesTable();
    }

    private void populateMessagesTable() {
        final List<DefaultMessage> allMessages = messageService.getAllMessages();
        Collections.reverse(allMessages);
        messagesTable.removeAllItems();
        int index = 1;
        for (DefaultMessage message : allMessages) {
            messagesTable.addItem(new Object[]{message.getMessageType().toString(), message.getRecipient(), message.getMessage()}, new Integer(index));
            index ++;
        }
    }

    private void addRequiredValidation() {
        messageTextArea.setRequired(true);
        messageTypeRadios.setRequired(true);
        recipientTextField.setRequired(true);
    }

    private void addComponentsToFiedsArea() {
        inputFieldsArea.addComponent(messageTypeRadios());
        inputFieldsArea.addComponent(recipientTextField);
        inputFieldsArea.addComponent(messageTextArea);
    }

    private void setDefaultStyling() {
        inputFieldsArea.setMargin(true);
        inputFieldsArea.setSpacing(true);
        recipientTextField.setWidth(INPUT_FIELDS_AREA_WIDTH_PERCENT, Sizeable.UNITS_PERCENTAGE);
        messageTextArea.setWidth(INPUT_FIELDS_AREA_WIDTH_PERCENT, Sizeable.UNITS_PERCENTAGE);
    }

    private void constructFields() {
        recipientTextField = new TextField("Recipient");
        messageTextArea = new TextArea("Message");
        messageTypeRadios = new OptionGroup();
    }

    private void manageSendClick() {
        String messageTypeValue = null;
        if (messageTypeRadios.getValue() != null) {
            messageTypeValue = messageTypeRadios.getValue().toString();
            final MessageType messageType = messageService.getMessageType(messageTypeValue);
            recipientTextField.removeAllValidators();
            recipientTextField.addValidator(getValidator(messageType));
        } else {
            recipientTextField.removeAllValidators();
        }
        final String recipientTextFieldValue = recipientTextField.getValue().toString();
        final String messageValue = messageTextArea.getValue().toString();

        if (mandatoryFieldsAreValid()) {
            messageService.createMessage(messageTypeValue, recipientTextFieldValue, messageValue);
            writeAllMessagesToFile();
            populateMessagesTable();
            initInputFields();
            displayConfirmationInfo();
        } else {
            getMainWindow().showNotification("Please complete the missing fields", Window.Notification.TYPE_ERROR_MESSAGE);
            logger.debug("validation error");
        }
    }

    private void displayConfirmationInfo() {
        Window.Notification notif = new Window.Notification("Excellent", "Message saved successfully!");
        notif.setDelayMsec(1000);
        getMainWindow().showNotification(notif);
    }

    //TODO: it should be done by crone, on prod. synchronized with the database
    private void writeAllMessagesToFile() {
        MsgSenderFileUtils.writeMessagesToFile(messageService.getAllMessages());
    }

    private boolean mandatoryFieldsAreValid() {
        return messageTypeRadios.isValid() && recipientTextField.isValid() && messageTextArea.isValid();
    }

    private Validator getValidator(MessageType messageType) {
        switch (messageType) {
            case EMAIL:
                return new EmailValidator("Recipient field value must be in e-mail format");
            case SMS:
                return new MobileValidator("Recipient field value must be in phone number format");
            default:
                throw new IllegalStateException("trying get validator for non existing type of " + DefaultMessage.class.getSimpleName());
        }
    }

    private OptionGroup messageTypeRadios() {
        final List<String> messageTypes = messageService.getMessageTypes();
        messageTypeRadios = new OptionGroup("Message Type", messageTypes);
        messageTypeRadios.setImmediate(true);
        return messageTypeRadios;
    }


    @Override
    @Remove
    public void close() {
        logger.info(this.getClass().getSimpleName() + " closed");
        super.close();
    }
}

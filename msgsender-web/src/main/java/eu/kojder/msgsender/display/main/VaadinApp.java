package eu.kojder.msgsender.display.main;

import com.vaadin.Application;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;
import eu.kojder.msgsender.model.DefaultMessage;
import eu.kojder.msgsender.model.MessageType;
import eu.kojder.msgsender.service.MessageService;
import eu.kojder.msgsender.util.MobileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import java.util.List;


/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 13:43
 */
@Stateful
public class VaadinApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(VaadinApp.class);
    public static final String EMAIL_REGEXP = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";

    private VerticalLayout inputFieldsArea = new VerticalLayout();
    private TextField recipientTextField;
    private TextArea messageTextArea;
    private OptionGroup messageTypeRadios;
    private static final int WIDTH_PERCENTAGE = 50;
    private Table messagesTable = new Table();

    @EJB
    MessageService messageService;


    @Override
    public void init() {
        logger.info("init");
        initView();
        initInputFields();
        initMessagesTable();


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
        messagesTable.removeAllItems();
        int index = 1;
        for (DefaultMessage message : allMessages) {
            messagesTable.addItem(new Object[]{message.getMessageType().toString(), message.getRecipient(), message.getMessage()}, new Integer(index));
            index ++;
        }
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

    private void addRequiredValidation() {
        messageTextArea.setRequired(true);
        messageTypeRadios.setRequired(true);
    }

    private void addComponentsToFiedsArea() {
        inputFieldsArea.addComponent(messageTypeRadios());
        inputFieldsArea.addComponent(recipientTextField);
        inputFieldsArea.addComponent(messageTextArea);
    }

    private void setDefaultStyling() {
        inputFieldsArea.setMargin(true);
        inputFieldsArea.setSpacing(true);
        recipientTextField.setWidth(WIDTH_PERCENTAGE, Sizeable.UNITS_PERCENTAGE);
        messageTextArea.setWidth(WIDTH_PERCENTAGE, Sizeable.UNITS_PERCENTAGE);
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
            populateMessagesTable();
            initInputFields();
        } else {
            getMainWindow().showNotification("Please complete the missing fields", Window.Notification.TYPE_ERROR_MESSAGE);
            logger.debug("recipient value {} doesn't match required pattern {}",recipientTextField, EMAIL_REGEXP);
        }
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

    private void initView() {
        final AbstractSplitPanel mainPanel = new HorizontalSplitPanel();
        mainPanel.setFirstComponent(inputFieldsArea);
        mainPanel.setSecondComponent(messagesTable);
        Window mainWindow = new Window("Message Sender", mainPanel);
        setMainWindow(mainWindow);
    }

    @Override
    @Remove
    public void close() {
        super.close();
    }
}

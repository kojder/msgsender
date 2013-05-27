package eu.kojder.msgsender.model;

import java.util.ArrayList;

/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 18:09
 */
public enum MessageType {
    SMS("sms","SMS", "[-+\\d() ]{8,16}", "Recipient must be valid phone number"),
    EMAIL("email","E-mail", "^([a-zA-Z0-9_\\.\\-+])+@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$", "Recipient must be valid e-mail");

    private final String key;
    private final String caption;
    private final String regexp;
    private final String errorMessage;

    private MessageType(String key, String caption, String regexp, String errorMessage) {
        this.key = key;
        this.caption = caption;
        this.regexp = regexp;
        this.errorMessage = errorMessage;
    }

    public String getKey() {
        return key;
    }

    public String getCaption() {
        return caption;
    }

    public String getRegexp() {
        return regexp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static MessageType getByCaption(String caption) {
        for (MessageType value : values()) {
            if (value.getCaption().equals(caption)) {
                return value;
            }
        }
        throw new IllegalStateException("try get " + MessageType.class.getSimpleName() + " for non existing caption: " + caption);
    }

    public static ArrayList<String> getCaptions() {
        ArrayList<String> result = new ArrayList<String>();
        for (MessageType value : values()) {
            result.add(value.getCaption());
        }
        return result;
    }

    @Override
    public String toString() {
        return caption;
    }
}

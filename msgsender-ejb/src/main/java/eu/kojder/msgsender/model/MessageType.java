package eu.kojder.msgsender.model;

/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 18:09
 */
public enum MessageType {
    SMS("sms","SMS"), EMAIL("email","E-mail");
    private final String key;
    private final String caption;

    private MessageType(String key, String caption) {
        this.key = key;
        this.caption = caption;
    }

    public String getKey() {
        return key;
    }

    public String getCaption() {
        return caption;
    }

    @Override
    public String toString() {
        return caption;
    }
}

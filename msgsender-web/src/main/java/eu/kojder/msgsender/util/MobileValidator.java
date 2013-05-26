package eu.kojder.msgsender.util;

import com.vaadin.data.validator.RegexpValidator;

public class MobileValidator extends RegexpValidator {

    /**
     * Creates a validator for checking that a string is a syntactically valid
     * phone number.
     *
     * @param errorMessage the message to display in case the value does not validate.
     */
    public MobileValidator(String errorMessage) {
        super("[-+\\d() ]{8,16}",true, errorMessage);
    }

}

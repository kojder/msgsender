package eu.kojder.msgsender.model;

import org.junit.Assert;
import org.junit.Test;

public class DefaultMessageTest {

    @Test
    public void valueOfPassTestEmail() throws Exception {
        //given
        final String messageTypeCaption = "E-mail";

        //when
        final DefaultMessage defaultMessage = DefaultMessage.valueOf(messageTypeCaption, "test@test.com", "e-mail message");

        //then
        Assert.assertTrue("should create " + EmailMessage.class.getSimpleName(),defaultMessage.getClass().equals(EmailMessage.class));
    }

    @Test
    public void valueOfPassTestSms() throws Exception {
        //given
        final String messageTypeCaption = "SMS";

        //when
        final DefaultMessage defaultMessage = DefaultMessage.valueOf(messageTypeCaption, "test@test.com", "e-mail message");

        //then
        Assert.assertTrue("should create " + SmsMessage.class.getSimpleName(),defaultMessage.getClass().equals(SmsMessage.class));
    }

    @Test
    public void valueOfErrorTest() throws Exception {
        //given
        final String messageTypeCaption = "E-mail";

        //when
        final DefaultMessage defaultMessage = DefaultMessage.valueOf(messageTypeCaption, "test@test.com", "e-mail message");

        //then
        Assert.assertFalse("should fail, expected class was e-mail",defaultMessage.getClass().equals(SmsMessage.class));
    }

}

package eu.kojder.msgsender.model;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class MessageTypeTest {

    @Test
    public void shouldGetProperKey() throws Exception {
        assertEquals("email", MessageType.EMAIL.getKey());
    }

    @Test
    public void testGetCaption() throws Exception {
        assertEquals("E-mail", MessageType.EMAIL.getCaption());
    }

    @Test
    public void shouldReturnProperEmailRegexp() throws Exception {
        assertEquals("^([a-zA-Z0-9_\\.\\-+])+@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$", MessageType.EMAIL.getRegexp());
    }

    @Test
    public void shouldReturnProperSMSRegexp() throws Exception {
        assertEquals("[-+\\d() ]{8,16}", MessageType.SMS.getRegexp());
    }

    @Test
    public void shouldFindMessageTypeByCaption() throws Exception {
        assertEquals(MessageType.EMAIL, MessageType.getByCaption("E-mail"));
    }

    @Test
    public void getCaptionsShouldReturnListOfCaptions() throws Exception {
        final ArrayList<String> captions = MessageType.getCaptions();

        assertEquals("wrong results number",MessageType.values().length, captions.size());
        assertTrue("wrong return type",captions instanceof ArrayList);


    }
}

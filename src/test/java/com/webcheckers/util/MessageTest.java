package com.webcheckers.util;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Message class in the Util-Tier.
 *
 * @author Nicholas Deary
 */
@Tag("Util-Tier")
public class MessageTest {
    /**
     * Tests that the info message is correct.
     */
    @Test
    public void testInfoMessage() {
        Message info = Message.info("Test");

        assertEquals(info.getText(), "Test");
        assertEquals(info.getType(), Message.Type.INFO);
        assertEquals(info.toString(), "{Msg INFO 'Test'}");
        assertTrue(info.isSuccessful());
    }

    /**
     * Tests that the error message is correct.
     */
    @Test
    public void testErrorMessage() {
        Message error = Message.error("Test");

        assertEquals(error.getText(), "Test");
        assertEquals(error.getType(), Message.Type.ERROR);
        assertEquals(error.toString(), "{Msg ERROR 'Test'}");
        assertFalse(error.isSuccessful());
    }
}

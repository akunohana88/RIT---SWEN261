package com.webcheckers.util;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the CurrentUserTest class in the Util-Tier.
 *
 * @author Nicholas Deary
 */
@Tag("Util-Tier")
public class CurrentUserTest {

    /**
     * Performs the whole test in one method because the class is small enough.
     */
    @Test
    public void testUser() {
        CurrentUser user = new CurrentUser("Test");
        assertEquals(user.getName(), "Test");
    }
}

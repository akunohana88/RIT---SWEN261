package com.webcheckers.util;

/**
 * CurrentUser is an entity that that stores the username of the user within a session.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class CurrentUser {
    private final String name;
    /**
     * Updates the name assigned to CurrentUser.
     *
     * @param name is a String that holds the username entered by the current session.
     */
    public CurrentUser(String name) {
        this.name = name;
    }
    /**
     * Updates the name assigned to CurrentUser.
     *
     * @return the name stored in currentUser.
     */
    public String getName() {
        return name;
    }
}

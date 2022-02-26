package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test king piece object in the model tier.
 *
 * @author Hana Ho
 */
@Tag("Model-tier")
public class SpaceTest {

    /**
     * Spaces used for testing
     */
    private Space space1 = new Space(Color.BLACK, new SinglePiece(Color.RED, Piece.Type.SINGLE), 3);
    private final Space space2 = new Space(Color.WHITE, null, 5);
    private final Space space3 = new Space(Color.BLACK, null, 2);

    /**
     * Test that occupant of space can change
     */
    @Test
    public void testChangeOccupant() {
        Piece occupant = new SinglePiece(Color.WHITE, Piece.Type.SINGLE);
        space1 = space1.changeOccupant(occupant);
        assertEquals(space1.getPiece(), occupant, "occupant should have changed");
    }

    /**
     * Test to get piece on a space
     */
    @Test
    public void testGetPiece() {
        assertNotNull(space1, "expected piece");
        assertNotNull(space1.getPieceColor(),"expected piece color");
        assertNull(space2.getPiece(), "expected null");
        assertNull(space2.getPieceColor(), "expected null");
    }

    /**
     * Test to get color of space
     */
    @Test
    public void testGetColor() {
        assertNotNull(space1, "expected piece");
        assertEquals(Color.BLACK, space1.getColor(), "expected black");
    }

    /**
     * Test if the space is valid(can place piece there)
     */
    @Test
    public void testIsValid() {
        assertFalse(space1.isValid(), "should be false because there is piece");
        assertFalse(space2.isValid(), "should be false because space is white");
        assertTrue(space3.isValid(), "should be true because space is black and no piece");
    }

    /**
     * Test to get cell idx number
     */
    @Test
    public void testGetCellIdx() {
        assertSame(space3.getCellIdx(), 2, "should return 2");
    }

    /**
     * Test to get cell idx number gets flipped and all the attributes are the same.
     */
    @Test
    public void testGetFlippedSpace() {
        Space flipped = space1.getFlippedSpace();
        assertSame(flipped.getCellIdx(), 7 - space1.getCellIdx());
        assertEquals(flipped.getPiece(), space1.getPiece());
        assertEquals(flipped.getColor(), space1.getColor());
    }
}

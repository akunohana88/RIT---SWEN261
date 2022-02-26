package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a test for the model tier Position
 *
 * @author Matthew Simoni
 */
@Tag("Model-tier")
public class PositionTest {
    /** Fields */
    private int row;
    private int col;

    /**
     * Executes before each test.
     */
    @BeforeEach
    public void beforeEach() {
        row = 1;
        col = 1;
    }

    /**
     * A valid position was created
     */
    @Test
    public void createPosition() {
        Position position = new Position(row,col);
        assertNotNull(position, "This shouldn't be null");
        assertNotNull(position.safeCreatePosition(row,col), "This should be a valid check");
        assertNull(position.safeCreatePosition(row+8, col+8), "This was given an invalid position");
    }

    /**
     * The position returned the proper info
     */
    @Test
    public void checkPositions() {
        Position position = new Position(row,col);
        assertEquals(row, position.getRow(), "Wrong row");
        assertEquals(col, position.getCell(), "Wrong column");
    }

    /**
     * The position returned the opposite
     */
    @Test
    public void checkOppositePos() {
        Position position = new Position(row,col);
        Position newPosition = position.getOtherPosition();

        assertEquals(7 - row, newPosition.getRow(), "Not the proper row");
        assertEquals(7 - col, newPosition.getCell(), "Not the proper col");
    }

    /**
     * Checks position information
     */
    @Test
    public void checkData(){
        Position position = new Position(row,col);
        Position newPosition = new Position(row,col);
        assertEquals(position, newPosition, "Positions of equal row and col are equal");
        assertEquals(position.hashCode(), newPosition.hashCode(), "Positions of equal row and col are equal");
        assertNotNull(position.toString(), "Displays a ToString");
    }

}

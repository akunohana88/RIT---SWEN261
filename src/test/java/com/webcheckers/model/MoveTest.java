package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a test for the model tier Move
 *
 * @author Matthew Simoni
 */
@Tag("Model-tier")
public class MoveTest {

    Position start;
    Position end;

    @BeforeEach
    public void beforeEach() {
        start = new Position(5,5);
        end = new Position(4,4);
    }

    /**
     * Tests to see if move is created
     */
    @Test
    public void createMove() {
        Move move = new Move(start, end);
        assertNotNull(move);
    }

    /**
     * Tests to see if the move has the correct info
     */
    @Test
    public void checkPositions() {
        Move move = new Move(start, end);
        assertEquals(start, move.getStart(), "Wrong Start");
        assertEquals(end, move.getEnd(), "Wrong End");
    }

    /**
     * Tests to see if the move opposite has the correct info
     */
    @Test
    public void checkOpposite() {
        Move move = new Move(start, end);

        assertEquals(move.getStart().getOtherPosition().getRow(), move.getOpposite().getStart().getRow() , "Start not flipped correctly at row");
        assertEquals(move.getStart().getOtherPosition().getCell(), move.getOpposite().getStart().getCell() , "Start not flipped correctly at cell");

        assertEquals(move.getStart().getOtherPosition().getRow(), move.getOpposite().getStart().getRow() , "End not flipped correctly at row");
        assertEquals(move.getStart().getOtherPosition().getRow(), move.getOpposite().getStart().getRow() , "End not flipped correctly at cell");
    }

    /**
     * Tests to see if a move is valid properly
     */
    @Test
    public void checkValidMove() {
        Move move = new Move(start, end);
        assertTrue(move.isSimpleMove());
    }

    /**
     * Tests to see if a jump is valid properly
     */
    @Test
    public void checkValidJump() {
        Move move = new Move(new Position(3, 4), new Position(1, 2));
        assertTrue(move.isJumpMove());
    }

    /**
     * Checks Move information
     */
    @Test
    public void checkData(){
        Move move = new Move(start, end);
        Move newMove = new Move(start, end);

        assertEquals(move, newMove, "Moves of equal start and end are equal");
        assertEquals(move.hashCode(), newMove.hashCode(), "Moves of equal start and end are equal");
        assertNotNull(move.toString(), "Displays a ToString");

    }
}

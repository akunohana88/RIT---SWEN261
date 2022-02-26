package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test king piece object in the model tier.
 *
 * @author Hana Ho
 */
@Tag("Model-tier")
public class KingPieceTest {

    KingPiece piece = new KingPiece(Color.RED, Piece.Type.KING);
    Move move0 = new Move(new Position(2, 4), new Position(1, 3));
    Move move1 = new Move(new Position(1, 3), new Position(2, 4));
    Move move2 = new Move(new Position(1, 5), new Position(1, 3));
    Move move3 = new Move(new Position(3, 4), new Position(1, 2));
    Move move4 = new Move(new Position(1, 2), new Position(3, 4));
    Move move5 = new Move(new Position(4, 3), new Position(1, 3));
    Move move6 = new Move(new Position(0,0), new Position(9,9));

    /**
     * Checks that isValidSimpleMove is working
     */
    @Test
    public void testIsValidSimpleMove() {
        assertTrue(piece.isValidSimpleMove(move0));
        assertTrue(piece.isValidSimpleMove(move1));
        assertFalse(piece.isValidSimpleMove(move2));
        assertFalse(piece.isValidSimpleMove(move3));
        assertFalse(piece.isValidSimpleMove(move4));
        assertFalse(piece.isValidSimpleMove(move5));
        assertFalse(piece.isValidSimpleMove(move6));
    }

    /**
     * Checks that isValidSingleJump is working
     */
    @Test
    public void testIsValidSingleJump() {
        assertFalse(piece.isValidSingleJump(move0));
        assertFalse(piece.isValidSingleJump(move1));
        assertFalse(piece.isValidSingleJump(move2));
        assertTrue(piece.isValidSingleJump(move3));
        assertTrue(piece.isValidSingleJump(move4));
        assertFalse(piece.isValidSingleJump(move5));
        assertFalse(piece.isValidSimpleMove(move6));
    }
}

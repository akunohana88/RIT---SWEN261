package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.Color.RED;
import static com.webcheckers.model.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test king piece object in the model tier.
 *
 * @author Hana Ho
 */
@Tag("Model-tier")
public class PieceTest {

    /**
     * Checks to make sure piece's color is set and returned correctly
     */
    @Test
    void TestGetColor() {
        Piece piece1 = new SinglePiece(RED, Piece.Type.SINGLE);
        Piece piece2 = new KingPiece(WHITE, Piece.Type.KING);
        Color getColor1 = piece1.getColor();
        Color getColor2 = piece2.getColor();
        assertEquals(RED, getColor1, "piece1 should be: " + RED);
        assertEquals(WHITE, getColor2, "piece2 should be: " + WHITE);
    }

    /**
     * Checks to make sure piece's type is set and returned correctly
     */
    @Test
    void testGetTypes() {
        Piece piece1 = new SinglePiece(RED, Piece.Type.SINGLE);
        Piece piece2 = new KingPiece(WHITE, Piece.Type.KING);
        Piece.Type type1 = piece1.getType();
        Piece.Type type2 = piece2.getType();
        assertEquals(Piece.Type.SINGLE, type1, "piece1 should be: " + Piece.Type.SINGLE);
        assertEquals(Piece.Type.KING, type2, "piece2 should be: " + Piece.Type.KING);
    }
}

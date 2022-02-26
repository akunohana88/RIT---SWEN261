package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class test the board object in the model tier.
 *
 * @author Nicholas Deary
 * @author Hana Ho
 */
@Tag("Model-tier")
public class BoardTest {
    /** Friendly Objects */
    private Player red;
    private Player white;
    private Game game;

    /**
     * This initializes the fields before each test.
     */
    @BeforeEach
    public void beforeEach() {
        red = new Player("red");
        white = new Player("white");
        game = new Game(red, white);
    }

    /**
     * Tests the creation of a board.
     */
    @Test
    public void createBoard() {
        Board board = new Board();
        assertNull(board.getPieceAtPosition(null, null), "Should be capable of grabbing nothing");
        assertNotNull(board);
    }

    /**
     * Tests that the board has the correct pattern.
     */
    @Test
    public void checkeredPattern() {
        Board board = new Board();
        assertNotNull(board);

        Iterator<Row> itrRow = board.iteratorByColor(true);
        Iterator<Space> itrSpaceR1= itrRow.next().iterator();
        Iterator<Space> itrSpaceR2= itrRow.next().iterator();
        Iterator<Space> itrSpaceR3= itrRow.next().iterator();
        Iterator<Space> itrSpaceR4= itrRow.next().iterator();
        Iterator<Space> itrSpaceR5= itrRow.next().iterator();
        Iterator<Space> itrSpaceR6= itrRow.next().iterator();
        Iterator<Space> itrSpaceR7= itrRow.next().iterator();
        Iterator<Space> itrSpaceR8= itrRow.next().iterator();
        LinkedList<Iterator<Space>> itrSpaces = new LinkedList<>(Arrays.asList(
                itrSpaceR1, itrSpaceR2, itrSpaceR3, itrSpaceR4,
                itrSpaceR5, itrSpaceR6, itrSpaceR7, itrSpaceR8));
        int row = 1;
        for(Iterator<Space> itr : itrSpaces) {
            if(row % 2 == 1) {
                for (int c = 1; c <= 8; c++) {
                    if (c % 2 == 1) {
                        assertEquals(itr.next().getColor(), Color.WHITE);
                    } else if (c % 2 == 0) {
                        assertEquals(itr.next().getColor(), Color.BLACK);
                    }
                }
            }
            else if(row % 2 == 0) {
                for (int c = 1; c <= 8; c++) {
                    if (c % 2 == 1) {
                        assertEquals(itr.next().getColor(), Color.BLACK);
                    } else if (c % 2 == 0) {
                        assertEquals(itr.next().getColor(), Color.WHITE);
                    }
                }
            }
            row++;
        }
    }

    /**
     * Tests that the iterator for the red side has white pieces on the top
     * of the board.
     */
    @Test
    public void iteratorForRed() {
        Board board = new Board();
        assertNotNull(board);

        Iterator<Row> itrRow = board.iteratorByColor(false);
        Iterator<Space> itrSpace= itrRow.next().iterator();
        itrSpace.next();
        Space secondOnTheTopRow = itrSpace.next();
        assertEquals(secondOnTheTopRow.getPiece().getColor(), Color.WHITE);
    }

    /**
     * Tests that the iterator for the white side has red pieces on the top
     * of the board.
     */
    @Test
    public void iteratorForWhite() {
        Board board = new Board();
        assertNotNull(board);

        Iterator<Row> itrRow = board.iteratorByColor(true);
        Iterator<Space> itrSpace= itrRow.next().iterator();
        itrSpace.next();
        Space secondOnTheTopRow = itrSpace.next();
        assertEquals(secondOnTheTopRow.getPiece().getColor(), Color.RED);
    }

    /**
     * Checks that the make move method works for the white side.
     */
    @Test
    public void makeMoveForWhiteTest() {
        Move move = new Move(new Position(5, 0), new Position(4, 1));
        Board board = new Board();
        board.makeMove(move, Color.WHITE);
        Iterator<Row> itrRow = board.iteratorByColor(true);
        for(int i = 0; i <= 3; i++) {
            itrRow.next();
        }
        assertEquals(itrRow.next().getPieceAtIndex(1).getColor(), Color.WHITE);
        assertNull(itrRow.next().getPieceAtIndex(0));
    }

    /**
     * Checks that the make move method works for the red side.
     */
    @Test
    public void makeMoveForRedTest() {
        Move move = new Move(new Position(5, 0), new Position(4, 1));
        Board board = new Board();
        board.makeMove(move, Color.RED);
        Iterator<Row> itrRow = board.iteratorByColor(false);
        for(int i = 0; i <= 3; i++) {
            itrRow.next();
        }
        assertEquals(itrRow.next().getPieceAtIndex(1).getColor(), Color.RED);
        assertNull(itrRow.next().getPieceAtIndex(0));
    }

    /**
     * Tests that piece becomes a king.
     */
    @Test
    public void testKingPromotion() {
        Position position = new Position(0, 1);
        Move move = new Move(new Position(1, 0), position);
        Board board = new Board();
        board.makeMove(move, Color.WHITE);
        assertEquals(board.getPieceAtPosition(position, Color.WHITE).getType(), Piece.Type.KING);
        Move move1 = new Move(new Position(1, 0), position);
        Board board1 = new Board();
        board1.makeMove(move1, Color.RED);
        assertEquals(board1.getPieceAtPosition(position, Color.RED).getType(), Piece.Type.KING);
    }

    /**
     * Checks if piece disappears when eaten.
     */
    @Test
    public void eatPiece() {
        Position position = new Position(2, 1);
        Move move = new Move(new Position(1, 0), new Position(3, 2));
        Board board = new Board();
        board.makeMove(move, Color.RED);
        assertEquals(board.getPieceAtPosition(position, Color.RED), null);
    }
}

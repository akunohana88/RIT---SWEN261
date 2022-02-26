package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * This class test the board object in the model tier.
 *
 * @author Matthew Simoni
 * @author Nicholas Deary
 */
@Tag("Model-tier")
public class GameTest {
    /** Friendly Objects */
    private Player red;
    private Player white;

    private Game CuT;

    @BeforeEach
    public void beforeEach() {
        red = new Player("red");
        white = new Player("white");
    }

    /**
     * Makes a game for testing
     */
    @Test
    public void createGame() {
        CuT = new Game(red, white);
        assertNotNull(CuT, "Game is not created properly");
    }

    /**
     * Makes a game for testing and sees if everything is starting properly
     */
    @Test
    public void gameMadeProperly() {
        CuT = new Game(red, white);

        assertNotNull(CuT, "Game is not created properly");
        //assertNull(CuT.getCurrentMoveInProcess(), "This should be null");
        assertNotNull(CuT.getBoard(), "Board is not made");
        assertEquals(red, CuT.getRedPlayer(), "Red player not set properly");
        assertEquals(white, CuT.getWhitePlayer(), "White player not set properly");
        assertEquals(Color.RED, CuT.getActiveColor(), "Starting color is not red");


    }

    /**
     * Makes a move to see if everything changes
     */
    @Test
    public void makingAMove() {
        CuT = new Game(red, white);

        Move move = new Move( new Position( 5, 5), new Position( 4, 4));

        assertEquals(Color.RED, CuT.getActiveColor(), "Color is started incorrectly");

        CuT.swapActiveColor();

        assertEquals(Color.WHITE, CuT.getActiveColor(), "Color is stopped incorrectly");

        CuT.swapActiveColor();

        assertEquals(Color.RED, CuT.getActiveColor(), "Color is started incorrectly");
    }

    /**
     * Tests that correct opponents are returned
     */
    @Test
    public void testGetOpponent() {
        Player player1 = new Player("red");
        Player player2 = new Player("white");
        Game game = new Game(player1, player2);
        assertEquals(game.getOpponent(player1), player2);
        assertEquals(game.getOpponent(player2), player1);
    }

    /**
     * Checks that winner is declared correctly
     */
    @Test void testDeclareWinner() {
        Player player1 = new Player("red");
        Player player2 = new Player("white");
        Game game = new Game(player1, player2);
        assertTrue(game.declareWinner(player1));
        assertNotNull(game.getWinner());
        assertFalse(game.declareWinner(new Player("hahaha")));
    }

    /**
     * Tests the methods regarding when currentMoveInProcess's size is 0
     */
    @Test
    public void testNoCurrentMoveInProcess() {
        CuT = new Game(red, white);
        assertEquals(CuT.backupMove(), "ERROR: How do you undo a move you never made?");
        assertEquals(CuT.submitTurn(), "ERROR: No move has been made.");
    }

    /**
     * Tests the takeTurn method in game.
     */
    @Test
    public void testTakeTurn() {
        CuT = new Game(red, white);
        String output = CuT.takeTurn(new Move(new Position(5, 2), new Position(4, 3)));
        assertEquals(output, Game.VALID_MOVE);
        CuT.submitTurn();
        output = CuT.takeTurn(new Move(new Position(5, 6), new Position(4, 7)));
        assertEquals(output, Game.VALID_MOVE);
        CuT.submitTurn();
        output = CuT.takeTurn(new Move(new Position(6, 3), new Position(5, 4)));
        assertEquals(output, Game.ERROR_INVALID_MOVE);
        CuT.submitTurn();
        output = CuT.takeTurn(new Move(new Position(6, 3), new Position(5, 2)));
        assertEquals(output, Game.VALID_MOVE);
        CuT.submitTurn();
        output = CuT.takeTurn(new Move(new Position(6, 5), new Position(5, 6)));
        assertEquals(output, Game.VALID_MOVE);
        CuT.submitTurn();
        output = CuT.takeTurn(new Move(new Position(4, 3), new Position(3, 2)));
        assertEquals(output, Game.VALID_MOVE);
        CuT.submitTurn();
        output = CuT.takeTurn(new Move(new Position(5, 0), new Position(4, 1)));
        assertEquals(output, Game.ERROR_INVALID_MOVE);
        CuT.submitTurn();
        output = CuT.takeTurn(new Move(new Position(5, 4), new Position(3, 6)));
        assertEquals(output, Game.JUMP_AGAIN);
        output = CuT.submitTurn();
        assertEquals(output, Game.ERROR_MORE_JUMPS);
        output = CuT.takeTurn(new Move(new Position(3, 6), new Position(1, 7)));
        assertEquals(output, Game.ERROR_INVALID_MOVE);
        output = CuT.takeTurn(new Move(new Position(6, 3), new Position(5, 4)));
        assertEquals(output, Game.ERROR_INVALID_MOVE);
        output = CuT.takeTurn(new Move(new Position(3, 6), new Position(1, 4)));
        assertEquals(output, Game.VALID_MOVE);
        CuT.submitTurn();
    }

    /**
     * Tests backupMove method in game.
     */
    @Test
    public void testBackupMove() {
        CuT = new Game(red, white);
        String output = CuT.backupMove();
        assertEquals(output, Game.ERROR_NO_MOVE_TO_UNDO);
        CuT.takeTurn(new Move(new Position(5, 2), new Position(4, 3)));
        output = CuT.backupMove();
        assertEquals(output, Game.UNDID_MOVE);
    }

    /**
     * Makes an empty board for testing.
     *
     * @return an empty board.
     */
    private Iterator<Row> getEmptyBoard() {
        int numOfRows = 6;
        int numOfCol = 6;
        Row[] rows = new Row[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            List<Space> currentRow = new ArrayList<>();
            for (int j = 0; j < numOfCol; j++) {
                if (i % 2 == j % 2) {
                    currentRow.add(new Space(Color.WHITE, null, j));
                } else {
                    currentRow.add(new Space(Color.BLACK, null, j));
                }
            }
            rows[i] = new Row(i, currentRow);
        }
        return Arrays.stream(rows).iterator();
    }

    /**
     * Tests anyMovesLeft in game.
     */
    @Test
    public void testNoMoreMoves() {
        CuT = new Game(red, white);
        boolean output = CuT.noMoreMovesLeft();
        assertFalse(output);
        Board board = mock(Board.class);
        CuT = new Game(red, white, board);
        when(board.iteratorByColor(true)).thenReturn(getEmptyBoard());
        when(board.iteratorByColor(false)).thenReturn(getEmptyBoard());
        output = CuT.noMoreMovesLeft();
        assertTrue(output);
        CuT.swapActiveColor();
        output = CuT.noMoreMovesLeft();
        assertFalse(output);
    }
}

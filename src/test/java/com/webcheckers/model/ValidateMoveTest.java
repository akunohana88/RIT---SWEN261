package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the ValidateMove class.
 *
 * @author Nicholas Deary
 */
@Tag("Model-Tier")
public class ValidateMoveTest {
    /**
     * Tests that a player can make a valid single simple move.
     */
    @Test
    public void testValidSingleSimpleMove() {
        Game game = new Game(new Player("p1"), new Player("p2"));
        ValidateMove validateMove = new ValidateMove(game);
        Move moveNegPos = new Move(new Position(5, 2), new Position(4, 3));
        Move moveNegNeg = new Move(new Position(5, 2), new Position(4, 1));
        assertTrue(validateMove.isMoveValid(moveNegPos));
        assertTrue(validateMove.isMoveValid(moveNegNeg));
    }

    /**
     * Tests that a player can make a invalid single simple move.
     */
    @Test
    public void testInvalidSingleSimpleMove() {
        Game game = new Game(new Player("p1"), new Player("p2"));
        ValidateMove validateMove = new ValidateMove(game);
        Move moveForward = new Move(new Position(5, 2), new Position(4, 2));
        Move moveRight = new Move(new Position(5, 2), new Position(5, 3));
        Move moveLeft = new Move(new Position(5, 2), new Position(5, 1));
        assertFalse(validateMove.isMoveValid(moveForward), "Moved forward one spaces is accepted.");
        assertFalse(validateMove.isMoveValid(moveRight), "Move right one space is accepted.");
        assertFalse(validateMove.isMoveValid(moveLeft), "Move left one space is accepted.");
    }

    /**
     * Tests that a player can make a valid single jump move.
     */
    @Test
    public void testValidSingleJumpMoveLeft() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        int numOfRows = 4;
        int numOfCol = 4;
        Row[] rows = new Row[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            List<Space> currentRow = new ArrayList<>();
            for (int j = 0; j < numOfCol; j++) {
                if (i % 2 == j % 2) {
                    currentRow.add(new Space(Color.WHITE, null, j));
                } else {
                    if (i == 3) {
                        currentRow.add(new Space(Color.BLACK, new SinglePiece(Color.RED, Piece.Type.SINGLE), j));
                    }
                    else if (i == 2) {
                        currentRow.add(new Space(Color.BLACK, new SinglePiece(Color.WHITE, Piece.Type.SINGLE), j));
                    }
                    else {
                        currentRow.add(new Space(Color.BLACK, null, j));
                    }
                }
            }
            rows[i] = new Row(i, currentRow);
        }
        when(board.iteratorByColor(false)).thenReturn(Arrays.stream(rows).iterator());
        when(board.getPieceAtPosition(new Position(2, 1), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.SINGLE));
        Move jumpLeft = new Move(new Position(3, 2), new Position(1, 0));
        assertTrue(validateMove.isMoveValid(jumpLeft));
    }

    /**
     * Tests that a player can make a valid single jump move.
     */
    @Test
    public void testValidSingleJumpMoveRight() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        int numOfRows = 4;
        int numOfCol = 4;
        Row[] rows = new Row[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            List<Space> currentRow = new ArrayList<>();
            for (int j = 0; j < numOfCol; j++) {
                if (i % 2 == j % 2) {
                    currentRow.add(new Space(Color.WHITE, null, j));
                } else {
                    if (i == 3) {
                        currentRow.add(new Space(Color.BLACK, new SinglePiece(Color.RED, Piece.Type.SINGLE), j));
                    }
                    else if (i == 2) {
                        currentRow.add(new Space(Color.BLACK, new SinglePiece(Color.WHITE, Piece.Type.SINGLE), j));
                    }
                    else {
                        currentRow.add(new Space(Color.BLACK, null, j));
                    }
                }
            }
            rows[i] = new Row(i, currentRow);
        }
        when(board.iteratorByColor(false)).thenReturn(Arrays.stream(rows).iterator());
        when(board.getPieceAtPosition(new Position(2, 1), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.SINGLE));
        Move jumpRight = new Move(new Position(3, 0), new Position(1, 2));
        assertTrue(validateMove.isMoveValid(jumpRight));
    }

    /**
     * Makes a board with a king piece for testing.
     *
     * @return a board with a king piece.
     */
    private Iterator<Row> getBoardWithKingInCenter() {
        int numOfRows = 4;
        int numOfCol = 4;
        Row[] rows = new Row[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            List<Space> currentRow = new ArrayList<>();
            for (int j = 0; j < numOfCol; j++) {
                if (i % 2 == j % 2) {
                    currentRow.add(new Space(Color.WHITE, null, j));
                } else {
                    if (i == 2) {
                        currentRow.add(new Space(Color.BLACK, new KingPiece(Color.RED, Piece.Type.KING), j));
                    }
                    else {
                        currentRow.add(new Space(Color.BLACK, null, j));
                    }
                }
            }
            rows[i] = new Row(i, currentRow);
        }
        return Arrays.stream(rows).iterator();
    }

    /**
     * Tests if a king can make a simple move.
     */
    @Test
    public void testValidKingSimpleMoveNegPos() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardWithKingInCenter());
        Move move = new Move(new Position(2, 1), new Position(1, 2));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Tests if a king can make a simple move.
     */
    @Test
    public void testValidKingSimpleMoveNegNeg() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardWithKingInCenter());
        Move move = new Move(new Position(2, 1), new Position(1, 0));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Tests if a king can make a simple move.
     */
    @Test
    public void testValidKingSimpleMovePosPos() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardWithKingInCenter());
        Move move = new Move(new Position(2, 1), new Position(3, 2));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Tests if a king can make a simple move.
     */
    @Test
    public void testValidKingSimpleMovePosNeg() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardWithKingInCenter());
        Move move = new Move(new Position(2, 1), new Position(3, 0));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Makes a board for testing a kings jump move.
     *
     * @return a board with many kings.
     */
    private Iterator<Row> getBoardWithFourKings() {
        int numOfRows = 4;
        int numOfCol = 4;
        Row[] rows = new Row[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            List<Space> currentRow = new ArrayList<>();
            for (int j = 0; j < numOfCol; j++) {
                if (i % 2 == j % 2) {
                    currentRow.add(new Space(Color.WHITE, null, j));
                } else {
                    if (i == 2) {
                        currentRow.add(new Space(Color.BLACK, new KingPiece(Color.RED, Piece.Type.KING), j));
                    }
                    else if (i == 1) {
                        currentRow.add(new Space(Color.BLACK, new KingPiece(Color.WHITE, Piece.Type.KING), j));
                    }
                    else {
                        currentRow.add(new Space(Color.BLACK, null, j));
                    }
                }
            }
            rows[i] = new Row(i, currentRow);
        }
        return Arrays.stream(rows).iterator();
    }

    /**
     * Tests that a king can make a single jump.
     */
    @Test
    public void testValidKingJumpMoveNegPos() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardWithFourKings());
        Move move = new Move(new Position(2, 1), new Position(0, 3));
        when(board.getPieceAtPosition(new Position(1, 2), Color.RED)).thenReturn(new KingPiece(Color.WHITE, Piece.Type.KING));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Tests that a king can make a single jump.
     */
    @Test
    public void testValidKingJumpMoveNegNeg() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardWithFourKings());
        Move move = new Move(new Position(2, 3), new Position(0, 1));
        when(board.getPieceAtPosition(new Position(1, 2), Color.RED)).thenReturn(new KingPiece(Color.WHITE, Piece.Type.KING));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Tests that a king can make a single jump.
     */
    @Test
    public void testValidKingJumpMovePosNeg() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.WHITE);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(true)).thenReturn(getBoardWithFourKings());
        Move move = new Move(new Position(1, 2), new Position(3, 0));
        when(board.getPieceAtPosition(new Position(2, 1), Color.WHITE)).thenReturn(new KingPiece(Color.RED, Piece.Type.KING));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Tests that a king can make a single jump.
     */
    @Test
    public void testValidKingJumpMovePosPos() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.WHITE);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(true)).thenReturn(getBoardWithFourKings());
        Move move = new Move(new Position(1, 0), new Position(3, 2));
        when(board.getPieceAtPosition(new Position(2, 1), Color.WHITE)).thenReturn(new KingPiece(Color.RED, Piece.Type.KING));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Makes a board for testing a single piece's multi-jump move.
     *
     * @return a board set up for a double jump.
     */
    private Iterator<Row> getBoardForSinglePieceMultiJump() {
        int numOfRows = 6;
        int numOfCol = 6;
        Row[] rows = new Row[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            List<Space> currentRow = new ArrayList<>();
            for (int j = 0; j < numOfCol; j++) {
                if (i % 2 == j % 2) {
                    currentRow.add(new Space(Color.WHITE, null, j));
                } else {
                    if (i == 5) {
                        currentRow.add(new Space(Color.BLACK, new KingPiece(Color.RED, Piece.Type.SINGLE), j));
                    }
                    else if (i == 4 || i == 2) {
                        currentRow.add(new Space(Color.BLACK, new KingPiece(Color.WHITE, Piece.Type.SINGLE), j));
                    }
                    else {
                        currentRow.add(new Space(Color.BLACK, null, j));
                    }
                }
            }
            rows[i] = new Row(i, currentRow);
        }
        return Arrays.stream(rows).iterator();
    }

    /**
     * Tests that single piece can do a multi jump
     */
    @Test
    public void testSinglePieceMultiJumpMove() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardForSinglePieceMultiJump());
        Move move = new Move(new Position(5, 0), new Position(3, 2));
        when(board.getPieceAtPosition(new Position(4, 1), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.SINGLE));
        when(board.getPieceAtPosition(new Position(2, 3), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.SINGLE));
        assertTrue(validateMove.isMoveValid(move));
    }

    /**
     * Tests if the jump can continued
     */
    @Test
    public void testCanJumpContinued() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardForSinglePieceMultiJump());
        Move move = new Move(new Position(5, 0), new Position(3, 2));
        when(board.getPieceAtPosition(new Position(4, 1), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.SINGLE));
        when(board.getPieceAtPosition(new Position(3, 2), Color.RED)).thenReturn(new SinglePiece(Color.RED, Piece.Type.SINGLE));
        when(board.getPieceAtPosition(new Position(2, 3), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.SINGLE));
        assertTrue(validateMove.canJumpContinue(move));
        when(board.getPieceAtPosition(new Position(4, 1), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.KING));
        when(board.getPieceAtPosition(new Position(3, 2), Color.RED)).thenReturn(new SinglePiece(Color.RED, Piece.Type.KING));
        when(board.getPieceAtPosition(new Position(2, 3), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.KING));
        assertTrue(validateMove.canJumpContinue(move));
        when(board.getPieceAtPosition(new Position(4, 1), Color.RED)).thenReturn(null);
        when(board.getPieceAtPosition(new Position(3, 2), Color.RED)).thenReturn(new SinglePiece(Color.RED, Piece.Type.KING));
        when(board.getPieceAtPosition(new Position(2, 3), Color.RED)).thenReturn(null);
        assertFalse(validateMove.canJumpContinue(move));
    }

    /**
     * Tests isJumpContinued in ValidateMove.
     */
    @Test
    public void testIsJumpContinued() {
        Board board = mock(Board.class);
        Game game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getActiveColor()).thenReturn(Color.RED);
        ValidateMove validateMove = new ValidateMove(game);
        when(board.iteratorByColor(false)).thenReturn(getBoardForSinglePieceMultiJump());
        Move move = new Move(new Position(5, 0), new Position(3, 2));
        Move move2 = new Move(new Position(3, 2), new Position(1, 4));
        when(board.getPieceAtPosition(new Position(4, 1), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.SINGLE));
        when(board.getPieceAtPosition(new Position(3, 2), Color.RED)).thenReturn(new SinglePiece(Color.RED, Piece.Type.SINGLE));
        when(board.getPieceAtPosition(new Position(2, 3), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.SINGLE));
        assertTrue(validateMove.isJumpContinued(move2, move));
        when(board.getPieceAtPosition(new Position(4, 1), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.KING));
        when(board.getPieceAtPosition(new Position(3, 2), Color.RED)).thenReturn(new SinglePiece(Color.RED, Piece.Type.KING));
        when(board.getPieceAtPosition(new Position(2, 3), Color.RED)).thenReturn(new SinglePiece(Color.WHITE, Piece.Type.KING));
        assertTrue(validateMove.isJumpContinued(move2, move));
        when(board.getPieceAtPosition(new Position(4, 1), Color.RED)).thenReturn(null);
        when(board.getPieceAtPosition(new Position(3, 2), Color.RED)).thenReturn(new SinglePiece(Color.RED, Piece.Type.KING));
        when(board.getPieceAtPosition(new Position(2, 3), Color.RED)).thenReturn(null);
        assertFalse(validateMove.isJumpContinued(move2, move));
    }
}

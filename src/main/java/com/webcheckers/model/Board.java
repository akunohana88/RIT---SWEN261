package com.webcheckers.model;

import java.util.*;
import java.lang.*;

/**
 * Represents a checkers board that the game is played on.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class Board {
    /**
     * Final number of rows in the board
     */
    public static final int ROWS = 8;
    /**
     * Final number of columns in the board
     */
    public static final int COLS = 8;
    /** Fields */
    private final Row[] rows;
    private final Stack<Row[]> traceBack;

    /**
     * Constructs new board.
     */
    public Board() {
        rows = new Row[ROWS];
        for (int i = 0; i < ROWS; i++) {
            List<Space> currentRow = new ArrayList<>();
            for (int j = 0; j < COLS; j++) {
                if (i % 2 == j % 2) {
                    currentRow.add(new Space(Color.WHITE, null, j));
                } else {
                    if (i < 3) {
                        currentRow.add(new Space(Color.BLACK, new SinglePiece(Color.RED, Piece.Type.SINGLE), j));
                    }
                    else if (i > 4) {
                        currentRow.add(new Space(Color.BLACK, new SinglePiece(Color.WHITE, Piece.Type.SINGLE), j));
                    }
                    else {
                        currentRow.add(new Space(Color.BLACK, null, j));
                    }
                }
            }
            rows[i] = new Row(i, currentRow);
        }
        traceBack = new Stack<>();
    }

    /**
     * Makes a move on the board.
     *
     * @param move the move that is being made.
     * @param activeColor the color from whose perspective the move is being made from.
     */
    public void makeMove(Move move, Color activeColor) {
        Row[] copy = new Row[rows.length];
        for(int i = 0; i < rows.length; i++) {
            copy[i] = rows[i].copyRow();
        }
        traceBack.add(copy);

        if(activeColor != Color.WHITE) {
            move = move.getOpposite();
        }
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCell();
        if(Math.abs(endRow-startRow) > 1){
            rows[(endRow + startRow) / 2].changeSpaceOccupant((endCol + startCol) / 2, null);
        }
        Piece movingPiece = rows[startRow].getPieceAtIndex(startCol);
        rows[startRow].changeSpaceOccupant(startCol, null);
        if (endRow == 0 || endRow == 7) {
            if (movingPiece.getType() == Piece.Type.SINGLE) {
                rows[endRow].changeSpaceOccupant(endCol, new KingPiece(movingPiece.getColor(), Piece.Type.KING));
            }
            else {
                rows[endRow].changeSpaceOccupant(endCol, movingPiece);
            }
        }
        else {
            rows[endRow].changeSpaceOccupant(endCol, movingPiece);
        }
    }

    /**
     * Undoes a move
     */
    public void undoMove() {
        Row[] old = traceBack.pop();
        System.arraycopy(old, 0, rows, 0, rows.length);
    }

    /**
     * Gets the piece on a space from the perspective of the player.
     *
     * @param position the position that is being checked.
     * @param perspective the perspective from which the position is being given.
     * @return the piece at the position.
     */
    public Piece getPieceAtPosition(Position position, Color perspective) {
        if(position == null || perspective == null) {
            return null;
        }
        if(perspective.equals(Color.WHITE)) {
            return rows[position.getRow()].getPieceAtIndex(position.getCell());
        }
        else {
            return rows[7 - position.getRow()].getPieceAtIndex(7 - position.getCell());
        }
    }

    /**
     *  An alternate iterator method that changes the direction of the board
     *  depending on the color of the current user.
     *
     * @param colorWhite True if the player is white, false if they are red

     * @return an iterator of the rows in this board class.
     */
    public Iterator<Row> iteratorByColor(Boolean colorWhite) {
        if(colorWhite) {
            return Arrays.stream(rows).iterator();
        }
        else {
            Row[] flippedBoard = new Row[ROWS];
            for(int i = 0; i < ROWS; i++) {
                flippedBoard[i] = rows[(ROWS-1)-i].flipRow();
            }
            return Arrays.stream(flippedBoard).iterator();
        }
    }
}

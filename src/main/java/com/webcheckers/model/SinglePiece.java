package com.webcheckers.model;

/**
 * The single piece class
 *
 * @author Hana Ho <hth2539@rit.edu>
 */
public class SinglePiece extends Piece{

    /**
     * Constructs the piece.
     *
     * @param color  piece's color
     * @param status piece's status
     */
    public SinglePiece(Color color, Type status) {
        super(color, status);
    }

    /**
     * Checks if piece can make simple move
     *
     * @param move move to be checked
     * @return if move is valid
     */
    @Override
    public boolean isValidSimpleMove(Move move) {
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCell();
        System.out.println(startRow +":"+ startCol + ", " + endRow + ":" + endCol);
        return startRow - endRow == 1 && startCol - endCol == 1;
    }

    /**
     * Checks if piece can make single jump
     *
     * @param move move to be checked
     * @return if move is valid
     */
    @Override
    public boolean isValidSingleJump(Move move) {
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCell();
        return startRow - endRow == 2 && startCol - endCol == 2;
    }
}

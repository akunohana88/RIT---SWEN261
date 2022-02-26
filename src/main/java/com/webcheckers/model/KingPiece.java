package com.webcheckers.model;

/**
 * The king piece class
 *
 * @author Hana Ho <hth2539@rit.edu>
 */
public class KingPiece extends Piece{

    /**
     * Constructs the piece.
     *
     * @param color  piece's color.
     * @param status piece's status.
     */
    public KingPiece(Color color, Type status) {
        super(color, status);
    }

    /**
     * Checks if piece can make simple move.
     *
     * @param move move to be checked.
     * @return if move is valid.
     */
    @Override
    public boolean isValidSimpleMove(Move move) {
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCell();

        return Math.abs(startRow - endRow) == 1 && Math.abs(startCol - endCol) == 1;
    }

    /**
     * Checks if piece can make single jump.
     *
     * @param move move to be checked.
     * @return if move is valid.
     */
    @Override
    public boolean isValidSingleJump(Move move) {
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCell();

        return Math.abs(startRow - endRow) == 2 && Math.abs(startCol - endCol) == 2;
    }

}

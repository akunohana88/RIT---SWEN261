package com.webcheckers.model;

/**
 * The piece class
 *
 * @author Hana Ho <hth2539@rit.edu>
 */
public abstract class Piece {
    //fields
    private final Color color;
    private Type status;

    /**
     * Type the pieces can have.
     */
    public enum Type{
        SINGLE, KING
    }

    /**
     * Constructs the piece.
     * @param color piece's color
     * @param status piece's status
     */
    public Piece(Color color, Type status) {
        this.color = color;
        this.status = status;
    }

    /**
     * Gets color of piece.
     * @return piece's color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Gets status of piece.
     * @return piece's status
     */
    public Type getType() {
        return this.status;
    }


    abstract boolean isValidSimpleMove(Move move);

    abstract boolean isValidSingleJump(Move move);
}

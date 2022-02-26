package com.webcheckers.model;

/**
 * Represents a space, or square, on a checkers board.
 *
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class Space {

    /**
     * Fields.
     */
    private final Piece piece;
    private final Color color;
    private final int cellIdx;

    /**
     * Constructs the square for the board.
     *
     * @param color color the square has.
     * @param piece whether there is a piece or not.
     */
    public Space(Color color, Piece piece, int cellIdx) {
        this.color = color;
        this.piece = piece;
        this.cellIdx = cellIdx;
    }


    /**
     * Adds or removes the occupant on the square.
     *
     * @param occupant piece or empty.
     * @return new square.
     */
    public Space changeOccupant(Piece occupant) {
        return new Space(color, occupant, cellIdx);
    }

    /**
     * Returns the piece on the space.
     *
     * @return the piece on that space
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Returns whether or not a piece can be placed on the square.
     *
     * @return if piece can be placed on space
     */
    public boolean isValid() {
        return color == Color.BLACK && piece == null;
    }

    /**
     * Returns the index of the space in relation to where it is in the row.
     *
     * @return index of space on the board from 0-7
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * Returns the color of the space.
     *
     * @return the color of the space.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the pieces color or null if there is no piece.
     *
     * @return the pieces color or null if there is no piece.
     */
    public Color getPieceColor() {
        if(piece != null) {
            return piece.getColor();
        }
        return null;
    }

    /**
     * Returns the mirrored version of the space.
     *
     * @return the mirrored version of the space.
     */
    public Space getFlippedSpace() {
        return new Space(this.color, this.piece, 7 - this.cellIdx);
    }
}

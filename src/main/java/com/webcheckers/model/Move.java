package com.webcheckers.model;

import java.util.Objects;

/**
 * Move class
 *
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class Move {
    private final Position start;
    private final Position end;

    /**
     * Constructor for move
     *
     * @param start start position
     * @param end end position
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return start position
     */
    public Position getStart() {
        return this.start;
    }

    /**
     * @return end position
     */
    public Position getEnd() {
        return this.end;
    }

    /**
     * Flips which direction the move is made. (Used in Game for backupMove.)
     *
     * @return a new move with the end at the start and the start at the end.
     */
//    public Move flipDirection() {
//        return new Move(end, start);
//    }

    //Is this still used in backupMove

    /**
     * Gets move from the other side of the board
     *
     * @return move from other side of the board
     */
    public Move getOpposite() {
        return new Move(start.getOtherPosition(), end.getOtherPosition());
    }

    /**
     * Checks if simple move is valid
     *
     * @return whether move is valid
     */
    public boolean isSimpleMove() {
        return start.getRow() - end.getRow() == 1 && Math.abs(start.getCell() - end.getCell()) == 1;
    }

    /**
     * Checks if single jump is valid
     *
     * @return whether jump is valid
     */
    public boolean isJumpMove() {
        return Math.abs(start.getRow() - end.getRow()) == 2 && Math.abs(start.getCell() - end.getCell()) == 2;
    }

    /**
     * Returns an integer hash code for the move object.
     *
     * @return an integer hash code for the move object.
     */
    @Override
    public int hashCode() {
        return start.hashCode() + end.hashCode();
    }

    /**
     * Test if the o parameter is a move object equal to this instance.
     *
     * @param o an object that could be a move object.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(start, move.start) && Objects.equals(end, move.end);
    }

    /**
     * Returns a string representation of the move object.
     *
     * @return a string representation of the move object.
     */
    @Override
    public String toString() {
        return "Move{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}

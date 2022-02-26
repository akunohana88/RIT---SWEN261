package com.webcheckers.model;

import java.util.Objects;

/**
 * Position class
 *
 * @author Hana Ho <hth2539@rit.edu>
 */
public class Position {

    private final int row;
    private final int cell;

    /**
     * Constructor for position
     *
     * @param row row from 0-7
     * @param cell cell from 0-7
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Creates a new position only if the parameters are correct.
     *
     * @param row an int of any size.
     * @param cell an int of any size.
     * @return a new position with row and cell or null if the inputs are wrong.
     */
    public static Position safeCreatePosition(int row, int cell) {
        if(row < 8 && row >= 0 && cell < 8 && cell >= 0) {
            return new Position(row, cell);
        }
        return null;
    }

    /**
     * Gets the other position of the board from the opposite side
     *
     * @return position from other side of the board
     */
    public Position getOtherPosition() {
        return new Position(7 - row,   7 - cell);
    }

    /**
     * @return row index of position
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return cell(column) index of position
     */
    public int getCell() {
        return this.cell;
    }

    /**
     * Makes a hash code for the position object.
     *
     * @return an integer hash code.
     */
      @Override
      public int hashCode() {
       return Integer.hashCode(row) + Integer.hashCode(cell);
     }


    /**
     * Tests if the parameter o is a position object equal to this one.
     *
     * @param o an object that could be a position.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && cell == position.cell;
    }

    /**
     * Returns a string representation of the position object.
     *
     * @return a string representation of the position object.
     */
    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", cell=" + cell +
                '}';
    }
}

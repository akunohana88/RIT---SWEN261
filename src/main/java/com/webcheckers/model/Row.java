package com.webcheckers.model;

import java.util.*;
import java.lang.*;

/**
 * Represents a row on a checkers board.
 *
 * @author Hana Ho <hth2539@rit.edu>
 */
public class Row implements Iterable<Space> {
    private int index;
    private final List<Space> spaces;

    /**
     * Creates a new row.
     *
     * @param index row number.
     * @param spaces spaces on the row.
     */
    public Row(int index, List<Space> spaces) {
        this.index = index;
        this.spaces = spaces;
    }

    /**
     * @return the row index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return spaces in the row
     */
    public List<Space> getSpaces() {
        return spaces;
    }

    /**
     * @return the flipped row.
     */
    public Row flipRow() {
        List<Space> squaresCopy = new LinkedList<>();
        for(int i = 0; i < 8; i++) {
            squaresCopy.add(spaces.get(7-i).getFlippedSpace());
        }
        return new Row(7-index, squaresCopy);
    }

    /**
     * Changes the occupant of the column to the provided piece.
     *
     * @param column the index that is being changed.
     * @param occupant the piece that the occupant in being changed to.
     */
    public void changeSpaceOccupant(int column, Piece occupant) {
        spaces.set(column, spaces.get(column).changeOccupant(occupant));
    }

    /**
     * @return copy of the current row
     */
    public Row copyRow(){
        List<Space> squaresCopy = new LinkedList<>();
        for(int i = 0; i < 8; i++) {
            squaresCopy.add(spaces.get(i));
        }
        return new Row(index, squaresCopy);
    }

    /**
     * Gets the piece at the given column.
     *
     * @param column the column or index that the piece is at.
     * @return the desired piece.
     */
    public Piece getPieceAtIndex(int column) {
        return spaces.get(column).getPiece();
    }

    /**
     * Iterates the spaces on the row.
     *
     * @return iterated spaces.
     */
    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}

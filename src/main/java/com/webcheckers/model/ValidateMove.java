package com.webcheckers.model;

import java.util.*;

import static com.webcheckers.model.Piece.Type.KING;
import static com.webcheckers.model.Piece.Type.SINGLE;

/**
 * Gets all the moves that a player can make.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class ValidateMove {
    private final Board board;
    private final Color activeColor;

    /**
     * Constructor for the ValidateMove class.
     *
     * @param game the game that is used to get the fields.
     */
    public ValidateMove(Game game) {
        this.board = game.getBoard();
        this.activeColor = game.getActiveColor();
    }

    /**
     * Checks the moves that can be made by the piece at the start position.
     * Precondition: there must be a piece at the start position because it is
     * assumed.
     *
     * @param start the starting position from which a piece is moving.
     * @return an hash set of all simple moves that can be made.
     */
    private HashSet<Move> singleSimpleMoves(Position start) {
        HashSet<Move> singleMoves = new HashSet<>();
        Position moveNegPos = Position.safeCreatePosition(start.getRow() - 1, start.getCell() + 1);
        Position moveNegNeg = Position.safeCreatePosition(start.getRow() - 1, start.getCell() - 1);
        if(moveNegPos != null && board.getPieceAtPosition(moveNegPos, activeColor) == null) {
            singleMoves.add(new Move(start, moveNegPos));
        }
        if(moveNegNeg != null && board.getPieceAtPosition(moveNegNeg, activeColor) == null) {
            singleMoves.add(new Move(start, moveNegNeg));
        }
        return singleMoves;
    }

    /**
     * Checks the jump moves that can be made by the piece at the start position.
     * Precondition: there must be a piece at the start position because it is
     * assumed.
     *
     * @param start the starting position from which a piece is moving.
     * @return an hash set of all jump moves that can be made.
     */
    private HashSet<Move> singleJumpMoves(Position start) {
        HashSet<Move> singleMoves = new HashSet<>();
        Position moveNegPos = Position.safeCreatePosition(start.getRow() - 1, start.getCell() + 1);
        Piece pieceNegPos = board.getPieceAtPosition(moveNegPos, activeColor);
        Position moveNegNeg = Position.safeCreatePosition(start.getRow() - 1, start.getCell() - 1);
        Piece pieceNegNeg = board.getPieceAtPosition(moveNegNeg, activeColor);

        if(moveNegPos != null && pieceNegPos != null && pieceNegPos.getColor() != activeColor) {
            Position jumpNegPos = Position.safeCreatePosition(start.getRow() - 2, start.getCell() + 2);
            if(jumpNegPos != null && board.getPieceAtPosition(jumpNegPos, activeColor) == null) {
                singleMoves.add(new Move(start, jumpNegPos));
            }
        }
        if(moveNegNeg != null && pieceNegNeg != null && pieceNegNeg.getColor() != activeColor) {
            Position jumpNegNeg = Position.safeCreatePosition(start.getRow() - 2, start.getCell() - 2);
            if(jumpNegNeg != null && board.getPieceAtPosition(jumpNegNeg, activeColor) == null) {
                singleMoves.add(new Move(start, jumpNegNeg));
            }
        }
        return singleMoves;
    }

    /**
     * Checks the moves that can be made by the piece at the start position.
     * Precondition: there must be a piece at the start position because it is
     * assumed.
     *
     * @param start the starting position from which a piece is moving.
     * @return an hash set of all simple moves that can be made.
     */
    private HashSet<Move> kingSimpleMoves(Position start) {
        HashSet<Move> kingMoves = new HashSet<>();
        Position movePosPos = Position.safeCreatePosition(start.getRow() + 1, start.getCell() + 1);
        Position movePosNeg = Position.safeCreatePosition(start.getRow() + 1, start.getCell() - 1);
        Position moveNegPos = Position.safeCreatePosition(start.getRow() - 1, start.getCell() + 1);
        Position moveNegNeg = Position.safeCreatePosition(start.getRow() - 1, start.getCell() - 1);
        if(movePosPos != null && board.getPieceAtPosition(movePosPos, activeColor) == null) {
            kingMoves.add(new Move(start, movePosPos));
        }
        if(movePosNeg != null && board.getPieceAtPosition(movePosNeg, activeColor) == null) {
            kingMoves.add(new Move(start, movePosNeg));
        }
        if(moveNegPos != null && board.getPieceAtPosition(moveNegPos, activeColor) == null) {
            kingMoves.add(new Move(start, moveNegPos));
        }
        if(moveNegNeg != null && board.getPieceAtPosition(moveNegNeg, activeColor) == null) {
            kingMoves.add(new Move(start, moveNegNeg));
        }
        return kingMoves;
    }

    /**
     * Checks the jump moves that can be made by the piece at the start position.
     * Precondition: there must be a piece at the start position because it is
     * assumed.
     *
     * @param start the starting position from which a piece is moving.
     * @return an hash set of all jump moves that can be made.
     */
    private HashSet<Move> kingJumpMoves(Position start) {
        HashSet<Move> kingMoves = new HashSet<>();
        Position movePosPos = Position.safeCreatePosition(start.getRow() + 1, start.getCell() + 1);
        Piece piecePosPos = board.getPieceAtPosition(movePosPos, activeColor);
        Position movePosNeg = Position.safeCreatePosition(start.getRow() + 1, start.getCell() - 1);
        Piece piecePosNeg = board.getPieceAtPosition(movePosNeg, activeColor);
        Position moveNegPos = Position.safeCreatePosition(start.getRow() - 1, start.getCell() + 1);
        Piece pieceNegPos = board.getPieceAtPosition(moveNegPos, activeColor);
        Position moveNegNeg = Position.safeCreatePosition(start.getRow() - 1, start.getCell() - 1);
        Piece pieceNegNeg = board.getPieceAtPosition(moveNegNeg, activeColor);
        if(movePosPos != null && piecePosPos != null && piecePosPos.getColor() != activeColor) {
            Position jumpPosPos = Position.safeCreatePosition(start.getRow() + 2, start.getCell() + 2);
            if(jumpPosPos != null && board.getPieceAtPosition(jumpPosPos, activeColor) == null) {
                kingMoves.add(new Move(start, jumpPosPos));
            }
        }
        if(movePosNeg != null && piecePosNeg != null && piecePosNeg.getColor() != activeColor) {
            Position jumpPosNeg = Position.safeCreatePosition(start.getRow() + 2, start.getCell() - 2);
            if(jumpPosNeg != null && board.getPieceAtPosition(jumpPosNeg, activeColor) == null) {
                kingMoves.add(new Move(start, jumpPosNeg));
            }
        }
        if(moveNegPos != null && pieceNegPos != null && pieceNegPos.getColor() != activeColor) {
            Position jumpNegPos = Position.safeCreatePosition(start.getRow() - 2, start.getCell() + 2);
            if(jumpNegPos != null && board.getPieceAtPosition(jumpNegPos, activeColor) == null) {
                kingMoves.add(new Move(start, jumpNegPos));
            }
        }
        if(moveNegNeg != null && pieceNegNeg != null && pieceNegNeg.getColor() != activeColor) {
            Position jumpNegNeg = Position.safeCreatePosition(start.getRow() - 2, start.getCell() - 2);
            if(jumpNegNeg != null && board.getPieceAtPosition(jumpNegNeg, activeColor) == null) {
                kingMoves.add(new Move(start, jumpNegNeg));
            }
        }
        return kingMoves;
    }

    /**
     * Uses all the helper methods to get a list of all possible moves that a player can make.
     *
     * @return a  list of all possible moves that a player can make.
     */
    public HashSet<Move> possibleMoves() {
        HashSet<Move> simpleMoves = new HashSet<>();
        HashSet<Move> jumpMoves = new HashSet<>();
        Iterator<Row> boardItr = board.iteratorByColor(activeColor.equals(Color.WHITE));
        while(boardItr.hasNext()) {
            Row row = boardItr.next();
            for(Space space : row.getSpaces()) {
                Piece piece = space.getPiece();
                if(piece != null && piece.getColor() == activeColor) {
                    if(piece.getType() == SINGLE) {
                        simpleMoves.addAll(singleSimpleMoves(new Position(row.getIndex(), space.getCellIdx())));
                        jumpMoves.addAll(singleJumpMoves(new Position(row.getIndex(), space.getCellIdx())));
                    }
                    else if(piece.getType() == KING) {
                        simpleMoves.addAll(kingSimpleMoves(new Position(row.getIndex(), space.getCellIdx())));
                        jumpMoves.addAll(kingJumpMoves(new Position(row.getIndex(), space.getCellIdx())));
                    }
                }
            }
        }
        if(jumpMoves.isEmpty()) {
            return simpleMoves;
        }
        else {
            return jumpMoves;
        }
    }

    /**
     * Validates a move and returns true or false depending on if it is valid or not.
     *
     * @param move a move that is being tested for validation.
     * @return true or false.
     */
    public boolean isMoveValid(Move move){
        HashSet<Move> whatToDo = possibleMoves();
        return whatToDo.contains(move);
    }

    /**
     * Checks if you can jump again.
     *
     * @param lastMove a move that is being tested for validation.
     * @return true or false.
     */
    public boolean canJumpContinue(Move lastMove){
        Position endOfLastMove = lastMove.getEnd();
        if(board.getPieceAtPosition(endOfLastMove, activeColor).getType().equals(SINGLE)) {
            HashSet<Move> whatToDo = singleJumpMoves(endOfLastMove);
            return !whatToDo.isEmpty();
        }
        if(board.getPieceAtPosition(endOfLastMove, activeColor).getType().equals(KING)) {
            HashSet<Move> whatToDo = kingJumpMoves(endOfLastMove);
            return !whatToDo.isEmpty();
        }
        return false;
    }

    /**
     * Tests if the jump can be continued off the first move.
     *
     * @param next the second move that will be made.
     * @param start the first move that was made.
     * @return true or false depending on if the end of the first move matches the
     * start of the second move.
     */
    public boolean isJumpContinued(Move next, Move start){
        Position endOfLastMove = start.getEnd();
        if(board.getPieceAtPosition(endOfLastMove, activeColor).getType().equals(SINGLE)) {
            HashSet<Move> whatToDo = singleJumpMoves(endOfLastMove);
            return whatToDo.contains(next);
        }
        if(board.getPieceAtPosition(endOfLastMove, activeColor).getType().equals(KING)) {
            HashSet<Move> whatToDo = kingJumpMoves(endOfLastMove);
            return whatToDo.contains(next);
        }
        return false;
    }
}
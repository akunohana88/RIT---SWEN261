package com.webcheckers.model;

import java.util.LinkedList;

/**
 * Creates the game that is to be played.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class Game {
    private final Board board;
    private final Player redPlayer;
    private final Player whitePlayer;
    private final String gameID;
    private final LinkedList<Move> currentMoveInProgress;
    private Color activeColor;
    private Player winner = null;
    private String whyGameOver = null;

    /** Static Strings */
    public static final String VALID_MOVE = "What a move!";
    public static final String JUMP_AGAIN = "You should jump again!";
    public static final String UNDID_MOVE = "You undid it!";
    public static final String SUBMITTED = "Turn submitted.";
    public static final String ERROR_INVALID_MOVE = "ERROR: You cannot move there.";
    public static final String ERROR_NO_MOVE_TO_UNDO = "ERROR: How do you undo a move you never made?";
    public static final String ERROR_MORE_JUMPS = "ERROR: You still have more jumps!";
    public static final String ERROR_NO_MOVE_SUBMITTED = "ERROR: No move has been made.";

    /**
     * Constructs an instance of game.
     *
     * @param startingPlayer a player that started the game.
     * @param secondPlayer the player selected and thus assigned white.
     */
    public Game(Player startingPlayer, Player secondPlayer) {
        this.board = new Board();
        this.redPlayer = startingPlayer;
        startingPlayer.setPlayerColor(Color.RED);
        this.whitePlayer = secondPlayer;
        secondPlayer.setPlayerColor(Color.WHITE);
        this.gameID = startingPlayer.getName() + secondPlayer.getName();
        this.activeColor = Color.RED;
        this.currentMoveInProgress = new LinkedList<>();

    }

    /**
     * Constructs an instance of game for testing.
     *
     * @param startingPlayer a player that started the game.
     * @param secondPlayer the player selected and thus assigned white.
     */
    public Game(Player startingPlayer, Player secondPlayer, Board board) {
        this.board = board;
        this.redPlayer = startingPlayer;
        startingPlayer.setPlayerColor(Color.RED);
        this.whitePlayer = secondPlayer;
        secondPlayer.setPlayerColor(Color.WHITE);
        this.gameID = startingPlayer.getName() + secondPlayer.getName();
        this.activeColor = Color.RED;
        this.currentMoveInProgress = new LinkedList<>();

    }

    /**
     * Gets the red player.
     *
     * @return the red player.
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Gets the white player.
     *
     * @return the white player.
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Gets color of player.
     *
     * @return the active color of the game.
     */
    public Color getActiveColor() {
        return activeColor;
    }

    /**
     * @param current current player
     * @return player's opponent
     */
    public Player getOpponent(Player current) {
        if(current.equals(whitePlayer)){
            return redPlayer;
        }
        else{
            return whitePlayer;
        }
    }

    /**
     * Gets the reason the game is over
     *
     * @return the reason
     */
    public String getWhyGameOver(){return whyGameOver;}


    /**
     * Sets the reason the game is over
     *
     * @param message Why the game is over
     */
    public void setWhyGameOver(String message){
        whyGameOver = message;
    }

    /**
     * Swaps activeColor
     */
    public void swapActiveColor() {
        if(activeColor.equals(Color.RED)){
            activeColor = Color.WHITE;
        }
        else {
            activeColor = Color.RED;
        }
    }

    /**
     * Gets the board.
     *
     * @return board a board based on the color passed in.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Undoes the last move.
     *
     * @return a string with the result message.
     */
    public String backupMove() {
        if(!currentMoveInProgress.isEmpty()) {
            currentMoveInProgress.removeLast();
            board.undoMove();
            return UNDID_MOVE;
        }
        return ERROR_NO_MOVE_TO_UNDO;
    }
    /**
     * Calls the makeMove method in board to change it.
     */
    public String submitTurn() {
        ValidateMove validator = new ValidateMove(this);
        boolean canJumpContinue = false;
        if(!currentMoveInProgress.isEmpty() && currentMoveInProgress.getLast().isJumpMove()) {
            canJumpContinue = validator.canJumpContinue(currentMoveInProgress.getLast());
        }
        if(!currentMoveInProgress.isEmpty() && !canJumpContinue) {
            swapActiveColor();
            currentMoveInProgress.clear();
            return SUBMITTED;
        }
        if(canJumpContinue) {
            return ERROR_MORE_JUMPS;
        }
        return ERROR_NO_MOVE_SUBMITTED;
    }

    /**
     * Sets the winner to the parameter of the player passed in if it is
     * one of the players in the game.
     *
     * @param winner a Player who has won the game.
     */
    public boolean declareWinner(Player winner) {
        if((winner.equals(redPlayer) || winner.equals(whitePlayer)) && this.winner == null) {
            this.winner = winner;
            return true;
        }
        return false;
    }

    /**
     * @return is there a winner
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * @return if player has any valid moves
     */
    public boolean noMoreMovesLeft() {
        if(new ValidateMove(this).possibleMoves().isEmpty()) {
            setWhyGameOver("No moves remaining");
            if (activeColor.equals(Color.RED)) {
                return declareWinner(getOpponent(getRedPlayer()));
            }
            else {
                return declareWinner(getOpponent(getWhitePlayer()));
            }
        }
        return false;
    }

    /**
     * Lets the player take his or her turn.
     *
     * @param move a move that the player is making.
     * @return a string with the results of the attempt to make the move.
     */
    public String takeTurn(Move move) {
        ValidateMove validator = new ValidateMove(this);
        if(currentMoveInProgress.isEmpty()) {
            boolean isValid = validator.isMoveValid(move);
            if(isValid) {
                currentMoveInProgress.add(move);
                board.makeMove(move, activeColor);
                if(move.isJumpMove() && validator.canJumpContinue(move)) {
                    return JUMP_AGAIN;
                }
                else {
                    return VALID_MOVE;
                }
            }
            else {
                return ERROR_INVALID_MOVE;
            }
        }
        else {
            boolean partOfJump = false;
            if(currentMoveInProgress.getLast().isJumpMove()) {
                    partOfJump = validator.isJumpContinued(move, currentMoveInProgress.getLast());
            }
            if(partOfJump) {
                boolean isValid = validator.canJumpContinue(currentMoveInProgress.getLast());
                if (isValid) {
                    currentMoveInProgress.add(move);
                    board.makeMove(move, activeColor);
                    if (validator.canJumpContinue(move)) {
                        return JUMP_AGAIN;
                    }
                    return VALID_MOVE;
                }
                else {
                    return ERROR_INVALID_MOVE;
                }
            }
            else {
                return ERROR_INVALID_MOVE;
            }
        }
    }
}

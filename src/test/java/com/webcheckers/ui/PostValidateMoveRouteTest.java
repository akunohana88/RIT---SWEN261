package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Game.*;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;


/**
 * The unit test suite for the PostValidateMove component.
 *
 * @author Matthew Simoni
 */
@Tag("UI-tier")
public class PostValidateMoveRouteTest {

    //Friendly objects
    private Gson gson;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Game game;
    private Player opponent;
    private Player currentPlayer;

    //Component under test
    private PostValidateMoveRoute CuT;

    //Mock data
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;
    private GetGameRoute GGR;


    /**
     * Starts the mock session and handles the mock versions that can be referenced later
     */
    @BeforeEach
    public void setup() {
        //mock versions
        request = mock(Request.class);
        session = mock(Session.class);

        //On call then return session
        when(request.session()).thenReturn(session);

        //mock versions
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);


        //Creates a player lobby and player to test login
        playerLobby = mock(PlayerLobby.class);
        gson = new Gson();
        gameCenter = mock(GameCenter.class);
        currentPlayer = new Player("user");
        opponent = new Player("The Enemy");

        game = new Game(currentPlayer, opponent);
        currentPlayer.setGame(game);
        //when(currentPlayer.getGame()).thenReturn(game);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        GGR = new GetGameRoute(gson, playerLobby, gameCenter, templateEngine);
        CuT = new PostValidateMoveRoute(gson , gameCenter, templateEngine);
    }

    /**
     * Test to see that the valid move was displayed correctly.
     **/
    @Test
    public void makingAValidMove() {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        when(playerLobby.getPlayer("The Enemy")).thenReturn(opponent);
        when(request.queryParams("playerNames")).thenReturn("The Enemy");
        Move madeMove = new Move(new Position(5, 0), new Position(4,1));
        when(request.queryParams("actionData")).thenReturn(gson.toJson(madeMove));

        GGR.handle(request,response);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        assertEquals(Game.VALID_MOVE, message.getText(), "The move wasn't made properly");
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, currentPlayer);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }

    /**
     * Test if an invalid move displays correctly.
     */
    @Test
    public void testInvalidMove() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        when(playerLobby.getPlayer("The Enemy")).thenReturn(opponent);
        when(request.queryParams("playerNames")).thenReturn("The Enemy");
        game.takeTurn(new Move(new Position(5, 2), new Position(4,3)));
        game.submitTurn();
        game.takeTurn(new Move(new Position(5, 2), new Position(4,3)));
        game.submitTurn();
        Move madeMove = new Move(new Position(5, 0), new Position(4,1));
        when(request.queryParams("actionData")).thenReturn(gson.toJson(madeMove));

        GGR.handle(request,response);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        assertEquals(Game.ERROR_INVALID_MOVE.substring(7), message.getText(), "The move was made properly");
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, currentPlayer);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }
}

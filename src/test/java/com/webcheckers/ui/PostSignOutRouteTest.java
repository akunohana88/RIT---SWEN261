package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.Request;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the PostSignInRoute class in the UI-Tier.
 *
 * @author Nicholas Deary
 */
@Tag("UI-Tier")
public class PostSignOutRouteTest {
    /** Friendly Objects */
    private PlayerLobby playerLobby;
    private Player player;

    /** Component Under Test */
    private PostSignOutRoute CuT;

    /** Mock Data */
    private spark.Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;

    /**
     * Executes before each test.
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

        playerLobby = new PlayerLobby();
        player = playerLobby.newPlayer("Hello There Stranger");

        CuT = new PostSignOutRoute(playerLobby, templateEngine);
    }

    /**
     * Tests that the sign out works.
     */
    @Test
    public void cleanSignOut() {
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(player);
        when(request.queryParams(PostSignInRoute.USERNAME)).thenReturn("uniquePlayerName");
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        assertFalse(playerLobby.getPlayer("Hello There Stranger").isOnline());
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(PostSignOutRoute.TITLE, PostSignOutRoute.TITLE_MESSAGE);
        testHelper.assertViewName(PostSignInRoute.HOME_VIEW);
    }
}

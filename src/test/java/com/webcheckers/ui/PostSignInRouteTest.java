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
 * Tests the PostSignInRoute class in the UI-Tier.
 *
 * @author Nicholas Deary
 */
@Tag("UI-Tier")
public class PostSignInRouteTest {
    /** Friendly Objects */
    private PlayerLobby playerLobby;

    /** Component Under Test */
    private PostSignInRoute CuT;

    /** Mock Data */
    private Request request;
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

        CuT = new PostSignInRoute(playerLobby, templateEngine);
    }

    /**
     * Tests that valid sign ins work correctly.
     */
    @Test
    public void validSignIn() {
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(null);
        when(request.queryParams(PostSignInRoute.USERNAME)).thenReturn("uniquePlayerName");
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome");
        testHelper.assertViewModelAttribute("signedIn", "Yes");
        testHelper.assertViewModelAttribute(PostSignInRoute.USERNAME, "uniquePlayerName");
        testHelper.assertViewName(PostSignInRoute.HOME_VIEW);
        assertNotNull(playerLobby.getPlayer("uniquePlayerName"));
    }

    /**
     * Tests that an invalid sign in is rejected.
     */
    @Test
    public void invalidSignIn() {
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(null);
        when(request.queryParams(PostSignInRoute.USERNAME)).thenReturn("uniquePlayerName");
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        playerLobby.newPlayer("uniquePlayerName");
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome");
        testHelper.assertViewModelAttribute("signedIn", "No");
        testHelper.assertViewName(PostSignInRoute.SIGNIN_VIEW);
    }

    /**
     * Tests what happens when a user attempts to sign in when already signed in.
     */
    @Test
    public void alreadySignedIn() {
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(new Player("uniquePlayerName"));
        when(request.queryParams(PostSignInRoute.USERNAME)).thenReturn("uniquePlayerName");
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        playerLobby.newPlayer("uniquePlayerName");
        assertThrows(HaltException.class, () -> {
            CuT.handle(request,response);
        });
    }
}

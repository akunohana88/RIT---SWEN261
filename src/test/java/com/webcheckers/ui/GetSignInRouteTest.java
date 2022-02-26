package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.*;

/**
 * Test the GetSignInRouteTest class in the UI-Tier.
 *
 * @author Hana Ho
 */
@Tag("UI-tier")
public class GetSignInRouteTest {
    /** Fields */
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;
    private GetSignInRoute getSignInRoute;

    /**
     * Executes before each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        getSignInRoute = new GetSignInRoute(engine);
    }

    /**
     * Makes sure that page doesn't redirect when user hasn't signed in
     */
    @Test
    public void notSignedIn() {
        getSignInRoute.handle(request, response);
        verify(response, never()).redirect(any());
    }
}

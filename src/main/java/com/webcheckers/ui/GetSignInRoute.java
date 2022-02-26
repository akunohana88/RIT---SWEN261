package com.webcheckers.ui;

import java.util.Objects;
import java.util.HashMap;

import com.webcheckers.util.Message;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;


/**
 * The UI Controller to GET the sign in page.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class GetSignInRoute implements Route{
    /**
     * Is the title attribute.
     */
    static final String TITLE_ATTR = "title";
    /**
     * Holds the title message, for the title attribute.
     */
    static final String TITLE = "Sign In";
    /**
     * Is the message attribute.
     */
    static final String MESSAGE = "message";
    /**
     * Holds the prompt message, for the message attribute.
     */
    private static final Message PROMPT_MSG = Message.info("Enter a username: ");
    /**
     * Holds the to return to.
     */
    static final String VIEW_NAME = "signin.ftl";
    /**
     * Holds the current templateEngine.
     */
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine  the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers Sign In page.
     *
     * @param request the HTTP request.
     * @param response the HTTP response.
     * @return the rendered HTML for the Sign In page.
     */
    @Override
    public String handle(Request request, Response response) {

        final HashMap<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        vm.put(MESSAGE, PROMPT_MSG);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}

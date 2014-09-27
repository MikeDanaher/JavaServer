package request;

import routes.Route;
import routes.Routes;

import java.util.Map;

public class RequestValidator {
    private String requestContent;
    private Request request;
    private Route requestedRoute;
    private Map<String, Route> validRoutes;

    public RequestValidator(String requestContent, Routes routes) {
        this.requestContent = requestContent;
        this.validRoutes = routes.getValidRoutes();
    }

    public Request validate() {
        if (requestContent.equals("")) {
            buildEmptyRequest();
        } else {
            parseRequest();
        }
        return request;
    }

    private void parseRequest() {
        request = new RequestParser().parse(requestContent);
        requestedRoute = validRoutes.get(request.path);
        if (requestedRoute != null) {
            validateRoute();
        } else {
            setRequestType("NotFound");
        }
    }

    private void validateRoute() {
        if (requestedRoute.authenticationRequired) {
            setRequestType("Authorized");
        } else if (isRedirect()) {
            setRequestType("Redirect");
        } else {
            setRequestType("Valid");
        }
    }

    private void buildEmptyRequest() {
        request = new Request();
        setRequestType("Empty");
    }

    private void setRequestType(String type) {
        request.type = type;
    }

    private boolean isRedirect() {
        return request.path.equals("/redirect");
    }
}

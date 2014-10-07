package request;

import routes.Route;
import routes.Routes;
import utilities.CaseSwitcher;

import java.util.Map;

public class RequestBuilder {
    private String requestContent;
    private Map<String, Route> validRoutes;
    private Request request;
    private Route requestedRoute;

    public RequestBuilder(String requestContent, Routes routes) {
        this.requestContent = requestContent;
        this.validRoutes = routes.getValidRoutes();
    }

    public Request build() {
        if (requestContent.equals("")) {
            buildEmptyRequest();
        } else {
            buildRequest();
        }
        return request;
    }

    private void buildEmptyRequest() {
        request = new Request();
        setRequestType("Empty");
    }

    private void buildRequest() {
        request = new RequestParser().parse(requestContent);
        requestedRoute = validRoutes.get(request.getPath());
        if (requestedRoute != null) {
            setAbsolutePath();
            setReadOnly();
            setRoute();
        } else {
            buildNotFoundRequest();
        }
    }

    private void setAbsolutePath() {
        request.setAbsolutePath(requestedRoute.absolutePath);
    }

    private void setReadOnly() {
        request.setReadOnly(requestedRoute.isReadOnly);
    }

    private void setRoute() {
        if (isAuthenticated()) {
            buildAuthorizedRequest();
        } else if (isRedirect()) {
            buildRedirectRequest();
        } else {
            buildValidRequest();
        }
    }

    private void buildNotFoundRequest() {
        setRequestType("NotFound");
    }

    private void buildAuthorizedRequest() {
        request.setPassPhrase(requestedRoute.authenticationString);
        setRequestType("Authorized");
    }

    private void buildRedirectRequest() {
        request.setRedirectPath(requestedRoute.redirectPath);
        setRequestType("Redirect");
    }

    private void buildValidRequest() {
        setRequestType(CaseSwitcher.toProperCase(request.getMethod()));
    }

    private void setRequestType(String type) {
        request.setType(type);
    }

    private boolean isAuthenticated() {
        return requestedRoute.authenticationRequired;
    }

    private boolean isRedirect() {
        return !requestedRoute.redirectPath.equals("");
    }

}

package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import utilities.CaseSwitcher;

import java.io.IOException;

public class AuthorizedHandler implements Handler {
    private ResponseBuilder builder;
    private Request request;

    public AuthorizedHandler(Request clientRequest) {
        this.request = clientRequest;
        this.builder = new ResponseBuilder();
    }

    public Response handle() throws IOException {
        String authHeader = request.getHeaders().get("Authorization");
        if (authHeader != null) {
            String passPhrase = authHeader.split(" ")[1];
            if (passPhrase.equals(request.getPassPhrase())) {
                return handleAuthenticatedRequest();
            } else {
                return handleUnauthenticatedRequest();
            }
        } else {
            return handleUnauthenticatedRequest();
        }
    }

    private Response handleAuthenticatedRequest() throws IOException {
        request.setType(CaseSwitcher.toProperCase(request.getMethod()));
        Handler handler = new HandlerFactory().build(request);
        return handler.handle();
    }

    private Response handleUnauthenticatedRequest() {
        builder.buildAuthenticationRequiredResponse();
        return builder.getResponse();
    }
}

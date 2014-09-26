package handlers;

import response.Response;

import java.io.IOException;

public interface Handler {

    public Response handle() throws IOException;

}

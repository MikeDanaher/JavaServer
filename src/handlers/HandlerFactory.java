package handlers;

import request.Request;

public class HandlerFactory {

    public Handler build(Request request) {
        try {
            Class<?> handlerClass = Class.forName("handlers." + request.getType() + "Handler");
            return (Handler)handlerClass.getDeclaredConstructor(Request.class).newInstance(request);
        } catch (Exception e) {
            return new NotAllowedHandler(request);
        }
    }
}

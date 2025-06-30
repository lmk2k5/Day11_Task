// AuthHandler.java
package in.edu.kristujayanti.handlers;

import in.edu.kristujayanti.services.AuthService;
import io.vertx.ext.web.RoutingContext;

public class AuthHandler {
    private final AuthService service;

    public AuthHandler(AuthService service) {
        this.service = service;
    }

    public void register(RoutingContext ctx) {
        service.register(ctx);
    }

    public void login(RoutingContext ctx) {
        service.login(ctx);
    }
}


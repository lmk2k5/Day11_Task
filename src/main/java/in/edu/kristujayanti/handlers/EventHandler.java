// EventHandler.java
package in.edu.kristujayanti.handlers;

import io.vertx.ext.web.RoutingContext;
import in.edu.kristujayanti.services.EventService;

public class EventHandler {
    private final EventService service;

    public EventHandler(EventService service) {
        this.service = service;
    }

    public void listEvents(RoutingContext ctx) {
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .end(service.listEvents().toString());
    }
}

package in.edu.kristujayanti.handlers;

import in.edu.kristujayanti.services.BookingService;
import io.vertx.ext.web.RoutingContext;

public class BookingHandler {
    private final BookingService service;

    public BookingHandler(BookingService service) {
        this.service = service;
    }

    public void bookToken(RoutingContext ctx) {
        var body = ctx.body().asJsonObject();
        String email = body.getString("email");
        String eventId = body.getString("eventId");

        service.book(email, eventId)
                .onSuccess(v -> ctx.response().end("Booking successful "))
                .onFailure(err -> ctx.response().setStatusCode(400).end(err.getMessage()));
    }
}
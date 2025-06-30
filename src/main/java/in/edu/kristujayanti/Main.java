package in.edu.kristujayanti;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import in.edu.kristujayanti.handlers.AuthHandler;
import in.edu.kristujayanti.handlers.BookingHandler;
import in.edu.kristujayanti.handlers.EventHandler;
import in.edu.kristujayanti.services.*;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        MongoConnection.getDatabase();

        MailService mailService = new MailService(vertx,"lohithm978@gmail.com","removed for security","lohithm978@gmail.com");
        AuthService authService = new AuthService(mailService);
        EventService eventService = new EventService();
        BookingService bookingService = new BookingService(mailService);

        AuthHandler authHandler = new AuthHandler(authService);
        EventHandler eventHandler = new EventHandler(eventService);
        BookingHandler bookingHandler = new BookingHandler(bookingService);

        router.post("/api/register").handler(authHandler::register);
        router.post("/api/login").handler(authHandler::login);
        router.get("/api/events").handler(eventHandler::listEvents);
        router.post("/api/events/book").handler(bookingHandler::bookToken);
        vertx.createHttpServer().requestHandler(router).listen(8080)
                .onSuccess(server -> System.out.println("Server running on http://localhost:8080"))
                .onFailure(Throwable::printStackTrace);
    }
}
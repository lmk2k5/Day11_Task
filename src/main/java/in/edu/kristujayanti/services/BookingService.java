package in.edu.kristujayanti.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.UUID;

import io.vertx.core.Future;
import io.vertx.core.Promise;

public class BookingService {
    private final MongoCollection<Document> events;
    private final MailService mailService;

    public BookingService(MailService mailService) {
        var db = in.edu.kristujayanti.MongoConnection.getDatabase();
        this.events = db.getCollection("events");
        this.mailService = mailService;
    }

    public Future<Void> book(String userEmail, String eventId) {
        Promise<Void> promise = Promise.promise();
        System.out.println("Attempting booking for Event ID: " + eventId);
        Document event = events.find(new Document("_id", eventId)).first();
        if (event == null || event.getInteger("seats", 0) <= 0) {
            System.out.println("Booking failed: Invalid event ID or no seats available.");
            promise.fail("Booking failed: No tokens available");
            return promise.future();
        }

        String eventName = event.getString("name");
        Document user = in.edu.kristujayanti.MongoConnection.getDatabase()
                .getCollection("users")
                .find(new Document("email", userEmail))
                .first();

        if (user == null) {
            promise.fail("Booking failed: User not registered");
            return promise.future();
        }
        Document filter = new Document("_id", eventId).append("seats", new Document("$gt", 0));
        Document update = new Document("$inc", new Document("seats", -1));
        var result = events.updateOne(filter, update);
        if (result.getModifiedCount() == 0) {
            System.out.println("Booking failed: Could not decrement seats.");
            promise.fail("Booking failed: No tokens available");
            return promise.future();
        }
        String tokenCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        mailService.send(userEmail, "Your Booking Token", "Your booking was successful!\n\nEvent ID: " + eventId +
                                "\nEvent Name: " + eventName +
                                "\nYour token: " + tokenCode)
                .onSuccess(v -> {
                    System.out.println("Booking email sent to: " + userEmail);
                    promise.complete();
                })
                .onFailure(err -> {
                    System.out.println("Email failed: " + err.getMessage());
                    promise.fail(err);
                });

        return promise.future();
    }
}

package in.edu.kristujayanti.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.UUID;

public class AuthService {
    private final MongoCollection<Document> users;
    private final MailService mailService;
    public AuthService(MailService mailService) {
        this.mailService = mailService;
        this.users = in.edu.kristujayanti.MongoConnection.getDatabase().getCollection("users");
    }
    public void register(RoutingContext ctx) {
        JsonObject body = ctx.body().asJsonObject();
        String email = body.getString("email");
        String name = body.getString("name");
        String password = UUID.randomUUID().toString().substring(0, 8);
        Document doc = new Document("email", email).append("name", name).append("password", password);
        users.insertOne(doc);
        mailService.send(email, "Your Password", "Welcome, your password is: " + password)
                .onSuccess(v -> ctx.response().end("Registration successful. Email sent to " + email))
                .onFailure(err -> ctx.response().setStatusCode(500).end("Failed to send email "+ err.getMessage()));
    }
    public void login(RoutingContext ctx) {
        JsonObject body = ctx.body().asJsonObject();
        String email = body.getString("email");
        String password = body.getString("password");
        Document found = users.find(new Document("email", email).append("password", password)).first();
        if (found != null) {
            ctx.response().end("Login successful");
        } else {
            ctx.response().setStatusCode(401).end("Invalid credentials");
        }
    }
}
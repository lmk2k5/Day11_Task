package in.edu.kristujayanti.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EventService {
    private final MongoCollection<Document> events;

    public EventService() {
        this.events = in.edu.kristujayanti.MongoConnection.getDatabase().getCollection("events");
    }

    public List<JsonObject> listEvents() {
        return StreamSupport.stream(events.find().spliterator(), false)
                .map(doc -> {
                    JsonObject json = new JsonObject(doc.toJson());

                    // Ensure _id is always a string
                    if (doc.containsKey("_id") && doc.get("_id") instanceof org.bson.types.ObjectId id) {
                        json.put("_id", id.toHexString());
                    } else if (doc.containsKey("_id")) {
                        json.put("_id", doc.get("_id").toString());
                    }

                    return json;
                })
                .collect(Collectors.toList());
    }

}

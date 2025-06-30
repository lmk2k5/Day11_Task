package in.edu.kristujayanti.services;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.StartTLSOptions;

public class MailService {
    private final MailClient client;
    private final String fromAddress;

    public MailService(Vertx vertx, String username, String password, String fromAddress) {
        MailConfig config = new MailConfig()
                .setHostname("smtp.gmail.com")
                .setPort(587)
                .setStarttls(StartTLSOptions.REQUIRED)
                .setUsername(username)
                .setPassword(password);

        this.client = MailClient.create(vertx, config);
        this.fromAddress = fromAddress;
    }

    public Future<Void> send(String to, String subject, String body) {
        MailMessage message = new MailMessage()
                .setFrom(fromAddress)
                .setTo(to)
                .setSubject(subject)
                .setText(body);

        return client.sendMail(message)
                .onSuccess(res -> System.out.println("Email sent to " + to))
                .onFailure(err -> System.err.println("Email failed: " + err.getMessage()))
                .mapEmpty();
    }
}

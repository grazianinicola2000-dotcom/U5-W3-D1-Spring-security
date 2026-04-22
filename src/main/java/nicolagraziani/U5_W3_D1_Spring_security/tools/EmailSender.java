package nicolagraziani.U5_W3_D1_Spring_security.tools;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EmailSender {
    private final String domainName;
    private final String apiKey;

    public EmailSender(@Value("${MAILGUN_DOMAIN_NAME}") String domainName, @Value("${MAILGUN_API_KEY}") String apiKey) {
        this.domainName = domainName;
        this.apiKey = apiKey;
    }

    public void sendRegistrationEmail(Employee recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "graziani.nicola2000@gmail.com")
                .queryString("to", recipient.getEmail()) // DEVE ESSERE IL DESTINATARIO VERIFICATO
                .queryString("subject", "BENVENUTO!!!!!")
                .queryString("text", "Ciao " + recipient.getName() + ", la tua registrazione è andata a buon fine!")
                .asJson();

        System.out.println(response.getBody());
    }
}

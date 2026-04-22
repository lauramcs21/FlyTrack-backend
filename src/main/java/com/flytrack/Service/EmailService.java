package com.flytrack.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;
import sendinblue.Configuration;
import sibModel.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${app.mail.from}")
    private String from;

    private String loadTemplate(String name) {
        try {
            ClassPathResource resource = new ClassPathResource("email/" + name);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando plantilla", e);
        }
    }

    public void enviarCodigoVerificacion(String to, String codigo) {

        Configuration.getDefaultApiClient().setApiKey(apiKey);
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(from);
        sender.setName("FlyTrack ✈️");

        String template = loadTemplate("verificacion.html");

        String html = template
                .replace("{{CODE}}", codigo)
                .replace("{{TIME}}", "5 minutos")
                .replace("{{YEAR}}", String.valueOf(LocalDateTime.now().getYear()));

        SendSmtpEmail email = new SendSmtpEmail();
        email.setSender(sender);
        email.setTo(Collections.singletonList(
                new SendSmtpEmailTo().email(to)
        ));
        email.setSubject("Código de verificación - FlyTrack");
        email.setHtmlContent(html);

        try {
            api.sendTransacEmail(email);
            System.out.println("[MAIL] Código enviado a " + to);
        } catch (Exception e) {
            System.err.println("[MAIL] Error enviando correo: " + e.getMessage());
        }
    }
}
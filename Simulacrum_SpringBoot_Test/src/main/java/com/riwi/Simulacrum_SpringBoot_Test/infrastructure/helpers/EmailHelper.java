package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.var;

@Component //al ser un helper (component puede ser cualquier cosa)
@AllArgsConstructor
public class EmailHelper {
    //intectar el servicio de email la libreria
    private final JavaMailSender mailSender;

    //encargado de enviar el mensaje
    public void sendMail(String destino, String name, String apellido, String date){
        MimeMessage message = mailSender.createMimeMessage(); //tipo de email (html)

        /*forma de darle formato de un localdatetime a string */
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String dateA = date.format(formatter);
        String htmlContent = this.readHTMLTemplate(name, apellido, date);
        try {
            message.setFrom(new InternetAddress("jccaste1002@gmail.com")); //de donde es el mensaje
            message.setSubject("confirmacion de registro"); //asunto del mensaje

            message.setRecipients(MimeMessage.RecipientType.TO, destino); //de donde a donde
            message.setContent(htmlContent,MediaType.TEXT_HTML_VALUE);

            mailSender.send(message);
            System.out.println("Email enviado");

        } catch (Exception e) {
            System.out.println("Error no se pudo enviar el email "+ e.getMessage());
        }
    }


    /*
     * metodo para obtener el html y reemplazar palabras
     */
    private String readHTMLTemplate(String name, String message, String date){
        //indicar donde se encuentra el template, Path viene de la libreria nio
        final Path path = java.nio.file.Paths.get("src/main/resources/emails/email_template.html");

        try (var lines = Files.lines(path)){
            var html = lines.collect(Collectors.joining());

            return html.replace("{name}",name).replace("{message}", message).replace("{date}", date);
        } catch (IOException e) {//otro tipo de exception
            System.out.println("No se pudo leer el html");
            throw new RuntimeException();
        }
    }
}

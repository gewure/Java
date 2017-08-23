package main.java;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Emailer {
    
    /**
     * Sends a email
     *
     * @param from ...sender
     * @param to   ...recipient
     * @param subject ...
     * @param msg     ...
     * @param host    ... SMTP-Host
     * @param port    ... SMTP-Port (default: 25)
     * @param user    ... the SMTP-Username (not the emailname, most of the times!)
     * @param pw      ... the password
     */
    public void sendMail(String from, String to, String subject, String msg, String host, int port, final String user, final String pw) {

        Properties properties = System.getProperties();
        
        /* define your adequate properties here */
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "false"); 
        properties.put("mail.smtp.auth.mechanisms","NTLM");  
        
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pw);
            }
        };
        Session mailSession = Session.getInstance(properties, auth);

        mailSession.setDebug(true); /* set false to have no output at all */

        try {

            Transport transport = mailSession.getTransport("smtp");

            MimeMessage message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(msg, "text/plain");
 
            transport.connect(host,user, pw);
            Transport.send(message);
            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
     /**
     * Test sendMail()
     * ... probably best to run this as a JUNIT test
     */
    public void sendMailTest() throws Exception {
        
        Emailer emailer = new Emailer();
       
        emailer.sendMail(
                "fromAddress@blamail.com",
                "toAddress@blablamail.com",
                "Testsubject",
                "This is a testtext"
                ,"your.smtp.server",
                25,                // 25 is default for SMTP
                "YOURUSERNAME",   //hint: it is usually not the emailaddress
                "YOURPASSWORD");
    }

}

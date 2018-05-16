package com.mobileai.luncert.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.mobileai.luncert.model.core.EMail;

import org.springframework.stereotype.Service;

@Service
public class MailSender {

    private class CusAuthenticator extends Authenticator {

        private String user;

        private String pass;

        public CusAuthenticator(String user,String pass){
            this.user=user;
            this.pass=pass;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user,pass);
        }

    }

    private Message createMessage(EMail mailInfo) throws MessagingException {
        Properties props = mailInfo.getProperties();
        Authenticator authenticator = null;
        // create a authenticator if validate required
        if (mailInfo.getValidate()) {
            authenticator = new CusAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        Session session = Session.getDefaultInstance(props, authenticator);

        Message msg = new MimeMessage(session);

        Address source = new InternetAddress(mailInfo.getSourceAddress());
        msg.setFrom(source);

        Address target = new InternetAddress(mailInfo.getTargetAddress());
        msg.setRecipient(Message.RecipientType.TO, target);

        msg.setSubject(mailInfo.getSubject());
        msg.setSentDate(new Date());

        return msg;
    }

    public void sendTextMail(EMail mailInfo) throws MessagingException {

        Message msg = createMessage(mailInfo);
        
        msg.setText(mailInfo.getContent());

        Transport.send(msg);

    }

    public void sendHTML(EMail mailInfo) throws MessagingException {
        
        Message msg = createMessage(mailInfo);

        Multipart multipart = new MimeMultipart();
        BodyPart html = new MimeBodyPart();
        html.setContent(mailInfo.getContent(), "text/html; charset = utf-8");
        multipart.addBodyPart(html);

        msg.setContent(multipart);

        Transport.send(msg);
    }

}
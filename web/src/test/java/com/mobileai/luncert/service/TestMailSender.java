package com.mobileai.luncert.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.mobileai.luncert.model.core.EMail;
import com.mobileai.luncert.utils.ResourceHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestMailSender {

    private String insertIdIntoMail(String mailHtml, String id) {
        int i = mailHtml.indexOf("<body>") + 6;
        StringBuilder builder = new StringBuilder();
        builder.append(mailHtml.substring(0, i))
                .append("<input type='hidden' value='")
                .append(id)
                .append("' />")
                .append(mailHtml.substring(i));
        return  builder.toString();
    }

    MailSender mailSender = new MailSender();

    @Test
    public void testSendText() throws MessagingException, IOException {
        EMail mailInfo = new EMail("1242005392@qq.com");

        mailInfo.setSubject("帐号激活");
        mailInfo.setContent(insertIdIntoMail(ResourceHelper.getResource("templates/checkMail.html"), "123"));

        mailSender.sendHTML(mailInfo);
    }

}
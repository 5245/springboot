package com.sxk.bootstrap;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @description 实现这个commandLineRunner接口后，spring启动容器就去执行，多个的话，可以用@Order定义执行顺序
 * @author sxk
 * @date 2016年7月21日
 */

@Component
public class StartTask implements CommandLineRunner {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void run(String... args) {

        //        try {
        //            long starttime = System.currentTimeMillis();
        //            System.out.println("开始发邮件...");
        //
        //            MimeMessage mimeMessage = mailSender.createMimeMessage();
        //
        //            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //            helper.setFrom("sxk5245@126.com");
        //            helper.setTo("sxk5245@126.com");
        //            helper.setSubject("主题：有附件");
        //            helper.setText("有附件的邮件");
        //
        //            FileSystemResource file = new FileSystemResource(new File("D://001.jpg"));
        //            helper.addAttachment("附件-1.jpg", file);
        //            helper.addAttachment("附件-2.jpg", file);
        //
        //            mailSender.send(mimeMessage);
        //
        //            System.out.println("邮件发送成功,用时：" + (System.currentTimeMillis() - starttime));
        //        } catch (MailException e) {
        //            e.printStackTrace();
        //        } catch (MessagingException e) {
        //            e.printStackTrace();
        //        }

    }
}

package com.qzx.user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: qc
 * @time: 2020/8/15 14:31
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RefreshScope
public class EmailUtil {

    private final JavaMailSender myJavaMailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String userName;

    /**
     *
     *
     * @description: 复杂邮件
      * @param info:邮件详情
     * @return:
     * @author: qc
     * @time: 2020/8/18 10:02
     */
    public  boolean sendEmail(EmailInfo info){
        MimeMessage mimeMessage;
        try {
            mimeMessage= myJavaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(info.getFrom()+"<"+userName+">");
            helper.setTo(info.getTo());
            helper.setSubject(info.getSubject());
            Context context=new Context();
            if (!ObjectUtils.isEmpty(info.getVariable())){
                Set<Map.Entry<String, Object>> entries = info.getVariable().entrySet();
                for (Map.Entry<String,Object> e:entries){
                    context.setVariable(e.getKey(),e.getValue());
                }
            }
            helper.setText(templateEngine.process(info.getTemplate(),context),true);
            myJavaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean sendSimpleEmail(EmailInfo info){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject(info.getSubject());
        mailMessage.setText(info.getText());
        mailMessage.setTo(info.getTo());
        mailMessage.setFrom(info.getFrom()+"<"+userName+">");
        myJavaMailSender.send(mailMessage);
        return true;
    }
}

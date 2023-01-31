package com.qzx.user.util;

import com.qzx.user.dto.EmailInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
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
     * @description: 复杂邮件
      * @param info:邮件详情
     * @return:
     * @author: qc
     * @time: 2020/8/18 10:02
     */
    public  boolean sendEmail(EmailInfo info){
        MimeMessage mimeMessage;
        try {
            mimeMessage = getMimeMessage();
            handleMimeMessageHelper(mimeMessage,info,null);
            myJavaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     *
     * @description: 发送带附件的复杂邮件
     * @param info:邮件详情
     * @param attachment
     * @return:
     * @author: qc
     * @time: 2020/8/18 10:02
     */
    public  boolean sendEmail(EmailInfo info, List<EmailFileInfo> attachment){
        MimeMessage mimeMessage;
        try {
            mimeMessage= myJavaMailSender.createMimeMessage();
            handleMimeMessageHelper(mimeMessage,info,attachment);
            myJavaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送基础邮件
     * @param info 邮件信息
     * @return 发送是否成功标识
     */
    public boolean sendSimpleEmail(EmailInfo info){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject(info.getSubject()); //发送主题
        mailMessage.setText(info.getText()); //发送内容
        mailMessage.setTo(info.getTo()); //接收人
        mailMessage.setFrom(info.getFrom()+"<"+userName+">");
        myJavaMailSender.send(mailMessage);
        return true;
    }

    /**
     * 获取MimeMessage
     * @return
     */
    private MimeMessage getMimeMessage(){
        return myJavaMailSender.createMimeMessage();
    }

    /**
     * 处理邮箱内容
     * @param info 邮件基础信息
     * @param attachment 邮件附件
     * @throws MessagingException
     */
    private void handleMimeMessageHelper(MimeMessage mimeMessage,EmailInfo info,List<EmailFileInfo> attachment) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(info.getFrom()+"<"+userName+">");
        helper.setTo(info.getTo());
        helper.setSubject(info.getSubject());
        Context context = getContext(info);
        if (!ObjectUtils.isEmpty(info.getTemplate())){
            // 使用html模板发送邮件
            helper.setText(templateEngine.process(info.getTemplate(),context),true);
        }
        if (!ObjectUtils.isEmpty(info.getText())){
            // 发送普通消息
            helper.setText(info.getText());
        }
        if (!ObjectUtils.isEmpty(attachment)){
            attachment.forEach(res->{
                try {
                    helper.addAttachment(res.getFileName(),res.getFile());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 模板引擎内容填充
     * @param info
     * @return
     */
    private Context getContext(EmailInfo info){
        Context context=new Context();
        // 封装context数据，模板需要用
        if (!ObjectUtils.isEmpty(info.getVariable())){
            Set<Map.Entry<String, Object>> entries = info.getVariable().entrySet();
            for (Map.Entry<String,Object> e:entries){
                context.setVariable(e.getKey(),e.getValue());
            }
        }
        return context;
    }
}

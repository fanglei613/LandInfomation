package com.jh.util.sms;

import com.jh.constant.PropertiesConstant;
import com.jh.util.PropertyUtil;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;


/**
 * 邮箱工具类
 * @version <1> 2018-06-07 cxw： Created.
 * @version<2> 2018-07-10 lcw : 将邮箱工具的配置文件单独剥离开来</2>
 */
public class EmailHelper {


    //邮件配置文件

    private static final String username = PropertyUtil.getPropertiesForConfig("username", PropertiesConstant.EMAIL_CONFFIG); //邮箱账号
    private static final String password = PropertyUtil.getPropertiesForConfig("password",PropertiesConstant.EMAIL_CONFFIG); //邮箱密码
    private static final String host = PropertyUtil.getPropertiesForConfig("email.host",PropertiesConstant.EMAIL_CONFFIG); //声明发送邮件使用的端口
    private static final String port = PropertyUtil.getPropertiesForConfig("email.port",PropertiesConstant.EMAIL_CONFFIG); //声明发送邮件使用的数字端口

    /**
     *邮件发送
     * @param emailParam 邮件参数
     * @return
     * @version <1> 2018-06-07 cxw： Created.
     */
    public static Boolean sendEmail(EmailParam emailParam){
        Boolean res = true;
        //邮件发送
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");//声明发送邮件使用的端口
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        Authenticator authenticator = new Authenticator(){
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
        Session session = Session.getDefaultInstance(props, authenticator);
        session.setDebug(true);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(username));
            try {
                message.setSubject(emailParam.getCompany());
                message.setContent(emailParam.getRemark()+"<br> 姓名："+emailParam.getPersonName()+"<br> 手机号码："+emailParam.getMobile()
                        +"<br> 邮箱："+emailParam.getEamil()+"<br> 公司："+emailParam.getCompany(),"text/html;charset=UTF-8");
                message.saveChanges();
                Transport.send(message);
            } catch (Exception e) {
                res = false;
                e.printStackTrace();
            }
        } catch (AddressException e) {
            res = false;
            e.printStackTrace();
        } catch (MessagingException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

}
package exception;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 发送邮件报告异常
 */
public class MailReport {
    //发件人
    private static String fromUser;
    //收件人
    private static ArrayList<String> toUsers;
    private static String toUser;
    //主机
    private static String host;
    //系统属性
    private static Properties properties;
    //默认session对象
    private static Session session;

    //TODO: 从配置文件中获取信息
    static {
        try {
            fromUser = "517606847@qq.com";

            //TODO: 从配置文件中添加收件人信箱
            //toUsers = new ArrayList<>();
            toUser = "xueyao_98@foxmail.com";

            host = "smtp.qq.com";

            properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.port", "587");

            properties.put("mail.smtp.auth", "true");
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromUser, "plpvwyaogmcqbjja");
                }
            });
            //session.setDebug(true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test
    public static void main(String[] args) {
        reportMessage(newMessage("测试JavaMail", "祝贺！\n成功编写一封邮件"));
    }

    public static MimeMessage newMessage(String subject, String text) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromUser));
            //message.addRecipients();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
            message.setSubject(subject, "UTF-8");
            message.setText(text, "UTF-8");

            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void reportMessage(Message msg) {
        try {
            Transport.send(msg);
            System.out.println("Send Successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

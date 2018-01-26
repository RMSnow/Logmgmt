package exception;

import com.sun.mail.util.MailSSLSocketFactory;
import entity.ConfInfo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 发送邮件报告异常
 */
public class MailReport {
    //发件人
    private static String fromUser = "517606847@qq.com";
    //收件人
    private static Address[] addresses;
    //主机
    private static String host = "smtp.qq.com";
    //系统属性
    private static Properties properties;
    //默认session对象
    private static Session session;

    public static final String INSECURE_IP = "[日志服务] 异常IP访问";

    static {
        try {
            //从配置文件中添加收件人信箱
            int toUsers = ConfInfo.recipients.size();
            addresses = new Address[toUsers];
            for (int i = 0; i < toUsers; i++) {
                //System.out.println(ConfInfo.recipients.get(i));
                addresses[i] = new InternetAddress(ConfInfo.recipients.get(i));
            }

            //设置邮件主机与端口
            properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.port", "587");

            //连接SSL并进行权限认证
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

    /**
     * 编写新邮件
     *
     * @param subject 邮件标题
     * @param text    邮件内容
     * @return
     */
    public static MimeMessage newMessage(String subject, String text) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromUser));
            message.addRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subject, "UTF-8");
            message.setText(text, "UTF-8");

            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 编写HTML邮件
     *
     * @param subject  邮件标题
     * @param richText HTML格式的邮件内容
     * @return
     */
    public static MimeMessage newRichMessage(String subject, String richText) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromUser));
            message.addRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subject, "UTF-8");
            message.setContent(richText, "text/html");

            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 发送邮件
     *
     * @param msg
     */
    public static void reportMessage(Message msg) {
        if (msg != null){
            try {
                Transport.send(msg);
                System.out.println("Send Emails Successfully...");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}

package dock;

import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MessageBuilder {

    private String token;

    private String url;

    private String name;

    private MessageDetail msg = new MessageDetail();

    private Map<String,String> param = new HashMap<>(16);

    private String entity;

    private MessageBuilder(){}

    public static MessageBuilder newMessage(){
        return new MessageBuilder();
    }

    public static MessageBuilder newMessage(String serviceName){
        MessageBuilder mb = new MessageBuilder();
        mb.name = serviceName;
        return mb;
    }

    public static MessageBuilder newMessage(String serviceName,String url){
        MessageBuilder mb = new MessageBuilder();
        mb.name = serviceName;
        mb.url = url;
        return mb;
    }

    public MessageBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public MessageBuilder setServiceName(String serviceName) {
        this.name = serviceName;
        return this;
    }

    public MessageBuilder setApiUrl(String url) {
        this.url = url;
        return this;
    }

    public MessageBuilder setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public MessageBuilder setParam(String key, Object value) {
        if(value != null)
            param.put(key, value.toString());
        return this;
    }

    public MessageBuilder setParam(Map<String,String> param) {
        this.param.putAll(param);
        return this;
    }

    public MessageBuilder setParam(Object o){
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(o.getClass());
        } catch (IntrospectionException e) {
            return null;
        }
        PropertyDescriptor[] descriptorSrc = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : descriptorSrc) {
            String name = property.getName();
            Method method = property.getReadMethod();
            try {
                Object value = method.invoke(o);
                //过滤类型信息与空字段
                if(value != null && !Objects.equals(name, "class"))
                    //URL编码
                    param.put(name, value.toString());
            } catch (Exception e) {
                return null;
            }
        }
        return this;
    }

    public MessageDetail build(){
        msg.setToken(token);
        msg.setUrl(url);
        msg.setName(name);
        msg.setParam(param);
        msg.setEntity(entity);
        return msg;
    }

}

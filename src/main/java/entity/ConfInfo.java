package entity;

import java.util.ArrayList;

/**
 * 获取配置文件信息
 */
public class ConfInfo {
    /* Basic Info */
    public static String serviceName;

    public static String pswd;

    public static String ip;

    public static String url;

    public static String logmgmtPort;

    public static String syslogPort;

    /* Dock */
    public static String registryIp;

    public static String registryPort;

    public static String discoverPort;

    /* MongoDB */
    public static String mongodbPort;

    public static String mongodbHost;

    public static String mongodbUserName;

    public static String mongodbPassword;

    /* Mail Report */
    public static ArrayList<String> recipients = new ArrayList<>();
}
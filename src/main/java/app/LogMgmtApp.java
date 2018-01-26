package app;

import conf.LogMgmtConf;
import entity.ConfInfo;
import exception.IpWhiteListGetter;
import exception.MailReport;
import health.HeartbeatTest;
import health.LogMgmtHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import res.*;

/**
 * 日志管理APP
 */
public class LogMgmtApp extends Application<LogMgmtConf> {
    @Override
    public String getName() {
        return ConfInfo.serviceName;
    }

    @Override
    public void initialize(Bootstrap<LogMgmtConf> bootstrap) {

    }

    @Override
    public void run(LogMgmtConf configuration, Environment environment) throws Exception {
        //health check
        final LogMgmtHealthCheck healCheck = new LogMgmtHealthCheck(configuration.getName());
        environment.healthChecks().register("LogMgmt", healCheck);

        final RequestLogRes requestLogRes = new RequestLogRes();
        final LoggingLogRes loggingLogRes = new LoggingLogRes();
        final RecordRes recordRes = new RecordRes();
        final ClientLogRes clientLogRes = new ClientLogRes();

        final ControlRes controlRes = new ControlRes();
        final HeartRes heartRes = new HeartRes();

        environment.jersey().register(requestLogRes);
        environment.jersey().register(loggingLogRes);
        environment.jersey().register(recordRes);
        environment.jersey().register(clientLogRes);

        environment.jersey().register(controlRes);
        environment.jersey().register(heartRes);

        /* 传入配置参数 */

        ConfInfo.serviceName = configuration.getName();
        ConfInfo.pswd = configuration.getPswd();
        ConfInfo.ip = configuration.getIp();
        ConfInfo.url = configuration.getUrl();
        ConfInfo.logmgmtPort = configuration.getLogmgmtPort();
        ConfInfo.syslogPort = configuration.getSyslogPort();

        ConfInfo.registryIp = configuration.getRegistryIp();
        ConfInfo.registryPort = configuration.getRegistryPort();
        ConfInfo.discoverPort = configuration.getDiscoverPort();

        ConfInfo.mongodbPort = configuration.getMongodbPort();
        ConfInfo.mongodbHost = configuration.getMongodbHost();
        ConfInfo.mongodbUserName = configuration.getMongodbUserName();
        ConfInfo.mongodbPassword = configuration.getMongodbPassword();

        ConfInfo.recipients = configuration.getRecipients();

        //心跳包检测与断线重连
        new Thread(new HeartbeatTest()).start();

        //IP白名单更新与异常IP捕获
        new Thread(new IpWhiteListGetter()).start();
    }

}
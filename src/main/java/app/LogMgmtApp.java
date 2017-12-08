package app;

import conf.LogMgmtConf;
import entity.ConfInfo;
import health.LogMgmtHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import res.ControlRes;
import res.HeartRes;
import res.LoggingLogRes;
import res.RequestLogRes;
import health.HeartbeatTest;

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

        final ControlRes controlRes = new ControlRes();
        final HeartRes heartRes = new HeartRes();

        environment.jersey().register(requestLogRes);
        environment.jersey().register(loggingLogRes);

        environment.jersey().register(controlRes);
        environment.jersey().register(heartRes);

        //传入配置参数
        ConfInfo.serviceName = configuration.getName();
        ConfInfo.ip = configuration.getIp();
        ConfInfo.url = configuration.getUrl();
        ConfInfo.logmgmtPort = configuration.getLogmgmtPort();
        ConfInfo.syslogPort = configuration.getSyslogPort();
        ConfInfo.mongodbPort = configuration.getMongodbPort();
        ConfInfo.mongodbHost = configuration.getMongodbHost();
        ConfInfo.mongodbUserName = configuration.getMongodbUserName();
        ConfInfo.mongodbPassword = configuration.getMongodbPassword();

        new Thread(new HeartbeatTest()).start();
    }

}
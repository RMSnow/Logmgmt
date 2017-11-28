package app;

import conf.LogMgmtConf;
import entity.ConfInfo;
import health.LogMgmtHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import res.LoggingLogRes;
import res.RequestLogRes;

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

        environment.jersey().register(requestLogRes);
        environment.jersey().register(loggingLogRes);

        ConfInfo.serviceName = configuration.getName();
        ConfInfo.port1 = configuration.getPort1();
        ConfInfo.port2 = configuration.getPort2();
    }

}

//java -jar target/cloudclass_logmgmt-1.0-SNAPSHOT.jar server logmgmt.yml
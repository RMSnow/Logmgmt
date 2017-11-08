package app;

import conf.LogMgmtConf;
import health.LogMgmtHealCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import res.LogsRes;

/**
 * 日志管理APP
 */
public class LogMgmtApp extends Application<LogMgmtConf> {
    public static void main(String[] args) throws Exception {
        new LogMgmtApp().run(args);
    }

    @Override
    public String getName() {
        return "logmgmt";
    }

    @Override
    public void initialize(Bootstrap<LogMgmtConf> bootstrap) {

    }

    @Override
    public void run(LogMgmtConf configuration, Environment environment) throws Exception {
        //health check
        final LogMgmtHealCheck healCheck = new LogMgmtHealCheck(configuration.getName());
        environment.healthChecks().register("LogMgmt", healCheck);

        final LogsRes logsRes = new LogsRes();
        environment.jersey().register(logsRes);
    }

}

package health;

import com.codahale.metrics.health.HealthCheck;

/**
 * 检查异常
 */
public class LogMgmtHealthCheck extends HealthCheck {
    private final String name;

    public LogMgmtHealthCheck(String name) {
        this.name = name;
    }

    @Override
    protected Result check() throws Exception {
        final String t = "logmgmt";
        if (!name.contains(t)) {
            return Result.unhealthy("Errors in HealthCheck");
        }
        return Result.healthy();
    }
}

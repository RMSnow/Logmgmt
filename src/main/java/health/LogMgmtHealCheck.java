package health;

import com.codahale.metrics.health.HealthCheck;

/**
 * 检查异常
 */
public class LogMgmtHealCheck extends HealthCheck{
    private final String name;

    public LogMgmtHealCheck(String name) {
        this.name = name;
    }

    @Override
    protected Result check() throws Exception{
        final String t = "logmgmt";
        if(!name.contains(t)){
            return Result.unhealthy("URI doesn't include \"logs\"");
        }
        return Result.healthy();
    }
}

import app.LogMgmtApp;

import java.util.Arrays;

/**
 * 日志管理的主入口
 */
public class LogMgmt {
    public static void main(final String[] args) {
        Arrays.asList(new LogMgmtApp()).forEach(
                (l) -> {
                    try{
                        l.run(args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

}

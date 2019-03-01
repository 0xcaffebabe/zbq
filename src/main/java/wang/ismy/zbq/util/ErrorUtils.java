package wang.ismy.zbq.util;

public class ErrorUtils {

    public static void error(String msg){
        throw new RuntimeException(msg);
    }
}

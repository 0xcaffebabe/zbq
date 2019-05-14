package wang.ismy.zbq.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现该接口的类都能获取userId
 *
 * @author my
 */
public interface UserIdGetter {

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    Integer getUserId();

    static List<Integer> getUserIdList(List<? extends UserIdGetter> list){

        return list.stream()
                .map(UserIdGetter::getUserId)
                .collect(Collectors.toList());
    }
}

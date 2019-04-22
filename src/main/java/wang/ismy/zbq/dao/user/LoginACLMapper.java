package wang.ismy.zbq.dao.user;

public interface LoginACLMapper {

    int insert(Integer userId);

    Boolean selectLoginStateByUserId(Integer userId);
}

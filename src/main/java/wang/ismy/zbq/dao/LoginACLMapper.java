package wang.ismy.zbq.dao;

public interface LoginACLMapper {

    int insert(Integer userId);

    Boolean selectLoginStateByUserId(Integer userId);
}

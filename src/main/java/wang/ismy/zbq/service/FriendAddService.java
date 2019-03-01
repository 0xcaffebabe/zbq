package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.FriendAddMapper;
import wang.ismy.zbq.dto.FriendAddDTO;
import wang.ismy.zbq.entity.FriendAdd;

@Service
public class FriendAddService {

    @Autowired
    private FriendAddMapper friendAddMapper;

    public int insertNew(FriendAddDTO friendAddDTO){

        FriendAdd friendAdd = new FriendAdd();
        BeanUtils.copyProperties(friendAddDTO,friendAdd);

        return friendAddMapper.insertNew(friendAdd);
    }

    public FriendAdd selectFriendAddByFromUserAndToUser(Integer from ,Integer to){
        return friendAddMapper.selectFriendAddByFromUserAndToUser(from,to);
    }
}

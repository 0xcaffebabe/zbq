package wang.ismy.zbq.service;

import org.apache.tomcat.util.http.fileupload.ThresholdingOutputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.FriendAddMapper;
import wang.ismy.zbq.dto.FriendAddDTO;
import wang.ismy.zbq.entity.FriendAdd;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendAddService {

    @Autowired
    private FriendAddMapper friendAddMapper;

    @Autowired
    private UserService userService;

    public int insertNew(FriendAddDTO friendAddDTO){


        User from = User.builder().userId(friendAddDTO.getFromUser()).build();

        if (userService.selectByPrimaryKey(friendAddDTO.getToUser()) != null){
            ErrorUtils.error(StringResources.TARGET_USER_NOT_EXIST);
        }

        User to = User.builder().userId(friendAddDTO.getToUser()).build();

        FriendAdd friendAdd = FriendAdd.builder()
                .fromUser(from)
                .toUser(to)
                .msg(friendAddDTO.getMsg()).build();
        return friendAddMapper.insertNew(friendAdd);
    }

    public FriendAdd selectFriendAddByFromUserAndToUser(Integer from ,Integer to){
        return friendAddMapper.selectFriendAddByFromUserAndToUser(from,to);
    }

    public List<FriendAdd> selectFriendAddListByToUser(Integer toUserId){
        var list = friendAddMapper.selectFriendAddListByToUserId(toUserId);
        List<Integer> userIdList = new ArrayList<>();
        User toUser = userService.selectByPrimaryKey(toUserId);
        for (var i : list){
            userIdList.add(i.getFromUser().getUserId());
        }


        if (list.size() == 0){
            return list;
        }

        List<User> users = userService.selectByUserIdBatch(userIdList);

        for (var i : list){
            i.setToUser(toUser);
            for (var j : users){
                if (j.getUserId().equals(i.getFromUser().getUserId())){
                    i.setFromUser(j);
                }
            }
        }
        return list;

    }
}

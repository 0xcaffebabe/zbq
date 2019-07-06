package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.SubscriptionMapper;
import wang.ismy.zbq.model.entity.Subscription;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Autowired
    private UserService userService;

    public void currentUserSubscripeAuthor(Integer author){

        var user = userService.selectByPrimaryKey(author);

        if (user == null){
            ErrorUtils.error(R.USERNAME_NOT_EXIST);
        }

        var currentUser = userService.getCurrentUser();

        if (subscriptionMapper.selectByUserAndAuthor(currentUser.getUserId(),user.getUserId()) != null){
            ErrorUtils.error(R.SUBSCRIPTION_EXIST);
        }

        Subscription subscription = new Subscription();
        subscription.setUser(currentUser);
        subscription.setAuthor(user);

        if (subscriptionMapper.insertNew(subscription) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

    }

    public List<Subscription> selectBatch(List<Integer> authorIdList,Integer userId){
        if (authorIdList == null || authorIdList.size() == 0){
            return List.of();
        }
        return subscriptionMapper.selectByAuthorBatch(authorIdList,userId);
    }

    public List<Integer> selectSubscriperAll(Integer author){
        return subscriptionMapper.selectSubscriperAll(author);
    }

    public void currentUserUnsubscribe(Integer author){
        var currentUser = userService.getCurrentUser();
        Subscription subscription = subscriptionMapper.selectByUserAndAuthor(currentUser.getUserId(),author);

        if (subscription == null){
            ErrorUtils.error(R.UNSUBSCRIBE);
        }

        if (subscriptionMapper.deleteByPrimaryKey(subscription.getSubscriptionId()) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }
    }
}

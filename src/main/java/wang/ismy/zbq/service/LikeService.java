package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.LikeMapper;
import wang.ismy.zbq.entity.Like;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeMapper likeMapper;


    public int createLikeRecord(LikeTypeEnum likeType, Integer contentId, User user){

        if (likeMapper.selectLikeByLikeTypeAndContentIdAndUserId(likeType.getCode(),contentId,user.getUserId()) != null){
            ErrorUtils.error(StringResources.LIKE_FAIL);
        }
        Like like = new Like();
        like.setLikeType(likeType.getCode());
        like.setContentId(contentId);
        like.setLikeUser(user);
        return likeMapper.insertNew(like);
    }

    public int removeLikeRecord(LikeTypeEnum likeType, Integer contentId, User user){
        return likeMapper.deleteLikeByLikeTypeAndContentIdAndUserId(likeType.getCode(),contentId,user.getUserId());
    }



    public List<Like> selectLikeListByLikeTypeAndContentId(LikeTypeEnum likeType,Integer contentId){
        return likeMapper.selectLikeListByLikeTypeAndContentId(likeType.getCode(),contentId);
    }

    public List<Like> selectLikeListByLikeTypeAndContentIdBatch(LikeTypeEnum likeType,List<Integer> contentIdList){
        if (contentIdList.size() == 0){
            return List.of();
        }
        return likeMapper.selectLikeListByLikeTypeAndContentIdBatch(likeType.getCode(),contentIdList);
    }

    public long countLike(Integer userId){
        return likeMapper.countLike(userId);
    }
}

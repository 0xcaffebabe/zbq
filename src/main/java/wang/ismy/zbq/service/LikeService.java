package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.LikeMapper;
import wang.ismy.zbq.entity.Like;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.LikeTypeEnum;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeMapper likeMapper;

    public int createLikeRecord(LikeTypeEnum likeType, Integer contentId, User likeUseer){
        Like like = new Like();
        like.setLikeType(likeType.getCode());
        like.setContentId(contentId);
        like.setLikeUser(likeUseer);
        return likeMapper.insertNew(like);
    }

    public List<Like> selectLikeListByLikeTypeAndContentId(LikeTypeEnum likeType,Integer contentId){
        return likeMapper.selectLikeListByLikeTypeAndContentId(likeType.getCode(),contentId);
    }

    public List<Like> selectLikeListByLikeTypeAndContentIdBatch(LikeTypeEnum likeType,List<Integer> contentIdList){
        return likeMapper.selectLikeListByLikeTypeAndContentIdBatch(likeType.getCode(),contentIdList);
    }
}

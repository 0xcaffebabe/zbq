package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.LikeMapper;
import wang.ismy.zbq.model.entity.like.Like;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.LikeCountVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author my
 */
@Service
public class LikeService {

    @Autowired
    private LikeMapper likeMapper;

    public int createLikeRecord(LikeTypeEnum likeType, Integer contentId, User user) {

        if (likeMapper.selectLikeByLikeTypeAndContentIdAndUserId(likeType.getCode(), contentId, user.getUserId()) != null) {
            ErrorUtils.error(R.LIKE_FAIL);
        }
        Like like = new Like();
        like.setLikeType(likeType.getCode());
        like.setContentId(contentId);
        like.setLikeUser(user);
        return likeMapper.insertNew(like);
    }

    public int removeLikeRecord(LikeTypeEnum likeType, Integer contentId, User user) {
        return likeMapper.deleteLikeByLikeTypeAndContentIdAndUserId(likeType.getCode(), contentId, user.getUserId());
    }


    public List<Like> selectLikeListByLikeTypeAndContentId(LikeTypeEnum likeType, Integer contentId) {
        return likeMapper.selectLikeListByLikeTypeAndContentId(likeType.getCode(), contentId);
    }

    public List<Like> selectLikeBatch(LikeTypeEnum likeType, List<Integer> contentIdList) {
        if (contentIdList.size() == 0) {
            return List.of();
        }
        return likeMapper.selectLikeListByLikeTypeAndContentIdBatch(likeType.getCode(), contentIdList);
    }

    public Map<Integer, Long> countLikeBatch(LikeTypeEnum likeType, List<Integer> contentIdList) {
        if (contentIdList.size() == 0) {
            return Map.of();
        }
        var list = likeMapper.countLikeByLikeTypeAndContentIdBatch(likeType.getCode(), contentIdList);
        Map<Integer,Long> map = new HashMap<>();

        for (var i : list) {

            map.put(i.getId(), i.getCount());
        }
        return map;
    }

    public LikeCountVO countLike(Integer userId) {
        LikeCountVO vo = new LikeCountVO();
        long stateLike = likeMapper.countStateLikeByUserId(userId);
        long contentLike = likeMapper.countContentLikeByUserId(userId);
        vo.setStateLike(stateLike);
        vo.setContentLike(contentLike);
        vo.setTotal(stateLike+contentLike);
        return vo;
    }

    public Map<Integer, Boolean> hasLikeBatch(LikeTypeEnum likeType, List<Integer> contentIdList
            , Integer userId) {
        if (contentIdList.size() == 0) {
            return Map.of();
        }
        var list = likeMapper.selectHasLikeByLikeTypeAndContentIdAndUserIdBatch(likeType.getCode(),contentIdList,userId);

        Map<Integer,Boolean> map = new HashMap<>();
        for (var i : list){
            if (i.get("content_id") != null){
                if ((Long)i.get("has_like") == 0){
                    map.put((Integer) i.get("content_id"),false);
                }else{
                    map.put((Integer) i.get("content_id"),true);
                }

            }
        }
        return map;
    }
}

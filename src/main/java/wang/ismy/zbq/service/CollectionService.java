package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CollectionMapper;
import wang.ismy.zbq.enums.CollectionTypeEnum;
import wang.ismy.zbq.model.dto.CollectionCountDTO;
import wang.ismy.zbq.model.dto.CollectionDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Collection;
import wang.ismy.zbq.model.vo.CollectionVO;
import wang.ismy.zbq.service.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
public class CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ContentService contentService;

    public int currentUserAddCollection(CollectionDTO collectionDTO){

        var currentUser = userService.getCurrentUser();

        Collection collection = new Collection();
        collection.setUser(currentUser);
        collection.setCollectionType(collectionDTO.getCollectionType());
        collection.setContentId(collectionDTO.getContentId());

        return collectionMapper.insertNew(collection);
    }

    public Collection selectByTypeAndContentId(CollectionTypeEnum typeEnum,Integer contentId,Integer userId){
        return collectionMapper.selectByTypeAndContentIdAndUserId(typeEnum.getCode(),contentId,userId);
    }

    public Map<Integer,CollectionCountDTO> selectCollectionCountBatchByType(
            CollectionTypeEnum typeEnum,
            List<Integer> contentIdList,
            Integer userId){

        if (contentIdList == null || contentIdList.size() == 0){
            return Map.of();
        }

        var list = collectionMapper.selectCollectionCountBatchByType(typeEnum.getCode(),contentIdList,userId);

        Map<Integer,CollectionCountDTO> ret = new HashMap<>();
        for (var i : list){
            ret.put(i.getCollectionId(),i);
        }
        return ret;
    }

    public List<CollectionVO> selectCurrentUserCollectionList(Page page){
        var currentUser = userService.getCurrentUser();

        var collectionList = collectionMapper.selectPaging(currentUser.getUserId(),page);

        List<CollectionVO> collectionVOList = collectionList.stream()
                .map(CollectionVO::convert)
                .collect(Collectors.toList());

        var contentIdList = collectionVOList.stream()
                .map(CollectionVO::getContentId)
                .collect(Collectors.toList());

        var countMap = selectCollectionCountBatchByType(CollectionTypeEnum.CONTENT,contentIdList,currentUser.getUserId());

        for (var i :collectionVOList){

            var countVO = countMap.get(i.getContentId());

            if (countVO != null){
                i.setCollectCount(countVO.getCollectionCount());
            }
        }

        addSummary(collectionVOList);

        return collectionVOList;

    }

    private void addSummary(List<CollectionVO> collectionVOList) {

        List<Integer> contentIdList = new ArrayList<>();

        for (var i :collectionVOList){
            if (i.getCollectionType().equals(CollectionTypeEnum.CONTENT.getCode())){
                contentIdList.add(i.getContentId());
            }
        }

        var map = contentService.selectTitleBatch(contentIdList);

        for (var i : collectionVOList){
            if (i.getCollectionType().equals(CollectionTypeEnum.CONTENT.getCode())){
                String title = map.get(i.getContentId());
                i.setSummary(title);
            }
        }
    }
}

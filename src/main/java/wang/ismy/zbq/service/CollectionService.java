package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CollectionMapper;
import wang.ismy.zbq.enums.CollectionTypeEnum;
import wang.ismy.zbq.model.dto.CollectionDTO;
import wang.ismy.zbq.model.entity.Collection;
import wang.ismy.zbq.service.user.UserService;

/**
 * @author my
 */
@Service
public class CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private UserService userService;

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
}

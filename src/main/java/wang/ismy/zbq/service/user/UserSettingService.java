package wang.ismy.zbq.service.user;

import com.google.gson.Gson;
import lombok.Setter;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.user.UserSettingMapper;
import wang.ismy.zbq.model.dto.UserSettingObject;
import wang.ismy.zbq.model.entity.user.UserSetting;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;

import javax.inject.Inject;

/**
 * @author my
 */
@Service
@Setter(onMethod_ = @Inject)
public class UserSettingService {

    private UserSettingMapper mapper;

    private UserService userService;

    /**
     * 根据用户ID获取设置
     * @param userId 用户ID
     * @return 用户设置实体
     */
    public UserSetting select(Integer userId) {
        var user = userService.selectByPrimaryKey(userId);

        if (user == null) {
            ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
        }

        var setting = mapper.select(userId);

        // 如果当前用户没有设置，则插入一条默认设置
        if (setting == null) {
            setting = new UserSetting();
            setting.setUser(user);
            setting.setContent(new Gson().toJson(UserSettingObject.empty()));

            if (mapper.insertNew(setting) != 1) {
                ErrorUtils.error(R.UNKNOWN_ERROR);
            }

            return mapper.select(userId);
        } else {
            return setting;
        }
    }

    public UserSetting selectCurrentUser(){
        return select(userService.getCurrentUser().getUserId());
    }

    public void updateCurrentUser(UserSettingObject object){
        update(object,userService.getCurrentUser().getUserId());
    }

    public void update(UserSettingObject obj,Integer userId){
        if (userService.selectByPrimaryKey(userId) == null){
            ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
        }

        var setting = select(userId);
        setting.setContent(new Gson().toJson(obj));

        if (mapper.update(setting) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

    }
}

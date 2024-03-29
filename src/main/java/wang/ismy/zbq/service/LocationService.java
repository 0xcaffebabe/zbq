package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.LocationMapper;
import wang.ismy.zbq.model.dto.LocationDTO;
import wang.ismy.zbq.model.entity.Location;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.LocationVO;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private UserService userService;

    public List<LocationVO> selectAll() {
        var locationList = locationMapper.selectAll();

        List<Integer> userIdList = new ArrayList<>();
        List<LocationVO> locationVOList = new ArrayList<>();
        Map<Integer, LocationVO> locationVOMap = new HashMap<>();
        for (var i : locationList) {
            LocationVO tmpLocationVO = LocationVO.convert(i);
            locationVOList.add(tmpLocationVO);
            userIdList.add(i.getUser().getUserId());
            if (!tmpLocationVO.getAnonymous()) {
                locationVOMap.put(i.getUser().getUserId(), tmpLocationVO);
            }

        }

        var userList = userService.selectByUserIdBatch(userIdList);
        for (var i : userList) {
            var tmpLocationVO = locationVOMap.get(i.getUserId());

            if (tmpLocationVO != null) {
                tmpLocationVO.setUserVO(UserVO.convert(i));
            }
        }
        return locationVOList;
    }

    public int createLocationRecordForCurrentUser(LocationDTO locationDTO) {
        User currentUser = userService.getCurrentUser();

        if (selectByUserId(currentUser.getUserId()) != null){
            ErrorUtils.error(R.LOCATION_EXIST);
        }

        Location location = new Location();
        BeanUtils.copyProperties(locationDTO,location);
        location.setUser(currentUser);

        return locationMapper.insertNew(location);

    }

    public Location selectByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }

        return locationMapper.selectByUserId(userId);
    }

    public Location selectCurrentUserLocation(){
        User user = userService.getCurrentUser();
        return selectByUserId(user.getUserId());
    }

    public int updateCurrentUserLocation(LocationDTO locationDTO) {
        User currentUser = userService.getCurrentUser();

        var location = selectByUserId(currentUser.getUserId());

        if (location == null){
            ErrorUtils.error(R.LOCATION_NOT_EXIST);
        }

        BeanUtils.copyProperties(locationDTO,location);

        return locationMapper.update(location);
    }
}

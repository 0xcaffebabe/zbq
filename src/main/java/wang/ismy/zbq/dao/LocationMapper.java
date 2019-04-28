package wang.ismy.zbq.dao;

import wang.ismy.zbq.model.entity.Location;

import java.util.List;

public interface LocationMapper {

    int insertNew(Location location);

    List<Location> selectAll();

    Location selectByUserId(Integer userId);

    int update(Location location);
}

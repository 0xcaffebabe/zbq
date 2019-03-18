package wang.ismy.zbq.dao;

import wang.ismy.zbq.entity.Location;

import java.util.List;

public interface LocationMapper {

    int insertNew(Location location);

    List<Location> selectAll();

    Location selectByUserId(Integer userId);
}

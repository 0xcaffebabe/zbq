package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CourseMapper;
import wang.ismy.zbq.entity.Course;
import wang.ismy.zbq.vo.CourseVO;
import wang.ismy.zbq.vo.UserVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserService userService;

    public List<CourseVO> selectAll() {
        var list = courseMapper.selectAll();

        var userIdList = list.stream()
                .map(i->i.getPublisher().getUserId())
                .collect(Collectors.toList());

        var userList = userService.selectByUserIdBatch(userIdList);

        List<CourseVO> ret = new ArrayList<>();
        for (var i : list){
            for (var j : userList){
                if (j.equals(i.getPublisher())){
                    CourseVO courseVO = CourseVO.convert(i);
                    UserVO userVO = UserVO.convert(j);
                    courseVO.setPublisher(userVO);
                    ret.add(courseVO);
                    break;
                }
            }
        }

        return ret;

    }
}

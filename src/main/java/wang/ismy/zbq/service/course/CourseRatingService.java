package wang.ismy.zbq.service.course;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.course.CourseRatingMapper;
import wang.ismy.zbq.model.UserIdGetter;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.course.CourseRatingDTO;
import wang.ismy.zbq.model.entity.course.Course;
import wang.ismy.zbq.model.entity.course.CourseRating;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.vo.course.CourseRatingVO;
import wang.ismy.zbq.model.vo.user.UserVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
public class CourseRatingService {

    @Autowired
    private CourseRatingMapper courseRatingMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    public void createCurrentUserRating(CourseRatingDTO dto) {

        Course course = courseService.selectByPrimaryKey(dto.getCourseId());
        if (course == null) {
            ErrorUtils.error(R.TARGET_COURSE_NOT_EXIST);
        }

        var currentUser = userService.getCurrentUser();

        if (courseRatingMapper.selectByCourseAndUser(dto.getCourseId(),currentUser.getUserId()) != null){
            ErrorUtils.error(R.RATING_EXIST);
        }

        CourseRating rating = new CourseRating();


        BeanUtils.copyProperties(dto, rating);
        rating.setCourse(course);
        rating.setUser(currentUser);
        if (courseRatingMapper.insertNew(rating) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }
    }

    public List<CourseRatingVO> selectRatings(Integer courseId, Page page){

        var list = courseRatingMapper.selectByCoursePaging(courseId,page);

        var courseRatingVOList = list.stream()
                .map(CourseRatingVO::convert)
                .collect(Collectors.toList());

        var userIdList = UserIdGetter.getUserIdList(list);

        var userList = userService.selectByUserIdBatch(userIdList);

        Map<Integer,UserVO> userVOMap = new HashMap<>();

        userList.forEach(e->{
            userVOMap.put(e.getUserId(),UserVO.convert(e));
        });

        courseRatingVOList.forEach(e->{
            var user = userVOMap.get(e.getUser().getUserId());

            e.setUser(user);
        });

        return courseRatingVOList;





    }
}

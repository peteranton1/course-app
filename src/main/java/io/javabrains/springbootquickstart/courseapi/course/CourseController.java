package io.javabrains.springbootquickstart.courseapi.course;

import io.javabrains.springbootquickstart.courseapi.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/topics/{topicId}/courses")
    public List<Course> getAllCourses(@PathVariable String topicId) {
        return courseService.getAllCourses(topicId);
    }

    @RequestMapping("/topics/{topicId}/courses/{courseId}")
    public Course findCourse(@PathVariable String topicId,
                            @PathVariable String courseId) {
        return courseService.findCourse(topicId, courseId).orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/topics/{topicId}/courses")
    public void addCourse(@PathVariable String topicId,
                         @RequestBody Course course) {
        course.setTopic(Topic.builder().id(topicId).build());
        courseService.addCourse(course);
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/topics/{topicId}/courses")
    public void updateCourse(@PathVariable String topicId,
                            @RequestBody Course course) {
        course.setTopic(Topic.builder().id(topicId).build());
        courseService.updateCourse(course);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/topics/{topicId}/courses/{courseId}")
    public void deleteCourse(@PathVariable String topicId,
                            @PathVariable String courseId) {
        courseService.deleteCourse(courseId);
    }
}

package io.javabrains.springbootquickstart.courseapi.course;

import io.javabrains.springbootquickstart.courseapi.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseAssembler courseAssembler;

    @RequestMapping("/topics/{topicId}/courses")
    public HttpEntity<List<CourseResource>> getAllCourses(@PathVariable String topicId) {
        List<Course> allCourses = courseService.getAllCourses(topicId);
        List<CourseResource> allCourseResources = courseAssembler.toResource(allCourses);
        allCourseResources.forEach(resource -> resource
                .add(linkTo(methodOn(CourseController.class)
                        .findCourse(resource.getTopicId(), resource.getId()))
                        .withSelfRel()));
        return new ResponseEntity<>(allCourseResources, HttpStatus.OK);
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

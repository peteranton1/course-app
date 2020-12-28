package io.javabrains.springbootquickstart.courseapi.controller;

import io.javabrains.springbootquickstart.courseapi.resource.CourseResource;
import io.javabrains.springbootquickstart.courseapi.service.CourseService;
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

    @RequestMapping("/topics/{topicId}/courses")
    public HttpEntity<List<CourseResource>> getAllCourseResources(@PathVariable String topicId) {
        List<CourseResource> allCourseResources = courseService.getAllCourseResources(topicId);
        allCourseResources.forEach(resource -> resource
                .add(linkTo(methodOn(CourseController.class)
                        .findCourse(resource.getTopicId(), resource.getId()))
                        .withSelfRel()));
        return new ResponseEntity<>(allCourseResources, HttpStatus.OK);
    }

    @RequestMapping("/topics/{topicId}/courses/{courseId}")
    public HttpEntity<CourseResource> findCourse(@PathVariable String topicId,
                                     @PathVariable String courseId) {
        return new ResponseEntity<>(courseService.findCourseResource(topicId, courseId)
                .map(resource -> resource
                        .add(linkTo(methodOn(CourseController.class)
                                .findCourse(resource.getTopicId(), resource.getId()))
                                .withSelfRel()))
                .orElse(null), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/topics/{topicId}/courses")
    public void addCourseResource(@PathVariable String topicId,
                                  @RequestBody CourseResource courseResource) {
        courseService.addCourseResource(courseResource);
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/topics/{topicId}/courses")
    public void updateCourseResource(@PathVariable String topicId,
                                     @RequestBody CourseResource courseResource) {
        courseService.updateCourseResource(courseResource);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/topics/{topicId}/courses/{courseId}")
    public void deleteCourseResource(@PathVariable String topicId,
                                     @PathVariable String courseId) {
        courseService.deleteCourseById(courseId);
    }
}

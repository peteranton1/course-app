package io.courseapi.controller;

import io.courseapi.service.CourseService;
import io.courseapi.resource.CourseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(method = RequestMethod.GET,
            value = "/topics/{topicId}/courses")
    public HttpEntity<List<CourseResource>> getAllCourseResources(
            @PathVariable String topicId) {
        List<CourseResource> allCourseResources = courseService
                .getAllCourseResources(topicId);
        allCourseResources.forEach(resource -> resource
                .add(linkTo(methodOn(CourseController.class)
                        .findCourse(resource.getTopicId(), resource.getId()))
                        .withSelfRel()));
        return new ResponseEntity<>(allCourseResources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/topics/{topicId}/courses/{courseId}")
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
            value = "/topics/{topicId}/courses/{courseId}")
    public HttpEntity<CourseResource> addCourseResource(@PathVariable String topicId,
                                                        @PathVariable String courseId,
                                                        @RequestBody CourseResource courseResource) {
        courseResource.setId(courseId);
        courseResource.setTopicId(topicId);
        CourseResource updatedResource = courseService.updateCourseResource(courseResource);
        if(nonNull(updatedResource)) {
            return new ResponseEntity<>(updatedResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/topics/{topicId}/courses/{courseId}")
    public HttpEntity<CourseResource> updateCourseResource(@PathVariable String topicId,
                                                           @PathVariable String courseId,
                                                           @RequestBody CourseResource courseResource) {
        courseResource.setId(courseId);
        courseResource.setTopicId(topicId);
        CourseResource updatedResource = courseService.updateCourseResource(courseResource);
        if(nonNull(updatedResource)) {
            return new ResponseEntity<>(updatedResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/topics/{topicId}/courses/{courseId}")
    public HttpEntity<CourseResource> deleteCourseResource(@PathVariable String topicId,
                                                           @PathVariable String courseId) {
        return new ResponseEntity<>(courseService.deleteCourseById(topicId, courseId), HttpStatus.OK);
    }
}

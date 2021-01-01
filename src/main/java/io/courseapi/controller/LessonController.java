package io.courseapi.controller;

import io.courseapi.resource.LessonAssembler;
import io.courseapi.resource.LessonResource;
import io.courseapi.service.LessonService;
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
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonAssembler lessonAssembler;

    @RequestMapping(method = RequestMethod.GET,
            value = "/topics/{topicId}/courses/{courseId}/lessons")
    public HttpEntity<List<LessonResource>> getAllLessonResources(
            @PathVariable String topicId,
            @PathVariable String courseId) {
        List<LessonResource> allLessonResources = lessonService
                .getAllLessonResources(topicId, courseId);
        allLessonResources.forEach(lesson -> lesson
                .add(linkTo(methodOn(LessonController.class)
                        .findLessonResource(topicId, lesson.getCourseId(), lesson.getId()))
                        .withSelfRel()));
        return new ResponseEntity<>(allLessonResources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public LessonResource findLessonResource(@PathVariable String topicId,
                                             @PathVariable String courseId,
                                             @PathVariable String lessonId) {
        return lessonService.findLessonResource(topicId, courseId, lessonId)
                .map(lesson -> lesson
                        .add(linkTo(methodOn(LessonController.class)
                                .findLessonResource(topicId, lesson.getCourseId(), lesson.getId()))
                                .withSelfRel()))
                .orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public HttpEntity<LessonResource> addLessonResource(@PathVariable String courseId,
                                                         @PathVariable String lessonId,
                                                         @RequestBody LessonResource lessonResource) {
        lessonResource.setId(lessonId);
        lessonResource.setCourseId(courseId);
        LessonResource updatedResource = lessonService.updateLessonResource(lessonResource);
        if(nonNull(updatedResource)) {
            return new ResponseEntity<>(updatedResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public HttpEntity<LessonResource> updateLessonResource(@PathVariable String courseId,
                                     @PathVariable String lessonId,
                                     @RequestBody LessonResource lessonResource) {
        lessonResource.setId(lessonId);
        lessonResource.setCourseId(courseId);
        LessonResource updatedResource = lessonService.updateLessonResource(lessonResource);
        if(nonNull(updatedResource)) {
            return new ResponseEntity<>(updatedResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public HttpEntity<LessonResource> deleteLessonResource(@PathVariable String topicId,
                                                           @PathVariable String courseId,
                                                           @PathVariable String lessonId) {
        return new ResponseEntity<>(lessonService.deleteLessonById(topicId, courseId, lessonId), HttpStatus.OK);
    }
}

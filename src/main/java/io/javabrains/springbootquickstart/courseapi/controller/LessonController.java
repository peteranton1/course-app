package io.javabrains.springbootquickstart.courseapi.controller;

import io.javabrains.springbootquickstart.courseapi.resource.LessonAssembler;
import io.javabrains.springbootquickstart.courseapi.resource.LessonResource;
import io.javabrains.springbootquickstart.courseapi.service.LessonService;
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
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonAssembler lessonAssembler;

    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons")
    public HttpEntity<List<LessonResource>> getAllLessonResources(
            @PathVariable String topicId,
            @PathVariable String courseId) {
        List<LessonResource> allLessonResources = lessonService.getAllLessonResources(courseId);
        allLessonResources.forEach(resource -> resource
                .add(linkTo(methodOn(LessonController.class)
                        .findLessonResource(resource.getCourseId(), resource.getId()))
                        .withSelfRel()));
        return new ResponseEntity<>(allLessonResources, HttpStatus.OK);
    }

    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public LessonResource findLessonResource(@PathVariable String courseId,
                                             @PathVariable String lessonId) {
        return lessonService.findLessonResource(courseId, lessonId)
                .map(resource -> resource
                        .add(linkTo(methodOn(LessonController.class)
                                .findLessonResource(resource.getCourseId(), resource.getId()))
                                .withSelfRel()))
                .orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/topics/{topicId}/courses/{courseId}/lessons")
    public void addLessonResource(@PathVariable String topicId,
                                  @PathVariable String courseId,
                                  @RequestBody LessonResource lessonResource) {
        lessonResource.setCourseId(courseId);
        lessonService.addLessonResource(lessonResource);
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/topics/{topicId}/courses/{courseId}/lessons")
    public void updateLessonResource(@PathVariable String topicId,
                                     @PathVariable String courseId,
                                     @RequestBody LessonResource lessonResource) {
        lessonResource.setCourseId(courseId);
        lessonService.updateLessonResource(lessonResource);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public void deleteLessonResource(@PathVariable String topicId,
                                     @PathVariable String courseId,
                                     @PathVariable String lessonId) {
        lessonService.deleteLessonById(lessonId);
    }
}

package io.javabrains.springbootquickstart.courseapi.lesson;

import io.javabrains.springbootquickstart.courseapi.course.Course;
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
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonAssembler lessonAssembler;

    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons")
    public HttpEntity<List<LessonResource>> getAllLessons(
            @PathVariable String topicId,
            @PathVariable String courseId) {
        List<Lesson> allLessons = lessonService.getAllLessons(courseId);
        List<LessonResource> lessonResources = lessonAssembler.toResource(allLessons);
        lessonResources.forEach(resource -> resource
                .add(linkTo(methodOn(LessonController.class)
                        .findLesson(topicId, resource.getCourseId(), resource.getId()))
                        .withSelfRel()));
        return new ResponseEntity<>(lessonResources, HttpStatus.OK);
    }

    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public Lesson findLesson(@PathVariable String topicId,
                             @PathVariable String courseId,
                             @PathVariable String lessonId) {
        return lessonService.findLesson(courseId, lessonId).orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/topics/{topicId}/courses/{courseId}/lessons")
    public void addLesson(@PathVariable String topicId,
                          @PathVariable String courseId,
                          @RequestBody Lesson lesson) {
        lesson.setCourse(Course.builder().id(courseId)
                .topic(Topic.builder().id(topicId).build()).build());
        lessonService.addLesson(lesson);
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/topics/{topicId}/courses/{courseId}/lessons")
    public void updateLesson(@PathVariable String topicId,
                             @PathVariable String courseId,
                             @RequestBody Lesson lesson) {
        lesson.setCourse(Course.builder().id(courseId)
                .topic(Topic.builder().id(topicId).build()).build());
        lessonService.updateLesson(lesson);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public void deleteLesson(@PathVariable String topicId,
                             @PathVariable String courseId,
                             @PathVariable String lessonId) {
        lessonService.deleteLesson(lessonId);
    }
}

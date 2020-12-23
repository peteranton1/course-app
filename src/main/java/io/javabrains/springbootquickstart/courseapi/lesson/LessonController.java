package io.javabrains.springbootquickstart.courseapi.lesson;

import io.javabrains.springbootquickstart.courseapi.course.Course;
import io.javabrains.springbootquickstart.courseapi.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons")
    public List<Lesson> getAllLessons(@PathVariable String topicId,
                                     @PathVariable String courseId) {
        return lessonService.getAllLessons(courseId);
    }

    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public Lesson findLesson(@PathVariable String topicId,
                            @PathVariable String courseId,
                            @PathVariable String lessonId) {
        return lessonService.findLesson(topicId, courseId, lessonId).orElse(null);
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

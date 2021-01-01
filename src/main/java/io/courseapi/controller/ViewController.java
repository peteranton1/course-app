package io.courseapi.controller;

import com.google.common.collect.ImmutableMap;
import io.courseapi.resource.CourseResource;
import io.courseapi.resource.LessonResource;
import io.courseapi.resource.TopicResource;
import io.courseapi.service.ViewService;
import io.courseapi.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static io.courseapi.view.ViewName.*;

@Controller
public class ViewController {

    private static final String ADD = "add";
    private static final int ID_WIDTH = 4;

    @Autowired
    private ViewService viewService;

    @Autowired
    private FieldValidator validator;

    @Autowired
    IdGenerator idGenerator;

    private final MessageEscaper escaper = MessageEscaper.escaper;

    @RequestMapping("/")
    public ModelAndView home() {
        try {
            ViewData viewData = viewService.listHome(HOME);
            return new ModelAndView(viewData.getViewName(), viewData.getModel());
        } catch (Exception e) {
            return new ModelAndView(HOME.getTemplatePath() +
                    "?message=Error Rendering");
        }
    }

    @RequestMapping("/topic")
    public ModelAndView topicList(@RequestParam @Nullable String message) {
        try {
            ViewData data = viewService.listTopic(escaper.escapeString(message));
            return new ModelAndView(data.getViewName(),
                    data.withMessage(message).getModel());
        } catch (Throwable t) {
            return new ModelAndView(TOPIC.getTemplatePath(),
                    ImmutableMap.of("message",
                            "Error Rendering: " + escaper.escapeException(t)));
        }
    }

    @GetMapping("/topic/{topicId}")
    public ModelAndView topicEdit(@PathVariable String topicId) {
        try {
            if(ADD.equals(topicId)){
                topicId = idGenerator.generateId("T", ID_WIDTH);
            }
            topicId = validator.sanitiseId(topicId);
            ViewData data = viewService.editTopic(topicId);
            return new ModelAndView(data.getViewName(), data.getModel());
        } catch (Throwable t) {
            return new ModelAndView(TOPIC_EDIT.getTemplatePath(),
                    ImmutableMap.of("message",
                            "Error Rendering: " + escaper.escapeException(t)));
        }
    }

    @PostMapping("/topic/{topicId}/update")
    public ModelAndView topicUpdate(@PathVariable String topicId,
                                    @ModelAttribute TopicResource topic) {
        String message = "";
        try {
            topicId = validator.sanitiseId(topicId);
            message = escaper.escapeString(topicId) + ": ";
            TopicResource topicUpdate = viewService.saveTopic(topic);
            message += "saved.";
        } catch (Exception e) {
            message += "Error saving. ";
        }
        String url = "redirect:/topic" +
                "?message=" + escaper.escapeString(message);
        return new ModelAndView(url);
    }

    @GetMapping("/topic/{topicId}/delete")
    public ModelAndView topicDelete(@PathVariable String topicId) {
        String message = "";
        try {
            topicId = validator.sanitiseId(topicId);
            message = escaper.escapeString(topicId) + ": ";
            TopicResource topicDelete = viewService.deleteTopic(topicId);
            message += "deleted.";
        } catch (Exception e) {
            message += "Error deleting. ";
        }
        String url = "redirect:/topic?message=" + escaper.escapeString(message);
        return new ModelAndView(url);
    }

    @RequestMapping("/topic/{topicId}/course")
    public ModelAndView courseList(@PathVariable String topicId,
                                   @RequestParam @Nullable String message) {
        try {
            topicId = validator.sanitiseId(topicId);
            message = escaper.escapeString(message);
            ViewData data = viewService.listCourse(topicId, message);
            return new ModelAndView(data.getViewName(),
                    data.withMessage(message).getModel());
        } catch (Throwable t) {
            return new ModelAndView(COURSE.getTemplatePath(),
                    ImmutableMap.of("message",
                            "Error Rendering: " + escaper.escapeException(t)));
        }
    }

    @GetMapping("/topic/{topicId}/course/{courseId}")
    public ModelAndView courseEdit(@PathVariable String topicId,
                                   @PathVariable String courseId) {
        try {
            topicId = validator.sanitiseId(topicId);
            if(ADD.equals(courseId)){
                courseId = topicId + ".";
                courseId += idGenerator.generateId("C", ID_WIDTH);
            }
            courseId = validator.sanitiseId(courseId);
            ViewData data = viewService.editCourse(topicId, courseId);
            return new ModelAndView(data.getViewName(), data.getModel());
        } catch (Throwable t) {
            return new ModelAndView(COURSE_EDIT.getTemplatePath(),
                    ImmutableMap.of("message",
                            "Error Rendering: " + escaper.escapeException(t)));
        }
    }

    @PostMapping("/topic/{topicId}/course/{courseId}/update")
    public ModelAndView courseUpdate(@PathVariable String topicId,
                                     @PathVariable String courseId,
                                     @ModelAttribute CourseResource course) {
        String message = "";
        try {
            topicId = validator.sanitiseId(topicId);
            courseId = validator.sanitiseId(courseId);
            course.setTopicId(topicId);
            course.setId(courseId);
            message = escaper.escapeString(courseId) + ": ";
            CourseResource courseUpdate = viewService.saveCourse(course);
            message += "saved.";
        } catch (Exception e) {
            message += "Error saving. ";
        }
        String url = "redirect:/topic/" +
                topicId + "/course" +
                "?message=" + escaper.escapeString(message);
        return new ModelAndView(url);
    }

    @GetMapping("/topic/{topicId}/course/{courseId}/delete")
    public ModelAndView courseDelete(@PathVariable String topicId,
                                     @PathVariable String courseId) {
        String message = "";
        try {
            topicId = validator.sanitiseId(topicId);
            courseId = validator.sanitiseId(courseId);
            message = escaper.escapeString(courseId) + ": ";
            CourseResource courseDeleted = viewService.deleteCourse(topicId, courseId);
            message += "deleted.";
        } catch (Exception e) {
            message += "Error deleting. ";
        }
        String url = "redirect:/topic/" +
                topicId + "/course" +
                "?message=" + escaper.escapeString(message);
        return new ModelAndView(url);
    }

    @RequestMapping("/topic/{topicId}/course/{courseId}/lesson")
    public ModelAndView lessonList(@PathVariable String topicId,
                                   @PathVariable String courseId,
                                   @RequestParam @Nullable String message) {
        try {
            topicId = validator.sanitiseId(topicId);
            courseId = validator.sanitiseId(courseId);
            message = escaper.escapeString(message);
            ViewData data = viewService.listLesson(topicId, courseId, message);
            return new ModelAndView(data.getViewName(),
                    data.withMessage(message).getModel());
        } catch (Throwable t) {
            return new ModelAndView(COURSE.getTemplatePath(),
                    ImmutableMap.of("message",
                            "Error Rendering: " + escaper.escapeException(t)));
        }
    }

    @GetMapping("/topic/{topicId}/course/{courseId}/lesson/{lessonId}")
    public ModelAndView lessonEdit(@PathVariable String topicId,
                                   @PathVariable String courseId,
                                   @PathVariable String lessonId) {
        try {
            topicId = validator.sanitiseId(topicId);
            courseId = validator.sanitiseId(courseId);
            if(ADD.equals(lessonId)){
                lessonId = courseId + ".";
                lessonId += idGenerator.generateId("L", ID_WIDTH);
            }
            lessonId = validator.sanitiseId(lessonId);
            ViewData data = viewService.editLesson(topicId, courseId, lessonId);
            return new ModelAndView(data.getViewName(), data.getModel());
        } catch (Throwable t) {
            return new ModelAndView(LESSON_EDIT.getTemplatePath(),
                    ImmutableMap.of("message",
                            "Error Rendering: " + escaper.escapeException(t)));
        }
    }

    @PostMapping("/topic/{topicId}/course/{courseId}/lesson/{lessonId}/update")
    public ModelAndView lessonUpdate(@PathVariable String topicId,
                                     @PathVariable String courseId,
                                     @PathVariable String lessonId,
                                     @ModelAttribute LessonResource lesson) {
        String message = "";
        try {
            topicId = validator.sanitiseId(topicId);
            courseId = validator.sanitiseId(courseId);
            lessonId = validator.sanitiseId(lessonId);
            lesson.setCourseId(courseId);
            lesson.setId(lessonId);
            message = escaper.escapeString(lessonId) + ": ";
            LessonResource lessonUpdate = viewService.saveLesson(lesson);
            message += "saved.";
        } catch (Exception e) {
            message += "Error saving. ";
        }
        String url = "redirect:/topic/" +
                topicId + "/course/" +
                courseId + "/lesson" +
                "?message=" + escaper.escapeString(message);
        return new ModelAndView(url);
    }

    @GetMapping("/topic/{topicId}/course/{courseId}/lesson/{lessonId}/delete")
    public ModelAndView lessonDelete(@PathVariable String topicId,
                                     @PathVariable String courseId,
                                     @PathVariable String lessonId) {
        String message = "";
        try {
            topicId = validator.sanitiseId(topicId);
            courseId = validator.sanitiseId(courseId);
            lessonId = validator.sanitiseId(lessonId);
            message = escaper.escapeString(lessonId) + ": ";
            LessonResource lessonDeleted = viewService.deleteLesson(topicId, courseId, lessonId);
            message += "deleted.";
        } catch (Exception e) {
            message += "Error deleting. ";
        }
        String url = "redirect:/topic/" +
                topicId + "/course/" +
                courseId + "/lesson" +
                "?message=" + escaper.escapeString(message);
        return new ModelAndView(url);
    }
}

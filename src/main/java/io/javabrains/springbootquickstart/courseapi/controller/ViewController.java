package io.javabrains.springbootquickstart.courseapi.controller;

import com.google.common.collect.ImmutableMap;
import io.javabrains.springbootquickstart.courseapi.resource.TopicResource;
import io.javabrains.springbootquickstart.courseapi.service.ViewService;
import io.javabrains.springbootquickstart.courseapi.view.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static io.javabrains.springbootquickstart.courseapi.view.MessageEscaper.escapeException;
import static io.javabrains.springbootquickstart.courseapi.view.MessageEscaper.escapeString;
import static io.javabrains.springbootquickstart.courseapi.view.ViewName.*;

@Controller
public class ViewController {

    @Autowired
    private ViewService viewService;

    @RequestMapping("/")
    public ModelAndView home() {
        try {
            ViewData data = viewService.listHome(HOME);
            return new ModelAndView(data.getViewName(), data.getModel());
        } catch (Exception e) {
            return new ModelAndView(HOME.getTemplatePath() +
                    "?message=Error Rendering");
        }
    }

    @RequestMapping("/topic")
    public ModelAndView topicList(@RequestParam @Nullable String message) {
        try {
            ViewData data = viewService.listTopic(escapeString(message));
            return new ModelAndView(data.getViewName(),
                    data.withMessage(message).getModel());
        } catch (Throwable t) {
            return new ModelAndView(TOPIC.getTemplatePath(),
                    ImmutableMap.of("message",
                            "Error Rendering: " + escapeException(t)));
        }
    }

    @GetMapping("/topic/{topicId}")
    public ModelAndView topicEdit(@PathVariable String topicId) {
        try {
            ViewData data = viewService.editTopic(escapeString(topicId));
            return new ModelAndView(data.getViewName(), data.getModel());
        } catch (Throwable t) {
            return new ModelAndView(TOPIC_EDIT.getTemplatePath(),
                    ImmutableMap.of("message",
                            "Error Rendering: " + escapeException(t)));
        }
    }

    @PostMapping("/topic/{topicId}/update")
    public ModelAndView topicUpdate(@PathVariable String topicId,
                                    @ModelAttribute TopicResource topic) {
        String message = escapeString(topicId) + ": ";
        try {
            TopicResource topicUpdate = viewService.saveTopic(topic);
            message += "saved.";
        } catch (Exception e) {
            message += "Error saving. ";
        }
        String url = "redirect:/topic" +
                "?message=" + escapeString(message);
        return new ModelAndView(url);
    }

    @GetMapping("/topic/{topicId}/delete")
    public ModelAndView topicDelete(@PathVariable String topicId) {
        String message = escapeString(topicId) + ": ";
        try {
            TopicResource topicUpdate = viewService.deleteTopic(topicId);
            message += "deleted.";
        } catch (Exception e) {
            message += "Error deleting. ";
        }
        String url = "redirect:/topic?message=" + escapeString(message);
        return new ModelAndView(url);
    }
}

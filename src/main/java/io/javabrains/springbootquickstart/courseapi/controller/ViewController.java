package io.javabrains.springbootquickstart.courseapi.controller;

import io.javabrains.springbootquickstart.courseapi.resource.TopicResource;
import io.javabrains.springbootquickstart.courseapi.service.TopicService;
import io.javabrains.springbootquickstart.courseapi.service.ViewService;
import io.javabrains.springbootquickstart.courseapi.view.ViewData;
import io.javabrains.springbootquickstart.courseapi.view.ViewName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @Autowired
    private ViewService viewService;

    @RequestMapping("/")
    public ModelAndView home() {
        ViewData data = viewService.viewData(ViewName.HOME);
        return new ModelAndView(data.getViewName(), data.getModel());
    }

    @GetMapping("/topic")
    public ModelAndView topicList() {
        ViewData data = viewService.viewData(ViewName.TOPIC);
        return new ModelAndView(data.getViewName(), data.getModel());
    }

    @GetMapping("/topic/{topicId}")
    public ModelAndView topicEdit(@PathVariable String topicId) {
        ViewData data = viewService.editTopic(topicId);
        return new ModelAndView(data.getViewName(), data.getModel());
    }

    @PostMapping("/topic/{topicId}")
    public ModelAndView topicSave(@PathVariable String topicId,
                                  @ModelAttribute TopicResource topic) {
        TopicResource topicUpdate = viewService.saveTopic(topic);
        return new ModelAndView("redirect:/topic/" + topicUpdate.getId());
    }
}

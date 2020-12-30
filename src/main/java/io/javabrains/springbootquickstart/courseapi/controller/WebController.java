package io.javabrains.springbootquickstart.courseapi.controller;

import com.google.common.collect.ImmutableMap;
import groovy.lang.GroovySystem;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @RequestMapping("/")
    public ModelAndView home() {
        return new ModelAndView("views/home", ImmutableMap.of(
                "bootVersion",
                Banner.class.getPackage().getImplementationVersion(),
                "groovyVersion", GroovySystem.getVersion()
        ));
    }
}

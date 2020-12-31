package io.courseapi.view;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ViewName {
    HOME(ImmutableList.of(""), "views/home"),
    TOPIC(ImmutableList.of("","topic"), "views/topic/list"),
    TOPIC_EDIT(ImmutableList.of("","topic"), "views/topic/edit"),
    COURSE(ImmutableList.of("","topic","course"), "views/course/list"),
    COURSE_EDIT(ImmutableList.of("","topic","course"), "views/course/edit"),
    LESSON(ImmutableList.of("","topic","course","lesson"), "views/lesson/list"),
    LESSON_EDIT(ImmutableList.of("","topic","course","lesson"), "views/lesson/edit");

    private final List<String> urlPath;
    private final String templatePath;
}

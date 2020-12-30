package io.javabrains.springbootquickstart.courseapi.view;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ViewName {
    HOME(ImmutableList.of("/"), "views/home"),
    TOPIC(ImmutableList.of("/topic"), "views/topic/list"),
    TOPIC_EDIT(ImmutableList.of("/topic"), "views/topic/edit");

    private final List<String> urlPath;
    private final String templatePath;
}

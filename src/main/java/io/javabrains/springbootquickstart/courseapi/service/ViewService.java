package io.javabrains.springbootquickstart.courseapi.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.javabrains.springbootquickstart.courseapi.resource.TopicResource;
import io.javabrains.springbootquickstart.courseapi.view.IdGenerator;
import io.javabrains.springbootquickstart.courseapi.view.ViewData;
import io.javabrains.springbootquickstart.courseapi.view.ViewName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ViewService {

    @Autowired
    TopicService topicService;

    @Autowired
    IdGenerator idGenerator;

    public ViewData listHome(ViewName viewName) {
        return ViewData.builder()
                .urlPath(viewName.getUrlPath())
                .viewName(viewName.getTemplatePath())
                .model(homeList(viewName))
                .build();
    }

    public ViewData listTopic(String message) {
        final ViewName viewName = ViewName.TOPIC;
        return ViewData.builder()
                .urlPath(viewName.getUrlPath())
                .viewName(viewName.getTemplatePath())
                .model(topicList(viewName, message))
                .build();
    }

    public ViewData editTopic(String topicId) {
        final ViewName viewName = ViewName.TOPIC_EDIT;
        return ViewData.builder()
                .urlPath(viewName.getUrlPath())
                .viewName(viewName.getTemplatePath())
                .model(topicEdit(viewName, topicId))
                .build();
    }

    public TopicResource saveTopic(TopicResource topic) {
        return topicService.updateTopicResource(topic);
    }

    public TopicResource deleteTopic(String topicId) {
        return topicService.deleteTopicById(topicId);
    }

    private Map<String, Object> homeList(ViewName viewName) {
        return ImmutableMap.of(
                "breadcrumbs", viewName.getUrlPath()
        );
    }

    private Map<String, Object> topicList(ViewName viewName, String message) {
        try {
            List<TopicResource> allTopicResources = topicService.getAllTopicResources();
            return ImmutableMap.of(
                    "breadcrumbs", viewName.getUrlPath(),
                    "topics", allTopicResources,
                    "message", Optional.ofNullable(message).orElse("")
            );
        } catch(Exception e){
            return ImmutableMap.of(
                    "breadcrumbs", viewName.getUrlPath(),
                    "topics", ImmutableList.of(),
                    "message", Optional.ofNullable(message).orElse("Unknown Error")
            );
        }
    }

    private Map<String, Object> topicEdit(ViewName viewName, String topicId) {
        TopicResource topicResource = topicService
                .findTopicResource(topicId)
                .orElse(TopicResource.builder().build());
        List<String> breadcrumbs = ImmutableList.of(
                viewName.getUrlPath().get(0),
                topicId
        );
        String newId = idGenerator.generateId("T", 6);
        return ImmutableMap.of(
                "breadcrumbs", breadcrumbs,
                "topic", topicResource,
                "newId", newId
        );
    }
}

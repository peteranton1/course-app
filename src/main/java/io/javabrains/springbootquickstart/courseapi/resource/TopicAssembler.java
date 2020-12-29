package io.javabrains.springbootquickstart.courseapi.resource;

import io.javabrains.springbootquickstart.courseapi.model.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.*;

@Component
public class TopicAssembler {

    public List<TopicResource> toResource(List<Topic> allTopics) {
        return allTopics.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    public TopicResource toResource(Topic topic) {
        requireNonNull(topic);
        return TopicResource.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .build();
    }

    public Topic toModel(TopicResource topicResource) {
        return Topic.builder()
                .id(topicResource.getId())
                .name(topicResource.getName())
                .description(topicResource.getDescription())
                .build();
    }
}

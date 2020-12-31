package io.courseapi.resource;

import io.courseapi.model.Topic;
import io.courseapi.view.FieldValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.*;

@Component
public class TopicAssembler {

    private final FieldValidator fieldValidator = new FieldValidator();

    public List<TopicResource> toResource(List<Topic> allTopics) {
        return allTopics.stream()
                .map(this::toResource)
               // .filter(fieldValidator::isValidResource)
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
        if(!fieldValidator.isValidResource(topicResource)){
            throw new IllegalArgumentException("Invalid topic Resource");
        }
        return Topic.builder()
                .id(topicResource.getId())
                .name(topicResource.getName())
                .description(topicResource.getDescription())
                .build();
    }
}

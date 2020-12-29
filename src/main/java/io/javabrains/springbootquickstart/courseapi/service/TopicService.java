package io.javabrains.springbootquickstart.courseapi.service;

import io.javabrains.springbootquickstart.courseapi.model.Topic;
import io.javabrains.springbootquickstart.courseapi.model.TopicRepository;
import io.javabrains.springbootquickstart.courseapi.resource.TopicAssembler;
import io.javabrains.springbootquickstart.courseapi.resource.TopicResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicAssembler topicAssembler;

    public List<TopicResource> getAllTopicResources() {
        List<Topic> allTopics = topicRepository.findAll();
        return topicAssembler.toResource(allTopics);
    }

    public Optional<TopicResource> findTopicResource(String topicId) {
        requireNonNull(topicId);
        return topicRepository.findById(topicId)
                .map(topic -> topicAssembler.toResource(topic));
    }

    public TopicResource updateTopicResource(TopicResource topicResource) {
        requireNonNull(topicResource);
        requireNonNull(topicResource.getId());
        Topic topic = topicAssembler.toModel(topicResource);
        Topic topicUpdated = topicRepository.save(topic);
        return topicAssembler.toResource(topicUpdated);
    }

    public TopicResource deleteTopicById(String topicId) {
        requireNonNull(topicId);
        Topic topic = topicRepository
                .findById(topicId)
                .orElse(null);
        if (nonNull(topic)) {
            topicRepository.delete(topic);
            return topicAssembler.toResource(topic);
        }
        return null;
    }
}

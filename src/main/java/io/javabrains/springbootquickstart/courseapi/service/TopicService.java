package io.javabrains.springbootquickstart.courseapi.service;

import io.javabrains.springbootquickstart.courseapi.model.Topic;
import io.javabrains.springbootquickstart.courseapi.model.TopicRepository;
import io.javabrains.springbootquickstart.courseapi.resource.TopicAssembler;
import io.javabrains.springbootquickstart.courseapi.resource.TopicResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<TopicResource> findTopicResource(String id) {
        return topicRepository.findById(id)
                .map(topic -> topicAssembler.toResource(topic));
    }

    public void addTopicResource(TopicResource topicResource) {
        Topic topic = topicAssembler.toModel(topicResource);
        topicRepository.save(topic);
    }

    public void updateTopicResource(TopicResource topicResource) {
        Topic topic = topicAssembler.toModel(topicResource);
        topicRepository.save(topic);
    }

    public void deleteTopicById(String id) {
        Topic topic = topicRepository.getOne(id);
        topicRepository.delete(topic);
    }
}

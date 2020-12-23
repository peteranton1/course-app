package io.javabrains.springbootquickstart.courseapi.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;
//    private List<Topic> topics = new ArrayList<>(Arrays.asList(
//                Topic.builder().id("spring").name("Spring Framework")
//                        .description("Spring Framework Desc").build()
//                ,Topic.builder().id("java").name("Java Core")
//                        .description("Java Core Desc").build()
//        ));

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<Topic> findTopic(String id) {
        return topicRepository.findById(id);
    }

    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public void updateTopic(String id, Topic topic) {
        topicRepository.save(topic);
    }

    public void deleteTopic(String id) {
        Topic topic = topicRepository.getOne(id);
        topicRepository.delete(topic);
    }
}

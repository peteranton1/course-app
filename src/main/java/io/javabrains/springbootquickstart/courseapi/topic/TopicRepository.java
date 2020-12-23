package io.javabrains.springbootquickstart.courseapi.topic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, String> {

    // getAllTopics()
    // getTopicGivenId(String id)
    // updateTopic(Topic t)
    // deleteTopic(String id)

}

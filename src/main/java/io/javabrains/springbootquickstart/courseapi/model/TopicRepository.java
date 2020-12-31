package io.javabrains.springbootquickstart.courseapi.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, String> {

}

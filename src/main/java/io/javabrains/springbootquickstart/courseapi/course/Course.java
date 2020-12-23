package io.javabrains.springbootquickstart.courseapi.course;

import io.javabrains.springbootquickstart.courseapi.topic.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private String id;
    private String name;
    private String description;

    @ManyToOne
    private Topic topic;
}

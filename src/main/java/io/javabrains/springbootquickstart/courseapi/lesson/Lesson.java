package io.javabrains.springbootquickstart.courseapi.lesson;

import io.javabrains.springbootquickstart.courseapi.course.Course;
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
public class Lesson {
    @Id
    private String id;
    private String name;
    private String description;

    @ManyToOne
    private Course course;
}

package io.javabrains.springbootquickstart.courseapi.course;

import io.javabrains.springbootquickstart.courseapi.topic.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Component
public class CourseAssembler {
    public List<CourseResource> toResource(List<Course> allCourses) {
        return allCourses.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    public CourseResource toResource(Course course) {
        requireNonNull(course);
        return CourseResource.builder()
                .id(course.getId())
                .topicId(ofNullable(course.getTopic())
                        .map(Topic::getId).orElse(""))
                .name(course.getName())
                .description(course.getDescription())
                .build();
    }
}

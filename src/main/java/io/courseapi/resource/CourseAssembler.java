package io.courseapi.resource;

import io.courseapi.model.TopicRepository;
import io.courseapi.model.Course;
import io.courseapi.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.*;
import static java.util.Optional.ofNullable;

@Component
public class CourseAssembler {

    @Autowired
    TopicRepository topicRepository;

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

    public Course toModel(CourseResource courseResource) {
        String topicId = courseResource.getTopicId();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        format("Topic Resource not found, id: %s", topicId)));
        return Course.builder()
                .id(courseResource.getId())
                .name(courseResource.getName())
                .description(courseResource.getDescription())
                .topic(topic)
                .build();
    }
}

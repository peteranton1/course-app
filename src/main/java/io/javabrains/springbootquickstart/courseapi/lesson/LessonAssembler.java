package io.javabrains.springbootquickstart.courseapi.lesson;

import io.javabrains.springbootquickstart.courseapi.course.Course;
import io.javabrains.springbootquickstart.courseapi.course.CourseResource;
import io.javabrains.springbootquickstart.courseapi.topic.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Component
public class LessonAssembler {
    public List<LessonResource> toResource(List<Lesson> lessons) {
        return lessons.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    public LessonResource toResource(Lesson lesson) {
        requireNonNull(lesson);
        return LessonResource.builder()
                .id(lesson.getId())
                .courseId(ofNullable(lesson.getCourse())
                        .map(Course::getId).orElse(""))
                .name(lesson.getName())
                .description(lesson.getDescription())
                .build();
    }
}

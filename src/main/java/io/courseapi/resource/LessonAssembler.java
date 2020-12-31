package io.courseapi.resource;

import io.courseapi.model.Course;
import io.courseapi.model.CourseRepository;
import io.courseapi.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Component
public class LessonAssembler {
    @Autowired
    CourseRepository courseRepository;

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

    public Lesson toModel(LessonResource lessonResource) {
        String courseId = lessonResource.getCourseId();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        format("Course Resource not found, id: %s", courseId)));
        return Lesson.builder()
                .id(lessonResource.getId())
                .name(lessonResource.getName())
                .description(lessonResource.getDescription())
                .course(course)
                .build();
    }
}

package io.courseapi.service;

import io.courseapi.model.Course;
import io.courseapi.resource.CourseAssembler;
import io.courseapi.model.CourseRepository;
import io.courseapi.resource.CourseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseAssembler courseAssembler;

    public List<CourseResource> getAllCourseResources(String topicId) {
        requireNonNull(topicId);
        List<Course> allCourses = courseRepository.findByTopicId(topicId);
        return courseAssembler.toResource(allCourses);
    }

    public Optional<CourseResource> findCourseResource(String topicId, String courseId) {
        requireNonNull(topicId);
        requireNonNull(courseId);
        return courseRepository.findById(courseId)
                .map(course -> courseAssembler.toResource(course));
    }

    public CourseResource updateCourseResource(CourseResource courseResource) {
        requireNonNull(courseResource);
        requireNonNull(courseResource.getId());
        requireNonNull(courseResource.getTopicId());
        Course course = courseAssembler.toModel(courseResource);
        Course courseUpdated = courseRepository.save(course);
        return courseAssembler.toResource(courseUpdated);
    }

    public CourseResource deleteCourseById(String topicId, String courseId) {
        requireNonNull(topicId);
        requireNonNull(courseId);
        Course course = courseRepository
                .findById(courseId)
                .orElse(null);
        if (nonNull(course)) {
            courseRepository.delete(course);
            return courseAssembler.toResource(course);
        }
        return null;
    }
}

package io.javabrains.springbootquickstart.courseapi.service;

import io.javabrains.springbootquickstart.courseapi.model.Course;
import io.javabrains.springbootquickstart.courseapi.model.CourseRepository;
import io.javabrains.springbootquickstart.courseapi.resource.CourseAssembler;
import io.javabrains.springbootquickstart.courseapi.resource.CourseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseAssembler courseAssembler;

    public List<CourseResource> getAllCourseResources(String topicId) {
        List<Course> allCourses = courseRepository.findByTopicId(topicId);
        return courseAssembler.toResource(allCourses);
    }

    public Optional<CourseResource> findCourseResource(String topicId, String courseId) {
        return courseRepository.findById(courseId)
                .map(course -> courseAssembler.toResource(course));
    }

    public void addCourseResource(CourseResource courseResource) {
        Course course = courseAssembler.toModel(courseResource);
        courseRepository.save(course);
    }

    public void updateCourseResource(CourseResource courseResource) {
        Course course = courseAssembler.toModel(courseResource);
        courseRepository.save(course);
    }

    public void deleteCourseById(String courseId) {
        courseRepository.findById(courseId)
                .ifPresent(c -> courseRepository.delete(c));
    }
}

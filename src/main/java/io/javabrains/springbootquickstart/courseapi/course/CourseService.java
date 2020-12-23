package io.javabrains.springbootquickstart.courseapi.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses(String topicId) {
        return courseRepository.findByTopicId(topicId);
    }

    public Optional<Course> findCourse(String topicId, String courseId) {
        return courseRepository.findById(courseId);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(String courseId) {
        courseRepository.findById(courseId)
                .ifPresent(c -> courseRepository.delete(c));
    }
}

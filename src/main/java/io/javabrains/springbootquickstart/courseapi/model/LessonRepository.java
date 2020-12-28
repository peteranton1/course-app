package io.javabrains.springbootquickstart.courseapi.model;

import io.javabrains.springbootquickstart.courseapi.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, String> {

     public List<Lesson> findByCourseId(String courseId);
}

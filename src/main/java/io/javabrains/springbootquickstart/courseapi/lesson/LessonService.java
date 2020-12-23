package io.javabrains.springbootquickstart.courseapi.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> getAllLessons(String courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    public Optional<Lesson> findLesson(String courseId, String lessonId) {
        return lessonRepository.findById(lessonId);
    }

    public void addLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public void updateLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public void deleteLesson(String lessonId) {
        lessonRepository.findById(lessonId)
                .ifPresent(c -> lessonRepository.delete(c));
    }
}

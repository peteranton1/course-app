package io.javabrains.springbootquickstart.courseapi.service;

import io.javabrains.springbootquickstart.courseapi.model.LessonRepository;
import io.javabrains.springbootquickstart.courseapi.model.Lesson;
import io.javabrains.springbootquickstart.courseapi.resource.LessonAssembler;
import io.javabrains.springbootquickstart.courseapi.resource.LessonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonAssembler lessonAssembler;

    public List<LessonResource> getAllLessonResources(String courseId) {
        List<Lesson> allLessons = lessonRepository.findByCourseId(courseId);
        return lessonAssembler.toResource(allLessons);
    }

    public Optional<LessonResource> findLessonResource(String courseId, String lessonId) {
        return lessonRepository.findById(lessonId)
                .map(lesson -> lessonAssembler.toResource(lesson));
    }

    public void addLessonResource(LessonResource lessonResource) {
        Lesson lesson = lessonAssembler.toModel(lessonResource);
        lessonRepository.save(lesson);
    }

    public void updateLessonResource(LessonResource lessonResource) {
        Lesson lesson = lessonAssembler.toModel(lessonResource);
        lessonRepository.save(lesson);
    }

    public void deleteLessonById(String lessonId) {
        lessonRepository.findById(lessonId)
                .ifPresent(c -> lessonRepository.delete(c));
    }
}

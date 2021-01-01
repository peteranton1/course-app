package io.courseapi.service;

import io.courseapi.model.LessonRepository;
import io.courseapi.model.Lesson;
import io.courseapi.resource.LessonAssembler;
import io.courseapi.resource.LessonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonAssembler lessonAssembler;

    public List<LessonResource> getAllLessonResources(String topicId, String courseId) {
        requireNonNull(topicId);
        requireNonNull(courseId);
        List<Lesson> allLessons = lessonRepository.findByCourseId(courseId);
        return lessonAssembler.toResource(allLessons);
    }

    public Optional<LessonResource> findLessonResource(String topicId,
                                                       String courseId,
                                                       String lessonId) {
        requireNonNull(topicId);
        requireNonNull(courseId);
        requireNonNull(lessonId);
        return lessonRepository.findById(lessonId)
                .map(lesson -> lessonAssembler.toResource(lesson));
    }

    public LessonResource updateLessonResource(LessonResource lessonResource) {
        requireNonNull(lessonResource);
        requireNonNull(lessonResource.getId());
        requireNonNull(lessonResource.getCourseId());
        Lesson lesson = lessonAssembler.toModel(lessonResource);
        Lesson lessonUpdated = lessonRepository.save(lesson);
        return lessonAssembler.toResource(lessonUpdated);
    }

    public LessonResource deleteLessonById(String topicId, String courseId, String lessonId) {
        requireNonNull(topicId);
        requireNonNull(courseId);
        requireNonNull(lessonId);
        Lesson lesson = lessonRepository
                .findById(lessonId)
                .orElse(null);
        if (nonNull(lesson)) {
            lessonRepository.delete(lesson);
            return lessonAssembler.toResource(lesson);
        }
        return null;
    }
}

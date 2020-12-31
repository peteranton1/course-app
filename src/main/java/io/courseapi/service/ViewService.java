package io.courseapi.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.courseapi.resource.CourseResource;
import io.courseapi.resource.LessonResource;
import io.courseapi.resource.TopicResource;
import io.courseapi.view.IdGenerator;
import io.courseapi.view.ViewData;
import io.courseapi.view.ViewName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class ViewService {

    @Autowired
    TopicService topicService;

    @Autowired
    CourseService courseService;

    @Autowired
    LessonService lessonService;

    @Autowired
    IdGenerator idGenerator;

    public ViewData listHome(ViewName viewName) {
        return ViewData.builder()
                .urlPath(constructBreadcrumb(viewName))
                .viewName(viewName.getTemplatePath())
                .model(homeList(viewName))
                .build();
    }

    public ViewData listTopic(String message) {
        final ViewName viewName = ViewName.TOPIC;
        return ViewData.builder()
                .urlPath(constructBreadcrumb(viewName))
                .viewName(viewName.getTemplatePath())
                .model(topicList(viewName, message))
                .build();
    }

    public ViewData editTopic(String topicId) {
        final ViewName viewName = ViewName.TOPIC_EDIT;
        return ViewData.builder()
                .urlPath(constructBreadcrumb(viewName, topicId))
                .viewName(viewName.getTemplatePath())
                .model(topicEdit(viewName, topicId))
                .build();
    }

    public TopicResource saveTopic(TopicResource topic) {
        return topicService.updateTopicResource(topic);
    }

    public TopicResource deleteTopic(String topicId) {
        return topicService.deleteTopicById(topicId);
    }

    public ViewData listCourse(String topicId, String message) {
        final ViewName viewName = ViewName.COURSE;
        return ViewData.builder()
                .urlPath(constructBreadcrumb(viewName, topicId))
                .viewName(viewName.getTemplatePath())
                .model(courseList(viewName, topicId, message))
                .build();
    }

    public ViewData editCourse(String topicId, String courseId) {
        final ViewName viewName = ViewName.COURSE_EDIT;
        return ViewData.builder()
                .urlPath(constructBreadcrumb(viewName, topicId, courseId))
                .viewName(viewName.getTemplatePath())
                .model(courseEdit(viewName, topicId, courseId))
                .build();
    }

    public CourseResource saveCourse(CourseResource course) {
        return courseService.updateCourseResource(course);
    }

    public CourseResource deleteCourse(String topicId, String courseId) {
        return courseService.deleteCourseById(topicId, courseId);
    }

    public ViewData listLesson(String topicId, String courseId, String message) {
        final ViewName viewName = ViewName.LESSON;
        return ViewData.builder()
                .urlPath(constructBreadcrumb(viewName, topicId, courseId))
                .viewName(viewName.getTemplatePath())
                .model(lessonList(viewName, topicId, courseId, message))
                .build();
    }

    private Map<String, Object> homeList(ViewName viewName) {
        return ImmutableMap.of(
                "breadcrumbs", constructBreadcrumb(viewName)
        );
    }

    private Map<String, Object> topicList(ViewName viewName, String message) {
        try {
            List<TopicResource> allTopicResources = topicService.getAllTopicResources();
            return ImmutableMap.of(
                    "breadcrumbs", constructBreadcrumb(viewName),
                    "topics", allTopicResources,
                    "message", Optional.ofNullable(message).orElse("")
            );
        } catch(Exception e){
            return ImmutableMap.of(
                    "breadcrumbs", constructBreadcrumb(viewName),
                    "topics", ImmutableList.of(),
                    "message", Optional.ofNullable(message).orElse("Unknown Error")
            );
        }
    }

    private Map<String, Object> courseList(ViewName viewName, String topicId, String message) {
        try {
            List<CourseResource> allCourseResources = courseService.getAllCourseResources(topicId);
            return ImmutableMap.of(
                    "breadcrumbs", constructBreadcrumb(viewName, topicId),
                    "topicId", topicId,
                    "courses", allCourseResources,
                    "message", Optional.ofNullable(message).orElse("")
            );
        } catch(Exception e){
            return ImmutableMap.of(
                    "breadcrumbs", constructBreadcrumb(viewName, topicId),
                    "topicId", topicId,
                    "courses", ImmutableList.of(),
                    "message", Optional.ofNullable(message).orElse("Unknown Error")
            );
        }
    }

    private Map<String, Object> lessonList(ViewName viewName, String topicId, String courseId, String message) {
        try {
            List<LessonResource> allLessonResources = lessonService.getAllLessonResources(topicId, courseId);
            return ImmutableMap.of(
                    "breadcrumbs", constructBreadcrumb(viewName, topicId, courseId),
                    "topicId", topicId,
                    "courseId", courseId,
                    "lessons", allLessonResources,
                    "message", Optional.ofNullable(message).orElse("")
            );
        } catch(Exception e){
            return ImmutableMap.of(
                    "breadcrumbs", constructBreadcrumb(viewName, topicId, courseId),
                    "topicId", topicId,
                    "courseId", courseId,
                    "lessons", ImmutableList.of(),
                    "message", Optional.ofNullable(message).orElse("Unknown Error")
            );
        }
    }

    private List<String> constructBreadcrumb(ViewName viewName) {
        return constructBreadcrumb(viewName, null, null, null);
    }

    private List<String> constructBreadcrumb(ViewName viewName, String topicId) {
        return constructBreadcrumb(viewName, topicId, null, null);
    }

    private List<String> constructBreadcrumb(ViewName viewName, String topicId, String courseId) {
        return constructBreadcrumb(viewName, topicId, courseId, null);
    }

    private List<String> constructBreadcrumb(ViewName viewName,
                                             String topicId,
                                             String courseId,
                                             String lessonId) {
        List<String> input = viewName.getUrlPath();
        List<String> outlet = new ArrayList<>();
        if(isNull(input) || input.size()==0) {
            return outlet;
        }
        addIfPresent(input, outlet, 0); // ""
        addIfPresent(input, outlet, 1); // "topic"
        addIdIfPresent(topicId, outlet);
        addIfPresent(input, outlet, 2); // "course"
        addIdIfPresent(courseId, outlet);
        addIfPresent(input, outlet, 3); // "lesson"
        addIdIfPresent(lessonId, outlet);

        return ImmutableList.<String>builder().addAll(outlet).build();
    }

    private void addIdIfPresent(String id, List<String> outlet) {
        if(nonNull(id)) {
            outlet.add(id);
        }
    }

    private void addIfPresent(List<String> input, List<String> outlet, int i) {
        if(input.size() > i ) {
            outlet.add(input.get(i));
        }
    }

    private Map<String, Object> topicEdit(ViewName viewName, String topicId) {
        TopicResource topicResource = topicService
                .findTopicResource(topicId)
                .orElse(TopicResource.builder().build());
        String newId = idGenerator.generateId("T", 6);
        return ImmutableMap.of(
                "breadcrumbs", constructBreadcrumb(viewName, topicId),
                "topic", topicResource,
                "newId", newId
        );
    }

    private Map<String, Object> courseEdit(ViewName viewName, String topicId, String courseId) {
        CourseResource courseResource = courseService
                .findCourseResource(topicId, courseId)
                .orElse(CourseResource.builder().build());
        String newId = idGenerator.generateId("T", 6);
        return ImmutableMap.of(
                "breadcrumbs", constructBreadcrumb(viewName, topicId, courseId),
                "topicId", topicId,
                "course", courseResource,
                "newId", newId
        );
    }
}

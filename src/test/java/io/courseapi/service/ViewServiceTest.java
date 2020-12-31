package io.courseapi.service;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.courseapi.resource.CourseResource;
import io.courseapi.resource.TopicResource;
import io.courseapi.view.IdGenerator;
import io.courseapi.view.ViewData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static io.courseapi.view.ViewName.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ViewServiceTest {

    @Mock
    private TopicService topicService;

    @Mock
    private CourseService courseService;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private ViewService underTest;

    @Test
    void shouldListHomeWhenOk() {
        ViewData expected = ViewData.builder()
                .urlPath(HOME.getUrlPath())
                .viewName(HOME.getTemplatePath())
                .model(ImmutableMap.of("breadcrumbs", HOME.getUrlPath()))
                .build();

        ViewData actual = underTest.listHome(HOME);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldListTopicWhenOk() {
        when(idGenerator.generateId(any(), anyInt())).thenReturn("X");
        List<TopicResource> topics = ImmutableList.of();
        when(topicService.getAllTopicResources()).thenReturn(topics);
        List<String> urlPath = ImmutableList.of("", "topic");

        ViewData expected = ViewData.builder()
                .urlPath(urlPath)
                .viewName(TOPIC.getTemplatePath())
                .model(ImmutableMap.of(
                        "breadcrumbs", urlPath,
                        "topics", topics,
                        "message", ""))
                .build();

        String message = null;
        ViewData actual = underTest.listTopic(message);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldEditTopicWhenOk() {
        when(idGenerator.generateId(any(), anyInt())).thenReturn("X");
        ImmutableList<TopicResource> topics = ImmutableList.of();
        when(topicService.getAllTopicResources()).thenReturn(topics);

        List<String> urlPath = ImmutableList.of("", "topic", "X");
        TopicResource topic = TopicResource.builder()
                .build();
        ViewData expected = ViewData.builder()
                .urlPath(urlPath)
                .viewName(TOPIC_EDIT.getTemplatePath())
                .model(ImmutableMap.of(
                        "breadcrumbs", urlPath,
                        "topic", topic,
                        "newId", "X"))
                .build();

        String topicId = "X";
        ViewData actual = underTest.editTopic(topicId);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldListCourseWhenOk() {
        String topicId = "Y";
        when(idGenerator.generateId(any(), anyInt())).thenReturn("X");
        ImmutableList<CourseResource> courses = ImmutableList.of();
        when(courseService.getAllCourseResources(topicId)).thenReturn(courses);

        ImmutableList<String> urlPath = ImmutableList.of("", "topic", topicId, "course");
        String message = "";
        ViewData expected = ViewData.builder()
                .urlPath(urlPath)
                .viewName(COURSE.getTemplatePath())
                .model(ImmutableMap.of(
                        "breadcrumbs", urlPath,
                        "topicId", topicId,
                        "courses", courses,
                        "message", message))
                .build();

        ViewData actual = underTest.listCourse(topicId, message);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldEditCourseWhenOk() {
        when(idGenerator.generateId(any(), anyInt())).thenReturn("X");
        String topicId = "Y";
        String courseId = "X";
        CourseResource course = CourseResource.builder()
                .id(courseId)
                .name(courseId + "name")
                .description(courseId + "Desc")
                .build();
        when(courseService.findCourseResource(topicId,courseId)).thenReturn(Optional.of(course));

        List<String> urlPath = ImmutableList.of("", "topic", topicId, "course", courseId);
        ViewData expected = ViewData.builder()
                .urlPath(urlPath)
                .viewName(COURSE_EDIT.getTemplatePath())
                .model(ImmutableMap.of(
                        "breadcrumbs", urlPath,
                        "topicId", topicId,
                        "course", course,
                        "newId", "X"))
                .build();

        ViewData actual = underTest.editCourse(topicId, courseId);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
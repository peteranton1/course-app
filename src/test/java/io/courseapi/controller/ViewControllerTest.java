package io.courseapi.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.courseapi.resource.CourseResource;
import io.courseapi.resource.LessonResource;
import io.courseapi.resource.TopicResource;
import io.courseapi.service.ViewService;
import io.courseapi.view.FieldValidator;
import io.courseapi.view.ViewData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import static io.courseapi.view.ViewName.HOME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ViewControllerTest {

    @Mock
    private ViewService viewService;

    @Mock
    private FieldValidator validator;

    @InjectMocks
    private ViewController underTest;

    @Test
    void shouldHomeWhenOk() {
        ImmutableMap<String, Object> modelMap = ImmutableMap.of("X1key", "X1value");
        ImmutableList<String> urlPath = ImmutableList.of("");
        String viewName = "home";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        when(viewService.listHome(HOME)).thenReturn(viewData);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.home();

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldTopicListWhenOk() {
        String message = "";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of(
                "X1key", "X1value",
                "message", message);
        ImmutableList<String> urlPath = ImmutableList.of("", "topic");
        String viewName = "views/topic/list";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        when(viewService.listTopic(any())).thenReturn(viewData);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.topicList(message);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldCourseListWhenOk() {
        String message = "";
        String topicId = "X";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of(
                "X1key", "X1value",
                "message", message);
        ImmutableList<String> urlPath = ImmutableList.of("", "topic", topicId);
        String viewName = "views/course/list";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(viewService.listCourse(topicId, message)).thenReturn(viewData);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.courseList(topicId, message);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldLessonListWhenOk() {
        String message = "";
        String topicId = "X";
        String courseId = "Y";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of(
                "X1key", "X1value",
                "message", message);
        ImmutableList<String> urlPath = ImmutableList.of("", "topic", topicId, "course", courseId, "lesson");
        String viewName = "views/lesson/list";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(validator.sanitiseId(courseId)).thenReturn(courseId);
        when(viewService.listLesson(topicId, courseId, message)).thenReturn(viewData);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.lessonList(topicId, courseId, message);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldTopicEditWhenOk() {
        String message = "";
        String topicId = "X";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of(
                "X1key", "X1value",
                "message", message);
        ImmutableList<String> urlPath = ImmutableList.of("", "topic");
        String viewName = "views/topic/edit";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(viewService.editTopic(topicId)).thenReturn(viewData);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.topicEdit(topicId);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldCourseEditWhenOk() {
        String message = "";
        String topicId = "X";
        String courseId = "Y";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of(
                "X1key", "X1value",
                "message", message);
        ImmutableList<String> urlPath = ImmutableList.of("", "topic", topicId, "course", courseId);
        String viewName = "views/course/edit";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(validator.sanitiseId(courseId)).thenReturn(courseId);
        when(viewService.editCourse(topicId, courseId)).thenReturn(viewData);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.courseEdit(topicId, courseId);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldLessonEditWhenOk() {
        String message = "";
        String topicId = "X";
        String courseId = "Y";
        String lessonId = "Z";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of(
                "X1key", "X1value",
                "message", message);
        ImmutableList<String> urlPath = ImmutableList.of("",
                "topic", topicId, "course", courseId, "lesson", lessonId);
        String viewName = "views/lesson/edit";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(validator.sanitiseId(courseId)).thenReturn(courseId);
        when(validator.sanitiseId(lessonId)).thenReturn(lessonId);
        when(viewService.editLesson(topicId, courseId, lessonId)).thenReturn(viewData);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.lessonEdit(topicId, courseId, lessonId);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldTopicUpdateWhenOk() {
        String message = "";
        String topicId = "X";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of();
        ImmutableList<String> urlPath = ImmutableList.of("", "topic");
        String viewName = "redirect:/topic?message=X: saved.";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        TopicResource topic = TopicResource.builder().build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(viewService.saveTopic(topic)).thenReturn(topic);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.topicUpdate(topicId, topic);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldCourseUpdateWhenOk() {
        String message = "";
        String topicId = "X";
        String courseId = "Y";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of();
        ImmutableList<String> urlPath = ImmutableList.of("", "topic", topicId, "course", courseId, "update");
        String viewName = "redirect:/topic/X/course?message=Y: saved.";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        CourseResource course = CourseResource.builder().build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(validator.sanitiseId(courseId)).thenReturn(courseId);
        when(viewService.saveCourse(course)).thenReturn(course);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.courseUpdate(topicId, courseId, course);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldLessonUpdateWhenOk() {
        String message = "";
        String topicId = "X";
        String courseId = "Y";
        String lessonId = "Z";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of();
        ImmutableList<String> urlPath = ImmutableList.of("",
                "topic", topicId, "course", courseId, "lesson", lessonId, "update");
        String viewName = "redirect:/topic/X/course/Y/lesson?message=Z: saved.";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        LessonResource lesson = LessonResource.builder().build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(validator.sanitiseId(courseId)).thenReturn(courseId);
        when(validator.sanitiseId(lessonId)).thenReturn(lessonId);
        when(viewService.saveLesson(lesson)).thenReturn(lesson);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.lessonUpdate(topicId, courseId, lessonId, lesson);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldTopicDeleteWhenOk() {
        String message = "";
        String topicId = "X";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of();
        ImmutableList<String> urlPath = ImmutableList.of("", "topic");
        String viewName = "redirect:/topic?message=X: deleted.";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        TopicResource topic = TopicResource.builder().build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(viewService.deleteTopic(topicId)).thenReturn(topic);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.topicDelete(topicId);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldCourseDeleteWhenOk() {
        String message = "";
        String topicId = "X";
        String courseId = "Y";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of();
        ImmutableList<String> urlPath = ImmutableList.of("", "topic", topicId, "course", courseId, "delete");
        String viewName = "redirect:/topic/X/course?message=Y: deleted.";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        CourseResource course = CourseResource.builder().build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(validator.sanitiseId(courseId)).thenReturn(courseId);
        when(viewService.deleteCourse(topicId, courseId)).thenReturn(course);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.courseDelete(topicId, courseId);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }

    @Test
    void shouldLessonDeleteWhenOk() {
        String message = "";
        String topicId = "X";
        String courseId = "Y";
        String lessonId = "Z";
        ImmutableMap<String, Object> modelMap = ImmutableMap.of();
        ImmutableList<String> urlPath = ImmutableList.of("",
                "topic", topicId, "course", courseId, "lesson", lessonId, "delete");
        String viewName = "redirect:/topic/X/course/Y/lesson?message=Z: deleted.";
        ViewData viewData = ViewData.builder()
                .viewName(viewName)
                .urlPath(urlPath)
                .model(modelMap)
                .build();
        LessonResource lesson = LessonResource.builder().build();
        when(validator.sanitiseId(topicId)).thenReturn(topicId);
        when(validator.sanitiseId(courseId)).thenReturn(courseId);
        when(validator.sanitiseId(lessonId)).thenReturn(lessonId);
        when(viewService.deleteLesson(topicId, courseId, lessonId)).thenReturn(lesson);
        ModelAndView expected = new ModelAndView(
                viewData.getViewName(), viewData.getModel());

        ModelAndView actual = underTest.lessonDelete(topicId, courseId, lessonId);

        Assertions.assertThat(actual.getViewName()).isEqualTo(expected.getViewName());
        Assertions.assertThat(actual.getModel()).isEqualTo(expected.getModel());
    }
}
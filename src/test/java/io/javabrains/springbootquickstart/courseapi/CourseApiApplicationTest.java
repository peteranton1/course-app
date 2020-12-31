package io.javabrains.springbootquickstart.courseapi;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseApiApplicationTest {

    private static final String TEST_HOST = "http://localhost:9020";
    private static ConfigurableApplicationContext context;

    @BeforeAll
    public static void start() throws Exception {
        Future<ConfigurableApplicationContext> future = Executors
                .newSingleThreadExecutor().submit(
                        () -> SpringApplication.run(CourseApiApplication.class)
                );
        context = future.get(Duration.ofSeconds(60)
                .getSeconds(), TimeUnit.SECONDS);
    }

    @AfterAll
    public static void stop() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    public void testHome() throws Exception {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(
                TEST_HOST, String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        String expectedBody = "<!DOCTYPE html><html><head>" +
                "<title>Topics, Courses, Lessons -  []</title>" +
                "<meta http-equiv='\"Content-Type\" content=\"text/html; charset=utf-8\"'/>" +
                "<link rel='stylesheet' href='/css/bootstrap.min.css'/></head>" +
                "<body class='body'><div class='container'><div class='navbar'>" +
                "<div class='navbar-inner'><a class='brand' href='/'>Topics, Courses and Lessons </a><var/>" +
                "<a class='brand' href=''></a></div></div>" +
                "<table class='table'><tr><td></td></tr></table>" +
                "<table class='table'><tr><td><div class=''>" +
                "<div>Maintain the lists of topics, courses and lessons.</div>" +
                "<a class='brand' href='/topic'>See Topics, Courses and Lessons </a>" +
                "</div></td></tr></table>" +
                "<table class='table'><tr><td></td></tr></table></div></body></html>";
        String actualBody = entity.getBody();
        assertThat(actualBody).contains(expectedBody);
    }

    @Test
    public void testHello() throws Exception {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(
                TEST_HOST + "/hello", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        String expectedBody = "Hi!";
        String actualBody = entity.getBody();
        assertThat(actualBody).contains(expectedBody);
    }

    @Test
    public void testTopicList() throws Exception {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(
                TEST_HOST + "/topic", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        String expectedBodyFrag1 = "<!DOCTYPE html><html><head>" +
                "<title>Topics, Courses, Lessons -  [, topic]</title>" +
                "<meta http-equiv='\"Content-Type\" content=\"text/html; charset=utf-8\"'/>" +
                "<link rel='stylesheet' href='/css/bootstrap.min.css'/></head><body class='body'>" +
                "<div class='container'><div class='navbar'><div class='navbar-inner'>" +
                "<a class='brand' href='/'>Topics, Courses and Lessons </a><var/>" +
                "<a class='brand' href='/topic'>/topic</a></div></div>" +
                "<table class='table'><tr><td></td></tr></table>" +
                "<table class='table'><tr><td><ul>";
        String expectedBodyFrag2 = "<li><a href='/topic/java101'>Java 101 : Java 101 Introductory topic</a></li>";
        String expectedBodyFrag3 = "<a href='/topic/add'>Add new topic</a>";
        String actualBody = entity.getBody();
        assertThat(actualBody).contains(expectedBodyFrag1);
        assertThat(actualBody).contains(expectedBodyFrag2);
        assertThat(actualBody).contains(expectedBodyFrag3);
    }

    @Test
    public void testTopicEdit() throws Exception {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(
                TEST_HOST + "/topic/java101", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        String expectedBodyFrag1 = "<!DOCTYPE html><html><head><title>" +
                "Topics, Courses, Lessons -  [, topic, java101]</title>" +
                "<meta http-equiv='\"Content-Type\" content=\"text/html; charset=utf-8\"'/>" +
                "<link rel='stylesheet' href='/css/bootstrap.min.css'/></head><body class='body'>" +
                "<div class='container'><div class='navbar'><div class='navbar-inner'>" +
                "<a class='brand' href='/'>Topics, Courses and Lessons </a><var/>" +
                "<a class='brand' href='/topic/java101'>/topic/java101</a></div></div>" +
                "<table class='table'><tr><td></td></tr></table>" +
                "<table class='table'><tr><td>";
        String expectedBodyFrag2 = "<div class=''>" +
                "<form id='editForm' action='/topic/java101/update' method='post'>" +
                "<input name='id' type='hidden' value='java101'/>" +
                "<label for='Name'>Name</label><input name='name' type='text' value='Java 101'/>" +
                "<label for='Description'>Description</label>" +
                "<input name='description' type='text' value='Java 101 Introductory topic'/>" +
                "<div class='form-actions'><input type='submit' value='Submit'/></div></form></div>" ;
        String expectedBodyFrag3 = "<div class=''><div>Maintain the lists of courses and lessons.</div>" +
                "<a class='brand' href='/topic/java101/course'>See Courses for Topic java101</a>" +
                "<div>&nbsp;</div>" +
                "<div>Remove Topic Java 101: Java 101 Introductory topic " +
                "(only if all courses removed first)</div>" +
                "<a class='brand' href='/topic/java101/delete'>" +
                "Delete Topic Java 101: Java 101 Introductory topic</a>" +
                "</div></td></tr></table>" +
                "<table class='table'><tr><td></td></tr></table></div></body></html>";

        String actualBody = entity.getBody();

        assertThat(actualBody).contains(expectedBodyFrag1);
        assertThat(actualBody).contains(expectedBodyFrag2);
        assertThat(actualBody).contains(expectedBodyFrag3);
    }

    @Test
    public void testCourseList() throws Exception {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(
                TEST_HOST + "/topic/java101/course", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        String expectedBodyFrag1 = "<!DOCTYPE html><html><head>" +
                "<title>List Courses [, topic, java101, course]</title>" +
                "<meta http-equiv='\"Content-Type\" content=\"text/html; charset=utf-8\"'/>" +
                "<link rel='stylesheet' href='/css/bootstrap.min.css'/></head><body class='body'>" +
                "<div class='container'><div class='navbar'><div class='navbar-inner'>" +
                "<a class='brand' href='/'>Topics, Courses and Lessons </a><var/>" +
                "<a class='brand' href='/topic/java101/course'>/topic/java101/course</a></div></div>" +
                "<table class='table'><tr><td></td></tr></table>" +
                "<table class='table'><tr><td><ul>";
        String expectedBodyFrag2 = "<li><a href='/topic/java101/course/java101.01'>" +
                "Java 101.01 : Java 101 Introductory course 01</a></li>";
        String expectedBodyFrag3 = "<a href='/topic/java101/course/add'>Add new course</a>";
        String actualBody = entity.getBody();
        assertThat(actualBody).contains(expectedBodyFrag1);
        assertThat(actualBody).contains(expectedBodyFrag2);
        assertThat(actualBody).contains(expectedBodyFrag3);
    }

    @Test
    public void testCourseEdit() throws Exception {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(
                TEST_HOST + "/topic/java101/course/java101.01", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        String expectedBodyFrag1 = "<!DOCTYPE html><html><head>" +
                "<title>Topics, Courses, Lessons -  [, topic, java101, course, java101.01]</title>" +
                "<meta http-equiv='\"Content-Type\" content=\"text/html; charset=utf-8\"'/>" +
                "<link rel='stylesheet' href='/css/bootstrap.min.css'/></head><body class='body'>" +
                "<div class='container'><div class='navbar'><div class='navbar-inner'>" +
                "<a class='brand' href='/'>Topics, Courses and Lessons </a><var/>" +
                "<a class='brand' href='/topic/java101/course/java101.01'>" +
                "/topic/java101/course/java101.01</a></div></div>" +
                "<table class='table'><tr><td></td></tr></table>" ;
        String expectedBodyFrag2 = "<div class=''>" +
                "<form id='editForm' action='/topic/java101/course/java101.01/update' method='post'>" +
                "<input name='id' type='hidden' value='java101.01'/>" +
                "<label for='Name'>Name</label><input name='name' type='text' value='Java 101.01'/>" +
                "<label for='Description'>Description</label>" +
                "<input name='description' type='text' value='Java 101 Introductory course 01'/>" +
                "<div class='form-actions'><input type='submit' value='Submit'/></div></form>" +
                "</div>" ;
        String expectedBodyFrag3 = "<div class=''><div>Maintain the lists of lessons.</div>" +
                "<a class='brand' href='/topic/java101/course/java101.01/lesson'>" +
                "See Lessons for Course java101.01</a><div>&nbsp;</div>" +
                "<div>Remove Course Java 101.01: Java 101 Introductory course 01 " +
                "(only if all lessons removed first)</div" +
                "><a class='brand' href='/topic/java101/course/java101.01/delete'>" +
                "Delete Topic Java 101.01: Java 101 Introductory course 01</a></div></td></tr></table>" +
                "<table class='table'><tr><td></td></tr></table></div></body></html>";
        String actualBody = entity.getBody();
        assertThat(actualBody).contains(expectedBodyFrag1);
        assertThat(actualBody).contains(expectedBodyFrag2);
        assertThat(actualBody).contains(expectedBodyFrag3);
    }

    @Test
    void testErrorPageDirectAccess() throws Exception {
        RestTemplate restTemplate = getRestTemplate();
        HttpServerErrorException actualException = null;

        try {
            restTemplate.getForEntity(TEST_HOST + "/error", Map.class);
        } catch (HttpServerErrorException e) {
            actualException = e;
        }

        assertThat(actualException).isNotNull();
        HttpStatus actualStatus = actualException.getStatusCode();
        assertThat(HttpStatus.INTERNAL_SERVER_ERROR).isEqualTo(actualStatus);

        String expectedBodyFrag1 = "<html><body><h1>Whitelabel Error Page</h1>" +
                "<p>This application has no explicit mapping for /error, " +
                "so you are seeing this as a fallback.</p>";
        String actualBody = actualException.getResponseBodyAsString();
        assertThat(actualBody).contains(expectedBodyFrag1);
    }


    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(singletonList(MediaType.TEXT_HTML));
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

}
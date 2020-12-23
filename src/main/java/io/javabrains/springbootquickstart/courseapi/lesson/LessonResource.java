package io.javabrains.springbootquickstart.courseapi.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class LessonResource extends RepresentationModel<LessonResource> {
    String id;
    String courseId;
    String name;
    String description;

    @JsonCreator
    public LessonResource(
            @JsonProperty("id") String id,
            @JsonProperty("courseId") String courseId,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.description = description;
    }
}

package io.courseapi.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CourseResource extends RepresentationModel<CourseResource> {
    @Setter
    String id;
    @Setter
    String topicId;
    String name;
    String description;

    @JsonCreator
    public CourseResource(
            @JsonProperty("id") String id,
            @JsonProperty("topicId") String topicId,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        this.id = id;
        this.topicId = topicId;
        this.name = name;
        this.description = description;
    }
}

package io.javabrains.springbootquickstart.courseapi.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class TopicResource extends RepresentationModel<TopicResource> {
    @Setter
    String id;
    String name;
    String description;

    @JsonCreator
    public TopicResource(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

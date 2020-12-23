package io.javabrains.springbootquickstart.courseapi.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private String id;
    private String name;
    private String description;
}

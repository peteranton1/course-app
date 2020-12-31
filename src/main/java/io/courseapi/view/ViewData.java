package io.courseapi.view;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class ViewData {
    List<String> urlPath;
    String viewName;
    Map<String, Object> model;

    public ViewData withMessage(String message) {
        Map<String, Object> modelTemp = new HashMap<>(model);
        modelTemp.putIfAbsent("message", MessageEscaper.escaper.escapeString(message));
        return ViewData.builder()
                .urlPath(urlPath)
                .viewName(viewName)
                .model(modelTemp)
                .build();
    }
}

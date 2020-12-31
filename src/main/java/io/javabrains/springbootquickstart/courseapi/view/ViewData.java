package io.javabrains.springbootquickstart.courseapi.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder
@AllArgsConstructor
public class ViewData {
    List<String> urlPath;
    String viewName;
    Map<String, Object> model;

    public ViewData withMessage(String message) {
        Map<String, Object> modelTemp = new HashMap<>(model);
        modelTemp.putIfAbsent("message", MessageEscaper.escapeString(message));
        return ViewData.builder()
                .urlPath(urlPath)
                .viewName(viewName)
                .model(modelTemp)
                .build();
    }
}

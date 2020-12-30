package io.javabrains.springbootquickstart.courseapi.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
@AllArgsConstructor
public class ViewData {
     List<String> urlPath;
     String viewName;
     Map<String, Object> model;
}

package io.courseapi.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionName {
    ADD("add"),
    UPDATE("update"),
    DELETE("delete");
    private final String action;
}

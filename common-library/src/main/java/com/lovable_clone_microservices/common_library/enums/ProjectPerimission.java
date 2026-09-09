package com.lovable_clone_microservices.common_library.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProjectPerimission {
    VIEW("project:view"),
    EDIT("project:edit"),
    DELETE("project:delete"),
    MANAGE_MEMBERS("project:manage_members"),
    VIEW_MEMBERS("project:view:members");

    private final String value;
}

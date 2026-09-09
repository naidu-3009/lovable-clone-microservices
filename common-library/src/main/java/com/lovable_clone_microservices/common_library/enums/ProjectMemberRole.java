package com.lovable_clone_microservices.common_library.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.lovable_clone_microservices.common_library.enums.ProjectPerimission.*;


@RequiredArgsConstructor
@Getter
public enum ProjectMemberRole {
    EDITOR(VIEW,EDIT,DELETE,VIEW_MEMBERS),//this type uses varchar construtor
    VIEWER(Set.of(VIEW,VIEW_MEMBERS)),//this uses the default one given by required args construtor
    OWNER(VIEW,EDIT,DELETE,MANAGE_MEMBERS,VIEW_MEMBERS);

    ProjectMemberRole(ProjectPerimission... permissions) {   //using varchar
        this.permissions=Set.of(permissions);
    }

    private final Set<ProjectPerimission> permissions;
}

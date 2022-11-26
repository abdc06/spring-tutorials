package me.abdc.springsecurity.security;

import lombok.AllArgsConstructor;

import java.util.Set;

import static me.abdc.springsecurity.security.ApplicationUserPermission.*;

@AllArgsConstructor
public enum ApplicationUserRole {
    STUDENT(Set.of()),
    ADMIN(Set.of(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Set.of(COURSE_READ, STUDENT_READ));

    private final Set<ApplicationUserPermission> permissions;
}

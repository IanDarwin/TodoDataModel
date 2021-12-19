package com.darwinsys.todo.test;

import com.darwinsys.todo.model.Project;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class ProjectTest {

    @Test
    public void testCache() {
        Project p1 = Project.of("Helping");
        Project.of("Analyzing");
        Project p3 = Project.of("Helping");
        assertSame(p1, p3);
    }
}

package com.darwinsys.todo.test;

import com.darwinsys.todo.model.Context;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class ContextTest {

    @Test
    public void testCache() {
        Context p1 = Context.of("Home Alone");
        Context.of("Office");
        Context p3 = Context.of("Home Alone");
        assertSame(p1, p3);
    }
}

package com.raymonde.render;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RendererFactoryTest {

    @Test
    public void shouldReturnTheDefaultRenderer() {
        // Expect
        assertThat(RendererFactory.createRenderer("default")).isInstanceOf(DefaultRenderer.class);
    }

    @Test
    public void shouldReturnTheMultiThreadedRenderer() {
        // Expect
        assertThat(RendererFactory.createRenderer("multi-threaded")).isInstanceOf(MultiThreadedRenderer.class);
    }

    @Test(expected = UnableToCreateRendererException.class)
    public void shouldRaiseExceptionIfRenderedIsUnkown() {
        // When
        RendererFactory.createRenderer("unknown-rendered");

        // Expect exception
    }
}
package com.raymonde.render;

import com.raymonde.exception.RayMondeException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RendererFactoryTest {

    @Test
    public void shouldReturnTheDefaultRenderer() throws RayMondeException {
        // Expect
        assertThat(RendererFactory.createRenderer("default")).isInstanceOf(DefaultRenderer.class);
    }

    @Test
    public void shouldReturnTheMultiThreadedRenderer() throws RayMondeException {
        // Expect
        assertThat(RendererFactory.createRenderer("multi-threaded")).isInstanceOf(MultiThreadedRenderer.class);
    }

    @Test(expected = RayMondeException.class)
    public void shouldRaiseExceptionIfRenderedIsUnkown() throws RayMondeException {
        // Expect
        assertThat(RendererFactory.createRenderer("unknown-rendered")).isInstanceOf(MultiThreadedRenderer.class);
    }
}
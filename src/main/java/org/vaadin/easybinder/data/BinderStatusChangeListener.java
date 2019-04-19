package org.vaadin.easybinder.data;

import com.vaadin.flow.function.SerializableEventListener;

@FunctionalInterface
public interface BinderStatusChangeListener extends SerializableEventListener {
    void statusChange(BinderStatusChangeEvent event);
}

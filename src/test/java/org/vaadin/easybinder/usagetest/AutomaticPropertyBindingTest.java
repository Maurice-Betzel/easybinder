package org.vaadin.easybinder.usagetest;

import java.util.stream.Stream;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Label;
import org.junit.BeforeClass;
import org.vaadin.easybinder.data.AutoBinder;
import org.vaadin.easybinder.testentity.Flight;


public class AutomaticPropertyBindingTest extends BaseTests {

    static AutoBinder<Flight> binder = new AutoBinder<>(Flight.class);

    @BeforeClass
    public static void setup() {
        binder.bindInstanceFields(form);
    }

    @Override
    protected void setBean(Flight flight) {
        binder.setBean(flight);
    }

    @Override
    protected Stream<HasValue<?, ?>> getFields() {
        return binder.getFields();
    }

    @Override
    protected boolean isValid() {
        return binder.isValid();
    }

    @Override
    protected void setStatusLabel(Label label) {
        binder.setStatusLabel(label);
    }

}

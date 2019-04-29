package org.vaadin.easybinder.usagetest;

import java.util.stream.Stream;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.BeforeClass;
import org.vaadin.easybinder.data.AutoBinder;
import org.vaadin.easybinder.testentity.Flight;
import org.vaadin.easybinder.testentity.FlightId.LegType;


public class BuildAndBindTest extends BaseTests {

    static AutoBinder<Flight> binder = new AutoBinder<>(Flight.class);

    @SuppressWarnings("unchecked")
    @BeforeClass
    public static void setup() {
        binder.buildAndBind("flightId");

        form.airline = (TextField) binder.getFieldForProperty("flightId.airline").get();
        form.flightNumber = (TextField) binder.getFieldForProperty("flightId.flightNumber").get();
        form.flightSuffix = (TextField) binder.getFieldForProperty("flightId.flightSuffix").get();
        form.date = (DatePicker) binder.getFieldForProperty("flightId.date").get();
        form.legType = (AbstractSinglePropertyField<ComboBox<LegType>, LegType>) binder.getFieldForProperty("flightId.legType").get();
//		form.sbt = (DateTimeField) binder.getFieldForProperty("sbt").get();
//		form.ebt = (DateTimeField) binder.getFieldForProperty("ebt").get();
//		form.abt = (DateTimeField) binder.getFieldForProperty("abt").get();
        form.gate = (TextField) binder.getFieldForProperty("gate").get();
        form.canceled = (Checkbox) binder.getFieldForProperty("canceled").get();
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

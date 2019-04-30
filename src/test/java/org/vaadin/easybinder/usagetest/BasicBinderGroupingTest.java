package org.vaadin.easybinder.usagetest;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.StatusChangeListener;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import org.junit.Test;
import org.vaadin.easybinder.data.BasicBinder;
import org.vaadin.easybinder.data.BinderStatusChangeListener;


public class BasicBinderGroupingTest {
	public static interface MyGroup {
	}

	public static class MyEntityGroupProvider implements DefaultGroupSequenceProvider<MyEntity> {
		@Override
		public List<Class<?>> getValidationGroups(final MyEntity object) {
			final List<Class<?>> classes = new ArrayList<>();

			classes.add(MyEntity.class);

			if (object != null && object.s1 != null) {
				classes.add(MyGroup.class);
			}

			return classes;
		}
	}

	@GroupSequenceProvider(MyEntityGroupProvider.class)
	public static class MyEntity {
		String s1;
		@NotNull(groups = MyGroup.class)
		String s2;

		public String getS1() {
			return s1;
		}

		public void setS1(String s1) {
			this.s1 = s1;
		}

		public String getS2() {
			return s2;
		}

		public void setS2(String s2) {
			this.s2 = s2;
		}

	}

	public static class MyEntity2 {
		@NotNull(groups = MyGroup.class)
		String s1;

		public String getS1() {
			return s1;
		}

		public void setS1(String s1) {
			this.s1 = s1;
		}
	}

	@Test
	public void testGroupValidationGroupProvider() {
		TextField field1 = new TextField();
		TextField field2 = new TextField();
		Label statusLabel = new Label();

		BasicBinder<MyEntity> binder = new BasicBinder<>();

		BinderStatusChangeListener binderStatusChangeListener = mock(BinderStatusChangeListener.class);
		binder.addStatusChangeListener(binderStatusChangeListener);

		binder.bind(field1, d -> d.getS1() == null ? "" : d.getS1(), (e, f) -> e.setS1("".equals(f) ? null : f), "s1");
		binder.bind(field2, d -> d.getS2() == null ? "" : d.getS2(), (e, f) -> e.setS2("".equals(f) ? null : f), "s2");

		binder.setStatusLabel(statusLabel);

		reset(binderStatusChangeListener);

		MyEntity bean = new MyEntity();

		assertTrue(binder.isValid());

		bean.setS1("test");

		assertTrue(binder.isValid());

		binder.setBean(bean);

		verify(binderStatusChangeListener, atLeast(1)).statusChange(assertArg(sc -> assertTrue(sc.hasValidationErrors())));

		assertFalse(binder.isValid());

		reset(binderStatusChangeListener);

		field1.setValue("");

		assertTrue(binder.isValid());

		verify(binderStatusChangeListener, atLeast(1)).statusChange(assertArg(sc -> assertFalse(sc.hasValidationErrors())));

		reset(binderStatusChangeListener);

		field1.setValue("test");

		verify(binderStatusChangeListener, times(1)).statusChange(assertArg(sc -> assertTrue(sc.hasValidationErrors())));

		reset(binderStatusChangeListener);

		binder.removeBean();

		verify(binderStatusChangeListener, times(1)).statusChange(assertArg(sc -> assertFalse(sc.hasValidationErrors())));
	}
}

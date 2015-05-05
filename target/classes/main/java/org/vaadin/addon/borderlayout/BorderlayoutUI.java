/* 
@ITMillApache2LicenseForJavaFiles@
 */
package org.vaadin.addon.borderlayout;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

@SuppressWarnings("serial")
@Theme("borderlayout")
public class BorderlayoutUI extends UI {
	AbstractComponent[] components = new AbstractComponent[5];
	private BorderLayout bl;
	private BorderLayout bl2;
	private String[] texts = { "NORTH", "SOUTH", "CENTER", "EAST", "WEST" };
	

	@Override
	protected void init(VaadinRequest request) {
		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();
		setContent(tabs);
		
		tabs.addTab(getSimpleExamle(), "Basic functionality");
		tabs.addTab(getAnotherExample(), "Sequential add");
	}

	private Component getSimpleExamle() {
		VerticalLayout vlo = new VerticalLayout();
		vlo.setHeight("100%");
		vlo.setWidth("100%");
		vlo.setSpacing(true);
		vlo.addComponent(getTestButtons());
		for (int i = 0; i < components.length; i++) {
			components[i] = new TextArea();
			((TextArea) components[i]).setValue(texts[i]);
			components[i].setSizeFull();
		}

		bl = new BorderLayout();
		vlo.addComponent(bl);
		vlo.setExpandRatio(bl, 1);

		bl.addComponent(components[0], BorderLayout.Constraint.NORTH);
		bl.addComponent(components[1], BorderLayout.Constraint.SOUTH);
		bl.addComponent(components[2], BorderLayout.Constraint.CENTER);
		bl.addComponent(components[3], BorderLayout.Constraint.EAST);
		bl.addComponent(components[4], BorderLayout.Constraint.WEST);
		return vlo;
	}

	private Component getAnotherExample() {
		VerticalLayout vlo = new VerticalLayout();
		vlo.setSpacing(true);
		HorizontalLayout hlo1 = new HorizontalLayout();
		hlo1.setSpacing(true);
		vlo.addComponent(hlo1);
		
		bl2 = new BorderLayout();
		vlo.addComponent(bl2);
		vlo.setExpandRatio(bl2, 1);
		
		
		final Button button1 = new Button("call addComponent(new TextField(\"test\"))");
		button1.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					bl2.addComponent(new TextField("test"));
				} catch (Exception e) {
					Notification.show(e.toString());
				}
			}
		});
		
		
		final Button button2 = new Button("Remove north");
		final Button button3 = new Button("Remove west");
		final Button button4 = new Button("Remove center");
		final Button button5 = new Button("Remove east");
		final Button button6 = new Button("Remove south");
		
		button2.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				bl2.removeComponent(Constraint.NORTH);
			}
		});
		button3.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				bl2.removeComponent(Constraint.WEST);
			}
		});
		button4.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				bl2.removeComponent(Constraint.CENTER);
			}
		});
		button5.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				bl2.removeComponent(Constraint.EAST);
			}
		});
		button6.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				bl2.removeComponent(Constraint.SOUTH);
			}
		});
		
		hlo1.addComponents(button1,button2,button3,button4,button5,button6);
		return vlo;
	}
	
	private Layout getTestButtons() {
		VerticalLayout vlo = new VerticalLayout();
		vlo.setSpacing(true);
		HorizontalLayout hlo1 = new HorizontalLayout();
		HorizontalLayout hlo2 = new HorizontalLayout();
		HorizontalLayout hlo3 = new HorizontalLayout();
		hlo1.setSpacing(true);
		hlo2.setSpacing(true);
		hlo3.setSpacing(true);
		final Button button1 = new Button("Set height 100%");
		final Button button2 = new Button("Set height 500px");
		final Button button3 = new Button("Set width 400px");
		final Button button4 = new Button("Set size full");
		final Button button5 = new Button("Remove center");
		final Button button6 = new Button("Remove south");
		final Button button7 = new Button("Add center");
		final Button button8 = new Button("Add south");
		final Button button9 = new Button("Remove north");
		final Button button10 = new Button("Add north");

		final Button button11 = new Button("Toggle margin");
		final Button button12 = new Button("Toggle spacing");

		hlo1.addComponent(button2);
		hlo1.addComponent(button3);
		hlo1.addComponent(button4);
		hlo1.addComponent(button1);
		hlo2.addComponent(button5);
		hlo2.addComponent(button7);
		hlo2.addComponent(button6);
		hlo2.addComponent(button8);
		hlo2.addComponent(button9);
		hlo2.addComponent(button10);
		hlo3.addComponent(button11);
		hlo3.addComponent(button12);

		vlo.addComponent(hlo1);
		vlo.addComponent(hlo2);
		vlo.addComponent(hlo3);

		button1.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.setHeight("100%");
			}
		});
		button2.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.setHeight("500px");
			}
		});
		button3.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.setWidth("400px");
			}
		});
		button4.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.setSizeFull();
			}
		});
		button5.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.removeComponent(components[2]);
			}
		});
		button6.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.removeComponent(components[1]);
			}
		});
		button7.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.addComponent(components[2], BorderLayout.Constraint.CENTER);
			}
		});
		button8.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.addComponent(components[1], BorderLayout.Constraint.SOUTH);
			}
		});
		button9.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.removeComponent(components[0]);
			}
		});
		button10.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.addComponent(components[0], BorderLayout.Constraint.NORTH);
			}
		});
		button11.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;
			private boolean margin = false;

			public void buttonClick(ClickEvent event) {
				margin = !margin;
				bl.setMargin(margin);
			}
		});
		button12.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7716267156088629379L;

			public void buttonClick(ClickEvent event) {
				bl.setSpacing(!bl.isSpacing());
			}
		});
		return vlo;
	}
}

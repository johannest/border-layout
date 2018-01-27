/* 
@ITMillApache2LicenseForJavaFiles@
 */
package org.vaadin.addon.borderlayout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * BorderLayout mimics {@link java.awt.BorderLayout} in Vaadin.
 * 
 */
@SuppressWarnings("serial")
public class BorderLayout extends VerticalLayout {

	public enum Constraint {
		NORTH, WEST, CENTER, EAST, SOUTH;
	}

	public static final String DEFAULT_MINIMUM_HEIGHT = "50px";

	private VerticalLayout mainLayout;
	private HorizontalLayout centerLayout;
	
	private String minimumNorthHeight = DEFAULT_MINIMUM_HEIGHT;
	private String minimumSouthHeight = DEFAULT_MINIMUM_HEIGHT;
	private String minimumWestWidth = DEFAULT_MINIMUM_HEIGHT;
	private String minimumEastWidth = DEFAULT_MINIMUM_HEIGHT;

	protected Component defaultNorth = new Label(" ");
	protected Component defaultWest = new Label(" ");
	protected Component defaultCenter = new Label(" ");
	protected Component defaultEast = new Label(" ");
	protected Component defaultSouth = new Label(" ");
	
	protected Component north = defaultNorth;
	protected Component west = defaultWest;
	protected Component center = defaultCenter;
	protected Component east = defaultEast;
	protected Component south = defaultSouth;

	/**
	 * Create a layout structure that mimics the traditional
	 * {@link java.awt.BorderLayout}.
	 */
	public BorderLayout() {
		mainLayout = new VerticalLayout();
		centerLayout = new HorizontalLayout();
		centerLayout.addComponent(west);
		centerLayout.addComponent(center);
		centerLayout.addComponent(east);
		centerLayout.setSizeFull();

		mainLayout.addComponent(north);
		mainLayout.addComponent(centerLayout);
		mainLayout.addComponent(south);
		mainLayout.setExpandRatio(centerLayout, 1);

		super.setWidth("100%");
		super.addComponent(mainLayout);
	}

	@Override
	public void setWidth(String width) {
		if (mainLayout == null) {
			return;
		}
		mainLayout.setWidth(width);
		centerLayout.setExpandRatio(center, 1);
		markAsDirty();
	}

	@Override
	public void setHeight(String height) {
		mainLayout.setHeight(height);
		west.setHeight("100%");
		center.setHeight("100%");
		east.setHeight("100%");
		centerLayout.setExpandRatio(center, 1);
		markAsDirty();
	}

	@Override
	public void setSizeFull() {
		super.setSizeFull();
		mainLayout.setSizeFull();
		centerLayout.setExpandRatio(center, 1);
		markAsDirty();
	}

	@Override
	public void setMargin(boolean margin) {
		mainLayout.setMargin(margin);
		markAsDirty();
	}
	
	@Override
	public void setMargin(MarginInfo marginInfo) {
	    mainLayout.setMargin(marginInfo);
	    markAsDirty();
	}
	
	@Override
	public MarginInfo getMargin() {
	    return mainLayout.getMargin();
	}

	@Override
	public void setSpacing(boolean spacing) {
		mainLayout.setSpacing(spacing);
		centerLayout.setSpacing(spacing);
		markAsDirty();
	}

	@Override
	public boolean isSpacing() {
		return (mainLayout.isSpacing() && centerLayout.isSpacing());
	}

	@Override
	public void removeComponent(Component c) {
		Constraint position = getConstraint(c);
		if (c!=null) {
			replaceComponent(c, getDefault(position));
		}
	}

	public void removeComponent(Constraint position) {
		Component c = getComponent(position);
		if (c!=null) {
			replaceComponent(c, getDefault(position));
		}
	}
	
	/**
	 * Add component into borderlayout
	 * 
	 * @param c
	 *            component to be added into layout
	 * @param constraint
	 *            place of the component (have to be on of BorderLayout.NORTH,
	 *            BorderLayout.WEST, BorderLayout.CENTER, BorderLayout.EAST, or
	 *            BorderLayout.SOUTH
	 */
	public void addComponent(Component c, Constraint constraint) {
		if (constraint == Constraint.NORTH) {
			mainLayout.replaceComponent(north, c);
			north = c;
			if (north.getHeight() < 0
					|| north.getHeightUnits() == Unit.PERCENTAGE) {
				north.setHeight(minimumNorthHeight);
			}
		} else if (constraint == Constraint.WEST) {
			centerLayout.replaceComponent(west, c);
			west = c;
			if (west.getWidth() < 0 || west.getWidthUnits() == Unit.PERCENTAGE) {
				west.setWidth(minimumWestWidth);
			}
		} else if (constraint == Constraint.CENTER) {
			centerLayout.replaceComponent(center, c);
			center = c;
			center.setHeight(centerLayout.getHeight(),
					centerLayout.getHeightUnits());
			center.setWidth("100%");
			centerLayout.setExpandRatio(center, 1);
		} else if (constraint == Constraint.EAST) {
			centerLayout.replaceComponent(east, c);
			east = c;
			if (east.getWidth() < 0 || east.getWidthUnits() == Unit.PERCENTAGE) {
				east.setWidth(minimumEastWidth);
			}
		} else if (constraint == Constraint.SOUTH) {
			mainLayout.replaceComponent(south, c);
			south = c;
			if (south.getHeight() < 0
					|| south.getHeightUnits() == Unit.PERCENTAGE) {
				south.setHeight(minimumSouthHeight);
			}
		} else {
			throw new IllegalArgumentException(
					"Invalid BorderLayout constraint.");
		}
		centerLayout.setExpandRatio(center, 1);
		markAsDirty();
	}

	/**
	 * Adds components into layout in the default order: 1. NORTH, 2. WEST, 3.
	 * CENTER, 4. EAST, 5. SOUTH until all slots are filled
	 * 
	 * @throws IllegalArgumentException
	 *             if layout is "full"
	 */
	@Override
	public void addComponent(Component c) {
		if (getComponent(Constraint.NORTH).equals(defaultNorth)) {
			addComponent(c, Constraint.NORTH);
			return;
		} else if (getComponent(Constraint.WEST).equals(defaultWest)) {
			addComponent(c, Constraint.WEST);
			return;
		} else if (getComponent(Constraint.CENTER).equals(defaultCenter)) {
			addComponent(c, Constraint.CENTER);
			return;
		} else if (getComponent(Constraint.EAST).equals(defaultEast)) {
			addComponent(c, Constraint.EAST);
			return;
		}  else if (getComponent(Constraint.SOUTH).equals(defaultSouth)) {
			addComponent(c, Constraint.SOUTH);
			return;
		} 
		throw new IllegalArgumentException(
				"All layout places are filled, please use addComponent(Component c, Constraint constraint) for force fill given place");
	}

	@Override
	public void replaceComponent(Component oldComponent, Component newComponent) {
		if (oldComponent == north) {
			mainLayout.replaceComponent(north, newComponent);
			north = newComponent;
		} else if (oldComponent == west) {
			centerLayout.replaceComponent(west, newComponent);
			west = newComponent;
		} else if (oldComponent == center) {
			centerLayout.replaceComponent(center, newComponent);
			centerLayout.setExpandRatio(newComponent, 1);
			center = newComponent;
		} else if (oldComponent == east) {
			centerLayout.replaceComponent(east, newComponent);
			east = newComponent;
		} else if (oldComponent == south) {
			mainLayout.replaceComponent(south, newComponent);
			south = newComponent;
		}
		centerLayout.setExpandRatio(center, 1);
		markAsDirty();
	}

	/**
	 * Set minimum height of the component in the BorderLayout.NORTH
	 * 
	 * @param minimumNorthHeight
	 */
	public void setMinimumNorthHeight(String minimumNorthHeight) {
		this.minimumNorthHeight = minimumNorthHeight;
	}

	/**
	 * Get minimum height of the component in the BorderLayout.NORTH
	 */
	public String getMinimumNorthHeight() {
		return minimumNorthHeight;
	}

	/**
	 * Set minimum height of the component in the BorderLayout.SOUTH
	 * 
	 * @param minimumNorthHeight
	 */
	public void setMinimumSouthHeight(String minimumSouthHeight) {
		this.minimumSouthHeight = minimumSouthHeight;
	}

	/**
	 * Get minimum height of the component in the BorderLayout.SOUTH
	 */
	public String getMinimumSouthHeight() {
		return minimumSouthHeight;
	}

	/**
	 * Set minimum height of the component in the BorderLayout.WEST
	 * 
	 * @param minimumNorthHeight
	 */
	public void setMinimumWestWidth(String minimumWestWidth) {
		this.minimumWestWidth = minimumWestWidth;
	}

	/**
	 * Get minimum height of the component in the BorderLayout.WEST
	 */
	public String getMinimumWestWidth() {
		return minimumWestWidth;
	}

	/**
	 * Set minimum height of the component in the BorderLayout.EAST
	 * 
	 * @param minimumNorthHeight
	 */
	public void setMinimumEastWidth(String minimumEastWidth) {
		this.minimumEastWidth = minimumEastWidth;
	}

	/**
	 * Get minimum height of the component in the BorderLayout.EAST
	 */
	public String getMinimumEastWidth() {
		return minimumEastWidth;
	}

	/**
	 * Return component from specific position
	 * 
	 * @param constraint
	 * @return
	 */
	public Component getComponent(Constraint position) {
		if (position == Constraint.NORTH) {
			return north;
		} else if (position == Constraint.WEST) {
			return west;
		} else if (position == Constraint.CENTER) {
			return center;
		} else if (position == Constraint.EAST) {
			return east;
		} else if (position == Constraint.SOUTH) {
			return south;
		} else {
			throw new IllegalArgumentException(
					"Invalid BorderLayout constraint.");
		}
	}
	

	private Component getDefault(Constraint position) {
		if (position == Constraint.NORTH) {
			return defaultNorth;
		} else if (position == Constraint.WEST) {
			return defaultWest;
		} else if (position == Constraint.CENTER) {
			return defaultCenter;
		} else if (position == Constraint.EAST) {
			return defaultEast;
		} else if (position == Constraint.SOUTH) {
			return defaultSouth;
		} else {
			throw new IllegalArgumentException(
					"Invalid BorderLayout constraint.");
		}
	}
	
	/**
	 * Returns position of given component or null if the layout doesn't contain it
	 * @param component
	 * @return
	 */
	public Constraint getConstraint(Component component) {
		if (north.equals(component)) {
			return Constraint.NORTH;
		} else if (west.equals(component)) {
			return Constraint.WEST;
		} else if (center.equals(component)) {
			return Constraint.CENTER;
		} else if (east.equals(component)) {
			return Constraint.EAST;
		} else if (south.equals(component)) {
			return Constraint.SOUTH;
		} else {
			return null;
		}
	}

	
	
	public BorderLayoutIterator<Component> getBorderLayoutComponentIterator() {
		return new BorderLayoutIterator<Component>(
				mainLayout.iterator(),
				centerLayout.iterator());
	}

	/**
	 * Iterate through the components of the borderlayout
	 * 
	 * TODO: Determine if the end user need to iterate components added into
	 * N/S/E/W locations??
	 * 
	 * @param <Component>
	 */
	@SuppressWarnings("hiding")
	private class BorderLayoutIterator<Component> implements
			Iterator<Component> {

		Iterator<Component> mainLayoutIter;
		Iterator<Component> centerLayoutIter;

		BorderLayoutIterator(Iterator<Component> mainLayoutIter,
				Iterator<Component> centerLayoutIter) {
			this.mainLayoutIter = mainLayoutIter;
			this.centerLayoutIter = centerLayoutIter;
		}

		public boolean hasNext() {
			return (mainLayoutIter.hasNext() || centerLayoutIter.hasNext());
		}

		public Component next() {
			if (mainLayoutIter.hasNext()) {
				return mainLayoutIter.next();
			} else {
				return centerLayoutIter.next();
			}
		}

		public void remove() {
			if (mainLayoutIter.hasNext()) {
				mainLayoutIter.remove();
			} else {
				centerLayoutIter.remove();
			}
		}

	}
}

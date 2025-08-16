package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {
	
	private Point upperLeft;
	private int height;
	private int width;
	
	
	public Rectangle() {
		
		
	}
	
	public Rectangle(Point upperLeft, int width, int height) {
		this.upperLeft = upperLeft;
		this.height = height;
		this.width = width;
		
		
	}
	
	public Rectangle(Point upperLeft, int height, int width, boolean selected) {
		this(upperLeft, height, width);
		setSelected(selected);
		
	}
	public Rectangle(Point upperLeft, int height, int width, boolean selected, Color color) {
		this(upperLeft, height, width, selected);
		setColor(color);
		
	}
	
	public Rectangle(Point upperLeft, int height, int width, boolean selected, Color color, Color innerColor) {
		this(upperLeft, height, width, selected, color);
		setInnerColor(innerColor);
		
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Rectangle) {
			return (int)(this.area() - ((Rectangle)o).area());
		}
		return 0;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		upperLeft.moveBy(byX, byY);
		
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawRect(upperLeft.getX(), upperLeft.getY(), width, height);
		
		this.fill(g);
		
		if(isSelected()) {
			g.setColor(Color.CYAN);
			g.drawRect(getUpperLeft().getX() - 3, getUpperLeft().getY() - 3, 6, 6);
			g.drawRect(getUpperLeft().getX() + getWidth() - 3, getUpperLeft().getY() - 3,  6,  6);
			g.drawRect(getUpperLeft().getX() - 3, getUpperLeft().getY() + getHeight() -3, 6, 6);
			g.drawRect(getUpperLeft().getX() + getWidth() - 3,getUpperLeft().getY() + getHeight() - 3 , 6, 6);
		}
	}
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(upperLeft.getX() + 1, getUpperLeft().getY() + 1, width - 1, height - 1);
	}
	
	public double area() {
		return width * height;
		
	}
	public boolean equals(Object obj) {
		if(obj instanceof Rectangle) {
			Rectangle temp = (Rectangle) obj;
			if(upperLeft.equals(temp.upperLeft) && width == temp.width && height == temp.height) {
				return true;
			}
			
		}
		return false;
	}
	public boolean contains(int x, int y) {
		return (upperLeft.getX() < x && upperLeft.getX() + width > x
				&& upperLeft.getY() < y && upperLeft.getY() + height > y);
		
	}
	
	public boolean contains(Point p) {
		return this.contains(p.getX(), p.getY());
	}
	
	public int circumference() {
		return 2 * width + 2 * height;
		
	}	
	
	
	@Override
	public void moveTo(int x, int y) {
		upperLeft.moveTo(x, y);
		
	}

	
	
	public Point getUpperLeft() {
		return upperLeft;
	}
	
	public void setUpperLeft(Point upperLeft) {
		this.upperLeft = upperLeft;
	}
	
	public int getHeight() {
		return height;
		
	}
	
	public void setHeight(int height) {
		this.height = height;
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
		
	}

	
	public String toString() {
		return "Rectangle (" + upperLeft.getX() + "," + upperLeft.getY() + ") --> width: " +  width +
				" --> height: " + height + " --> outlineColor: " + getColor().getRGB() +
				" --> innerColor: " + getInnerColor().getRGB();
		
	}
	
	@Override
	public Rectangle clone() {
		return new Rectangle(upperLeft, height, width, isSelected(), getColor(), getInnerColor());
	}
	

	
}

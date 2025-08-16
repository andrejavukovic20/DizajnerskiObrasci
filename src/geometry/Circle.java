package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape{
	
	protected Point center;
	protected int radius;
	
	
	public Circle() {
		
		
	}
	
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
		
	}
	
	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		this.selected = selected;
		
	}
	public Circle(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		setColor(color);
		
	}
	public Circle(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		setInnerColor(innerColor);
		
	}
	@Override
	public int compareTo(Object o) {
		if(o instanceof Circle) {
			return (int)(this.area() - ((Circle) o).area());
		}
		return 0;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);
	
	}

	
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(center.getX()- radius + 1,center.getY() - radius +1, radius*2-2, radius*2-2);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(center.getX() - radius, center.getY() - radius, radius*2, radius*2);
		this.fill(g);
		
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(center.getX() - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() - radius - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() + radius - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() - 2, center.getY() - radius - 2, 4, 4);
			g.drawRect(center.getX() - 2, center.getY() + radius - 2, 4, 4);
		}
		
	}
	public double area() {
		return radius * radius * Math.PI;
	}
	public boolean equals(Object obj) {
		if(obj instanceof Circle) {
			Circle temp = (Circle) obj;
			if(center.equals(temp.center) && radius == temp.radius) {
				return true;
			}
		}
		return false;
	}
	public boolean contains(int x, int y) {
		return center.distance(x, y) <= radius;
		
	}
	
	public boolean contains(Point p) {
		return this.contains(p.getX(), p.getY());
		
	}
	
	public double circumference() {
		return 2 * radius * Math.PI;
		
	}
	
	
	
	@Override
	public void moveTo(int x, int y) {
		center.moveTo(x, y);
	}

	
	public Point getCenter() {
		return center;
	}
	
	public void setCenter(Point center) {
		this.center = center;
		
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) throws Exception {
		if (radius < 0) {
			throw new Exception("radius ne moze biti manji od 0");
			
		}
		this.radius = radius;
		
	}	

	public String toString() {
		
		return "Circle (" + center.getX() + "," + center.getY() + ") --> radius: " + radius +
				" --> outlineColor: " + getColor().getRGB() + " --> innerColor: " +
				getInnerColor().getRGB();
	}
	
	@Override
	public Circle clone() {
		return new Circle(center, radius, isSelected(), getColor(), getInnerColor());
	}

}

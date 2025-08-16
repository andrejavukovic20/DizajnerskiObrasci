package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {
	private int innerRadius;
	
	public Donut() {
		
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
		
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
		
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		setColor(color);
		
	}
	

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);
		
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		Ellipse2D el = new Ellipse2D.Float(center.getX() - innerRadius, center.getY() - innerRadius, 2*innerRadius, 2*innerRadius);
		Ellipse2D outline = new Ellipse2D.Float(center.getX() - getRadius(), center.getY() - getRadius(), 2*getRadius(), 2*getRadius());
		Area outer = new Area(outline);
		Area inner = new Area(el);
		outer.subtract(inner);
		g.setColor(getInnerColor());
		g2D.fill(outer);
		g.setColor(getColor());
		g2D.draw(outer);
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.center.getX() - 2, this.center.getY() -2, 4, 4);
			g.drawRect(this.center.getX() + this.radius -1, this.center.getY() -2, 4, 4);
			g.drawRect(this.center.getX() - this.radius - 2, this.center.getY() - 2, 4, 4);
			g.drawRect(this.center.getX() - 2, this.center.getY() - this.radius -2, 4, 4);
			g.drawRect(this.center.getX() -2, this.center.getY() - this.radius - 2, 4, 4);
			g.drawRect(this.center.getX() - 2, this.center.getY() +this.radius -2 , 4, 4);
		}
		
		
		
	}
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(getCenter().getX() - this.innerRadius, getCenter().getY() - this.innerRadius, this.innerRadius*2, this.innerRadius*2);
	}
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
		
	}
	
	
	
	public boolean equals(Object obj) {
		if(obj instanceof Donut) {
			Donut temp = (Donut) obj;
			if(center.equals(temp.center) && innerRadius == temp.innerRadius && radius == temp.radius) {
				return true;
			}
		}
		return false;
	}
	public boolean contains(int x, int y) {
		return super.contains(x, y) && center.distance(x, y) >=innerRadius;
		
	}
	
	public boolean contains(Point p) {
		return this.contains(p.getX(), p.getY());
		
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	
	
	public String toString() {
		return "Donut (" + center.getX() + "," + center.getY() + ") --> radius: " + radius +
				" --> innerRadius: " + innerRadius + " --> outlineColor: " + getColor().getRGB() +
				" --> innerColor: " + getInnerColor().getRGB();
		
	}
	
	@Override
	public Donut clone() {
		return new Donut(getCenter(), radius, innerRadius, isSelected(), getColor(), getInnerColor());
	}

}

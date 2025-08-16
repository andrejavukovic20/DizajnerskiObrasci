package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape{
	private int x;
	private int y;
	
	
	public Point() {
		
		
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
		
	}
	
	public Point(int x, int y, boolean selected, Color color) {
		this(x, y, selected);
		setColor(color);
		
	}
	public int compareTo(Object o) {
		if(o instanceof Point) {
			Point p = new Point(0, 0);
			return (int)(this.distance(p.getX(),p.getY()) - ((Point) o).distance(p.getX(), p.getY()));
		}
		return 0;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		x += byX;
		y += byY;
		
	
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(x - 2, y, x + 2, y);
		g.drawLine(x, y + 2, x, y - 2);
		
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(x - 2, y - 2, 4, 4);
		}
		
	}
	
	public double distance(int x, int y) {
		int dX = this.x-x;
		int dY = this.y-y;
		double d = Math.sqrt(dX*dX + dY*dY);
		return d;
	}
	
	
	
	public boolean equals(Object obj) {
		if(obj instanceof Point) {
			Point temp = (Point) obj;
			if(x == temp.x && y == temp.y) {
				return true;
				
			}
		}
		return false;
	}
	public boolean contains(int x, int y) {
		return this.distance(x, y) <= 2;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x=x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int newY) {
		y = newY;
		
	}	
	public String toString() {
		return "Point (" + x + "," + y + ") --> outlineColor: " + getColor().getRGB();
		
	}

	@Override
	public void moveTo(int x, int y) {
		this.x=x;
		this.y=y;
		
	}
	@Override
	public Point clone() {
		return new Point(x,y,isSelected(),getColor());
	}
}

package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape{
	private Point startPoint;
	private Point endPoint;
	
	
	public Line() {
		
		
	}
	
	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		setSelected(selected);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint, endPoint, selected);
		setColor(color);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Line) {
			return (int)(this.lenght() - ((Line) o).lenght());
		}
		return 0;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		startPoint.moveBy(byX, byY);
		endPoint.moveBy(byX, byY);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
		
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(getStartPoint().getX() - 2, getStartPoint().getY() - 2, 4, 4);
			g.drawRect(getEndPoint().getX(), getEndPoint().getY(), 4, 4);
		}
	}
	
	
	public double lenght() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
		
	}
	public boolean equals(Object obj) {
		if(obj instanceof Line) {
			Line temp = (Line) obj;
			return startPoint.equals(temp.startPoint) && endPoint.equals(temp.endPoint);
		}
		return false;
	}
	
	public boolean contains(int x, int y) {
		return(startPoint.distance(x, y) + endPoint.distance(x, y)) - lenght() <= 2;
	}
		
	
	@Override
	public void moveTo(int x, int y) {
		
	}

	

	public Point getStartPoint() {
		return startPoint;
	}
	
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
		
	}
	
	public Point getEndPoint() {
		return endPoint;
		
	}
	
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
		
	}
	
	
	public String toString() {
		return "Line (" + startPoint.getX() + "," + startPoint.getY() + ")"  + " --> " +
				"(" + endPoint.getX() + "," + endPoint.getY() + ")" + " --> outlineColor: " + 
				getColor().getRGB();
		
	}

	public Line clone() {
		return new Line(startPoint,endPoint,isSelected(),getColor());
	}
	

}

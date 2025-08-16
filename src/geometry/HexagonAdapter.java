package geometry;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape{

private Hexagon hexagon;
	
	public HexagonAdapter(){
	}
	
	public HexagonAdapter(Hexagon hexagon){
		this.hexagon = hexagon;
	}
	
	
	public HexagonAdapter(Point center, int radius, boolean selected, Color color, Color innerColor){
		hexagon = new Hexagon(center.getX(), center.getY(), radius);
		hexagon.setBorderColor(color);
		hexagon.setAreaColor(innerColor);
		hexagon.setSelected(selected);
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public double area() {
		return 0;
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof HexagonAdapter) {
			HexagonAdapter temp = (HexagonAdapter) obj;
			if(getCenter().equals(temp.getCenter()) && getRadius() == temp.getRadius()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
	}

	public Point getCenter() {
		return new Point(hexagon.getX(), hexagon.getY());
	}
	
	public void setCenter(Point center) {
		hexagon.setX(center.getX());
		hexagon.setY(center.getY());
	}

	public int getRadius() {
		return hexagon.getR();
	}
	
	public void setRadius(int radius) {
		hexagon.setR(radius);
	}
	
	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
	}
	
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}
	
	public void setInnerColor(Color color) {
		hexagon.setAreaColor(color);
	}
	
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
	}
	
	public String toString() {
		
		return "Hexagon (" + getCenter().getX() + "," + getCenter().getY() + ") --> radius: " +
				getRadius() + " --> outlineColor: " + getColor().getRGB() + " --> innerColor: " +
				getInnerColor().getRGB();
	}
	
	@Override
	public HexagonAdapter clone() {
		return new HexagonAdapter(getCenter(), getRadius(), isSelected(), getColor(), getInnerColor());
	}
}

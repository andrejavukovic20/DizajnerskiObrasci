package command;

import geometry.Point;

public class ModifyPointCommand implements Command{
	
	private Point tempPoint;
	private Point prevPoint;
	private Point newPoint;
	
	public ModifyPointCommand(Point prevPoint, Point newPoint) {
		this.prevPoint = prevPoint;
		this.newPoint = newPoint;
	}
	
	@Override
	public void execute() {
		this.tempPoint = prevPoint.clone();
		this.prevPoint.setX(newPoint.getX());
		this.prevPoint.setY(newPoint.getY());
		this.prevPoint.setColor(newPoint.getColor());
		
	}
	
	@Override
	public void unexecute() {
		this.prevPoint.setX(tempPoint.getX());
		this.prevPoint.setY(tempPoint.getY());
		this.prevPoint.setColor(tempPoint.getColor());
	}

}

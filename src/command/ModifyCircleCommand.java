package command;

import geometry.Circle;

public class ModifyCircleCommand implements Command{

	private Circle tempCircle;
	private Circle prevCircle;
	private Circle newCircle;
	
	
	public ModifyCircleCommand(Circle prevCircle, Circle newCircle) {
		this.prevCircle = prevCircle;
		this.newCircle = newCircle;
		
	}
	
	public void execute() {
		this.tempCircle = prevCircle.clone();
		this.prevCircle.setCenter(newCircle.getCenter());
		try {
			this.prevCircle.setRadius(newCircle.getRadius());
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.prevCircle.setColor(newCircle.getColor());
		this.prevCircle.setInnerColor(newCircle.getInnerColor());
	}
	
	public void unexecute() {
		this.prevCircle.setCenter(tempCircle.getCenter());
		try {
			this.prevCircle.setRadius(tempCircle.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.prevCircle.setColor(tempCircle.getColor());
		this.prevCircle.setInnerColor(tempCircle.getInnerColor());
	}
}

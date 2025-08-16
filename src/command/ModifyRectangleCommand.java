package command;

import geometry.Rectangle;

public class ModifyRectangleCommand implements Command{
	
	private Rectangle tempRectangle;
	private Rectangle prevRectangle;
	private Rectangle newRectangle;
	
	
	public ModifyRectangleCommand(Rectangle prevRectangle, Rectangle newRectangle) {
		this.prevRectangle = prevRectangle;
		this.newRectangle = newRectangle;
		
	}
	
	
	public void execute() {
		this.tempRectangle = prevRectangle.clone();
		this.prevRectangle.setUpperLeft(newRectangle.getUpperLeft());
		this.prevRectangle.setHeight(newRectangle.getHeight());
		this.prevRectangle.setWidth(newRectangle.getWidth());
		this.prevRectangle.setColor(newRectangle.getColor());
		this.prevRectangle.setInnerColor(newRectangle.getInnerColor());
	}
	
	public void unexecute() {
		System.out.println("sssss");
		this.prevRectangle.setUpperLeft(tempRectangle.getUpperLeft());
		this.prevRectangle.setHeight(tempRectangle.getHeight());
		this.prevRectangle.setWidth(tempRectangle.getWidth());
		this.prevRectangle.setColor(tempRectangle.getColor());
		this.prevRectangle.setInnerColor(tempRectangle.getInnerColor());
	}

}

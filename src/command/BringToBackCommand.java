package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCommand implements Command {
	
	private DrawingModel model;
	private Shape selectedShape;
	private int prevIndex;

	
	public BringToBackCommand(DrawingModel model, Shape selectedShape) {
		this.model = model;
		this.selectedShape = selectedShape;
	}
	
	
	public void execute() {
		int index = model.getShapes().indexOf(selectedShape);
		this.prevIndex = index;
		model.getShapes().remove(index);
		model.getShapes().add(0, selectedShape);
	}
	
	public void unexecute() {
		int index = model.getShapes().indexOf(selectedShape);
		model.getShapes().remove(index);
		model.getShapes().add(this.prevIndex, selectedShape);
	}
}

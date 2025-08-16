package command;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCommand implements Command{
	
	private DrawingModel model;
	private Shape selectedShape;
	
	
	public ToBackCommand(DrawingModel model, Shape selectedShape) {
		this.model = model;
		this.selectedShape = selectedShape;
	}
	
	public void execute() {
		int index = model.getShapes().indexOf(selectedShape);
		model.getShapes().remove(index);
		model.getShapes().add(index -1, selectedShape);
	}
	
	public void unexecute() {
		int index = model.getShapes().indexOf(selectedShape);
		model.getShapes().remove(index);
		model.getShapes().add(index + 1, selectedShape);
	}
	

}

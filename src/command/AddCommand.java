package command;

import geometry.Shape;
import mvc.DrawingModel;


public class AddCommand implements Command{
	
	private DrawingModel model;
	private Shape shape;
	
	public AddCommand(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		if(shape.isSelected())
			model.getSelectedShapes().add(shape);
		model.getShapes().add(shape);
	}

	@Override
	public void unexecute() {
		model.getSelectedShapes().remove(shape);
		model.getShapes().remove(shape);
	}

}

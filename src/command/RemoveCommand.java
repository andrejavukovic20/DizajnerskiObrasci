package command;

import java.util.ArrayList;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveCommand implements Command{
	
	private DrawingModel model;
	private Shape shape;
	private ArrayList<Shape> shapeForDelete = new ArrayList<Shape>();
	 
	
	public RemoveCommand(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	public RemoveCommand(DrawingModel model, ArrayList<Shape> shapeForDelete) {
		this.model = model;
		this.shapeForDelete = shapeForDelete;
	}
	@Override
	public void execute() {
		if(shapeForDelete.size() == 0){
			model.getSelectedShapes().remove(shape);
			model.getShapes().remove(shape);
		}else {
			for(int i=0; i < shapeForDelete.size(); i++) {
				model.getSelectedShapes().remove(shapeForDelete.get(i));
				model.getShapes().remove(shapeForDelete.get(i));
			}
		}
		
	}

	@Override
	public void unexecute() {
		if(shapeForDelete.size() == 0) {
			model.getSelectedShapes().add(shape);
			model.getShapes().add(shape);
		}else {
			for(int i = 0; i < shapeForDelete.size(); i++) {
				model.getSelectedShapes().add(shapeForDelete.get(i));
				model.getShapes().add(shapeForDelete.get(i));
			}
		}
	
	}
}

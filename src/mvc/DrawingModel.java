package mvc;

import java.io.Serializable;
import java.util.ArrayList;

import geometry.Shape;

public class DrawingModel implements Serializable {
	//model skriva gdje se nalaze podaci
	private ArrayList<Shape> shapes;
	private ArrayList<Shape> selectedShapes;
	
	public DrawingModel() {
		shapes = new ArrayList<Shape>();
		selectedShapes = new ArrayList<Shape>();
	}
	
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
	
	public ArrayList<Shape> getSelectedShapes(){
		return selectedShapes;
	}
	
	public void setSelectedShapes(ArrayList<Shape> selectedShapes) {
		this.selectedShapes = selectedShapes;
	}

	
}

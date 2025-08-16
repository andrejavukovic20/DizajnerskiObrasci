package mvc;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Shape;

public class DrawingView extends JPanel{
	
	private DrawingController controller;
	private DrawingModel model;

	@Override
	public void paint(Graphics g) {
		//treba nam zbog operativnog sistema na kojem se izvrsava
		super.paint(g);
		if(model != null){
			Iterator<Shape> it = model.getShapes().iterator();
			while(it.hasNext()) {
				//uzmi sljedeci i iscrtaj se
				it.next().draw(g);
			}
			
		}
		
	} 
	
	public DrawingController getController() {
		return controller;
	}
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public DrawingModel getModel() {
		return model;
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

}

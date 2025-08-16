package mvc;



public class DrawingApp {

	public static void main(String[] args) {
		//kreirali smo model
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame(); //objekat klase frame
		//velicina frame-a
		frame.setSize(1212, 599);
		frame.setTitle("Andreja Vukovic IT64/2019");
		DrawingView view = frame.getPnlDrawing();
		view.setModel(model);
		DrawingController controller = new DrawingController(frame, model);
		controller.addObserver(frame);
		frame.setController(controller);
		
		//da bude vidljiv
		frame.setVisible(true);
	}

}

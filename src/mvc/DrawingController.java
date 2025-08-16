package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import command.AddCommand;
import command.BringToBackCommand;
import command.BringToFrontCommand;
import command.Command;
import command.ModifyCircleCommand;
import command.ModifyDonutCommand;
import command.ModifyHexagonCommand;
import command.ModifyLineCommand;
import command.ModifyPointCommand;
import command.ModifyRectangleCommand;
import command.RemoveCommand;
import command.ToBackCommand;
import command.ToFrontCommand;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.Observable;
import observer.Observer;
import strategy.FileManager;
import strategy.FileObjectManipulator;
import strategy.FileStrategy;
import strategy.FileTxtManipulator;

public class DrawingController implements Observable{
	
	private DrawingFrame frame;
	private DrawingModel model;
	
	private Point startPoint;
	private Shape selectedShape;
	
	private ArrayList<Command> undoList = new ArrayList<Command>();
	private ArrayList<Command> redoList = new ArrayList<Command>();
	private DefaultListModel<String> listModel;
	
	private boolean deleteBtn = false;
	private boolean modifyBtn = false;
	private List<Observer> observers = new ArrayList<Observer>();
	
	private JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
	private List<String> loggerFile;
	ArrayList<Shape> shapeForDelete;
	
	public DrawingController(DrawingFrame frame, DrawingModel model) {
		//setujemo vrijednosti da ne budu null
		this.frame = frame;
		this.model = model;
		this.listModel = frame.listModel;
		
	}
	
	private void addUndoList(Command command) {
		undoList.add(command);
	}
	
	private void addRedoList(Command command) {
		redoList.add(command);
	}
	
	private void addListModel(Command command, Shape shape) {
		String loggerText = "";
		if(command.toString().contains("Add")) {
			loggerText = "Draw ";
		}else if(command.toString().contains("BringToFront")) {
			loggerText = "BringToFront ";
		}else if(command.toString().contains("BringToBack")) {
			loggerText = "BringToBack ";
		} else if(command.toString().contains("ToFront")) {
			loggerText = "ToFront ";
		} else if(command.toString().contains("ToBack")) {
			loggerText = "ToBack ";
		} else if(command.toString().contains("Modify")) {
			loggerText = "Modify ";
		} else if(command.toString().contains("Remove")) {
			loggerText = "Remove ";
			if(shape == null) {
				notifyObservers();
				loggerText = loggerText + "shapes";
				redoList.clear();
				listModel.addElement(loggerText);
				return;
			}
		}
		
		notifyObservers();
		loggerText = loggerText + shape.toString();
		redoList.clear();
		listModel.addElement(loggerText);
		
	}
	
	public void drawShape(String shapeBtn, int x, int y) {
		if(shapeBtn.equals("Point")) {
			drawPoint(x, y);
		}else if(shapeBtn.equals("Line")) {
			if(startPoint == null)
				startPoint = new Point(x,y);
			else {
				drawLine(x, y);
			}
		}else if(shapeBtn.equals("Circle")) {
			drawCircle(x, y);
		}else if(shapeBtn.equals("Donut")) {
			drawDonut(x, y);
		}else if(shapeBtn.equals("Rectangle")) {
			drawRectangle(x, y);
		}else if(shapeBtn.equals("Hexagon")) {
			drawHexagon(x, y);
		}
		frame.repaint();
	}
	
	public void selectShape(int x, int y, boolean ctrlDown) {
		selectedShape = null;
		ListIterator<Shape> iterator = model.getShapes().listIterator(model.getShapes().size());
		
		while (iterator.hasPrevious()) {
			Shape shape = iterator.previous();
			
			if(shape.contains(x, y) && !shape.isSelected()) {
				selectedShape = shape;
				if(ctrlDown == false) {
					clearSelectedShapes();
					model.getSelectedShapes().add(selectedShape);
					selectedShape.setSelected(true);
					listModel.addElement("Select " + selectedShape.toString());
					
				}else {
					model.getSelectedShapes().add(selectedShape);
					selectedShape.setSelected(true);
					listModel.addElement("Select " + selectedShape.toString());
				}
				break;
			}else if(shape.contains(x, y) && shape.isSelected()) {
				break;
			}else {
				if(model.getSelectedShapes().size() >= 1 && ctrlDown == false) {
					clearSelectedShapes();
				}
			}
		}
		
		notifyObservers();
		frame.repaint();
	}
	
	private void clearSelectedShapes() {
		for(Shape s : model.getSelectedShapes()) {
			listModel.addElement("Unselect " + s.toString());
			s.setSelected(false);
		}
		model.getSelectedShapes().clear();
	}
	
	private void drawPoint(int x, int y) {
		Point newShape = new Point(x, y, false, frame.getBtnOutlineColor().getBackground());
		AddCommand addPoint = new AddCommand(model, newShape);
		addPoint.execute();
		addUndoList(addPoint);
		addListModel(addPoint, newShape);
	}
	
	private void drawLine(int x, int y) {
		Line newShape = new Line(startPoint, new Point(x, y), false, frame.getBtnOutlineColor().getBackground());
		startPoint = null;
		AddCommand addLine = new AddCommand(model, newShape);
		addLine.execute();
		addUndoList(addLine);
		//da se ispise u logeru
		addListModel(addLine, newShape);
	}
	
	private void drawCircle(int x, int y) {
		DlgCircle dialog = new DlgCircle();

		dialog.getTxtX().setText("" + Integer.toString(x));
		dialog.getTxtX().setEditable(false);
		dialog.getTxtY().setText("" + Integer.toString(y));
		dialog.getTxtY().setEditable(false);
		dialog.getBtnInnerColor().setVisible(false);
		dialog.getBtnOutlineColor().setVisible(false);
		dialog.setVisible(true);
		
		if (dialog.isConfirm()) {
			Circle newShape = dialog.getCircle();
			newShape.setInnerColor(frame.getBtnInnerColor().getBackground());
			newShape.setColor(frame.getBtnOutlineColor().getBackground());
			AddCommand addCircle = new AddCommand(model, newShape);
			addCircle.execute();
			addUndoList(addCircle);
			addListModel(addCircle, newShape);
		}
	}
	private void drawDonut(int x, int y) {
		DlgDonut dialog = new DlgDonut();
		dialog.setModal(true);
		dialog.getTxtX().setText("" + Integer.toString(x));
		dialog.getTxtX().setEditable(false);
		dialog.getTxtY().setText("" + Integer.toString(y));
		dialog.getTxtY().setEditable(false);
		dialog.getBtnInnerColor().setVisible(false);
		dialog.getBtnOutlineColor().setVisible(false);
		dialog.setVisible(true);

		if (dialog.isConfirm()) {
			Donut newShape = dialog.getDonut();
			newShape.setInnerColor(frame.getBtnInnerColor().getBackground());
			newShape.setColor(frame.getBtnOutlineColor().getBackground());
			AddCommand addDonut = new AddCommand(model, newShape);
			addDonut.execute();
			addUndoList(addDonut);
			addListModel(addDonut, newShape);
		}
	}
	
	private void drawRectangle(int x, int y) {
		DlgRectangle dialog = new DlgRectangle();
		dialog.setModal(true);
		dialog.getTxtX().setText("" + Integer.toString(x));
		dialog.getTxtX().setEditable(false);
		dialog.getTxtY().setText("" + Integer.toString(y));
		dialog.getTxtY().setEditable(false);
		dialog.getBtnInnerColor().setVisible(false);
		dialog.getBtnOutlineColor().setVisible(false);
		dialog.setVisible(true);

		if (dialog.isConfirm()) {
			Rectangle newShape =dialog.getRect();
			newShape.setInnerColor(frame.getBtnInnerColor().getBackground());
			newShape.setColor(frame.getBtnOutlineColor().getBackground());
			AddCommand addRectangle = new AddCommand(model, newShape);
			addRectangle.execute();
			addUndoList(addRectangle);
			addListModel(addRectangle, newShape);
		}	
	}
	
	private void drawHexagon(int x, int y) {
		DlgHexagon dialog = new DlgHexagon();

		dialog.getTxtX().setText("" + Integer.toString(x));
		dialog.getTxtX().setEditable(false);
		dialog.getTxtY().setText("" + Integer.toString(y));
		dialog.getTxtY().setEditable(false);
		dialog.getBtnInnerColor().setVisible(false);
		dialog.getBtnOutlineColor().setVisible(false);
		dialog.setVisible(true);
		
		if (dialog.isConfirm()) {
			HexagonAdapter newShape = dialog.getHexagonAdapter();
			newShape.setInnerColor(frame.getBtnInnerColor().getBackground());
			newShape.setColor(frame.getBtnOutlineColor().getBackground());
			AddCommand addHexagon = new AddCommand(model, newShape);
			addHexagon.execute();
			addUndoList(addHexagon);
			addListModel(addHexagon, newShape);
		}
	}
	
	public void modifyShape() {
		if(selectedShape != null) {
			if(selectedShape instanceof Point) {
				
				Point p = (Point) selectedShape;
				DlgPoint dialog = new DlgPoint();
				
				dialog.getTxtX().setText("" + Integer.toString(p.getX()));
				dialog.getTxtY().setText("" + Integer.toString(p.getY()));
				dialog.getBtnColor().setBackground(p.getColor());
				dialog.setVisible(true);
				
				if(dialog.isConfirm()) {
					ModifyPointCommand modifyPoint = new ModifyPointCommand((Point)selectedShape, dialog.getP());
					modifyPoint.execute();
					addUndoList(modifyPoint);
					addListModel(modifyPoint, dialog.getP());
				}

			}else if(selectedShape instanceof Donut) {
				Donut donut = (Donut) selectedShape;
				DlgDonut dialogd = new DlgDonut();

				dialogd.getTxtX().setText("" + Integer.toString(donut.getCenter().getX()));
				dialogd.getTxtY().setText("" + Integer.toString(donut.getCenter().getY()));
				dialogd.getTxtR().setText("" + Integer.toString(donut.getRadius()));
				dialogd.getTxtInnerR().setText("" + Integer.toString(donut.getInnerRadius()));
				dialogd.getBtnInnerColor().setBackground(donut.getInnerColor());
				dialogd.getBtnOutlineColor().setBackground(donut.getColor());
				dialogd.setModal(true);
				dialogd.setVisible(true);

				if (dialogd.isConfirm()) {
					ModifyDonutCommand modifyDonut = new ModifyDonutCommand((Donut)selectedShape, dialogd.getDonut());
					modifyDonut.execute();
					addUndoList(modifyDonut);
					addListModel(modifyDonut, dialogd.getDonut());
				}
				
				
			}else if(selectedShape instanceof Circle && (selectedShape instanceof Donut) == false) {
				Circle circle = (Circle) selectedShape;
				DlgCircle dialog = new DlgCircle();

				dialog.getTxtX().setText("" + Integer.toString(circle.getCenter().getX()));
				dialog.getTxtY().setText("" + Integer.toString(circle.getCenter().getY()));
				dialog.getTxtR().setText("" + Integer.toString(circle.getRadius()));
				dialog.getBtnInnerColor().setBackground(circle.getInnerColor());
				dialog.getBtnOutlineColor().setBackground(circle.getColor());

				dialog.setVisible(true);
				dialog.setModal(true);

				if (dialog.isConfirm()) {
					ModifyCircleCommand modifyCircle = new ModifyCircleCommand((Circle) selectedShape, dialog.getCircle());
					modifyCircle.execute();
					addUndoList(modifyCircle);
					addListModel(modifyCircle, dialog.getCircle());
				}
				
			}else if (selectedShape instanceof Line) {
				Line line = (Line) selectedShape;
				DlgLine dialog = new DlgLine();

				dialog.getTxtSPX().setText("" + Integer.toString(line.getStartPoint().getX()));
				dialog.getTxtSPY().setText("" + Integer.toString(line.getStartPoint().getY()));
				dialog.getTxtEPX().setText("" + Integer.toString(line.getEndPoint().getX()));
				dialog.getTxtEPY().setText("" + Integer.toString(line.getEndPoint().getY()));
				dialog.getBtnOutlineColor().setBackground(line.getColor());

				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					ModifyLineCommand modifyLine = new ModifyLineCommand((Line)selectedShape, dialog.getLine());
					modifyLine.execute();
					addUndoList(modifyLine);
					addListModel(modifyLine, dialog.getLine());
				}
			}else if(selectedShape instanceof Rectangle) {
				Rectangle rect = (Rectangle) selectedShape;
				DlgRectangle dialog = new DlgRectangle();

				dialog.getTxtX().setText("" + Integer.toString(rect.getUpperLeft().getX()));
				dialog.getTxtY().setText("" + Integer.toString(rect.getUpperLeft().getY()));
				dialog.getTxtHeight().setText("" + Integer.toString(rect.getHeight()));
				dialog.getTxtWidth().setText("" + Integer.toString(rect.getWidth()));
				dialog.getBtnInnerColor().setBackground(rect.getInnerColor());
				dialog.getBtnOutlineColor().setBackground(rect.getColor());
				dialog.setModal(true);
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					ModifyRectangleCommand modifyRectangle = new ModifyRectangleCommand((Rectangle)selectedShape, dialog.getRect());
					modifyRectangle.execute();
					addUndoList(modifyRectangle);
					addListModel(modifyRectangle, dialog.getRect());
				}
			}else if(selectedShape instanceof HexagonAdapter) {
				
				HexagonAdapter hexagon = (HexagonAdapter) selectedShape;
				DlgHexagon dialog = new DlgHexagon();

				dialog.getTxtX().setText("" + Integer.toString(hexagon.getCenter().getX()));
				dialog.getTxtY().setText("" + Integer.toString(hexagon.getCenter().getY()));
				dialog.getTxtR().setText("" + Integer.toString(hexagon.getRadius()));
				dialog.getBtnInnerColor().setBackground(hexagon.getInnerColor());
				dialog.getBtnOutlineColor().setBackground(hexagon.getColor());

				dialog.setVisible(true);
				dialog.setModal(true);

				if (dialog.isConfirm()) {
					ModifyHexagonCommand modifyHexagon = new ModifyHexagonCommand((HexagonAdapter) selectedShape, dialog.getHexagonAdapter());
					modifyHexagon.execute();
					addUndoList(modifyHexagon);
					addListModel(modifyHexagon, dialog.getHexagonAdapter());
				}
			}
		}
		frame.repaint();
	}
	
	public void deleteShape() {
		ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
		selectedShapes.addAll(model.getSelectedShapes());
		shapeForDelete = new ArrayList<Shape>();
		if(selectedShapes.size() == 1) {
			if(selectedShape != null) {
				int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Warning message",
						JOptionPane.YES_NO_OPTION);
				
				if(selectedOption == JOptionPane.YES_OPTION) {
					RemoveCommand removeShape = new RemoveCommand(model, selectedShape);
					removeShape.execute();
					addUndoList(removeShape);
					addListModel(removeShape, selectedShape);
					
				}
			}
		}else {
			int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Warning",
					JOptionPane.YES_NO_OPTION);
			
			if(selectedOption == JOptionPane.YES_OPTION) {
				for(Shape s: selectedShapes) {
					shapeForDelete.add(s);
				}
					
				RemoveCommand removeShape = new RemoveCommand(model, shapeForDelete);
				removeShape.execute();
				addUndoList(removeShape);
				addListModel(removeShape, null);
				
			}
			
		}
		frame.repaint();
	}
	
	public void bringToFront() {
		if(selectedShape != null && model.getSelectedShapes().size() == 1 &&
				model.getShapes().indexOf(selectedShape) != model.getShapes().size() - 1) {
			
			BringToFrontCommand bringToFront = new BringToFrontCommand(model, selectedShape);
			bringToFront.execute();
			frame.repaint();
			addUndoList(bringToFront);
			addListModel(bringToFront, selectedShape);
		}else {
			JOptionPane.showMessageDialog(null, "You haven't selected one shape or shape is already in front!", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void bringToBack() {
		if(selectedShape != null && model.getSelectedShapes().size() == 1 &&
				model.getShapes().indexOf(selectedShape) != 0) {
			
			BringToBackCommand bringToBack = new BringToBackCommand(model, selectedShape);
			bringToBack.execute();
			frame.repaint();
			addUndoList(bringToBack);
			addListModel(bringToBack, selectedShape);
			
		}else {
			JOptionPane.showMessageDialog(null, "You haven't selected one shape or shape is already in back", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void toFront() {
		if(selectedShape != null && model.getSelectedShapes().size() ==1 &&
				model.getShapes().indexOf(selectedShape) != model.getShapes().size() - 1) {
			
			ToFrontCommand toFront = new ToFrontCommand(model, selectedShape);
			toFront.execute();
			frame.repaint();
			addUndoList(toFront);
			addListModel(toFront, selectedShape);
			
		}else {
			JOptionPane.showMessageDialog(null, "You haven't selected one shape or shape is already in front!", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void toBack() {
		if(selectedShape != null && model.getSelectedShapes().size() ==1 &&
				model.getShapes().indexOf(selectedShape) != 0) {
			
			ToBackCommand toBack = new ToBackCommand(model, selectedShape);
			toBack.execute();
			frame.repaint();
			addUndoList(toBack);
			addListModel(toBack, selectedShape);
			
		}else {
			JOptionPane.showMessageDialog(null, "You haven't selected one shape or shape is already in back!", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void undo() {
		int lastCommand = undoList.size() -1;
		if(lastCommand <0) {
			JOptionPane.showMessageDialog(null, "You haven't elements in undo list!", "Error",
					JOptionPane.WARNING_MESSAGE);
		}else {
			undoList.get(lastCommand).unexecute();
			listModel.addElement("Undo " + undoList.get(lastCommand).toString());
			addRedoList(undoList.get(lastCommand));
			undoList.remove(lastCommand);
			frame.repaint();
			notifyObservers();
		}
	}
	
	public void redo() {
		int lastCommand = redoList.size() -1;
		if(lastCommand < 0) {
			JOptionPane.showMessageDialog(null, "You haven't elements in redo list", "Error",
					JOptionPane.WARNING_MESSAGE);
		}else {
			redoList.get(lastCommand).execute();
			listModel.addElement("Redo " + redoList.get(lastCommand).toString());
			addUndoList(redoList.get(lastCommand));
			redoList.remove(lastCommand);
			frame.repaint();
			notifyObservers();
		}
	}
	
	public void saveTxt() {
		int saveClick = fileChooser.showSaveDialog(frame.getPnlDrawing());
		if(saveClick == JFileChooser.APPROVE_OPTION) {
			FileStrategy saveTxt = new FileTxtManipulator(fileChooser.getSelectedFile().getPath() + ".txt", frame);
			FileStrategy fileManager = new FileManager(saveTxt);
			fileManager.save();
			
		}
	}
	
	public void saveObject() {
		int saveClick = fileChooser.showSaveDialog(frame.getPnlDrawing());
		if(saveClick == JFileChooser.APPROVE_OPTION) {
			FileStrategy saveObject = new FileObjectManipulator(fileChooser.getSelectedFile().getPath(), model);
			FileStrategy fileManager = new FileManager(saveObject);
			fileManager.save();
		}
	}
	
	
	public void load() {
		int loadClick = fileChooser.showOpenDialog(frame.getPnlDrawing());
		if(loadClick == JFileChooser.APPROVE_OPTION) {
			if(fileChooser.getSelectedFile().getPath().endsWith("txt")) {
				FileTxtManipulator loadTxt = new FileTxtManipulator(fileChooser.getSelectedFile().getPath(), frame);
				FileStrategy fileManager = new FileManager(loadTxt);
				fileManager.load();
				loggerFile = loadTxt.getLoggerFile();
			}else {
				FileStrategy loadObject = new FileObjectManipulator(fileChooser.getSelectedFile().getPath(),model);
				FileStrategy fileManager = new FileManager(loadObject);
				fileManager.load();
				frame.repaint();
			}
		}
	}
	
	
	public void next() {
		if(loggerFile != null && loggerFile.size() > 0) {
			String[] log = loggerFile.get(0).split(" ");
			switch(log[0]) {
			case "Draw":
				if(log[1].equals("Point")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					Point point = new Point(x, y, false, new Color(Integer.parseInt(log[5])));
					AddCommand addPoint = new AddCommand(model, point);
					addPoint.execute();
					addUndoList(addPoint);
					addListModel(addPoint, point);
				}else if(log[1].equals("Line")) {
					int startX = Integer.parseInt(log[2].split(",")[0].substring(1));
					int startY = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					int endX = Integer.parseInt(log[4].split(",")[0].substring(1));
					int endY = Integer.parseInt(log[4].split("")[1].substring(0, log[4].split(",")[1].length() -1));
					Line line = new Line(new Point(startX, startY), new Point(endX, endY), false, new Color(Integer.parseInt(log[7])));
					AddCommand addLine = new AddCommand(model, line);
					addLine.execute();
					addUndoList(addLine);
					addListModel(addLine, line);
				}else if(log[1].equals("Rectangle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					Rectangle rectangle = new Rectangle(new Point(x, y), Integer.parseInt(log[8]),
							Integer.parseInt(log[5]), false, new Color(Integer.parseInt(log[11])),
							new Color(Integer.parseInt(log[14])));
					AddCommand addRectangle = new AddCommand(model, rectangle);
					addRectangle.execute();
					addUndoList(addRectangle);
					addListModel(addRectangle, rectangle);
				}else if(log[1].equals("Circle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Circle circle = new Circle(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					AddCommand addCircle = new AddCommand(model, circle);
					addCircle.execute();
					addUndoList(addCircle);
					addListModel(addCircle, circle);
				}else if(log[1].equals("Donut")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Donut donut = new Donut(new Point(x, y), Integer.parseInt(log[5]), Integer.parseInt(log[8]),
							false, new Color(Integer.parseInt(log[11])), new Color(Integer.parseInt(log[14])));
					AddCommand addDonut = new AddCommand(model, donut);
					addDonut.execute();
					addUndoList(addDonut);
					addListModel(addDonut, donut);
				} else if(log[1].equals("Hexagon")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					HexagonAdapter hexagon = new HexagonAdapter(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					AddCommand addHexagon = new AddCommand(model, hexagon);
					addHexagon.execute();
					addUndoList(addHexagon);
					addListModel(addHexagon, hexagon);
				}
			break;
			
			case "Select":
				int index = 0;
				if(log[1].equals("Point")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Point point = new Point(x,y, false, new Color(Integer.parseInt(log[5])));
					index = model.getShapes().indexOf(point);
				}else if(log[1].equals("Line")) {
					int startX = Integer.parseInt(log[2].split(",")[0].substring(1));
					int startY = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					int endX = Integer.parseInt(log[4].split(",")[0].substring(1));
					int endY = Integer.parseInt(log[4].split(",")[1].substring(0, log[4].split(",")[1].length() -1));
					Line line = new Line(new Point(startX, startY), new Point(endX, endY), false,
							new Color(Integer.parseInt(log[7])));
					index = model.getShapes().indexOf(line);
				} else if(log[1].equals("Rectangle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Rectangle rectangle = new Rectangle(new Point(x, y), Integer.parseInt(log[8]),
							Integer.parseInt(log[5]), false, new Color(Integer.parseInt(log[11])),
							new Color(Integer.parseInt(log[14])));
					index = model.getShapes().indexOf(rectangle);
				} else if(log[1].equals("Circle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Circle circle = new Circle(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					index = model.getShapes().indexOf(circle);
				}else if(log[1].equals("Donut")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Donut donut = new Donut(new Point(x, y), Integer.parseInt(log[5]), Integer.parseInt(log[8]),
							false, new Color(Integer.parseInt(log[11])), new Color(Integer.parseInt(log[14])));
					index = model.getShapes().indexOf(donut);
				}else if(log[1].equals("Hexagon")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					HexagonAdapter hexagon = new HexagonAdapter(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					index = model.getShapes().indexOf(hexagon);
				}
				
				selectedShape = model.getShapes().get(index);
				selectedShape.setSelected(true);
				model.getSelectedShapes().add(selectedShape);
				listModel.addElement("Select " + selectedShape.toString());
			break;
			
			case "Unselect":
				int index1 = 0;
				if(log[1].equals("Point")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					Point point = new Point(x, y, false, new Color(Integer.parseInt(log[5])));
					index1 = model.getShapes().indexOf(point);
				}else if(log[1].equals("Line")) {
					int startX = Integer.parseInt(log[2].split(",")[0].substring(1));
					int startY = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					int endX = Integer.parseInt(log[4].split(",")[0].substring(1));
					int endY = Integer.parseInt(log[4].split(",")[1].substring(0, log[4].split(",")[1].length() - 1));
					Line line = new Line(new Point(startX, startY), new Point(endX, endY), false, new Color(Integer.parseInt(log[7])));
					index1 = model.getShapes().indexOf(line);
				} else if(log[1].equals("Rectangle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Rectangle rectangle = new Rectangle(new Point(x, y), Integer.parseInt(log[8]),
							Integer.parseInt(log[5]), false, new Color(Integer.parseInt(log[11])),
							new Color(Integer.parseInt(log[14])));
					index1 = model.getShapes().indexOf(rectangle);
				} else if(log[1].equals("Circle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Circle circle = new Circle(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					index1 = model.getShapes().indexOf(circle);
				} else if(log[1].equals("Donut")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Donut donut = new Donut(new Point(x, y), Integer.parseInt(log[5]), Integer.parseInt(log[8]),
							false, new Color(Integer.parseInt(log[11])), new Color(Integer.parseInt(log[14])));
					index1 = model.getShapes().indexOf(donut);
				} else if(log[1].equals("Hexagon")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					HexagonAdapter hexagon = new HexagonAdapter(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					index1 = model.getShapes().indexOf(hexagon);
				}
				model.getShapes().get(index1).setSelected(false);
				model.getSelectedShapes().remove(model.getShapes().get(index1));
				listModel.addElement("Unselect " + model.getShapes().get(index1).toString());
			break;
			
			case "ToFront":
				toFront();
			break;
			case "ToBack":
				toBack();
			break;
			case "BringToFront":
				bringToFront();
			break;
			case "BringToBack":
				bringToBack();
			break;
			case "Undo":
				undo();
			break;
			case "Redo":
				redo();
			break;
			
			case "Remove":
				if(log[1].equals("shapes")) {
					shapeForDelete =new ArrayList<Shape>();
					for(Shape s : model.getSelectedShapes()) {
						shapeForDelete.add(s);
					}
					RemoveCommand removeShape = new RemoveCommand(model, shapeForDelete);
					removeShape.execute();
					addUndoList(removeShape);
					addListModel(removeShape, null);
					break;
				}
				int index2 = 0;
				if(log[1].equals("Point")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					Point point = new Point(x,y, false, new Color(Integer.parseInt(log[5])));
					index2 = model.getShapes().indexOf(point);
				}else if(log[1].equals("Line")) {
					int startX = Integer.parseInt(log[2].split(",")[0].substring(1));
					int startY = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					int endX = Integer.parseInt(log[4].split(",")[0].substring(1));
					int endY = Integer.parseInt(log[4].split(",")[1].substring(0, log[4].split(",")[1].length() - 1));
					Line line = new Line(new Point(startX, startY), new Point(endX, endY), false, new Color(Integer.parseInt(log[7])));
					index2 = model.getShapes().indexOf(line);
				}else if(log[1].equals("Rectangle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Rectangle rectangle = new Rectangle(new Point(x, y), Integer.parseInt(log[8]),
							Integer.parseInt(log[5]), false, new Color(Integer.parseInt(log[11])),
							new Color(Integer.parseInt(log[14])));
					index2 = model.getShapes().indexOf(rectangle);
				}else if(log[1].equals("Circle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					Circle circle = new Circle(new Point(x,y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					index2 = model.getShapes().indexOf(circle);
							
					
				}else if(log[1].equals("Donut")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Donut donut = new Donut(new Point(x, y), Integer.parseInt(log[5]), Integer.parseInt(log[8]),
							false, new Color(Integer.parseInt(log[11])), new Color(Integer.parseInt(log[14])));
					index2 = model.getShapes().indexOf(donut);
					
				} else if(log[1].equals("Hexagon")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					HexagonAdapter hexagon = new HexagonAdapter(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					index2 = model.getShapes().indexOf(hexagon);
				}
				
				RemoveCommand removeCommand = new RemoveCommand(model,model.getShapes().get(index2));
				addUndoList(removeCommand);
				addListModel(removeCommand, model.getShapes().get(index2));
				removeCommand.execute();
			
			break;
			
			case "Modify":
				if(log[1].equals("Point")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					Point point = new Point(x, y, false, new Color(Integer.parseInt(log[5])));
					ModifyPointCommand modifyPoint = new ModifyPointCommand((Point) selectedShape, point);
					modifyPoint.execute();
					addUndoList(modifyPoint);
					addListModel(modifyPoint, point);
				}else if(log[1].equals("Line")) {
					int startX = Integer.parseInt(log[2].split(",")[0].substring(1));
					int startY = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() -1));
					int endX = Integer.parseInt(log[4].split(",")[0].substring(1));
					int endY = Integer.parseInt(log[4].split(",")[1].substring(0, log[4].split(",")[1].length() -1));
					Line line = new Line(new Point(startX, startY), new Point(endX, endY), false, new Color(Integer.parseInt(log[7])));
					ModifyLineCommand modifyLine = new ModifyLineCommand((Line) selectedShape, line);
					modifyLine.execute();
					addUndoList(modifyLine);
					addListModel(modifyLine, line);
					
				}else if(log[1].equals("Rectangle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Rectangle rectangle = new Rectangle(new Point(x, y), Integer.parseInt(log[8]),
							Integer.parseInt(log[5]), false, new Color(Integer.parseInt(log[11])),
							new Color(Integer.parseInt(log[14])));
					ModifyRectangleCommand modifyRectangle = new ModifyRectangleCommand((Rectangle) selectedShape, rectangle);
					modifyRectangle.execute();
					addUndoList(modifyRectangle);
					addListModel(modifyRectangle, rectangle);
				} else if(log[1].equals("Circle")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Circle circle = new Circle(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					ModifyCircleCommand modifyCircle = new ModifyCircleCommand((Circle) selectedShape, circle);
					modifyCircle.execute();
					addUndoList(modifyCircle);
					addListModel(modifyCircle, circle);
				} else if(log[1].equals("Donut")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					Donut donut = new Donut(new Point(x, y), Integer.parseInt(log[5]), Integer.parseInt(log[8]),
							false, new Color(Integer.parseInt(log[11])), new Color(Integer.parseInt(log[14])));
					ModifyDonutCommand modifyDonut = new ModifyDonutCommand((Donut) selectedShape, donut);
					modifyDonut.execute();
					addUndoList(modifyDonut);
					addListModel(modifyDonut, donut);
				} else if(log[1].equals("Hexagon")) {
					int x = Integer.parseInt(log[2].split(",")[0].substring(1));
					int y = Integer.parseInt(log[2].split(",")[1].substring(0, log[2].split(",")[1].length() - 1));
					HexagonAdapter hexagon = new HexagonAdapter(new Point(x, y), Integer.parseInt(log[5]), false,
							new Color(Integer.parseInt(log[8])), new Color(Integer.parseInt(log[11])));
					ModifyHexagonCommand modifyHexagon = new ModifyHexagonCommand((HexagonAdapter) selectedShape, hexagon);
					modifyHexagon.execute();
					addUndoList(modifyHexagon);
					addListModel(modifyHexagon, hexagon);
				}
			break;
			}
			notifyObservers();
			loggerFile.remove(0);
			frame.repaint();
		}else {
			JOptionPane.showMessageDialog(null, "You don't have shape in logger file!", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		if(model.getSelectedShapes().size() == 1) {
			deleteBtn = true;
			modifyBtn = true;
		}else if(model.getSelectedShapes().size() >= 2) {
			deleteBtn = true;
			modifyBtn = false;
		}else {
			deleteBtn = false;
			modifyBtn = false;
		}
		
		Iterator<Observer> it = observers.iterator();
		while(it.hasNext()) {
			it.next().update(deleteBtn, modifyBtn);
		}
	}
	


	

}

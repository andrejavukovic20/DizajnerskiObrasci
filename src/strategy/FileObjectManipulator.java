package strategy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mvc.DrawingModel;

public class FileObjectManipulator implements FileStrategy{
	
	private String filePath;
	private DrawingModel model;
	
	public FileObjectManipulator(String filePath, DrawingModel model) {
		this.filePath = filePath;
		this.model = model;
	}
	
	@Override
	public void save() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(model);
			objectOutputStream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void load() {
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			DrawingModel readModel = (DrawingModel)objectInputStream.readObject();
			for(int i =0; i < readModel.getShapes().size(); i++) {
				model.getShapes().add(readModel.getShapes().get(i));
			}
			objectInputStream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}

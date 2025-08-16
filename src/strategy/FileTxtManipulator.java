package strategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mvc.DrawingFrame;

public class FileTxtManipulator implements FileStrategy{
	
	private String filePath;
	private DrawingFrame frame;
	private List<String> loggerFile = new ArrayList<String>();
	
	public FileTxtManipulator(String filePath, DrawingFrame frame) {
		this.filePath = filePath;
		this.frame = frame;
	}
	
	
	public void save() {
		try {
			List<Object> listModel = Arrays.asList(frame.getListModel().toArray());
			//upis u txt
			PrintWriter printWriter = new PrintWriter(filePath);
			for(int i =0; i< listModel.size(); i++) {
				printWriter.println(listModel.get(i));
			}
			printWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void load() {
		try {
			FileReader fileReader = new FileReader(new File(filePath));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null){
				loggerFile.add(line);
			}
			bufferedReader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<String> getLoggerFile(){
		return loggerFile;
	}
	
	public void setLoggerFile(List<String> loggerFile) {
		this.loggerFile = loggerFile;
	}

}

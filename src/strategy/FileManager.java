package strategy;

public class FileManager implements FileStrategy{
	
	private FileStrategy fileStrategy;
	
	public FileManager(FileStrategy fileStrategy) {
		this.fileStrategy = fileStrategy;
	}
	
	@Override
	public void save() {
		fileStrategy.save();
	}

	@Override
	public void load() {
		fileStrategy.load();
	}
}

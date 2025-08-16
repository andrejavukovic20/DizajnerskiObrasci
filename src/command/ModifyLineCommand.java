package command;

import geometry.Line;

public class ModifyLineCommand implements Command{

	private Line tempLine;
	private Line prevLine;
	private Line newLine;
	
	public ModifyLineCommand(Line prevLine, Line newLine) {
		this.prevLine = prevLine;
		this.newLine = newLine;
	}

	@Override
	public void execute() {
		this.tempLine = prevLine.clone();
		this.prevLine.setStartPoint(newLine.getStartPoint());
		this.prevLine.setEndPoint(newLine.getEndPoint());
		this.prevLine.setColor(newLine.getColor());
	}

	@Override
	public void unexecute() {
		this.prevLine.setStartPoint(tempLine.getStartPoint());
		this.prevLine.setEndPoint(tempLine.getEndPoint());
		this.prevLine.setColor(tempLine.getColor());
	}
}

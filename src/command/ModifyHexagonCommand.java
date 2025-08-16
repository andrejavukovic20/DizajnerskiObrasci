package command;

import geometry.HexagonAdapter;

public class ModifyHexagonCommand implements Command {
	
	private HexagonAdapter tempHexagon;
	private HexagonAdapter prevHexagon;
	private HexagonAdapter newHexagon;
	
	
	public ModifyHexagonCommand(HexagonAdapter prevCircle, HexagonAdapter newCircle) {
		this.prevHexagon = prevCircle;
		this.newHexagon = newCircle;
	}

	
	public void execute() {
		this.tempHexagon = prevHexagon.clone();
		this.prevHexagon.setCenter(newHexagon.getCenter());
		this.prevHexagon.setRadius(newHexagon.getRadius());
		this.prevHexagon.setColor(newHexagon.getColor());
		this.prevHexagon.setInnerColor(newHexagon.getInnerColor());
	}
	
	public void unexecute() {
		this.prevHexagon.setCenter(tempHexagon.getCenter());
		this.prevHexagon.setRadius(tempHexagon.getRadius());
		this.prevHexagon.setColor(tempHexagon.getColor());
		this.prevHexagon.setInnerColor(tempHexagon.getInnerColor());
	}
}

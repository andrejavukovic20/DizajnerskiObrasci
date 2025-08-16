package command;

import geometry.Donut;

public class ModifyDonutCommand implements Command{
	
	private Donut tempDonut;
	private Donut prevDonut;
	private Donut newDonut;
	
	
	public ModifyDonutCommand(Donut prevDonut, Donut newDonut) {
		this.prevDonut = prevDonut;
		this.newDonut = newDonut;
	}

	@Override
	public void execute() {
		this.tempDonut = prevDonut.clone();
		this.prevDonut.setCenter(newDonut.getCenter());
		try {
			this.prevDonut.setRadius(newDonut.getRadius());
			this.prevDonut.setInnerRadius(newDonut.getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.prevDonut.setColor(newDonut.getColor());
		this.prevDonut.setInnerColor(newDonut.getInnerColor());
	}

	@Override
	public void unexecute() {
		this.prevDonut.setCenter(tempDonut.getCenter());
		try {
			this.prevDonut.setRadius(tempDonut.getRadius());
			this.prevDonut.setInnerRadius(tempDonut.getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.prevDonut.setColor(tempDonut.getColor());
		this.prevDonut.setInnerColor(tempDonut.getInnerColor());
	}

}

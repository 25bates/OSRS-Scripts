import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.script.Category;

@ScriptManifest(author = "Cullen Bates", name = "My First Script", version = 1.0, 
description = "Simple Gang", category = Category.THIEVING)
public class TestScript extends AbstractScript {

	@Override
	public void onStart() {
		log("hacker time");
	}
	
	private enum State{
		STEAL, DROP, WAIT
	};
	
	private State getState() {
		GameObject stall = getGameObjects().closest("Tea stall");
		if(!getInventory().isEmpty())
			return State.DROP;
		if(stall != null)
			return State.STEAL;
		return State.WAIT;
	}
	
	
	@Override
	public void onExit() {
		log("u done");
	}
	

	@Override
	public int onLoop() {
		switch(getState()) {
		
		case STEAL:
			GameObject stall = getGameObjects().closest("Tea stall");
			if(stall != null)
				stall.interact("Steal-from");
			break;
			
		case DROP:
			getInventory().drop("Cup of Tea");
			break;
			
		case WAIT:
			Calculations.random(0, 1000);
			break;
			
		}
		
		return Calculations.random(500, 600);
		
	}
	
		
}
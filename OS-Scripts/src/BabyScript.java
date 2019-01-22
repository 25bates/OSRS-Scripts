import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.script.Category;

@ScriptManifest(author = "Cullen Bates", name = "BabyScript", version = 1.0, 
description = "Simple Gang", category = Category.COMBAT)
public class BabyScript extends AbstractScript {
	
	public static final Filter<NPC> cowFilter = npc -> npc != null
			&& npc.getName().equals("Cow") 
			&& npc.getInteractingCharacter() == null;
	
	
	
	@Override
	public void onStart() {
		log("hacker time");
	}
	
	private enum State{
		FIGHT, EAT, WAIT
	};
	
	private State getState() { 
		if(getLocalPlayer().getHealthPercent() < 30)
			return State.EAT;
		if(getLocalPlayer().canAttack() && 
		   getLocalPlayer().getHealthPercent() > 30)
			return State.FIGHT;
		return State.WAIT;
	}
	
	
	@Override
	public void onExit() {
		log("u done");
	}
	

	@Override
	public int onLoop() {
		log(getState().toString());
		switch(getState()) {
		
		case EAT:
			if(!getTabs().isOpen(Tab.INVENTORY))
			{
				getTabs().open(Tab.INVENTORY);
			}
			else
			{
				Item food = getInventory().get(
						item -> item != null && item.hasAction("Eat"));
				if(food != null)
				{
					food.interact("Eat");
					MethodProvider.sleepUntil(() -> getLocalPlayer().getHealthPercent() > 70, 1500);
				}
			}
			break;
			
		case FIGHT:
			if(getLocalPlayer().isInCombat())
				break;
			else
			{
				NPC cow = getNpcs().closest(cowFilter);
				cow.interact("Attack");
			}
				
			
		case WAIT:
			Calculations.random(0, 1000);
			break;
			
		}
		
		return Calculations.random(500, 600);
		
	}
	
		
}
package game;

import engine.GameContainer;
import engine.Input;

public class Controller
{
	private Input input;
	
	private boolean forwardKey = false;
	private boolean backwardKey = false;
	private boolean jumpKey = false;
	private boolean attackKey = false;
	
	public Controller()
	{
		
	}
	
	public void init(GameContainer gc)
	{
		input = gc.getInput();
	}
	
	public void update(GameContainer gc, float dt)
	{
		
	}
}

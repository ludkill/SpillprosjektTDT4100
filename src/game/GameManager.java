package game;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;

public class GameManager extends AbstractGame
{
	
	public GameManager()
	{
		push(new PlayState());
	}
	
	@Override
	public void update(GameContainer gc, float dt)
	{
		peek().update(gc, dt);
	}
	
	@Override
	public void render(GameContainer gc, Renderer r)
	{
		peek().render(gc, r);
	}
	
	public static void main(String[] args)
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(426);
		gc.setHeight(240);
		gc.setScale(3);
		gc.setLockFrameRate(false);
		gc.setClearScreen(true);
		gc.setDynamicLights(false);
		gc.setLightingEnabled(false);
		gc.start();
	}
	
	@Override
	public void init(GameContainer gc)
	{
		// TODO Auto-generated method stub
		
	}
}

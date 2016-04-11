package game;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;

public class GameManager extends AbstractGame
{
	PlayState level1 = new PlayState();
	
	public GameManager()
	{
		push(level1);
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
		r.setClearColor(0xff7EC0EE);
	}
	
	public static void main(String[] args)
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(426);
		gc.setHeight(240);
		gc.setScale(3);
		gc.setLockFrameRate(false);
		gc.setClearScreen(true);
		gc.setDynamicLights(true);
		gc.setLightingEnabled(false);
		gc.start();
	}
	
	@Override
	public void init(GameContainer gc)
	{
		level1.init(gc);
	}
}

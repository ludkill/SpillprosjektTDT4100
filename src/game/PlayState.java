package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.ObjectManager;
import engine.components.State;
import engine.fx.animations.Sprite;

public class PlayState extends State
{
	
	public PlayState()
	{
		Sprite tileSet = new Sprite();
		tileSet.loadSprite("/tileSet.png");
		
		
		manager = new ObjectManager();
		Level level = new Level("res/map.txt", tileSet , manager);
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		manager.updateObjects(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		manager.renderObjects(gc, r);
	}

	@Override
	public void dispose()
	{
		manager.disposeObjects();
	}

	public ObjectManager getManager()
	{
		return manager;
	}

	public void setManager(ObjectManager manager)
	{
		this.manager = manager;
	}

}

package test;

import engine.GameContainer;
import engine.Renderer;
import engine.components.ObjectManager;
import engine.components.State;

public class PlayState extends State
{
	
	public PlayState()
	{
//		manager = new ObjectManager();
//		manager.addObject(new Player(0,0));
//		manager.addObject(new Ball(152, 112));
//		manager.addObject(new Enemy(384, 0));
//		manager.addObject(new Grant(0, 0));
		manager.addObject(new Function());
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

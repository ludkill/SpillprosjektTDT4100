package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;
import engine.fx.Image;

public class Tile extends GameObject
{
	private Image sprite;
	private int collisionValue;
	
	public Tile(int x, int y, Image sprite, int collisionValue)
	{
		setTag("tile");
		this.x = x;
		this.y = y;
		height = 32;
		width = 32;
		this.collisionValue = collisionValue;
		
		this.sprite = sprite;
		if(collisionValue == 1)
		{
			
			addComponent(new Collider());
		}
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		updateComponents(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawImage(sprite, (int) x, (int) y);
	}

	@Override
	public void componentEvent(String name, GameObject object)
	{

	}

	@Override
	public void dispose()
	{
		
	}

	public int getCollisionValue()
	{
		return collisionValue;
	}

	public void setCollisionValue(int collisionValue)
	{
		this.collisionValue = collisionValue;
	}
}

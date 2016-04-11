package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;
import engine.fx.Image;

public class Player extends GameObject
{
	private Image sprite;
	private Controller controller;
	
	private float prevX, prevY;
	
	public Player(int x, int y, int height, int width, Image sprite)
	{
		setTag("player");
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.sprite = sprite;
		
		prevX = x;
		prevY = y;
		
		addComponent(new Collider());
	}
	
	@Override
	public void update(GameContainer gc, float dt)
	{

	}
	
	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawImage(sprite, (int) x, (int) y);
	}
	
	@Override
	public void componentEvent(String name, GameObject object)
	{
		if (name.equalsIgnoreCase("collider"))
		{
			if (object.getY() < prevY - height)
			{
			
			} else if (object.getY() - object.getHeight() > prevY)
			{
			
			}
			
		}
	}
	
	@Override
	public void dispose()
	{
	
	}
}

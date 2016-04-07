package test;

import java.awt.event.KeyEvent;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;

public class Player extends GameObject
{
	
	public Player(int x, int y)
	{
		setTag("player");
		this.x = x;
		this.y = y;
		width = 16;
		height = 64;
		addComponent(new Collider());
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		if(gc.getInput().isKey(KeyEvent.VK_W))
		{
			y -= 70 * dt;
			if( y < 0)
			{
				y = 0;
			}
		}
		
		if(gc.getInput().isKey(KeyEvent.VK_S))
		{
			y += 70 * dt;
			if( y > gc.getHeight() - height)
			{
				y =  gc.getHeight() - height;
			}
		}
		updateComponents(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawRect((int)x, (int)y, (int)width, (int)height, 0xffffffff);
	}

	@Override
	public void dispose()
	{
		
	}

	@Override
	public void componentEvent(String name, GameObject object)
	{
		
	}

}

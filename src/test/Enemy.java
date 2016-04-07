package test;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;
import engine.fx.ShadowType;

public class Enemy extends GameObject
{
	private GameObject target = null;
	
	public Enemy(int x, int y)
	{
		this.x = x;
		this.y = y;
		width = 16;
		height = 64;
		
		addComponent(new Collider());
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		if(target == null)
		{
			target = gc.getGame().peek().getManager().findObject("ball");
		}
		
		
		if(target.getY() + target.getHeight() / 2 - height / 2 > y + 30)
		{
			y += dt * 150;
		}
		
		if(target.getY() + target.getHeight()/2 - height/ 2 < y - 30)
		{
			y -= dt * 150;
		}
		
		updateComponents(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawFillRect((int)x, (int)y, (int)width, (int)height, 0xffff0000,ShadowType.TOTAL);
	}

	@Override
	public void componentEvent(String name, GameObject object)
	{
		
	}

	@Override
	public void dispose()
	{
		
	}

}

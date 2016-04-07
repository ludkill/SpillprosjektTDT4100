package test;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;
import engine.fx.Light;

public class Ball extends GameObject
{
	boolean left = true;
	float speedY = 0;
	
	Light light = new Light(0xffffffff, 100);
	
	public Ball(int x, int y)
	{
		setTag("ball");
		this.x = x;
		this.y = y;
		width = 16;
		height = 16;
		addComponent(new Collider());
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		if(left)
		{
			x -= 150 * dt;
		}
		else
		{
			x += 150 * dt;
		}
		
		if(y < 0)
		{
			y = 0;
			speedY *= -1;
		}
		if(y + height > gc.getHeight())
		{
			y = gc.getHeight() - height;
			speedY *= -1;
		}
		
		if(x < 0 || x > gc.getWidth() - height)
		{
			x = 152;
			y = 112;
			speedY = 0;
		}
		
		y += speedY;
		
		updateComponents(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawRect((int)x, (int)y, (int)width, (int)height, 0xff00ff00);
		r.drawLight(light, (int)(x + width/2), (int)(y + width/2));
	}

	@Override
	public void dispose()
	{
		
	}

	@Override
	public void componentEvent(String name, GameObject object)
	{
		if(name.equalsIgnoreCase("collider"))
		{
			if(object.getX() < x)
			{
				left = false;
				x = object.getX() + object.getWidth();
			}
			else
			{
				left = true;
				x = object.getX() - this.getWidth();
			}
			
			speedY = -8*(object.getY() + object.getHeight() / 2 - y  - height / 2) / object.getHeight();
		}
	}

}

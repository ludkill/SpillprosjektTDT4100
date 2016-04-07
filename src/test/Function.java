package test;

import com.sun.glass.events.KeyEvent;

import engine.GameContainer;
import engine.Renderer;
import engine.components.GameObject;
import engine.fx.Image;
import engine.fx.Light;
import engine.fx.ShadowType;

public class Function extends GameObject
{
	double x;
	int y;
	int transX;
	
	Image image = new Image("/sky.png");
	Light light = new Light(0xffbbbbbb, 200);
	
	public Function()
	{
		x = 0;
		y = (int) Math.sin(x);
		transX = 0;
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		if(gc.getInput().isKey(KeyEvent.VK_D))
		{
			transX += 200 * dt;
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawRect(0, 0, gc.getWidth(), gc.getHeight(), 0xff7EC0EE);
		r.drawImage(image);
		r.drawLight(light, 399, 0);
		x = 0 + transX;
		while(x - transX < gc.getWidth())
		{
			y = (int) (30* Math.sin(0.02 * x) + 1 * Math.sin(0.2 * x + 30) + 20 * Math.sin(0.007 * x) - 10 * Math.sin(0.002 * x) + 0.5 * Math.sin(0.25 * x + 10));
			
			r.setPixel((int)(x - transX), y + 200, 0xff005C09, ShadowType.FADE);
			r.setPixel((int)(x - transX), y + 200 - 1, 0xff005C09, ShadowType.FADE);
			r.setPixel((int)(x - transX), y + 200 - 2, 0xff005C09, ShadowType.FADE);
			while(y < gc.getHeight())
			{
				y++;
				r.setPixel((int)(x - transX), (int) y + 200, 0xff573B0C, ShadowType.FADE);
			}
			x++;
		}
	}

	@Override
	public void componentEvent(String name, GameObject object)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

}

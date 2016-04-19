package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;
import engine.components.ObjectManager;
import engine.fx.Light;
import engine.fx.animations.Animation;
import engine.fx.animations.Sprite;

public class Projectile extends GameObject
{
	int speedX, speedY;
	
	ObjectManager manager;
	Animation animation;
	Sprite sprite = new Sprite();
	Light light = new Light(0xff008800, 10);
	
	
	public Projectile(int x, int y, int speedX, int speedY, ObjectManager manager)
	{
		setTag("projectile");
		this.x = x;
		this.y = y;
		this.width = 5;
		this.height = 5;
		this.speedX = speedX;
		this.speedY = speedY;
		this.manager = manager;
		
		
		addComponent(new Collider());
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		this.x += speedX * dt;
		this.y += speedY * dt;
		
//		animation.update(dt);
		
		updateComponents(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		r.drawFillRect((int) x, (int) y, (int) width, (int) height, 0xff00ff00);
		r.drawLight(light,(int) (x + width / 2), (int) (y + height / 2));
	}

	@Override
	public void componentEvent(String name, GameObject object)
	{
		if (name.equalsIgnoreCase("collider"))
		{			
			if (object instanceof Player)
			{
				manager.removeObject(this);
			}
			
			if (object instanceof Tile)
			{
				manager.removeObject(this);
			}
			
			if (object instanceof Enemy)
			{
				manager.removeObject(this);
			}
		}
	}

	@Override
	public void dispose()
	{
		
	}

}

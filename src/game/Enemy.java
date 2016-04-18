package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.GameObject;
import engine.fx.animations.Animation;
import engine.fx.animations.Sprite;

public class Enemy extends GameObject
{
	
	private Sprite sprite = new Sprite();
	private Animation animation;
	
	private int health;
	private int damage;
	private int speedX;
	
	public Enemy(int x, int y, int height, int width, int health, int damage)
	{
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.health = health;
		this.damage = damage;
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		x += speedX * dt;
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		
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

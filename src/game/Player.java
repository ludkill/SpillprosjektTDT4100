package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;
import engine.fx.Image;
import engine.fx.animations.Animation;
import engine.fx.animations.Frame;
import engine.fx.animations.Sprite;

public class Player extends GameObject
{
	private Sprite sprite;
	private Controller controller;
	
	private Animation animation;
	
	private float prevX, prevY;
	private float forceX, forceY;
	private float speedX, speedY;
	
	public Player(int x, int y, int height, int width)
	{
		setTag("player");
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.controller = new Controller();
		
		prevX = x;
		prevY = y;
		
		forceX = 0;
		forceY = 150;
		speedX = 0;
		speedY = 0;
		
		addComponent(new Collider());
		
		sprite = new Sprite();
		sprite.loadSprite("/walking.png");
		sprite.setTileSize(64);
		Image[] frames = {sprite.getSpriteAsImage(0), sprite.getSpriteAsImage(1)};
		animation = new Animation(frames, 0.2);
	}
	
	public void init(GameContainer gc)
	{
		controller.init(gc);
	}
	
	@Override
	public void update(GameContainer gc, float dt)
	{
		prevX = x;
		prevY = y;
		
		controller.update(gc, dt);
		
		if(controller.isJumpKeyPressed())
		{
			speedY = -100;
		}
		
		speedX += forceX * dt;
		speedY += forceY * dt;
		
		x += speedX * dt;
		y += speedY * dt;
		
		animation.update(dt);
		animation.start();
		
		updateComponents(gc, dt);
	}
	
	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawImage(animation.getSprite(), (int)(x + 0.5f), (int)(y + 0.5f));
	}
	
	@Override
	public void componentEvent(String name, GameObject object)
	{
		if (name.equalsIgnoreCase("collider"))
		{
			if(object.getTag().equals("tile"))
			{
				if (object.getY() < prevY - height)
				{
					y = prevY;
					speedY = 0;
				} else if (object.getY() - object.getHeight() > prevY)
				{
					y = prevY;
					speedY = 0;
				}
			}
		}
	}
	
	@Override
	public void dispose()
	{
	
	}
}

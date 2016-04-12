package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;
import engine.fx.Image;
import engine.fx.Light;
import engine.fx.ShadowType;
import engine.fx.animations.Animation;
import engine.fx.animations.Sprite;

public class Player extends GameObject
{
	private Sprite sprite;
	private Controller controller;
	private Animation RightWalkingAnimation;
	private Animation LeftWalkingAnimation;
	private Light light;
	
	private float prevX, prevY;
	private float forceX, forceY;
	private float speedX, speedY;
	private boolean grounded = true;
	private boolean left = true;
	
	public Player(int x, int y, int height, int width)
	{
		setTag("player");
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.controller = new Controller();
		
		light = new Light(0xffbbddbb, 150);
		
		prevX = x;
		prevY = y;
		
		forceX = 0;
		forceY = 150;
		speedX = 0;
		speedY = 0;
		
		addComponent(new Collider());
		
		sprite = new Sprite();
		sprite.loadSprite("/wizard.png");
		sprite.setHeight(48);
		sprite.setWidth(32);
		sprite.setShadowType(ShadowType.FADE);
		Image[] walkingFramesRight = { sprite.getSpriteAsImage(16), sprite.getSpriteAsImage(17),
				sprite.getSpriteAsImage(18), sprite.getSpriteAsImage(19), sprite.getSpriteAsImage(20),
				sprite.getSpriteAsImage(21), sprite.getSpriteAsImage(22), sprite.getSpriteAsImage(23) };
		Image[] walkingFramesLeft = { sprite.getSpriteAsImage(15), sprite.getSpriteAsImage(14),
				sprite.getSpriteAsImage(13), sprite.getSpriteAsImage(12), sprite.getSpriteAsImage(11),
				sprite.getSpriteAsImage(10), sprite.getSpriteAsImage(9), sprite.getSpriteAsImage(8) };
				
		RightWalkingAnimation = new Animation(walkingFramesRight, 0.12);
		LeftWalkingAnimation = new Animation(walkingFramesLeft, 0.12);
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
		
		if (grounded)
		{
			forceX = 0;
			if (controller.isJumpKeyDown())
			{
				grounded = false;
				speedY = -100;
			}
			
			if (controller.isForwardKeyDown())
			{
				speedX = 100;
				RightWalkingAnimation.start();
				LeftWalkingAnimation.stop();
				left = false;
			} else if (controller.isBackwardKeyDown())
			{
				speedX = -100;
				RightWalkingAnimation.stop();
				LeftWalkingAnimation.start();
				left = true;
			} else
			{
				speedX = 0;
				RightWalkingAnimation.reset();
				LeftWalkingAnimation.reset();
				RightWalkingAnimation.stop();
				LeftWalkingAnimation.stop();
			}
		} else
		{
			if (controller.isForwardKeyDown())
			{
				forceX = 200;
			} else if (controller.isBackwardKeyDown())
			{
				forceX = -200;
			} else
			{
				forceX = 0;
			}
		}
		
		speedX += forceX * dt;
		speedY += forceY * dt;
		
		if (speedX > 100)
		{
			speedX = 100;
		}
		if (speedX < -100)
		{
			speedX = -100;
		}
		
		x += speedX * dt;
		y += speedY * dt;
		
		RightWalkingAnimation.update(dt);
		LeftWalkingAnimation.update(dt);
		
		updateComponents(gc, dt);
	}
	
	@Override
	public void render(GameContainer gc, Renderer r)
	{
		if (left)
		{
			r.drawImage(LeftWalkingAnimation.getSprite(), (int) (x - 8.5f), (int) (y + 0.5f));
			r.drawLight(light, (int) (x + 0.5f), (int) (y + 24.5f));
		} else
		{
			r.drawImage(RightWalkingAnimation.getSprite(), (int) (x - 8.5f), (int) (y + 0.5f));
			r.drawLight(light, (int) (x + 20.5f), (int) (y + 24.5f));
		}
	}
	
	@Override
	public void componentEvent(String name, GameObject object)
	{
		if (name.equalsIgnoreCase("collider"))
		{
			if (object.getTag().equals("tile"))
			{
				float playerCenterX = x + width / 2;
				float playerCenterY = y + height / 2;
				float objectCenterX = object.getX() + object.getWidth() / 2;
				float objectCenterY = object.getY() + object.getHeight() / 2;
				
				double dx = x - prevX;
				double dy = y - prevY;
				
				int directionX = (int) Math.signum(dx);
				int directionY = (int) Math.signum(dy);
				
				double dh1 = Math.abs((playerCenterX + width / 2) - (objectCenterX - object.getWidth() / 2));
				double dh2 = Math.abs((playerCenterX - width / 2) - (objectCenterX + object.getWidth() / 2));
				double dv1 = Math.abs((playerCenterY + height / 2) - (objectCenterY - object.getHeight() / 2));
				double dv2 = Math.abs((playerCenterY - height / 2) - (objectCenterY + object.getHeight() / 2));
				
				double dh = Math.min(dh1, dh2);
				double dv = Math.min(dv1, dv2);
				
				if(dh > dv)
				{
					y -= directionY * dv;
					speedY = 0;
					grounded = true;
				}else
				{
					x -= directionX * dh;
					speedX = 0;
				}
			}
		}
	}
	
	@Override
	public void dispose()
	{
	
	}
}

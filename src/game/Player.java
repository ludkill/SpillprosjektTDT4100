package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.Collider;
import engine.components.GameObject;
import engine.components.ObjectManager;
import engine.components.State;
import engine.fx.Image;
import engine.fx.Light;
import engine.fx.Pixel;
import engine.fx.ShadowType;
import engine.fx.animations.Animation;
import engine.fx.animations.Sprite;

public class Player extends GameObject
{
	private State state;
	private ObjectManager manager;
	
	private Sprite sprite;
	private Controller controller;
	private Animation rightWalkingAnimation;
	private Animation leftWalkingAnimation;
	private Animation rightJumpingAnimation;
	private Animation leftJumpingAnimation;
	private Animation rightAttackAnimation;
	private Animation leftAttackAnimation;
	private Light light;	
	private float prevX, prevY;
	private float forceX, forceY;
	private float speedX, speedY;
	private boolean grounded = true;
	private boolean left = true;
	
	private boolean walkingLeft = true;
	private boolean walkingRight = false;
	private boolean jumpingLeft;
	private boolean jumpingRight;
	private boolean attackingLeft;
	private boolean attackingRight;
	boolean spawnLeftProjectile = false;
	boolean spawnRightProjectile = false;
	
	private int health;
	private int mana;
	
	
	public Player(int x, int y, int height, int width, State state)
	{
		setTag("player");
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.controller = new Controller();
		this.state = state;
		this.manager = state.getManager();
		
		health = 100;
		mana = 100;
		
		light = new Light(0xffbbddbb, 150);
		
		prevX = x;
		prevY = y;
		
		forceX = 0;
		forceY = 300;
		speedX = 0;
		speedY = 0;
		
		addComponent(new Collider());
		
		sprite = new Sprite();
		sprite.loadSprite("/wizard.png");
		sprite.setHeight(48);
		sprite.setWidth(32);
		sprite.setShadowType(ShadowType.FADE);
		Image[] walkingFramesRight = { sprite.getSpriteAsImage(16), 
									   sprite.getSpriteAsImage(17),
									   sprite.getSpriteAsImage(18), 
									   sprite.getSpriteAsImage(19), 
									   sprite.getSpriteAsImage(20),
									   sprite.getSpriteAsImage(21), 
									   sprite.getSpriteAsImage(22),
									   sprite.getSpriteAsImage(23) };
		
		Image[] walkingFramesLeft = { sprite.getSpriteAsImage(15), 
								  	  sprite.getSpriteAsImage(14),
									  sprite.getSpriteAsImage(13), 
									  sprite.getSpriteAsImage(12), 
									  sprite.getSpriteAsImage(11),
									  sprite.getSpriteAsImage(10), 
									  sprite.getSpriteAsImage(9), 
									  sprite.getSpriteAsImage(8) };
		
		Image[] jumpingFramesLeft = { sprite.getSpriteAsImage(32), 
									  sprite.getSpriteAsImage(33),
									  sprite.getSpriteAsImage(34),
									  sprite.getSpriteAsImage(35) };
		
		Image[] jumpingFramesRight = { sprite.getSpriteAsImage(36), 
									   sprite.getSpriteAsImage(37),
									   sprite.getSpriteAsImage(38), 
									   sprite.getSpriteAsImage(39) };
		
		Image[] attackFramesLeft = { sprite.getSpriteAsImage(24), 
				   					  sprite.getSpriteAsImage(25),
				   					  sprite.getSpriteAsImage(26), 
				   					  sprite.getSpriteAsImage(27) };
		
		Image[] attackFramesRight = { sprite.getSpriteAsImage(28), 
					  				  sprite.getSpriteAsImage(29),
					  				  sprite.getSpriteAsImage(30), 
					  				  sprite.getSpriteAsImage(31) };
		
				
		rightWalkingAnimation = new Animation(walkingFramesRight, 0.12);
		leftWalkingAnimation = new Animation(walkingFramesLeft, 0.12);
		rightJumpingAnimation = new Animation(jumpingFramesRight, 0.12);
		leftJumpingAnimation = new Animation(jumpingFramesLeft, 0.12);
		rightAttackAnimation = new Animation(attackFramesRight, 0.2);
		leftAttackAnimation = new Animation(attackFramesLeft, 0.12);
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

		if(spawnRightProjectile && rightAttackAnimation.isStopped())
		{
			manager.addObject(new Projectile((int)(x + 20.5f), (int)(y + 24.5f), 300, 0, manager));
			spawnRightProjectile = false;
		}
		if(spawnLeftProjectile && leftAttackAnimation.isStopped())
		{
			manager.addObject(new Projectile((int)(x + 0.5f), (int)(y + 24.5f), -300, 0, manager));
			spawnLeftProjectile = false;
		}
		
		if (grounded)
		{
			forceX = 0;
			if (controller.isJumpKeyDown())
			{
				grounded = false;
				speedY = -200;
				if (left)
				{
					stopMovement();
					playOnceAnimation(leftJumpingAnimation);
					
					jumpingLeft = true;
				}
				else
				{
					stopMovement();
					playOnceAnimation(rightJumpingAnimation);
					
					jumpingRight = true;
				}
			}
			
			if (controller.isForwardKeyDown())
			{
				if(left)
				{
					spawnLeftProjectile = false;
				}
				
				speedX = 100;
				left = false;
				
				if (rightAttackAnimation.isStopped())
				{
					stopMovement();
					startAnimation(rightWalkingAnimation);
					
					walkingRight = true;
				}
			} else if (controller.isBackwardKeyDown())
			{
				if(!left)
				{
					spawnRightProjectile = false;
				}
				
				speedX = -100;
				left = true;
				
				if (leftAttackAnimation.isStopped())
				{
					stopMovement();
					startAnimation(leftWalkingAnimation);
					
					walkingLeft = true;
				}
			} else
			{
				speedX = 0;
				if(rightAttackAnimation.isStopped() && leftAttackAnimation.isStopped()
					&& leftJumpingAnimation.isStopped() && rightJumpingAnimation.isStopped())
				{
					stopAllAnimations();
				}
			}
		} else
		{
			if (controller.isForwardKeyDown())
			{
				
				forceX = 200;
				left = false;
				
				if (rightAttackAnimation.isStopped())
				{
					startAnimation(rightWalkingAnimation);
					stopMovement();
					jumpingRight = true;
				}	
			} else if (controller.isBackwardKeyDown())
			{
				forceX = -200;
				left = true;
				if (leftAttackAnimation.isStopped())
				{
					startAnimation(leftWalkingAnimation);
					stopMovement();
					jumpingLeft = true;
				}
			} else
			{
				forceX = -0.01f * speedX * speedX * Math.signum(speedX);
			}
		}
		
		if(controller.isAttackKeyDown())
		{
			if(left)
			{
				if(leftAttackAnimation.isStopped())
				{
					spawnLeftProjectile = true;
					stopMovement();
					playOnceAnimation(leftAttackAnimation);
					attackingLeft = true;
				}
			} else
			{
				if(rightAttackAnimation.isStopped())
				{
					spawnRightProjectile = true;
					stopMovement();
					playOnceAnimation(rightAttackAnimation);
					attackingRight = true;	
				}
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
		
		
		updateAnimations(dt);
		
		grounded = false;
		updateComponents(gc, dt);
	}
	
	@Override
	public void render(GameContainer gc, Renderer r)
	{
		if (left)
		{
			r.drawLight(light, (int) (x + 0.5f), (int) (y + 24.5f));
		} else
		{
			r.drawLight(light, (int) (x + 20.5f), (int) (y + 24.5f));
		}
		
		if(walkingLeft)
		{
			r.drawImage(leftWalkingAnimation.getSprite(), (int) (x - 8.5f), (int) (y + 0.5f));
		} else if (walkingRight)
		{
			r.drawImage(rightWalkingAnimation.getSprite(), (int) (x - 8.5f), (int) (y + 0.5f));
		} else if (jumpingLeft)
		{
			r.drawImage(leftJumpingAnimation.getSprite(), (int) (x - 8.5f), (int) (y + 0.5f));
		} else if (jumpingRight)
		{
			r.drawImage(rightJumpingAnimation.getSprite(), (int) (x - 8.5f), (int) (y + 0.5f));
		} else if (attackingLeft)
		{
			r.drawImage(leftAttackAnimation.getSprite(), (int) (x - 8.5), (int) (y + 0.5f));
		} else if (attackingRight)
		{
			r.drawImage(rightAttackAnimation.getSprite(), (int) (x - 8.5), (int) (y + 0.5f));
		}

		
		r.drawFillRect((int) (x-10.5f), (int) (y-5.5f), health * 4 / 10, 4, Pixel.RED);
		r.drawRect((int) (x-10.5f), (int) (y-5.5f), 40, 4, 0xff000000);
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
	
	private void updateAnimations(float dt)
	{
		rightWalkingAnimation.update(dt);
		leftWalkingAnimation.update(dt);
		rightJumpingAnimation.update(dt);
		leftJumpingAnimation.update(dt);
		rightAttackAnimation.update(dt);
		leftAttackAnimation.update(dt);
	}
	
	private void stopAllAnimations()
	{
		rightWalkingAnimation.stop();
		leftWalkingAnimation.stop();
		rightJumpingAnimation.stop();
		leftJumpingAnimation.stop();
		rightAttackAnimation.stop();
		leftAttackAnimation.stop();
		rightWalkingAnimation.reset();
		leftWalkingAnimation.reset();
		rightJumpingAnimation.reset();
		leftJumpingAnimation.reset();
		rightAttackAnimation.reset();
		leftAttackAnimation.reset();
	}
	
	private void stopAllOtherAnimations(Animation animation)
	{
		if (animation != rightWalkingAnimation)
		{
			rightWalkingAnimation.stop();
			rightWalkingAnimation.reset();
		} else if (animation != leftWalkingAnimation)
		{
			leftWalkingAnimation.stop();
			leftWalkingAnimation.reset();
		} else if (animation != rightJumpingAnimation)
		{
			rightJumpingAnimation.stop();
			rightJumpingAnimation.reset();
		} else if (animation != leftJumpingAnimation)
		{
			leftJumpingAnimation.stop();
			leftJumpingAnimation.reset();
		} else if (animation != rightAttackAnimation)
		{
			rightAttackAnimation.stop();
			rightAttackAnimation.reset();
		} else if (animation != leftAttackAnimation)
		{
			leftAttackAnimation.stop();
			leftAttackAnimation.reset();
		}
	}
	
	private void startAnimation(Animation animation)
	{
		stopAllOtherAnimations(animation);
		animation.start();
	}
	
	private void playOnceAnimation(Animation animation)
	{
		stopAllAnimations();
		animation.playOnce();
	}
	
	private void stopMovement()
	{
		attackingLeft = false;
		attackingRight = false;
		walkingLeft = false;
		walkingRight = false;
		jumpingLeft = false;
		jumpingRight = false;
	}
}

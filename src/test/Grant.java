package test;

import java.awt.event.KeyEvent;

import engine.GameContainer;
import engine.Renderer;
import engine.components.GameObject;
import engine.fx.Image;
import engine.fx.animations.Animation;
import engine.fx.animations.Sprite;

public class Grant extends GameObject
{
	Animation animation;
	
	public Grant(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		Sprite sprite = new Sprite();
		sprite.loadSprite("/animationTest.png");
		sprite.setTileSize(32);
		Image[] frames = {new Image(sprite.getSprite(0, 0)),
						  new Image(sprite.getSprite(1, 0)),
					      new Image(sprite.getSprite(2, 0)),};
		
		animation = new Animation(frames, 0.4);
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		if(gc.getInput().isKey(KeyEvent.VK_W))
		{
			animation.start();
		}
		
		animation.update(dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawImage(animation.getSprite(), 50, 50);
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

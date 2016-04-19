package game;

import engine.GameContainer;
import engine.Renderer;
import engine.components.ObjectManager;
import engine.components.State;
import engine.fx.Light;
import engine.fx.Pixel;
import engine.fx.animations.Sprite;

public class PlayState extends State
{
	Player player;
	Camera camera;
	Level level;
	int score;
	Light light = new Light(0xffffffff, 20);
	int enemies;
	
	public PlayState()
	{
		Sprite tileSet = new Sprite();
		tileSet.loadSprite("/tileSet.png");
		
		
		manager = new ObjectManager();
		this.level = new Level("map.txt", tileSet , manager);
		
		player = new Player(100,500, 48, 16, this);
		manager.addObject(player);
		
		camera = new Camera();
		
		Enemy enemy = new Enemy(100,100,10,10,10,10, player, level, this);
		manager.addObject(enemy);
		
		enemies = 1;
		
	}
	
	public void init(GameContainer gc)
	{
		player.init(gc);
	}

	@Override
	public void update(GameContainer gc, float dt)
	{		
		if(score - enemies == 0)
		{
			if(enemies == 0)
			{
				manager.addObject(new Enemy((int)(player.getY() + Math.random() * 50), (int)(player.getX() + Math.random() * 100), 10, 10, 10, 10, player, level, this));
				enemies += 1;
			}
			for (int i = 0; i < Math.sqrt(score);i++)
			{
				manager.addObject(new Enemy((int)(player.getY() + Math.random() * 50), (int)(player.getX() + Math.random() * 100), 10, 10, 10, 10, player, level, this));
				enemies += 1;
			}
		}
		
		camera.update(gc, dt, player);
		manager.updateObjects(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		camera.render(gc, r);
		manager.renderObjects(gc, r);
		r.drawString(String.valueOf(score), Pixel.WHITE, (int) (player.getX() + 7.5f), (int) (player.getY() -10.5f), true, true);
	}

	@Override
	public void dispose()
	{
		manager.disposeObjects();
	}

	public ObjectManager getManager()
	{
		return manager;
	}

	public void setManager(ObjectManager manager)
	{
		this.manager = manager;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public void resetEnemies()
	{
		enemies = 0;
	}

}

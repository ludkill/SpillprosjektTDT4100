package game;

import java.util.ArrayList;

import engine.GameContainer;
import engine.Renderer;
import engine.components.AStar;
import engine.components.Collider;
import engine.components.GameObject;
import engine.components.ObjectManager;
import engine.components.State;
import engine.fx.animations.Animation;
import engine.fx.animations.Sprite;

public class Enemy extends GameObject
{
	
	private Sprite sprite = new Sprite();
	private Animation animation;
	private Player target;
	private Level level;
	private ObjectManager manager;
	private PlayState state;
	
	private int health;
	private int damage;
	private float speedX;
	private float speedY;
	
	public Enemy(int x, int y, int height, int width, int health, int damage, Player target, Level level, PlayState state)
	{
		setTag("enemy");
		
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.health = health;
		this.damage = damage;
		this.target = target;
		this.level = level;
		this.manager = state.getManager();
		this.state = state;
		
		addComponent(new Collider());
	}

	@Override
	public void update(GameContainer gc, float dt)
	{		
		int[] blocks = level.getCollision();
		ArrayList<int[]> blocked = new ArrayList<>();
		for(int i = 0; i < blocks.length; i++)
		{
			if(blocks[i] == 1)
			{
				blocked.add(new int[]{i % level.getLevelW(), i / level.getLevelW()});
			}
		}
		int[][] array = new int[blocked.size()][2];

		for (int i = 0; i < blocked.size(); i++) {
		    array[i][0] = blocked.get(i)[0];
		    array[i][1] = blocked.get(i)[1];
		}
		
		int[] move;
		
		if (x > 0 && y > 0 && target.getX() > 0 && target.getY() > 0)
		{
			move = AStar.getMove(level.getLevelW(), level.getLevelH(),
					(int) ((x + width / 2) / level.getTileWidth()),
					(int) ((y + height / 2) / level.getTileHeight()), 
					(int) ((target.getX() + target.getWidth() / 2) / level.getTileWidth()), 
					(int) ((target.getY() + target.getHeight() / 2) / level.getTileHeight()), 
					array);			
		} else
		{
			move = new int[]{(int)x / level.getLevelW(),(int)y / level.getTileHeight()};
		}
		
		if(move == null)
		{
			move = new int[]{(int)x / level.getLevelW(),(int)y / level.getTileHeight()};
		}
		
		int targetX = move[0];
		int targetY = move[1];
		
		speedX = (float) (Math.signum(targetX*level.getTileWidth() - x) * 50 + 0.5f);
		speedY = (float) (Math.signum(targetY*level.getTileHeight() - y) * 50) + 0.5f;
		
		x += speedX * dt;
		y += speedY * dt;
		
		updateComponents(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.drawFillRect((int) x, (int) y, (int) width, (int) height, 0xffffffff);
	}

	@Override
	public void componentEvent(String name, GameObject object)
	{
		if (name.equalsIgnoreCase("collider"))
		{
			if (object instanceof Projectile)
			{
				manager.removeObject(this);
				state.setScore(state.getScore() + 1);
			}
			else if (object.getTag().equals("player"))
			{
				((Player)object).setHealth(((Player)object).getHealth() - 1);
			}
		}
	}

	@Override
	public void dispose()
	{
		
	}

}

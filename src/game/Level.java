package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import engine.GameContainer;
import engine.Renderer;
import engine.components.ObjectManager;
import engine.fx.Image;
import engine.fx.ShadowType;
import engine.fx.animations.Sprite;

public class Level
{
	private int levelW, levelH;
	private int[] tiles;
	private int[] collision;
	private Sprite tileSet;
	private ObjectManager manager;
	private int tileWidth = 32;
	private int tileHeight = 32;

	public Level(String path, Sprite tileSet, ObjectManager manager)
	{
		this.tileSet = tileSet;
		this.manager = manager;
		
		loadMap(path);
		createMap();
	}
	
	public void update(GameContainer gc, float dt)
	{
		
	}
	
	public void render(GameContainer gc, Renderer r)
	{
		
	}
	
	private void loadMap(String path)
	{
		if(path == null)
		{
			return;
		}
		
		try
		{
			ClassLoader classLoader = getClass().getClassLoader();
			InputStreamReader is = new InputStreamReader(classLoader.getResourceAsStream(path));
			BufferedReader in = new BufferedReader(is);
			
			levelW = in.read();
			levelH = in.read();
			
			tiles = new int[levelW * levelH];
			collision = new int[levelW * levelH];
			
			for(int i = 0; i < tiles.length; i++)
			{
				tiles[i] = in.read();
				collision[i] = in.read();
			}
			
			in.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void createMap()
	{
		for(int i = 0; i < tiles.length; i++)
		{
			if(tiles[i] > 0)
			{
				int x = i % levelW;
				int y = i / levelW;
				Image sprite = tileSet.getSpriteAsImage(tiles[i]);
				sprite.shadowType = ShadowType.FADE;
				int collisionValue = collision[i];
				manager.addObject(new Tile(x * tileWidth , y * tileHeight, sprite, collisionValue));
			}
		}
	}

	public int[] getCollision()
	{
		return collision;
	}

	public int getTileWidth()
	{
		return tileWidth;
	}

	public void setTileWidth(int tileWidth)
	{
		this.tileWidth = tileWidth;
	}

	public int getLevelW()
	{
		return levelW;
	}

	public int getLevelH()
	{
		return levelH;
	}

	public int[] getTiles()
	{
		return tiles;
	}

	public int getTileHeight()
	{
		return tileHeight;
	}
}

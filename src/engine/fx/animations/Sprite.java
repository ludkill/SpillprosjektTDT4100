package engine.fx.animations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.fx.Image;
import engine.fx.ShadowType;

public class Sprite
{
	
	private BufferedImage spriteSheet;
	private int tileSize = 32;
	private ShadowType shadowType = ShadowType.NONE;
	
	public BufferedImage loadSprite(String path)
	{	
		try
		{
			spriteSheet = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return spriteSheet;
	}
	
	public BufferedImage getSprite(int xGrid, int yGrid)
	{
		
		if (spriteSheet == null)
		{
			return null;
		}
		
		return spriteSheet.getSubimage(xGrid * tileSize, yGrid * tileSize, tileSize, tileSize);
	}
	
	public Image getSpriteAsImage(int xGrid, int yGrid)
	{
		return(new Image(getSprite(xGrid, yGrid), shadowType));
	}
	
	public BufferedImage getSpriteSheet()
	{
		return spriteSheet;
	}

	public void setSpriteSheet(BufferedImage spriteSheet)
	{
		this.spriteSheet = spriteSheet;
	}

	public int getTileSize()
	{
		return tileSize;
	}

	public void setTileSize(int tileSize)
	{
		this.tileSize = tileSize;
	}
	
	public Image getSpriteAsImage(int id)
	{
		if(id < 0 || id > (spriteSheet.getWidth()/tileSize) * 
				(spriteSheet.getHeight()/tileSize))
		{
			return null;
		}
		return(new Image(getSprite(id % (spriteSheet.getWidth()/tileSize),
				id / (spriteSheet.getWidth()/tileSize)), shadowType));
	}

	public ShadowType getShadowType()
	{
		return shadowType;
	}

	public void setShadowType(ShadowType shadowType)
	{
		this.shadowType = shadowType;
	}
	
}
package engine.fx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	public int width, height;
	public ShadowType shadowType = ShadowType.NONE;
	public int[] pixels;
	
	public Image(String path, ShadowType shadowType)
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		this.shadowType = shadowType;
	
		image.flush();
	}
	
	public Image(String path)
	{
		this(path, ShadowType.NONE);
	}
	
	public Image(int width, int height, int[] p)
	{
		this.width = width;
		this.height = height;
		this.pixels = p;
	}
	
	public Image(BufferedImage image, ShadowType shadowType)
	{
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		this.shadowType = shadowType;
		
		image.flush();
	}
	
	public Image(BufferedImage image)
	{
		this(image, ShadowType.NONE);
	}
}

package engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import engine.fx.Font;
import engine.fx.Image;
import engine.fx.ImageTile;
import engine.fx.Light;
import engine.fx.LightRequest;
import engine.fx.Pixel;
import engine.fx.ShadowType;

public class Renderer
{
	private GameContainer gc;
	
	private int width, height;
	private int[] pixels;
	private int[] lightmap;
	private ShadowType[] shadowMap;
	private Font font = Font.STANDARD;
	private int ambientLight = Pixel.getColor(1, 0.1f, 0.2f, 0.3f);
	private int clearColor = 0xff000000;
	
	private int transX, transY;
	
	private boolean translate = true;
	
	private ArrayList<LightRequest> lightRequests = new ArrayList<LightRequest>();
	
	public Renderer(GameContainer gc)
	{
		this.gc = gc;
		
		width = gc.getWidth();
		height = gc.getHeight();
		pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		lightmap = new int[pixels.length];
		shadowMap = new ShadowType[pixels.length];
	}
	
	public void setPixel(int x, int y, int color, ShadowType shadowType)
	{
		if (translate)
		{
			x -= transX;
			y -= transY;
		}
		
		if (x < 0 || x >= width || y < 0 || y >= height || color == 0xffff00ff || color << 32 == 0)
		{
			return;
		}
		pixels[x + y * width] = color;
		shadowMap[x + y * width] = shadowType;
	}
	
	public ShadowType getLightBlock(int x, int y)
	{

		x -= 2 * transX;
		y -= 2 * transY;
		
		if (x < 0 || x >= width || y < 0 || y >= height)
		{
			return ShadowType.TOTAL;
		}
		return shadowMap[x + y * width];
	}
	
	public void setLightMap(int x, int y, int color)
	{
		x -= transX;
		y -= transY;
		
		if (x < 0 || x >= width || y < 0 || y >= height)
		{
			return;
		}
		lightmap[x + y * width] = Pixel.getMax(color, lightmap[x + y * width]);
	}
	
	public void drawString(String text, int color, int offX, int offY, boolean centerX, boolean centerY)
	{
		// in case you don't have lower case letters in font
		text = text.toUpperCase();
		int offset = 0;
		
		if(centerX)
		{
			offset = - font.getStringSize(font, text) / 2;
		}
				
		for (int i = 0; i < text.length(); i++)
		{
			int unicode = text.codePointAt(i) - 32;
			
			for (int x = 0; x < font.widths[unicode]; x++)
			{
				for (int y = 1; y < font.image.height; y++)
				{
					if (font.image.pixels[x + font.offsets[unicode] + y * font.image.width] == 0xffffffff)
					{
						int drawX = x + offX + offset;
						int drawY =  y + offY - 1;
						
						if(centerY)
						{
							drawY -= font.image.height / 2;
						}
						
						setPixel(x + offX + offset, drawY, color, ShadowType.NONE);
					}
				}
			}
			offset += font.widths[unicode];
		}
	}
	
	public void clear()
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				pixels[x + y * width] = clearColor;
			}
		}
	}
	
	public void flushMaps()
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				setPixel(x, y, Pixel.getLightBlend(pixels[x + y * width], lightmap[x + y * width], ambientLight),
						shadowMap[x + y * width]);
				lightmap[x + y * width] = ambientLight;
			}
		}
	}
	
	public void drawLightArray()
	{
		for (LightRequest lr : lightRequests)
		{
			drawLightRequest(lr.light, lr.x, lr.y);
		}
		lightRequests.clear();
	}
	
	public void drawImage(Image image, int offX, int offY)
	{
		for (int x = 0; x < image.width; x++)
		{
			for (int y = 0; y < image.height; y++)
			{
				setPixel(x + offX, y + offY, image.pixels[x + y * image.width], image.shadowType);
			}
		}
	}
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
	{
		for (int x = 0; x < image.tileWidth; x++)
		{
			for (int y = 0; y < image.tileHeight; y++)
			{
				setPixel(x + offX, y + offY,
						image.pixels[x + (tileX * image.tileWidth) + (y + (tileY * image.tileHeight)) * image.width],
						image.shadowType);
			}
		}
	}
	
	public void drawRect(int offX, int offY, int width, int height, int color, ShadowType shadowType)
	{
		for (int x = 0; x < width; x++)
		{
			
			setPixel(x + offX, offY, color, shadowType);
			setPixel(x + offX, offY + height - 1, color, shadowType);
		}
		
		for (int y = 0; y < height; y++)
		{
			
			setPixel(offX, y + offY, color, shadowType);
			setPixel(offX + width - 1, y + offY, color, shadowType);
		}
	}
	
	public void drawFillRect(int offX, int offY, int width, int height, int color, ShadowType shadowType)
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				setPixel(x + offX, y + offY, color, shadowType);
			}
		}
	}
	
	public void drawLight(Light light, int offX, int offY)
	{
		if (gc.isDynamicLights() || gc.isLightingEnabled())
			lightRequests.add(new LightRequest(light, offX, offY));
	}
	
	private void drawLightRequest(Light light, int offX, int offY)
	{
		if (gc.isDynamicLights())
		{
			for (int i = 0; i <= light.diameter; i++)
			{
				drawLightLine(light.radius, light.radius, i, 0, light, offX, offY);
				drawLightLine(light.radius, light.radius, i, light.diameter, light, offX, offY);
				drawLightLine(light.radius, light.radius, 0, i, light, offX, offY);
				drawLightLine(light.radius, light.radius, light.diameter, i, light, offX, offY);
			}
		} else
		{
			for (int x = 0; x < light.diameter; x++)
			{
				for (int y = 0; y < light.diameter; y++)
				{
					setLightMap(x + offX - light.radius, y + offY - light.radius, light.getLightValue(x, y));
				}
			}
		}
	}
	
	private void drawLightLine(int x0, int y0, int x1, int y1, Light light, int offX, int offY)
	{
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx - dy;
		int e2;
		
		float power = 1.0f;
		boolean hit = false;
		
		while (true)
		{
			if (light.getLightValue(x0, y0) == 0xff000000)
				break;
				
			int screenX = x0 - light.radius + offX;
			int screenY = y0 - light.radius + offY;
			
			if (power == 1)
			{
				setLightMap(screenX, screenY, light.getLightValue(x0, y0));
			} else
			{
				setLightMap(screenX, screenY, Pixel.getColorPower(light.getLightValue(x0, y0), power));
			}
			
			if (x0 == x1 && y0 == y1)
				break;
			if (getLightBlock(screenX, screenY) == ShadowType.TOTAL)
				break;
			if (getLightBlock(screenX, screenY) == ShadowType.FADE)
				power -= 0.01f;
			if (getLightBlock(screenX, screenY) == ShadowType.HALF && hit == false)
			{
				hit = true;
				power /= 2;
			}
			if (getLightBlock(screenX, screenY) == ShadowType.HALF && hit == true)
				hit = false;
			if (power <= 0.1)
				break;
				
			e2 = 2 * err;
			
			if (e2 > -1 * dy)
			{
				err -= dy;
				x0 += sx;
			}
			
			if (e2 < dx)
			{
				err += dx;
				y0 += sy;
			}
		}
	}
	
	public Font getFont()
	{
		return font;
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public int getTransX()
	{
		return transX;
	}
	
	public void setTransX(int transX)
	{
		this.transX = transX;
	}
	
	public int getTransY()
	{
		return transY;
	}
	
	public void setTransY(int transY)
	{
		this.transY = transY;
	}
	
	public int getAmbientLight()
	{
		return ambientLight;
	}
	
	public void setAmbientLight(int ambientLight)
	{
		this.ambientLight = ambientLight;
	}
	
	public void drawImage(Image image)
	{
		drawImage(image, 0, 0);
	}
	
	public void drawImageTile(ImageTile imageTile, int tileX, int tileY)
	{
		drawImageTile(imageTile, 0, 0, tileX, tileY);
	}
	
	public void drawLight(Light light)
	{
		drawLight(light, 0, 0);
	}
	
	public void drawFillRect(int offX, int offY, int width, int height, int color)
	{
		drawFillRect(offX, offY, width, height, color, ShadowType.NONE);
	}
	
	public void drawRect(int offX, int offY, int width, int height, int color)
	{
		drawRect(offX, offY, width, height, color, ShadowType.NONE);
	}

	public boolean isTranslate()
	{
		return translate;
	}

	public void setTranslate(boolean translate)
	{
		this.translate = translate;
	}
	public void drawString(String text, int color, int offX, int offY)
	{
		drawString(text, color, offX, offY, false, false);
	}
}
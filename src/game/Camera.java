package game;

import engine.GameContainer;
import engine.Renderer;

public class Camera
{
	private float camX, camY;
	private float deltaX, deltaY;
	private float lerp = 2.0f;
	
	public Camera()
	{
		camX = 0;
		camY = 0;
	}
	
	public void update(GameContainer gc, float dt, Player player)
	{
		camY += ((player.getY() + player.getHeight() / 2) - (camY + gc.getHeight() / 2)) * lerp * dt;
		camX += ((player.getX() + player.getWidth() / 2) - (camX + gc.getWidth() / 2)) * lerp * dt;
	}
	
	public void render(GameContainer gc, Renderer r)
	{
		r.setTransX((int) (camX + 0.5f));
		r.setTransY((int) (camY + 0.5f));
	}
	
	public float getCamX()
	{
		return camX;
	}
	
	public void setCamX(float camX)
	{
		this.camX = camX;
	}
	
	public float getCamY()
	{
		return camY;
	}
	
	public void setCamY(float camY)
	{
		this.camY = camY;
	}
}

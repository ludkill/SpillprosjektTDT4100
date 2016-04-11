package engine.fx.animations;

import java.util.ArrayList;
import java.util.List;

import engine.fx.Image;

public class Animation
{
	
	private double deltaTime;
	private double frameDelay;
	private int currentFrame;
	private int animationDirection;
	private int totalFrames;
	
	private boolean stopped;
	private boolean loop;
	
	private List<Frame> frames = new ArrayList<Frame>();
	
	public Animation(Image[] frames, double frameDelay)
	{
		this.frameDelay = frameDelay;
		this.stopped = true;
		
		for (int i = 0; i < frames.length; i++)
		{
			addFrame(frames[i], frameDelay);
		}
		
		this.deltaTime = 0;
		this.frameDelay = frameDelay;
		this.currentFrame = 0;
		this.animationDirection = 1;
		this.totalFrames = this.frames.size();
		
	}
	
	public void start()
	{
		if (!stopped)
		{
			return;
		}
		
		if (frames.size() == 0)
		{
			return;
		}
		
		stopped = false;
		loop = true;
	}
	
	public void playOnce()
	{
		if (!stopped)
		{
			return;
		}
		
		if (frames.size() == 0)
		{
			return;
		}
		
		stopped = false;
		loop = false;
	}
	
	public void stop()
	{
		if (frames.size() == 0)
		{
			return;
		}
		
		stopped = true;
	}
	
	public void restart()
	{
		if (frames.size() == 0)
		{
			return;
		}
		
		stopped = false;
		currentFrame = 0;
	}
	
	public void reset()
	{
		this.stopped = true;
		this.deltaTime = 0;
		this.currentFrame = 0;
	}
	
	private void addFrame(Image frame, double duration)
	{
		if (duration <= 0)
		{
			System.err.println("Invalid duration: " + duration);
			throw new RuntimeException("Invalid duration: " + duration);
		}
		
		frames.add(new Frame(frame, duration));
		currentFrame = 0;
	}
	
	public Image getSprite()
	{
		return frames.get(currentFrame).getFrame();
	}
	
	public void update(double dt)
	{	
		if (stopped)
			return;
			
		deltaTime += dt;
		
		if (deltaTime > frameDelay)
		{
			deltaTime = 0;
			currentFrame += animationDirection;
			
			if (currentFrame > totalFrames - 1)
			{
				currentFrame = 0;
			} else if (currentFrame < 0)
			{
				currentFrame = totalFrames - 1;
			}
			
			if (currentFrame == 0 && loop == false)
				stopped = true;
		}
		
	}
	
}
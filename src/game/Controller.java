package game;

import java.awt.event.KeyEvent;

import engine.GameContainer;
import engine.Input;

public class Controller
{
	private Input input;
	
	private int forwardKey = KeyEvent.VK_RIGHT;
	private int backwardKey = KeyEvent.VK_LEFT;
	private int jumpKey = KeyEvent.VK_UP;
	private int attackKey = KeyEvent.VK_A;
	
	private boolean forwardKeyPressed = false;
	private boolean backwardKeyPressed = false;
	private boolean jumpKeyPressed = false;
	private boolean attackKeyPressed = false;
	
	public Controller()
	{
		
	}
	
	public void init(GameContainer gc)
	{
		input = gc.getInput();
	}
	
	public void update(GameContainer gc, float dt)
	{
		forwardKeyPressed = (boolean) input.isKeyPressed(forwardKey);
		backwardKeyPressed = (boolean) input.isKeyPressed(backwardKey);
		jumpKeyPressed = (boolean) input.isKeyPressed(jumpKey);
		attackKeyPressed = (boolean) input.isKeyPressed(attackKey);

	}

	public int getForwardKey()
	{
		return forwardKey;
	}

	public void setForwardKey(int forwardKey)
	{
		this.forwardKey = forwardKey;
	}

	public int getBackwardKey()
	{
		return backwardKey;
	}

	public void setBackwardKey(int backwardKey)
	{
		this.backwardKey = backwardKey;
	}

	public int getJumpKey()
	{
		return jumpKey;
	}

	public void setJumpKey(int jumpKey)
	{
		this.jumpKey = jumpKey;
	}

	public int getAttackKey()
	{
		return attackKey;
	}

	public void setAttackKey(int attackKey)
	{
		this.attackKey = attackKey;
	}

	public boolean isForwardKeyPressed()
	{
		return forwardKeyPressed;
	}

	public void setForwardKeyPressed(boolean forwardKeyPressed)
	{
		this.forwardKeyPressed = forwardKeyPressed;
	}

	public boolean isBackwardKeyPressed()
	{
		return backwardKeyPressed;
	}

	public void setBackwardKeyPressed(boolean backwardKeyPressed)
	{
		this.backwardKeyPressed = backwardKeyPressed;
	}

	public boolean isJumpKeyPressed()
	{
		return jumpKeyPressed;
	}

	public void setJumpKeyPressed(boolean jumpKeyPressed)
	{
		this.jumpKeyPressed = jumpKeyPressed;
	}

	public boolean isAttackKeyPressed()
	{
		return attackKeyPressed;
	}

	public void setAttackKeyPressed(boolean attackKeyPressed)
	{
		this.attackKeyPressed = attackKeyPressed;
	}
}

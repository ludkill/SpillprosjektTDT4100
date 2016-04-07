package engine.components;

import engine.GameContainer;
import engine.Renderer;

public class Collider extends Component
{
	private GameObject object;
	private float x, y, halfWidth, halfHeight;
	
	public Collider()
	{
		setTag("collider");
	}

	@Override
	public void update(GameContainer gc, GameObject object, float dt)
	{
		if(this.object == null)
		{
			this.object = object;
		}
		halfWidth = object.getWidth() / 2;
		halfHeight = object.getHeight() / 2;
		x = object.getX() + halfWidth;
		y = object.getY() + halfHeight;
		gc.getPhysics().addCollider(this);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		
	}
	
	public void collision(GameObject object)
	{
		this.object.componentEvent(tag, object);
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getHalfWidth()
	{
		return halfWidth;
	}

	public void setHalfWidth(float halfWidth)
	{
		this.halfWidth = halfWidth;
	}

	public float getHalfHeight()
	{
		return halfHeight;
	}

	public void setHalfHeight(float halfHeight)
	{
		this.halfHeight = halfHeight;
	}

	public GameObject getObject()
	{
		return object;
	}

	public void setObject(GameObject object)
	{
		this.object = object;
	}

}

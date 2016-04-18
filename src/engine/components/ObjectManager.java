package engine.components;

import java.util.ArrayList;

import engine.GameContainer;
import engine.Renderer;

public class ObjectManager
{
	ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	public void updateObjects(GameContainer gc, float dt)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update(gc, dt);
			
			if(objects.get(i).isDead())
			{
				objects.remove(i);
			}
		}
	}
	public void renderObjects(GameContainer gc, Renderer r)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).render(gc, r);
		}
	}
	
	public void disposeObjects()
	{
		for(GameObject go : objects)
		{
			go.dispose();
		}
	}
	
	public void addObject(GameObject object)
	{
		objects.add(object);
	}
	
	public GameObject findObject(String tag)
	{
		for(GameObject go : objects)
		{
			if(go.getTag().equalsIgnoreCase(tag))
			{
				return go;
			}
		}
		return null;
	}
	
	public void removeObject(GameObject ob)
	{
		while(objects.remove(ob));
	}
}

package rogue.entity;

import org.newdawn.slick.Renderable;

public interface Entity extends Renderable {	
	public abstract void onCreate();
	public abstract void onCollide(Entity entity);
	public abstract void onDestroy();
}

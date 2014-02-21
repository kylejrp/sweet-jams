package rogue.entity;


public abstract class Entity {
	private int health;
	private char textRepresentaion;
	
	public Entity(int health){
		this.health = health;
	}
	
	public String toString(){
		return Character.toString(textRepresentaion);
	}
		
	public abstract void onCreate();
	public abstract void onCollide(Entity entity);
	public abstract void onDestroy();
	
}

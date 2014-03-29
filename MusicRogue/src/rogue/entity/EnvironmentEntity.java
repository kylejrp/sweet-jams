package rogue.entity;

public class EnvironmentEntity extends Entity {


	private char charRep ;
	
	public enum environmentType
	{
		WALL, HALLWAY, FLOOR
	}
	
	
	public EnvironmentEntity(environmentType t)
	{
		switch(t)
		{
		case WALL: charRep = '#' ;
		break ;
		case HALLWAY: charRep = '%' ;
		break ;
		case FLOOR: charRep = '.' ;
		default: 
			break;
		}
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollide(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(float x, float y) {
		// TODO Auto-generated method stub
		
	}
	
	
	public char getCharRep()
	{
		return charRep ;
	}

}

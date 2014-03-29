package rogue.map;

import java.util.Random;

import rogue.entity.EnvironmentEntity;

public class MapGenerator {

	private static int getRandomDimension(int min, int max) {
		Random randomInt = new Random();
		int value = Math.abs(randomInt.nextInt(max - 1));
		while (value < min) {
			value = Math.abs(randomInt.nextInt(max - 1));
		}
		return value;
	}

	private static void buildRoom(int wmin, int wmax, int lmin, int lmax,
			EnvironmentEntity[][] map, int startX, int startY) {
		// Width is y axis, length is x axis.
		int width = getRandomDimension(wmin, wmax);
		int length = getRandomDimension(lmin, lmax);
		for (int i = startY; i < startY + width && i < map.length; i++) {
			for (int j = startX; j < startX + length && j < map.length; j++) {
				EnvironmentEntity ground = new EnvironmentEntity(
						EnvironmentEntity.environmentType.FLOOR);
				map[i][j] = ground;
			}
		}
	}

	private static void buildMultipleRooms(EnvironmentEntity[][] map) {
		Random randomInt = new Random();
		int numberOfRooms = 350;

		for (int i = 0; i < numberOfRooms; i++) {
			int startX = randomInt.nextInt(63);
			int startY = randomInt.nextInt(63);
			buildRoom(3, 7, 3, 7, map, startX, startY);

		}

	}

	private static void buildCorridors(EnvironmentEntity[][] map)
	{
		for (int i = 0; i < map.length; i+=4) {
			for(int j=0; j<map.length; j++)
			{
				EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.environmentType.FLOOR);
				map[i][j] = ground;
			}
		}
	}
	
	private static void buildWalls(EnvironmentEntity[][] map) {
		int x = map.length;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++) {
				if (map[i][j] == null) {
					EnvironmentEntity ground = new EnvironmentEntity(
							EnvironmentEntity.environmentType.WALL);
					map[i][j] = ground;
				}
			}
		}
		for (int i = 0; i < x; i++) {
			EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.environmentType.WALL);
			map[i][0] = ground;
		}
		for (int i = 0; i < x; i++) {
			EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.environmentType.WALL);
			map[i][x - 1] = ground;
		}
		for (int i = 0; i < x; i++) {
			EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.environmentType.WALL);
			map[0][i] = ground;
		}
		for (int i = 0; i < x; i++) {
			EnvironmentEntity ground = new EnvironmentEntity(
					EnvironmentEntity.environmentType.WALL);
			map[x - 1][i] = ground;
		}
	}

	public static EnvironmentEntity[][] generateBottomLayer(int x) {
		EnvironmentEntity[][] mapBottomLayer = new EnvironmentEntity[x][x];
		buildMultipleRooms(mapBottomLayer);
		buildCorridors(mapBottomLayer);
		buildWalls(mapBottomLayer);

		return mapBottomLayer;
	}

	public static EnvironmentEntity[][] generateTopLayer(int x) {
		EnvironmentEntity[][] mapTopLayer = new EnvironmentEntity[x][x];
		return mapTopLayer;
	}

	public static void main(String[] args) {
		// This is just to test that I didn't fucking erupt everything.
		EnvironmentEntity[][] mapTest;
		mapTest = generateBottomLayer(64);
		for (int i = 0; i < mapTest.length; i++) {
			for (int j = 0; j < mapTest.length; j++) {
				if (mapTest[i][j] != null) {
					System.out.print(mapTest[i][j].getCharRep());
				} else {
					System.out.print("0");
				}
			}
			System.out.println();
		}

	}

}

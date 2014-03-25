package rogue.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;

public class MapGenerator 
{

	private static int getRandomDimension(int min, int max)
	{
		Random randomInt = new Random() ;
		int value = Math.abs(randomInt.nextInt(max-1)) ;
		while(value < min)
		{
			value = Math.abs(randomInt.nextInt(max-1)) ;
		}
		return value ;
	}
	
	
	private static void buildRoom(int wmin, int wmax, int lmin, int lmax, EnvironmentEntity[][] map)
	{
		//Width is y axis, length is x axis.
		int width = getRandomDimension(wmin, wmax) ;
		int length = getRandomDimension(lmin, lmax) ;
		
		
		
		
		// Build the random placement/non-collision part.
		
		
		
		
		int startX = 0 ;
		int startY = 0 ;
		
		
		for (int i = startY; i<width; i++)
		{
			for(int j=startY; j<length; j++)
			{
				if(i == startY || i == width - 1)
				{
					EnvironmentEntity ground = new EnvironmentEntity(EnvironmentEntity.environmentType.WALL) ;
					map[i][j] = ground ;
				}
				else if(i != startX || i != startX + width - 1)
				{
					if(j == startX || j == startX + length -1)
					{
						EnvironmentEntity ground = new EnvironmentEntity(EnvironmentEntity.environmentType.WALL) ;
						map[i][j] = ground ;
					}
					else if(j != startX || j != startX + length -1)
					{
						EnvironmentEntity ground = new EnvironmentEntity(EnvironmentEntity.environmentType.FLOOR) ;
						map[i][j] = ground ;
					}
				}
				
			}
		}
	}
	

	public static EnvironmentEntity[][] generateBottomLayer(int x, int y)
	{
		EnvironmentEntity[][] mapBottomLayer = new EnvironmentEntity[x][y];
		buildRoom(10, 50, 10, 50, mapBottomLayer) ;
		
		return mapBottomLayer ;
	}
	
	public static EnvironmentEntity[][] generateTopLayer(int x, int y)
	{
		EnvironmentEntity[][] mapTopLayer = new EnvironmentEntity[x][y];
		return mapTopLayer ;
	}
	

	
	public static void main(String[] args)
	{
		//This is just to test that I didn't fucking erupt everything.
		EnvironmentEntity[][] mapTest ;
		mapTest = generateBottomLayer(80, 60) ;
		for(int i = 0; i < mapTest.length ; i++)
		{
			for (int j = 0; j < mapTest[0].length; j++)
			{
				if(mapTest[i][j] != null)
				{
					System.out.print(mapTest[i][j].getCharRep());
				}
				else
				{
					System.out.print("0");
				}
			}
			System.out.println();
		}
			
		
	}
	
	
	
	
	
	
	
	
	
	
}

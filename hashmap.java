package lect20.MyHashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class hashmap {

	public static void main(String[] args) {
		
		
		Random  rand=new Random();
		int[] arr={1,7,4,0,-1,-7,4,8};
		/*for(int i=0;i<arr.length;i++)
		{
			arr[i]=rand.
		}*/
		
		System.out.println(sumUptoZero(arr));
		
	}
	
	public static ArrayList<Integer> sumUptoZero(int[] arr)
	{
		HashMap<Integer,Integer> visited=new HashMap<Integer,Integer>();
		ArrayList<Integer> retval=new ArrayList<Integer>();
		for(int i=0;i<arr.length;i++)
		{
			if(!visited.containsKey(arr[i]))
			{
				visited.put(arr[i], 1);
			}
			else
			{
				visited.put(arr[i], visited.remove(arr[i])+1);
			}
			
			if(visited.containsKey(-arr[i]))
			{
				retval.add(arr[i]);
				retval.add(-arr[i]);
			}
		}
		
		return retval;
	}

}

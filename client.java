package lect20.MyHashtable;

public class client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Hashtable<String,Integer> table=new Hashtable<String,Integer>(10);
		String[] str={"this","is","my","world","that","is","your","world","ting","world"};
		for(int i=0;i<str.length;i++)
		{
			try{Integer v=table.get(str[i]);
			if(v==null)
			{
				table.add(str[i], 1);
			}
			else
			{
				table.add(str[i], v+1);
			}}catch(Exception e)
			{
				//swallow it.
			}
			table.display();
		}
		/*try{System.out.println(table.remove("is"));
		System.out.println(table.remove("this"));
		System.out.println(table.remove("is"));
		System.out.println(table.remove("this"));
		System.out.println(table.remove("is"));
		}catch(Exception e)
		{
			//swallow it
		}*/
		System.out.println("\n\n\n**************\n\n");
		//table.removingDuplicates2();
		System.out.println(table.MaximumFrequencyKey());
		//System.out.println(table.uniqueCharacters());
		//table.display();
	}

}

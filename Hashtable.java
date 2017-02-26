package lect20.MyHashtable;

import java.util.ArrayList;

import lect11.my_linked_list.LinkedList;

//Hashtable is another data structure whose complexity of adding removing is much smaller than others
public class Hashtable<K extends Comparable<K>,V> implements Hashtable_Interface<K,V> {

	private class ht_node implements Comparable<ht_node>{
		K key;	//key stores the actual data
		V value;	//value stores the particular times the data has occurred.
		
		public ht_node(K key,V value)
		{
			this.key=key;
			this.value=value;
		}
		
		public String toString()
		{
			String retval="";
			retval+="{"+this.key+","+this.value+"}";
			return retval;
		}

		public int compareTo(ht_node other) {
			if(this.equals(other))
			{
				return 0;
			}
			else
			{
				return 1;
			}
		}
		
		public boolean equals(Object other)
		{
			ht_node otherNode=(ht_node)other;
			return this.key.equals(otherNode.key);
		}
	
	}
	
	private ArrayList<LinkedList<ht_node>> BucketArray;
	private int size;
	public static final int DEFAULT_CAPACITY=10;
	
	public Hashtable()
	{
		this(DEFAULT_CAPACITY);
	}
	
	public Hashtable(int capacity)
	{
		this.size=0;
		this.BucketArray=new ArrayList<LinkedList<ht_node>>();
		for(int i=0;i<capacity;i++)
		{
			this.BucketArray.add(null);
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size()==0;
	}

	//adding an element in a hashtable requires finding and getting the element first
	//if we are given bucketIndex,and the average length of any linkedlist in a
	//hashtable is n/N,therefore the complexity for add function is n/N,which is
	//always kept<=0.75.
	
	public void add(K key, V value) {
		
		int BucketIndex=this.hashfunction(key);
		//1.find the suitable bucket index for the new key
		
		LinkedList<ht_node> list=this.BucketArray.get(BucketIndex);
		//2.get the already existing linkedlist in the bucket index,which can be
		//null or some element belonging.
		
		ht_node node=new ht_node(key,value);
		
		if(list==null)
		{
			list=new LinkedList<ht_node>();
			list.add_at_last(node);
			this.BucketArray.set(BucketIndex, list);
			this.size++;
		}
		
		else
		{
			int FindIndex=list.find_element(node);
			if(FindIndex==-1)
			{
				list.add_at_last(node);
				this.size++;
				this.BucketArray.set(BucketIndex,list);
			}
			else
			{
				ht_node new_node=list.get_at(FindIndex);
				new_node.value=value;
			}
		}
		
		//rehash
		double n=(1.0*this.size())/this.BucketArray.size();
		if(n>0.75)
		{
			this.rehash();
		}
	}

	private void rehash()
	{
		ArrayList<LinkedList<ht_node>> temp=this.BucketArray;
		this.BucketArray=new ArrayList<LinkedList<ht_node>>();
		
		for(int i=0;i<2*temp.size();i++)
		{
			this.BucketArray.add(null);
		}
		
		for(int i=0;i<temp.size();i++)
		{
			LinkedList<ht_node> list=temp.get(i);
			try{while(list!=null && !list.is_empty())
			{
				ht_node node=list.remove_first();
				this.add(node.key, node.value);
			}}catch(Exception e)
			{
				//never comes here
			}
		}
		temp=null;
	}
	
	private int hashfunction(K key)
	{
		return Math.abs(key.hashCode()%this.BucketArray.size());
	}
	
	//infact, removing and getting function also requires scanning through a given
	//linkedlist,and the length is n/N,therefore complexity is n/N
	public V remove(K key) throws HashtableEmptyException {
		
		if(this.isEmpty())
		{
			throw new HashtableEmptyException();
		}
		int BucketIndex=this.hashfunction(key);
		
		LinkedList<ht_node> list=this.BucketArray.get(BucketIndex);
		
		if(list==null)
		{
			System.out.println("this item("+key+") is not in the hashtable");
			return null;
		}
		else
		{
			int FindIndex=list.find_element(new ht_node(key,null));
			if(FindIndex==-1)
			{
				System.out.println("this item("+key+") is not in the hashtable");
				return null;
			}
			else
			{
				try{
					ht_node node=list.get_at(FindIndex);
					Integer NodeValue=(Integer)node.value;
					if(NodeValue>1)
					{
						NodeValue=NodeValue-1;
						node.value=(V)NodeValue;
						return node.value;
					}
					else 
					{
						ht_node Node=list.remove_at(FindIndex);
						return Node.value;
					}
				//return node.value;
				
				}catch(Exception e)
				{
					throw new HashtableEmptyException();
				}
				
			}
		}
	}

	public V get(K key) throws HashtableEmptyException {
		if(this.isEmpty())
		{
			return null;
		}
		int BucketIndex=this.hashfunction(key);
		
		LinkedList<ht_node> list=this.BucketArray.get(BucketIndex);
		
		if(list==null)
		{
			//System.out.println("this item is not in the hashtable");
			return null;
		}
		else
		{
			int FindIndex=list.find_element(new ht_node(key,null));
			if(FindIndex==-1)
			{
				//System.out.println("this item is not in the hashtable");
				return null;
			}
			else
			{
				try{ht_node node=list.get_at(FindIndex);
				return node.value;
				}catch(Exception e)
				{
					throw new HashtableEmptyException();
				}
				
			}
		}
	}

	public void display() {
		System.out.println(this.BucketArray);
	}

	public void removingDuplicates()//bad complexity
	{
		for(int i=0;i<this.BucketArray.size();i++){
			
			LinkedList<ht_node> list=this.BucketArray.get(i);
			int j=0;
			while(list!=null && j!=list.size())
			{
				ht_node node=list.get_at(j);
				Integer NodeValue=(Integer)node.value;
				if(NodeValue>1)
				{
					NodeValue=1;
					node.value=(V)NodeValue;
				}
				j++;
			}
		}
	}
	
	public void removingDuplicates2()//bad complexity
	{
		ArrayList<LinkedList<ht_node>> temp=this.BucketArray;
		this.BucketArray=new ArrayList<LinkedList<ht_node>>();
		
		for(int i=0;i<temp.size();i++)
		{
			this.BucketArray.add(null);
		}
		
		for(int i=0;i<temp.size();i++)
		{
			LinkedList<ht_node> list=temp.get(i);
			while(list!=null && !list.is_empty())
			{
				try{ht_node node=list.remove_first();
				Integer value=1;
				this.add(node.key, (V)value);
				}catch(Exception e)
				{
					//never comes here
				}
			}
		}
	}

	public ArrayList<K> uniqueCharacters()//bad complexity ,somewhere around n square
	{
		ArrayList<K> retval=new ArrayList<K>();
		 
		for(int i=0;i<this.BucketArray.size();i++)
		{
			LinkedList<ht_node> list=this.BucketArray.get(i);
			int j=0;
			while(list!=null && j<list.size())
			{
				ht_node node=list.get_at(j);
				Integer NodeValue=(Integer)node.value;
				if(NodeValue==1)
				{
					retval.add(node.key);
				}
				j++;
			}
		}
		return retval;
	}
	
	public K MaximumFrequencyKey()//bad complexity 
	{
		Integer max=1;
		K retval=null;
		for(int i=0;i<this.BucketArray.size();i++)
		{
			LinkedList<ht_node> list=this.BucketArray.get(i);
			int j=0;
			while(list!=null && j<list.size())
			{
				ht_node node=list.get_at(j);
				Integer n=(Integer)node.value;
				if(n>max)
				{
					max=n;
					retval=node.key;
				}
				j++;
			}
		}
		
		return retval;
	}
	
	//all three above done questions have complexity n/N square,where
	//n/N itself is <1, therefore its square is also <1.,hence no bad complexity
	
	
	
	
	
	
	
	
	
	
	
	
}

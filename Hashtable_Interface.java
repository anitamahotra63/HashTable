package lect20.MyHashtable;

public interface Hashtable_Interface<K,V> {

	int size();//size of hashtable
	boolean isEmpty();//specifies if hashtable is empty or not
	void add(K key,V value);
	V remove(K key) throws HashtableEmptyException;
	V get(K key) throws HashtableEmptyException;
	void display();
}

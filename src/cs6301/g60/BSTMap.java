
/** @author 
 *  Binary search tree map (starter code)
 *  Implement this class using one of the BST implementations: BST, AVLTree, RedBlackTree, or, SplayTree.
 *  Do not use TreeMap or any of Java's maps.
 **/

package cs6301.g60;


import java.util.Iterator;

public class BSTMap<K extends Comparable<? super K>, V> implements Iterable<K> {
	
	AVLTree<Entry> tree;
	
	class Entry implements Comparable<Entry>{
		K key;
		V value;
		Entry(K x, V v){
			key = x;
			value = v;
		}
		
		@Override
		public int compareTo(Entry element){
			return key.compareTo(element.key);
			
		}
	}
	
    BSTMap() {
    	tree = new AVLTree<>();
    }

    public V get(K key) {
    	Entry entry = new Entry(key,null);
    	entry = tree.find(entry).element;
    	return entry.value;
    }

    public boolean put(K key, V value) {
    	Entry entry = new Entry(key, value);
    	return tree.add(entry);
    }

    // Iterate over the keys stored in the map, in order
    public Iterator<K> iterator() {
    	return new BSTMapIterator(this);
    }
    
    public class BSTMapIterator implements Iterator<K>{
    	BSTMap<K,V> map = null;
    	Iterator<Entry> it;
    	Entry element;
    	BSTMapIterator(BSTMap<K,V> mapTree){
    		map = mapTree;
    		it = mapTree.tree.iterator();
    	}
    	
    	public boolean hasNext() {
    		return it.hasNext();
    	}
    	
    	public K next(){
    		element = it.next();
    		return element.key;
    	}
    }
    
    public static void main(String[] args){
    	BSTMap<String, Integer> map = new BSTMap<>();
    	map.put("varun", 1);
    	map.put("shivan", 2);
    	System.out.println(map.get("varun"));
    	map.put("varun", 4);
    	System.out.println(map.get("varun"));
    	
    	for(String name: map){
    		System.out.println(name);
    	}
    }
}

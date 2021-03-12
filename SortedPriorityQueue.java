package Warren_Lab07;
import java.util.Iterator;
import java.util.Comparator;

public class SortedPriorityQueue <K,V > extends AbstractPriorityQueue<K,V>{

	private PositionalList <Entry<K,V>> list = new LinkedPositionalList<>();

	public SortedPriorityQueue() {
		super();
	} 

	public SortedPriorityQueue(Comparator<K> comp) {
		super(comp);
	}


	public Entry<K,V>insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K,V> newest = new PQEntry <>(key,value);
		Position<Entry<K,V> >walk = list.last();

		while (walk!= null && compare(newest,walk.getElement())<0)
			walk = list.before(walk);
		if (walk==null)
			list.addFirst(newest);
		else
			list.addAfter(walk,newest);
		return newest;
	}

	public Entry<K,V> min(){
		if (list.isEmpty()) return null;
		return list.first().getElement();
	}

	public Entry<K,V> removeMin(){
		if (list.isEmpty()) return null;
		return list.remove(list.first());
	}

	public int size() {
		return list.size();
	}
	
	public int sort(String r) {
		int spot;
		
		if (r.length()<8) {
			spot = 11;
		}
		else {
			spot = Integer.parseInt(String.valueOf(r.charAt(r.length()-1)));
		}
		
		return spot;
	}

	public void print() {
		
		Iterator<Entry<K, V>> ElementIterator = list.iterator();
		
		System.out.println();
		while(ElementIterator.hasNext()) {
			System.out.println(ElementIterator.next().getValue()+"\n");

		}
		
	}

}

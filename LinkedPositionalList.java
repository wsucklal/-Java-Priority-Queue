package Warren_Lab07;
import java.util.Iterator;

import java.util.NoSuchElementException;

public class LinkedPositionalList<E>implements PositionalList<E>, Iterable<E> {

	private static class Node <E> implements Position <E>{

		E element;
		Node<E> next;
		Node<E> prev;

		public Node(E e, Node<E> p, Node <E> n)
		{
			this.element = e;
			this.next = n;
			this.prev = p;

		}

		public E getElement () throws IllegalStateException {
			return this.element ;
		}

		public void setElement(E e) 
		{
			this.element = e;
		}

		public Node<E> getNext(){
			return this.next;
		}

		public void setNext (Node <E> n) {
			this.next = n;
		}

		public Node<E> getPrev(){
			return this.prev;
		}

		public void setPrev(Node <E> p){
			this.prev = p;
		}
	}

	private Node <E> header;
	private Node <E> trailer;
	private int size = 0;

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public LinkedPositionalList() {
		header = new Node<E>(null,null,null);
		trailer = new Node<E>(null,header,null);
		header.setNext(trailer);
	}

	private Node<E> check (Position<E> p) throws IllegalArgumentException{

		if(!(p instanceof Node))
			throw new IllegalArgumentException("Invalid p");

		Node<E> n = (Node<E>)p;

		if (n.getNext() == null)
			throw new IllegalArgumentException ("This node doesn't belong to the list");

		return n;
	}

	private Position<E> position(Node<E> n){
		if ( n == header||n ==trailer) 
			return null;

		return n;
	}

	public Position<E> first(){
		return position(header.getNext());
	}

	public Position<E> last(){
		return position(trailer.getPrev());
	}

	public Position<E> before (Position<E> p) throws IllegalArgumentException{
		Node <E> n = check(p);
		return position(n.getPrev());
	}

	public Position<E> after (Position <E> p) throws IllegalArgumentException{
		Node<E> n = check(p);
		return position(n.getNext());
	}

	public Position <E> addBetween (E e,Node<E> pred, Node<E> succ){
		Node<E> newNode = new Node (e,pred,succ);
		pred.setNext(newNode);
		succ.setPrev(newNode);
		size++;
		return position (newNode);
	}

	public Position<E> addFirst(E e){
		return addBetween(e,this.header,this.header.getNext());
	}

	public Position<E> addLast(E e){
		return addBetween(e,this.trailer.getPrev(),this.trailer);
	}

	public Position<E> addBefore (Position<E> p, E e) throws IllegalArgumentException{
		Node <E> n = check (p);
		return addBetween (e,n.getPrev(),n);
	}

	public Position<E> addAfter (Position <E> p, E e) throws IllegalArgumentException{
		Node <E> n = check(p);
		return addBetween (e,n,n.getNext());
	}

	public E set(Position<E> p, E e) throws IllegalArgumentException{
		Node<E> n = check(p);
		E temp = n.getElement();
		n.setElement(e);

		return temp;
	}

	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> n = check(p);

		E temp = n.getElement();

		Node <E> pred = n.getPrev();
		Node <E> succ = n.getNext();

		pred.setNext(succ);
		succ.setPrev(pred);
		size--;

		n.setElement(null);
		n.setNext(null);
		n.setPrev(null);

		return temp;
	}

	private class PositionIterator implements Iterator<Position<E>>{
		Position <E> cursor = first();
		Position <E> recent = null;

		public boolean hasNext() {
			return(cursor != null);
		}

		public Position <E> next() throws NoSuchElementException {
			if (cursor == null)
				throw new NoSuchElementException();
			recent = cursor;
			cursor = after(cursor);
			return recent;
		}

		public void remove() throws IllegalStateException{

			if (recent == null)
				throw new IllegalStateException ();
			LinkedPositionalList.this.remove(recent);
			recent = null;
		}

	}

	public class PositionIterable implements Iterable <Position<E>>{

		public Iterator<Position<E>> iterator()  {
			return new PositionIterator();
		}
	}

	public Iterable<Position<E>> positions(){
		return new PositionIterable();
	}

	public class ElementIterator implements Iterator<E>{

		PositionIterator posIterator = new PositionIterator();

		public boolean hasNext() {
			return posIterator.hasNext();
		}

		public E next() {
			return posIterator.next().getElement();
		}

		public void remove() {
			posIterator.remove();
		}
	}

	public Iterator<E> iterator(){
		return new ElementIterator();
	}


}

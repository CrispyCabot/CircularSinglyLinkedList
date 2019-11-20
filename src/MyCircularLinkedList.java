//Chris Bridewell

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class MyCircularLinkedList<E> implements MyList<E> {
	
	private Node<E> tail;
	private int size = 0;

	//Creates an empty list
	public MyCircularLinkedList() {
	}

	//Create a list from an array of objects
	public MyCircularLinkedList(E[] objects) {
		for (int i = 0; i < objects.length; i++)
			add(objects[i]);
	}

	@Override//Add an element at a specific index
	public void add(int index, E e) {
		if (size == 0) { //If list is empty it creates a new object pointing to itself
			tail = new Node<>(e);
			tail.next = tail;
			size++;
		}
		else if (index == 0) //Use addFirst method if index is 0
			addFirst(e);
		else if (index == size) { //Use addLast method if index is size
			addLast(e);
		}
		else if (index > size) { //Throw Index exception if given too high of an index
			throw new IndexOutOfBoundsException("Index is too high");
		}
		else if (index < 0) { //Index exception with index less than 0
			throw new IndexOutOfBoundsException("Index is too low");
		}
		else {
			Node<E> current = tail; //Set current to tail
			Node<E> newNode = new Node<>(e);
			for (int i=0; i< index; i++) { //Go through list up to right before index
				current = current.next;
			}
			newNode.next = current.next; //Set pointer of new node to above index
			current.next = newNode; //Change pointer to newNode
			size++;
		}
	}

	@Override //Gets an element at the index given
	public E get(int index) {
		if (size == 0 || index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		Node<E> current = tail; //Sets current to tail
		for (int i=0; i<= index; i++) { //Loops through list index amount of times
			current = current.next;
		}
		return current.element; //Return the element at the index
	}

	@Override //Returns the index of the first occurrence of object e, returns -1 if not found
	public int indexOf(Object e) {
		Node<E> current = tail;
		int index = 0;
		for (int i=0; i<size; i++) {
			current = current.next;
			if (current.element.equals(e)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	@Override //Returns index of last occurrence of object e, returns -1 if not found
	public int lastIndexOf(E e) {
		Node<E> current = tail;
		int index = 0;
		int lastFound = -1;
		for (int i=0; i<size; i++) {
			current = current.next;
			if (current.element.equals(e)) {
				lastFound = index;
			}
			index++;
		}
		return lastFound;
	}

	@Override //Removes and returns an element based on index given
	public E remove(int index) {
		if (size == 1) {
			this.clear();
			return null;
		}
		else {
			Node<E> current = tail;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			E temp = current.next.element;
			current.next = current.next.next;
			size--;
			return temp;
		}
	}

	@Override //Sets the object at an index to the object given
	public E set(int index, E e) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		Node<E> current = tail;
		for (int i=0; i<=index; i++) {
			current = current.next;
		}
		E temp = current.element;
		current.element = e;
		return temp;
	}

	@Override //Checks if an object is contained in the list
	public boolean contains(Object e) {
		Node<E> current = tail;
		for (int i = 0; i<size; i++) {
			current = current.next;
			if (current.element.equals(e)) {
				return true;
			}
		}
		return false;
	}

	@Override //returns the size of the list
	public int size() {
		return size;
	}

	@Override //toString representation of the list
	public String toString() {
		String str = "[";
		Node<E> current = tail;
		if (size == 0) {
			return "[]";
		}
		for (int i=0; i<size; i++) {
			current = current.next;
			if (i == size-1) {
				return str + current.element.toString() + "]";
			}
			str += current.element.toString() + ", ";
		}
		return "This shouldn't have printed";
	}

	@Override //empties the list
	public void clear() {
		size = 0;
		tail.next = null;
		tail.element = null;
	}

	//Add element at beginning of list
	public void addFirst(E e) {
		if (size == 0) {
			tail = new Node<>(e);
			tail.next = tail;
			size++;
		}
		else {
			Node<E> newNode = new Node<>(e);
			newNode.next = tail.next;
			tail.next = newNode;
			size++;
		}
	}

	//Add element to end of list
	public void addLast(E e) {
		if (size == 0) {
			tail = new Node<>(e);
			tail.next = tail;
			size++;
		}
		else {
			Node<E> newNode = new Node<>(e);
			newNode.next = tail.next;
			tail.next = newNode;
			tail = newNode;
			size++;
		}
	}

	//Returns first element of list
	public E getFirst() {
		if (size == 0) {
			throw new NoSuchElementException("List is empty");
		}
		return tail.next.element;
	}

	//Returns last element of list
	public E getLast() {
		if (size == 0) {
			throw new NoSuchElementException("List is empty");
		}
		return tail.element;
	}

	//Removes and returns first element of list
	public E removeFirst() {
		if (size == 0) {
			throw new NoSuchElementException("List is empty");
		}
		E temp = tail.next.element;
		tail.next = tail.next.next;
		return temp;
	}

	//Removes and returns last element of list
	public E removeLast() {
		if (size == 0) {
			throw new NoSuchElementException("List is empty");
		}
		E temp = tail.element;
		Node<E> current = tail;
		for (int i=0; i<size-1; i++) {
			current = current.next;
		}
		current.next = current.next.next;
		return temp;
	}

	//iterator thing
	@Override
	public java.util.Iterator iterator() {
		return new ListIterator<>();
	}

	//Node class
	private static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element) {
			this.element = element;
		}
	}

	private class ListIterator<E> implements Iterator<E> {
		Node<E> current = (Node<E>) tail;
		int index = -1;
		boolean canRemove = false;

		@Override
		public boolean hasNext() {
			if (index >= size) {
				return false;
			}
			index += 1;
			return true;
		}

		@Override
		public E next() {
			canRemove = true;
			current = current.next;
			index++;
			return current.element;
		}

		@Override
		public void remove() {
			if (index ==-1 || index >= size || !canRemove) {
				throw new IllegalStateException();
			}
			canRemove = false;
			Node<E> current = (Node<E>) tail;
			for (int i=0; i<size;i++) { //Traverse to current index in list
				current = current.next;
			}
			current.next = current.next.next;
			size--;
			index--;
			current = current.next;
		}
	}
}
/*
 * TCSS 342
 * Assignment 1 - Burger Baron
 */

import java.util.NoSuchElementException;

/**
 * Builds a stack from scratch using a linked structure.
 * 
 * @author Shannon Murphy
 * @version April 8, 2015
 *
 */
public class MyStack<Type> {

	/** Class to create a new Node for the linked structure. */
	private class Node {
		/** The item being added to the burger. */
		public Type myItem;

		/** The next item. */
		public Node myNext;

		/** Constructor for a new Node. */
		public Node(Type theItem, Node theNext) {
			myItem = theItem;
			myNext = theNext;
		}
	}

	private Node myTop;

	/** Constructs an empty MyStack. */
	 public MyStack () {
		 myTop = null;
	 }
	 
	/**
	 * Returns whether or not the myTop node is null. If it is null, the stack
	 * is empty.
	 * 
	 * @return true if the MyStack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return myTop == null;
	}

	/**
	 * Adds the passed item to the top of the MyStack.
	 * 
	 * @param theItem the item to be added.
	 */
	public void push(Type theItem) {
		myTop = new Node(theItem, myTop);
	}

	/**
	 * Removes and returns the item on the top of the MyStack.
	 * 
	 * @return the top item.
	 */
	public Type pop() {
		Type item = peek();
		myTop = myTop.myNext;
		return item;
	}

	/**
	 * Returns the item on the top of the MyStack but does not remove it.
	 * 
	 * @return the top item.
	 */
	public Type peek() {
		if (myTop == null) {
			throw new NoSuchElementException();
		}
		return myTop.myItem;
	}

	/**
	 * Returns the number of items in the MyStack.
	 * 
	 * @return the number of items.
	 */
	public int size() {
		int count = 0;
		for (Node node = myTop; node != null; node = node.myNext) {
			count++;
		}
		return count;
	}

	@Override
	/** Returns a String representation of the MyStack. */
	public String toString() {
		String result = "[";
		result += pop().toString();
		while (!isEmpty()) {
			result += ", " + pop().toString();
		}
		result += "]";
		return result;
	}
}
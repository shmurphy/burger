/*
 * TCSS 342
 * Assignment 1 - Burger Baron 
 */

/**
 * Represents a burger at the Burger Baron.
 *
 * @author Shannon Murphy
 * @version April 8, 2015
 *
 */
public class Burger {

	private int myPattyCount;

	/** The top half of the burger. */
	private final MyStack<String> myBurgerTop;

	/** The bottom half of the burger. */
	private final MyStack<String> myBurgerBottom;

	/**
	 * A temporary stack to move ingredients into when rearranging the other
	 * stacks.
	 */
	private final MyStack<String> myTempBurger;

	/** Flag to keep track of whether there is cheese on the burger. */
	private boolean myCheese;

	/**
	 * Constructs a new Burger that is either plain or has the works. A Baron
	 * Burger has the works, and a plain burger just has a bun and a patty.
	 * 
	 * @param theWorks true if the Burger is a Baron Burger, false if not.
	 */
	public Burger(final boolean theWorks) {
		myPattyCount = 0;
		myCheese = false;
		myBurgerTop = new MyStack<String>();
		myBurgerBottom = new MyStack<String>();
		myTempBurger = new MyStack<String>();

		myBurgerTop.push("Bun");
		myBurgerBottom.push("Bun");
		if (theWorks) {
			addCategory("Sauce");
			addCategory("Veggies");
			addPatty();
			addCategory("Cheese");
		} else {
			addPatty();
		}
	}

	/**
	 * Changes all of the patties to the passed patty type.
	 * 
	 * @param thePattyType the type of patty to replace all patties on the Burger.
	 */
	public void changePatties(final String thePattyType) {
		// change top patties
		if (myPattyCount == 2) {
			myBurgerTop.pop();
			myBurgerTop.push(thePattyType);
		} else if (myPattyCount == 3) {
			myBurgerTop.pop();
			myBurgerTop.pop();
			myBurgerTop.push(thePattyType);
			myBurgerTop.push(thePattyType);
		}
		// change bottom patty
		while (!searchBottom("Beef")) {
			myTempBurger.push(myBurgerBottom.pop());
		}
		myBurgerBottom.pop();
		myBurgerBottom.push(thePattyType);
		popTempBurger("Bottom");
	}

	/**
	 * Adds a patty to the Burger in the correct location up to the max of 3.
	 * Increments the patty count with each add.
	 */
	public void addPatty() {
		if (myPattyCount == 0) { // add first patty
			if (myCheese) {
				while (!searchBottom("Pepperjack") && !searchBottom("Mozzarella")
						&& !searchBottom("Cheddar")) {
					myTempBurger.push(myBurgerBottom.pop());
				}
				myBurgerBottom.push("Beef");
				popTempBurger("Bottom");
			} else if (!myCheese) {
				while (!myBurgerBottom.isEmpty()) {
					myTempBurger.push(myBurgerBottom.pop());
				}
				myBurgerBottom.push("Beef");
				popTempBurger("Bottom");
			}
		} else if (myPattyCount == 1 || myPattyCount == 2) { // add 2nd & 3rd
																// patties
			myBurgerTop.push("Beef");
		} else if (myPattyCount >= 3) {
			System.err.println("You can only have 3 patties!");
		}
		myPattyCount++;
	}

	/**
	 * Removes one patty from the Burger down to the minimum of one. Decrements
	 * the patty count with each removal.
	 */
	public void removePatty() {
		if (myPattyCount > 1) {
			myBurgerTop.pop();
			myPattyCount--;
		} else {
			System.err.println("You must have at least one patty!");
		}
	}

	/**
	 * Adds all items of the passed type to the Burger in the proper locations.
	 * 
	 * @param theType the category type to be added to the Burger.
	 */
	public void addCategory(final String theType) {
		if ("Sauce".equals(theType)) {
			addIngredient("Mayonnaise");
			addIngredient("Baron-Sauce");
			addIngredient("Mustard");
			addIngredient("Ketchup");
		} else if ("Veggies".equals(theType)) {
			addIngredient("Pickle");
			addIngredient("Lettuce");
			addIngredient("Tomato");
			addIngredient("Onions");
			addIngredient("Mushrooms");
		} else if ("Cheese".equals(theType)) {
			addIngredient("Pepperjack");
			addIngredient("Mozzarella");
			addIngredient("Cheddar");
		}

	}

	/**
	 * Removes all items of the passed type from the Burger.
	 * 
	 * @param theType the type of category to be removed from the Burger.
	 */
	public void removeCategory(final String theType) {
		if ("Sauce".equals(theType)) {
			removeIngredient("Mayonnaise");
			removeIngredient("Baron-Sauce");
			removeIngredient("Mustard");
			removeIngredient("Ketchup");
		} else if ("Veggies".equals(theType)) {
			removeIngredient("Lettuce");
			removeIngredient("Tomato");
			removeIngredient("Onions");
			removeIngredient("Mushrooms");
			removeIngredient("Pickle");
		} else if ("Cheese".equals(theType)) {
			removeIngredient("Pepperjack");
			removeIngredient("Mozzarella");
			removeIngredient("Cheddar");
		}
	}

	/**
	 * Adds the passed ingredient type to the Burger in the proper location.
	 * 
	 * @param theType the ingredient to be added to the Burger.
	 */
	public void addIngredient(final String theType) {
		if ("Pickle".equals(theType)) {
			while (!myBurgerTop.isEmpty()) {
				myTempBurger.push(myBurgerTop.pop());
			}
			myBurgerTop.push("Pickle");
			popTempBurger("Top");

		} else {
			final MyStack<String> fullTop = createFullBurger("Top");
			final MyStack<String> fullBottom = createFullBurger("Bottom");
			String next = "";
			while (!fullTop.peek().equals(theType) && !fullTop.peek().equals("Bun")) {
				fullTop.pop();
			}

			next = fullTop.peek();
			if (next.equals(theType)) { // the type was found on the top
				addToTop(next, fullTop, theType);
			} else {
				if (theType.equals("Pepperjack")) {
					while (!myBurgerBottom.isEmpty()) {
						myTempBurger.push(myBurgerBottom.pop());
					}
					myBurgerBottom.push("Pepperjack");
					popTempBurger("Bottom");
				} else {
					addToBottom(next, fullBottom, theType);
				}
			}
		}
	}

	/**
	 * Removes the passed ingredient type from the Burger.
	 * 
	 * @param theType the ingredient to be removed.
	 */
	public void removeIngredient(final String theType) {
		boolean removed = false; // whether or not the type has been removed
									// already
		while (!myBurgerTop.isEmpty() && !searchTop(theType)) {
			myTempBurger.push(myBurgerTop.pop());
		}
		if (!myBurgerTop.isEmpty() && searchTop(theType)) {
			myBurgerTop.pop();
			removed = true;
		}
		popTempBurger("Top");

		if (!removed) {
			while (!myBurgerBottom.isEmpty() && !searchBottom(theType)) {
				myTempBurger.push(myBurgerBottom.pop());
			}
			if (!myBurgerBottom.isEmpty() && searchBottom(theType)) {
				myBurgerBottom.pop();
			}
			popTempBurger("Bottom");
		}
	}

	/**
	 * Creates a full burger to be used to check for proper ingredient
	 * locations.
	 * 
	 * @return a burger with all ingredients.
	 */
	private MyStack<String> createFullBurger(final String theHalf) {
		final MyStack<String> fullStack = new MyStack<String>();
		if (theHalf.equals("Top")) {
			fullStack.push("Pickle");
			fullStack.push("Bun");
			fullStack.push("Mayonnaise");
			fullStack.push("Baron-Sauce");
			fullStack.push("Lettuce");
			fullStack.push("Tomato");
			fullStack.push("Onions");
		} else {
			fullStack.push("Pepperjack");
			fullStack.push("Mozzarella");
			fullStack.push("Cheddar");
			fullStack.push("Beef");
			fullStack.push("Mushrooms");
			fullStack.push("Mustard");
			fullStack.push("Ketchup");
			fullStack.push("Bun");
		}
		return fullStack;
	}

	/**
	 * Pops all of the items off of the temporary burger and pushes them onto
	 * either the top or bottom half of the main burger.
	 * 
	 * @param theHalf either the top half or bottom half of the burger.
	 */
	private void popTempBurger(final String theHalf) {
		if ("Bottom".equals(theHalf)) {
			while (!myTempBurger.isEmpty()) {
				myBurgerBottom.push(myTempBurger.pop());
			}
		} else if ("Top".equals(theHalf)) {
			while (!myTempBurger.isEmpty()) {
				myBurgerTop.push(myTempBurger.pop());
			}
		}
	}

	/**
	 * Searches the bottom half of the Burger for the passed type by calling
	 * peek().
	 * 
	 * @param theType the ingredient being searched for.
	 * @return true if peek() matches the desired ingredient, false otherwise.
	 */
	private boolean searchBottom(final String theType) {
		return myBurgerBottom.peek().equals(theType);
	}

	/**
	 * Searches the top half of the Burger for the passed type by calling
	 * peek().
	 * 
	 * @param theType
	 *            the ingredient being searched for.
	 * @return true if peek() matches the desired ingredient, false otherwise.
	 */
	private boolean searchTop(final String theType) {
		return myBurgerTop.peek().equals(theType);
	}

	/**
	 * Adds an ingredient to the top of the burger by searching the fully loaded
	 * top half for the correct location.
	 * 
	 * @param theNext the ingredient next to the ingredient to be added.
	 * @param theFullTop the top half of a burger with the works.
	 * @param theType the ingredient to be added.
	 */
	private void addToTop(String theNext, final MyStack<String> theFullTop,
			final String theType) {
		boolean found = false;
		theFullTop.pop();
		theNext = theFullTop.peek();

		while (!found) {
			while (!myBurgerTop.isEmpty()) {
				if (!searchTop(theNext)) {
					myTempBurger.push(myBurgerTop.pop());
				} else if (searchTop(theNext)) {
					found = true;
					myTempBurger.push(theType);
					myTempBurger.push(myBurgerTop.pop());
				}
			}
			popTempBurger("Top");
			if (!found) {
				theFullTop.pop();
				theNext = theFullTop.peek();
			}
		}
	}

	/**
	 * Adds an ingredient to the bottom of the burger by searching a fully
	 * loaded bottom half for the correct location.
	 * 
	 * @param theNext the ingredient next to the ingredient to be added.
	 * @param theFullBottom the bottom half of a burger with the works.
	 * @param theType the ingredient to be added.
	 */
	private void addToBottom(String theNext, final MyStack<String> theFullBottom,
			final String theType) {
		boolean found = false;
		while (!theFullBottom.peek().equals(theType)) {
			theFullBottom.pop();
		}
		theNext = theFullBottom.peek();
		if (theNext.equals(theType) && !theType.equals("Pepperjack")) { 
			theFullBottom.pop();
			theNext = theFullBottom.peek();
			while (!found) {
				while (!myBurgerBottom.isEmpty()) {
					if (!searchBottom(theNext)) {
						myTempBurger.push(myBurgerBottom.pop());
					} else if (searchBottom(theNext)) {
						found = true;
						myTempBurger.push(theType);
						myTempBurger.push(myBurgerBottom.pop());
					}
				}
				popTempBurger("Bottom");
				if (!found) {
					if (theFullBottom.peek().equals("Pepperjack")) {
						while (!myBurgerBottom.isEmpty()) {
							myTempBurger.push(myBurgerBottom.pop());
						}
						myTempBurger.push(theType);
						popTempBurger("Bottom");
						found = true;
					} else {
						theFullBottom.pop();
						theNext = theFullBottom.peek();
					}
				}
			}
		}
	}

	@Override
	/** Returns a String representation of the Burger by combining the top half
	 * and bottom half. */
	public String toString() {
		MyStack<String> finalOrder = new MyStack<String>();
		while (!myBurgerBottom.isEmpty()) {
			finalOrder.push(myBurgerBottom.pop());
		}
		while (!myBurgerTop.isEmpty()) {
			finalOrder.push(myBurgerTop.pop());
		}
		return finalOrder.toString();
	}
}

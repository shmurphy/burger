/*
 * TCSS 342
 * Assignment 1 - Burger Baron 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver for the Burger class. 
 * @author shmurphy
 *
 */
public class Main {
	private static Burger myBurger;

	/**
	 * Reads file input and prints out orders.
	 * 
	 * @param theArgs command line arguments.
	 */
	public static void main(final String[] theArgs) {
		try {
			final Scanner sc = new Scanner(new File("customer.txt"));
//			 final Scanner sc = new Scanner(new File("testing.txt"));

			int i = 0;
			while (sc.hasNextLine()) {
				String order = sc.nextLine();
				System.out.println("Processing order " + i + ": " + order);
				parseLine(order);
				i++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}

		testMyStack();
		testBurger();
	}

	/**
	 * Scans a line of the file input and builds a burger based upon the order.
	 * Each line is a new order for a new burger.
	 * 
	 * @param theOrder the line of input from the file.
	 */
	public static void parseLine(String theOrder) {
		final Scanner sc = new Scanner(theOrder);
		boolean baronBurger = false;
		String next = sc.next();
		String pattyCount = "";
		String pattyType = "";
		if (next.equals("Single") || next.equals("Double") || next.equals("Triple")) {
			pattyCount = next;
			next = sc.next();
		} else {
			pattyCount = "Single";
		}
		if (next.equals("Beef") || next.equals("Chicken") || next.equals("Veggie")) {
			pattyType = next;
			next = sc.next();
		} else {
			pattyType = "Beef";
		}
		if (next.equals("Baron")) {
			baronBurger = true;
			next = sc.next();
		} else {
			baronBurger = false;
		}
		setUpBurger(pattyCount, pattyType, baronBurger, sc);
		System.out.println(myBurger);
		sc.close();
	}

	/** Test method for the MyStack class. */
	public static void testMyStack() {
		final MyStack<Integer> intStack = new MyStack<Integer>();
		intStack.push(1);
		intStack.push(2);
//		System.out.println(s.peek().equals(2)); // should be true
		intStack.pop();
		intStack.pop();
		// System.out.println(s.isEmpty()); // should be true
	}

	/** Test method for the Burger class. */
	public static void testBurger() {
		final Burger burger = new Burger(false);
		final Burger baron = new Burger(true);
		
		burger.addCategory("Cheese");
		burger.addCategory("Veggies");
		burger.addCategory("Sauce");
		burger.removeIngredient("Pickle");
		burger.removeIngredient("Mushrooms");
		burger.addIngredient("Mustard");
		burger.addPatty();
		burger.changePatties("Chicken");
//		System.out.println(burger);
		
		baron.addPatty();
		baron.removeCategory("Veggies");
		baron.removeCategory("Cheese");
		baron.removeCategory("Sauce");
		baron.addIngredient("Onions");
		baron.addIngredient("Pickle");
		baron.addIngredient("Cheddar");
		baron.changePatties("Veggie");
		baron.removePatty();
//		System.out.println(baron);

	}

	/**
	 * Sets up a new Burger based on whether it has the works or not.
	 * 
	 * @param thePattyCount the number of patties.
	 * @param thePattyType the type of patty.
	 * @param theWorks true if it's a baron burger, false otherwise.
	 * @param theScanner the Scanner used the read the file.
	 */
	private static void setUpBurger(String thePattyCount, String thePattyType,
			boolean theWorks, Scanner theScanner) {
		myBurger = new Burger(theWorks);
		if (thePattyCount.equals("Double")) {
			myBurger.addPatty();
		} else if (thePattyCount.equals("Triple")) {
			myBurger.addPatty();
			myBurger.addPatty();
		}
		if (theWorks) {
			setUpBaronBurger(theScanner);
		} else {
			setUpRegularBurger(theScanner);
		}
		if (thePattyType.equals("Chicken")) {
			myBurger.changePatties("Chicken");
		} else if (thePattyType.equals("Veggie")) {
			myBurger.changePatties("Veggie");
		}
	}

	/**
	 * Sets up a new Baron Burger with possible omissions based on the file input.
	 * 
	 * @param theScanner the scanner used to read the file.
	 */
	private static void setUpBaronBurger(Scanner theScanner) {
		String next = "";
		if (theScanner.hasNext()) {
			theScanner.next(); // skip "with"
			theScanner.next(); // skip "no"
			next = theScanner.next(); // first omission

			while (!next.equals("but")) {
				if (next.equals("Veggies") || next.equals("Cheese") || next.equals("Sauce")) {
					myBurger.removeCategory(next);
				} else {
					myBurger.addIngredient(next);
				}
				if (theScanner.hasNext()) {
					next = theScanner.next();
				} else {
					next = "but"; // omissions are done, drop out of loop
				}
			}
			while (theScanner.hasNext()) {
				next = theScanner.next(); // remaining exceptions
				myBurger.addIngredient(next);
			}
		}
	}

	/** 
	 * Sets up a new plain burger with possible additions based on the file input.
	 * @param theScanner
	 */
	private static void setUpRegularBurger(Scanner theScanner) {
		String next = "";
		if (theScanner.hasNext()) {
			theScanner.next(); // skip "with"
			next = theScanner.next();
			while (!next.equals("but")) {
				if (next.equals("Veggies") || next.equals("Cheese") || next.equals("Sauce")) {
					myBurger.addCategory(next);
				} else {
					myBurger.addIngredient(next);
				}
				if (theScanner.hasNext()) {
					next = theScanner.next();
				} else {
					next = "but"; // additions are done, drop out of loop
				}
			}
			while (theScanner.hasNext()) {
				next = theScanner.next(); // remaining exceptions
				myBurger.removeIngredient(next);
			}
		}
	}
}

package project3;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * This class represents a list of Species objects, implemented as a doubly-linked list. It implements the Iterable interface.
 * All elements in the list are maintained in ascending/increasing order based on the natural order of the elements.
 * This list does not allow null elements.
 * @author Adam Soliman
 * @version 3-6-24
 */
public class SpeciesList implements Iterable<Species> {
	private class Node {
		
		Species data;
        Node prev;
        Node next;

        public Node(Species data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
        
	}
	
    private Node head;
    private Node tail;
    private int size;
    
    /**
     * Constructs a new empty sorted linked list of Species objects.
     */
	public SpeciesList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Adds the specified Species to this SpeciesList in ascending order.
	 * If this list contains an element that is equal to species argument, 
	 * the counties in two objects are merged and stored in the object in the list.
	 * @param species	input species to be added to the list
	 * @return	true if the species could be added, false otherwise.
	 */
	public boolean add(Species species) throws IllegalArgumentException {
		if (species == null)
			throw new IllegalArgumentException("Null objects are not allowed");
		if (this.head == null) {
			head = new Node(species);
			tail = head;
			size++;
			return true;
		} else if (head.next == null) {
			if (head.data.equals(species)) {
				return false; // species is already the head of the list.
			}
			if (head.data.compareTo(species) < 0) {
				head.next = new Node(species);
				tail = head.next;
				tail.prev = head; // two element list
				size++;
				return true;
			} else {
				Node spe = new Node(species);
				Node temp = head;
				head = spe;
				spe.next = temp;
				tail = temp;
				tail.prev = spe;
				size++;
				return true;
			}
		}
		Node temp = head;
		if (temp.data.compareTo(species) > 0) {
			Node spe = new Node(species);
			spe.next = temp;
			head = spe;
			temp.prev = head;
			size++;
			return true;
		}
		while (temp != null) {
			if (temp.data.compareTo(species) < 0) {
				temp = temp.next;
				continue;
			} else if (temp.data.equals(species)) {
				// if Species is already in the list, merge counties
				for (String c : species.getCounties()) {
				    temp.data.addCounty(c);
				}
				return false;
			} else {
				// Move specified Species into the list before temp
				Node spe = new Node(species);
				Node pre = temp.prev;
				pre.next = spe;
				temp.prev = spe;
				spe.next = temp;
				spe.prev = pre;
				size++;
				return true;
			}
		}
		// add node to the end of the list.
		Node added = new Node(species);
		tail.next = added;
		added.prev = tail;
		tail = added;
		size++;
		return true;
	}
	
	/**
	 * Adds the specified species element and its associated county to the list in ascending order.
	 * @param species	Species element to be added to the list.
	 * @param county	county to be added to the specific Species' list.
	 * @return	true if the element was successfully added, false otherwise.
	 * @throws IllegalArgumentException	if the input Species is null, or the input county is null or empty.
	 */
	public boolean add(Species species, String county) throws IllegalArgumentException {
		if (species == null)
			throw new IllegalArgumentException("Null objects are not allowed");
		if (this.head == null) {
			head = new Node(species);
			tail = head;
			head.data.addCounty(county);
			size++;
			return true;
		} else if (head.next == null) {
			if (head.data.equals(species)) {
				return false; // species is already the head of the list.
			}
			if (head.data.compareTo(species) < 0) {
				head.next = new Node(species);
				tail = head.next;
				tail.prev = head; // two element list
				tail.data.addCounty(county);
				size++;
				return true;
			} else {
				Node spe = new Node(species);
				Node temp = head;
				head = spe;
				spe.next = temp;
				tail = temp;
				tail.prev = spe;
				head.data.addCounty(county);
				size++;
				return true;
			}
			
		}
		Node temp = head;
		// Check if the head should be changed
		if (temp.data.compareTo(species) > 0) {
			Node spe = new Node(species);
			spe.next = temp;
			head = spe;
			temp.prev = head;
			head.data.addCounty(county);
			size++;
			return true;
		}
		while (temp != null) {
			if (temp.data.compareTo(species) < 0) {
				temp = temp.next;
				continue;
			} else if (temp.data.equals(species)) {
				// if Species is already in the list, merge counties
				for (String c : species.getCounties()) {
				    temp.data.addCounty(c);
				}
				temp.data.addCounty(county);
				return false;
			} else {
				// Move specified Species into the list before temp
				Node spe = new Node(species);
				Node pre = temp.prev;
				pre.next = spe;
				temp.prev = spe;
				spe.next = temp;
				spe.prev = pre;
				spe.data.addCounty(county);
				size++;
				return true;
			}
		}
		// Node should be the new tail.
		Node added = new Node(species);
		tail.next = added;
		added.prev = tail;
		tail = added;
		tail.data.addCounty(county);
		size++;
		return true;
	}
	
	/**
	 * Removes all elements from this SpeciesList.
	 */
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Checks if this species list contains an element equal to the input Species.
	 * @param o	specified element to be checked if in list.
	 * @return	true if the specified element is in the list, false otherwise.
	 */
	public boolean contains(Object o)  {
		if (o == null) {
			return false;
		}
		Species obj = (Species) o;
		for (Species s : this) {
			if (s.equals(obj))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if this species list is equal to the input object.
	 * @param o	specified list to be compared to with this list.
	 * @return	true if this list and the specified list have the same objects in the same order, false otherwise.
	 * @throws IllegalArgumentException	if the input list is null or not a SpeciesList object.
	 */
	public boolean equals(Object o) {
		if (o == null) {
			throw new IllegalArgumentException("Null objects not allowed.");
		} else if (!(o instanceof SpeciesList)) {
			throw new IllegalArgumentException("Argument not of specified type, SpeciesList.");
		}
		
		SpeciesList obj = (SpeciesList) o;
		if (obj.size() != this.size()) {
			return false;
		}	
		for (int i = 0; i < this.size()-1; i++) {
			if ( !(this.get(i).equals(obj.get(i))) ) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the element in this SpeciesList at the specified index.
	 * @param index	specified index of the element to return.
	 * @return	the element at the specified index.
	 * @throws IndexOutOfBoundsException if the specified index is not in range.
	 */
	public Species get(int index) throws IndexOutOfBoundsException {	
	    if (index < 0 || index >= size()) {
	        throw new IndexOutOfBoundsException("Index is not in range.");
	    }
	    if (head == null) {
	        throw new NullPointerException("List is empty.");
	    }
	    int counter = 0;
	    Node temp = head;
	    while (temp != null) {
	    	if (counter == index) {
	    		break;
	    	} else {
	    		temp = temp.next;
	    		counter++;
	    	}
	    }
	    
	    return temp.data;
	}
	
	/**
	 * Returns a SpeciesList with all elements in this SpeciesList that match the keyword.
	 * @param keyword	the key word to search for in this list.
	 * @return	a list of Species objects that contain the specified keyword in either 
	 * the common name or the scientific name.
	 */
	public SpeciesList getByName(String keyword) {
		if (keyword == null || keyword.equals("")) {
			throw new IllegalArgumentException("Null/empty keywords are not allowed");
		}
		SpeciesList output = new SpeciesList();
		String key = keyword.toLowerCase();
		for (Species s : this) {
			if (s.getCommonName().toLowerCase().contains(key)) {
				output.add(s);
			} else if (s.getScientificName().toLowerCase().contains(key)) {
				output.add(s);
			}
		}
		if (output.size() != 0) {
			return output;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the index of the first occurrence of the specified element, or -1 if the element is not in the list.
	 * @param o	element to be searched for.
	 * @return	the index of the specified element.
	 */
	public int indexOf(Object o) {
		if (o == null) {
			throw new IllegalArgumentException("Null objects not allowed.");
		} else if (!(o instanceof SpeciesList)) {
			throw new IllegalArgumentException("Argument not of specified type, SpeciesList.");
		}
		if (size() == 0) {
			return -1;
		}
		Species sp = (Species) o;
		int counter = 0;
		Node temp = head;
		while (temp != null) {	
			if (temp.data.equals(sp)) {
				return counter;
			}
			counter++;
			temp = temp.next;
		}
		
		return -1;
	}
	
	/**
	 * Removes the specified object from the list.
	 * @param o	specified object to be removed.
	 * @return	true if the object was successfully removed, false otherwise.
	 */
	public boolean remove(Object o) {
		if (o == null) {
			return false;
		} else if (!(o instanceof Species)) {
			return false;
		}
		if (size() == 0) {
			return false;
		}
		Node temp = head;
		while (temp != null) {
			if (temp.data.equals(o)) {
				break;
			} else {
				temp = temp.next;
			}
		}
		if (temp == null)
			return false; // element not in list.
		
		if (temp == head) {
			if (head.next != null) {
				head = head.next;
				return true;
			} else {
				head = null;
				tail = null;
				return true;
			}
		} else if (temp == tail) {
		    tail = tail.prev;
		    tail.next = null;
		    return true;
		} else {
			Node p = temp.prev;
			Node a = temp.next;
			p.next = a;
			a.prev = p;
			return true;
		}	
	}
	
	/**
	 * Returns the size of this list.
	 * @return	size of this SpeciesList.
	 */
	public int size() { return size; }
	
	/**
	 * Returns a String representation of the list. 
	 * The string representation consists of a list of the lists' elements common names and scientific names 
	 * in ascending order, enclosed in square brackets ("[]"). Adjacent elements are separated by the characters: 
	 * ", " (comma and space). [commonName1 (scientificName1), commonName2 (scientificName2), ...] 
	 * An empty list is represented by "[]".
	 */
	public String toString() {
		if (size() == 0)
			return "[]";
		
		String output = "[";
		for (Species s : this) {
			output += s.getCommonName() + " (" + s.getScientificName() + "), ";
		}
		
		return output.substring(0,output.length()-2) + "]";
	}
	
	/**
	 * Returns an Iterator for the SpeciesList class over Species objects.
	 * @return	Returns an iterator over the elements in the list.
	 */
	@Override
	public Iterator<Species> iterator() { return new Itr(); }
	
	/**
	 * An iterator implementation for iterating through the elements of a SpeciesList.
	 * This iterator traverses the elements of the SpeciesList in the order they were added.
	 */
	private class Itr implements Iterator<Species> {

        protected Node current = head;
        
        Itr() {}

        /**
         * Checks if there is a next element in the SpeciesList
         * @return	true if a next element exists, false otherwise.
         */
		@Override
		public boolean hasNext() {
			return current != null;
		}

		/**
		 * Returns the next element of this SpeciesList
		 * @return the next element of this SpeciesList, if it exists
		 * @throws NoSuchElementException	if a next element does not exist
		 */
		@Override
		public Species next() throws NoSuchElementException {
			if (!hasNext()) {
                throw new NoSuchElementException("No more elements in this list");
            } else {
            	Species species = current.data; // grab the species from the current node
                current = current.next;	// move 'current' to the next element
                return species;
            }
		}
        
	}
	
	/**
	 * Returns a ListIterator over Species elements in this list.
	 * @return	Returns a list iterator over the elements in the list.
	 */
	public ListIterator<Species> listIterator() { return new Ltr(0); }
	
	/**
	 * An ListIterator implementation for iterating through the elements of a SpeciesList.
	 * This iterator traverses the elements of the SpeciesList in the order they were added.
	 * This iterator supports bidirectional traversal, element insertion, and element removal operations.
	 */
	private class Ltr extends Itr implements ListIterator<Species> {
		
		private Node lastRet;
		private int index = 0;

		/**
		 * Constructs a ListIterator
		 * @param index	starting index.
		 */
		public Ltr(int index) {
			if (index < 0 || index > size)
                throw new IndexOutOfBoundsException("Index: " + index);
			lastRet = null;
			if (index == size) {
				current = null;
			} else {
				Node x = head;
				for (int i = 0; i < index; i++) {
					x = x.next;
				}
				current = x;
			}
			this.index = index;
		}
				
		/**
		 * Checks if there is a next element.
		 * @return true if there is a next element, false otherwise.
		 */
		@Override
		public boolean hasNext() {
			return index < size;
		}

		/**
		 * Returns the next element in this ListIterator.
		 * @return the next element.
		 */
		@Override
		public Species next() {
			if (!hasNext()) {
                throw new NoSuchElementException("No more elements in this list");
            } else {
            	lastRet = current;
                current = current.next;	// move 'current' to the next element
                index++;
                return lastRet.data;
            }
		}

		/**
		 * Determines if this ListIterator has a previous element
		 * @return True if there is a previous element, false otherwise.
		 */
		@Override
		public boolean hasPrevious() {
			return index > 0;
		}

		/**
		 * Returns the previous element in this ListIterator, if one exists.
		 * @return the previous element.
		 */
		@Override
		public Species previous() throws NoSuchElementException {
			if (!hasPrevious()) {
                throw new NoSuchElementException("No previous element");
            }
			if (current == null) {
				current = tail;
			} else {
				current = current.prev;
			}
			lastRet = current;
			index--;
			return lastRet.data;
		}

		/**
		 * Returns the index of the next element to be returned.
		 * @return index of next element to be returned.
		 */
		@Override
		public int nextIndex() {
			return index;
		}

		/**
		 * Returns the index of the previous element
		 * @return index of previous element.
		 */
		@Override
		public int previousIndex() {
			return index-1;
		}

		/**
		 * Optional method as per the ListIterator java documentation, throws UnsupportedOperationException
		 * @throws UnsupportedOperationException	Method not supported.
		 */
		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("The remove operation is not supported by this list iterator");
		}

		/**
		 * Sets the last returned element to the specified element.
		 */
		@Override
		public void set(Species e) {
			if (lastRet == null) {
                throw new IllegalStateException();
            }
            lastRet.data = e;			
		}

		/**
		 * Optional method as per the ListIterator java documentation, throws UnsupportedOperationException
		 * @throws UnsupportedOperationException	Method not supported.
		 */
		@Override
		public void add(Species e) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("The add operation is not supported by this list iterator");			
		}
	
	}
	
}
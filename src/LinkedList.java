package project3;
public class LinkedList {
public static class Node {
		
		int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
        
	}

	private Node head;
	private Node tail;
	
	public LinkedList() {
		head = null;
		tail = null;
	}

	public boolean swapLastTwo() {
		Node temp = head;
		while (temp.next.next != tail) {
			temp = temp.next;
		}
		
		
		
		
		
		return false;
	}
	
	public static void main(String[] args) {
		Node a = new Node(1);
		Node b = new Node(2);
		Node c = new Node(3);
		Node d = new Node(4);

	}




}

package com.CK;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}

// Recursive
class Solution {
    public Node flatten(Node head) {
        Node dummyH = new Node();
        dummyH.next = head;

        if (head == null)
            return null;

        if (head.child == null) {
            Node flattenNext = flatten(head.next);
            head.next = flattenNext;
            if (flattenNext != null )
                flattenNext.prev = head;
            return head;
        }

        if (head.next == null) {
            Node flattenChild = flatten(head.child);
            head.next = flattenChild;
            head.child = null;
            if (flattenChild != null )
                flattenChild.prev = head;
            return head;
        }


        Node flattenChild = flatten(head.child), flattenNext = flatten(head.next), curr = head;
        curr.next = flattenChild;
        curr.child = null;
        flattenChild.prev = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = flattenNext;
        flattenNext.prev = curr;
        return dummyH.next;

    }
}

//Iterative
class Solution {
    public Node flatten(Node head) {
        if( head == null) return head;
        // Pointer
        Node p = head;
        while( p!= null) {
            /* CASE 1: if no child, proceed */
            if( p.child == null ) {
                p = p.next;
                continue;
            }
            /* CASE 2: got child, find the tail of the child and link it to p.next */
            Node temp = p.child;
            // Find the tail of the child
            while( temp.next != null )
                temp = temp.next;
            // Connect tail with p.next, if it is not null
            temp.next = p.next;
            if( p.next != null )  p.next.prev = temp;
            // Connect p with p.child, and remove p.child
            p.next = p.child;
            p.child.prev = p;
            p.child = null;
        }
        return head;
    }
}

//Stack
class Solution {
    public Node flatten(Node head) {
        Node curt = head;
        Stack<Node> stack = new Stack<>(); // store curt.next when curt.child is not null

        while(curt != null) {
            if(curt.child != null) {
                stack.push(curt.next); // might be null
                curt.next = curt.child;
                if(curt.next != null) curt.next.prev = curt;
                curt.child = null;
            } else if(curt.next == null && !stack.isEmpty()) { // reach of tail of child, reconnet the next of parent
                curt.next = stack.pop();
                if(curt.next != null) curt.next.prev = curt;
            }

            curt = curt.next;
        }

        return head;
    }
}
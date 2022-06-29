package it.antanysavage.sm.player.sequences.model;

public class QueueLinkedList<T> {

    private int total;
    private Node first, last;
    private Node current;
    private Node playing;


    public QueueLinkedList() {
    }

    public QueueLinkedList<T> enqueue(T ele) {
        current = last;
        last = new Node();
        last.element = ele;
        

        if (total++ == 0) {
            first = last;
        } else {
            current.next = last;
        }
        
        last.next = first;

        return this;
    }

    public T dequeue() {
        if (total == 0) {
            throw new java.util.NoSuchElementException();
        }
        T ele = (T) first.element;
        first = first.next;
        if (--total == 0) {
            last = null;
        } 
        
        return ele;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node tmp = first;
        while (tmp != null) {
            sb.append(tmp.element).append(", ");
            tmp = tmp.next;
        }
        return sb.toString();
    }

    public Node getFirst() {
        return first;
    }

    public Node getCurrent() {
        return current;
    }

    public void setCurrent(Node current) {
        this.current = current;
    }

    int getTotal() {
        return total;
    }

    void empty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Node getPlaying() {    	
        return playing;
    }

    public Node getLast() {
        return last;
    }
    

    public void setPlaying(Node playing) {
        this.playing = playing;
    }

    boolean isEmpty() {
       return this.total == 0;
    }
  
}

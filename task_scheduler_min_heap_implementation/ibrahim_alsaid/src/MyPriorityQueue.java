/**
 * An interface for a priority queue data structure
 * @param <T> Type that extends Comparable interface
 */
interface MyPriorityQueue<T extends Comparable<T>> {
    /**
     * Add an element to the priority queue.
     * Time Complexity: O(log n) 
     * @param t the element to add
     */
    void add(T t);
    
    /**
     * Time Complexity: O(log n)
     * @return the highest priority element, or null if the queue is empty
     */
    T poll();
    
    /**
     * Time Complexity: O(1)
     * @return true if the queue is empty false otherwise
     */
    Boolean isEmpty();
}
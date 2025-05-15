/**
 * implementation of a min heap data structure that acts as a priority queue
 * the heap is implemented using an array-based representation of a binary tree
 * @param <T> Type that extends Comparable interface
 */
public class MyHeap<T extends Comparable<T>> implements MyPriorityQueue<T> {
    private T[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Time Complexity: O(1)
     */
    @SuppressWarnings("unchecked")
    public MyHeap() {
        heap = (T[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Add a new element to the heap and maintains the min heap property
     * Time Complexity: O(log n)
     * @param t the element to add to the heap
     */
    @Override
    public void add(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null element to the heap");
        }
        
        if (size == heap.length) {
            resize();
        }
        
        heap[size] = t;
        
        siftUp(size);
        
        size++;
    }

    /**
     * Time Complexity: O(log n)  
     * @return the minimum element in the heap, or null if the heap is empty
     */
    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        
        T min = heap[0];
        
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
                if (size > 0) {
            siftDown(0);
        }
        
        return min;
    }

    /**
     * Checks if the heap is empty
     * Time Complexity: O(1)
     * @return true if the heap is empty false otherwise
     */
    @Override
    public Boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Resizes the heap by doubling its capacity
     * Time Complexity: O(n) 
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newHeap = (T[]) new Comparable[heap.length * 2];
        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
    
    /**
     * Maintain the min heap property by sifting up an element
     * Time Complexity: O(log n)
     * @param index the index of the element to sift up
     */
    private void siftUp(int index) {
        int parent = (index - 1) / 2;
        
        if (index > 0 && heap[index].compareTo(heap[parent]) < 0) {
            swap(index, parent);
            siftUp(parent);
        }
    }
    
    /**
     * Maintain the min heap property by sifting down an element
     * Time Complexity: O(log n) 
     * @param index the index of the element to sift down
     */
    private void siftDown(int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        
        if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
            smallest = left;
        }
        
        if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }
        
        if (smallest != index) {
            swap(index, smallest);
            siftDown(smallest);
        }
    }
    
    /**
     * Swaps two elements in the heap
     * Time Complexity: O(1)
     * @param i the index of the first element
     * @param j the index of the second element
     */
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
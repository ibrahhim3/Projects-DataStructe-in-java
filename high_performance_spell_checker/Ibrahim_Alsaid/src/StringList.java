public class StringList {
    private String[] data;
    private int size;
    private int capacity;
    
    public StringList() {
        this.capacity = 10;
        this.data = new String[capacity];
        this.size = 0;
    }
    
    public void add(String item) {
        if (size >= capacity) {
            resize();
        }
        data[size++] = item;
    }
    
    public String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[index];
    }
    
    public int size() {
        return size;
    }
    
    public boolean contains(String item) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(item)) {
                return true;
            }
        }
        return false;
    }
    
    public String[] toArray() {
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = data[i];
        }
        return result;
    }
    
    private void resize() {
        capacity *= 2;
        String[] newData = new String[capacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
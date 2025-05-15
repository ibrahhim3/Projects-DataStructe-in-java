public class GTUHashMap<K, V> {
    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private int collisions;
    
    @SuppressWarnings("unchecked")
    public GTUHashMap() {
        this.capacity = 11;
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
        this.collisions = 0;
    }
    
    @SuppressWarnings("unchecked")
    public GTUHashMap(int initialCapacity) {
        this.capacity = nextPrime(initialCapacity);
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
        this.collisions = 0;
    }
    
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        if ((double) (size + 1) / capacity > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
        
        int hash = Math.abs(key.hashCode());
        int index = hash % capacity;
        int i = 0;
        
        while (true) {
            Entry<K, V> entry = table[index];
            
            if (entry == null || entry.isDeleted) {
                table[index] = new Entry<>(key, value);
                if (entry == null) {
                    size++;
                } else {
                    size++;
                    entry.isDeleted = false;
                }
                return;
            } else if (entry.key.equals(key)) {
                entry.value = value;
                return;
            } else {
               if (entry != null && !entry.isDeleted && !entry.key.equals(key)) {
    collisions++;
}
                i++;
                index = (hash + i*i) % capacity;
                
                if (i >= capacity) {
                    rehash();
                    put(key, value);
                    return;
                }
            }
        }
    }
    
    public V get(K key) {
        if (key == null) {
            return null;
        }
        
        int hash = Math.abs(key.hashCode());
        int index = hash % capacity;
        int i = 0;
        
        while (true) {
            Entry<K, V> entry = table[index];
            
            if (entry == null) {
                return null;
            }
            
            if (!entry.isDeleted && entry.key.equals(key)) {
                return entry.value;
            }
            
            i++;
            index = (hash + i*i) % capacity;
            
            if (i >= capacity) {
                return null;
            }
        }
    }
    
    public void remove(K key) {
        if (key == null) {
            return;
        }
        
        int hash = Math.abs(key.hashCode());
        int index = hash % capacity;
        int i = 0;
        
        while (true) {
            Entry<K, V> entry = table[index];
            
            if (entry == null) {
                return;
            }
            
            if (!entry.isDeleted && entry.key.equals(key)) {
                entry.isDeleted = true;
                size--;
                return;
            }
            
            i++;
            index = (hash + i*i) % capacity;
            
            if (i >= capacity) {
                return;
            }
        }
    }
    
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        
        int hash = Math.abs(key.hashCode());
        int index = hash % capacity;
        int i = 0;
        
        while (true) {
            Entry<K, V> entry = table[index];
            
            if (entry == null) {
                return false;
            }
            
            if (!entry.isDeleted && entry.key.equals(key)) {
                return true;
            }
            
            i++;
            index = (hash + i*i) % capacity;
            
            if (i >= capacity) {
                return false;
            }
        }
    }
    
    public int size() {
        return size;
    }
    
    @SuppressWarnings("unchecked")
    private void rehash() {
        int oldCapacity = capacity;
        Entry<K, V>[] oldTable = table;
        
        capacity = nextPrime(oldCapacity * 2);
        table = (Entry<K, V>[]) new Entry[capacity];
        size = 0;
        collisions = 0;
        
        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> entry = oldTable[i];
            if (entry != null && !entry.isDeleted) {
                put(entry.key, entry.value);
            }
        }
    }
    
    private int nextPrime(int n) {
        if (n <= 2) return 2;
        
        int start = (n % 2 == 0) ? n + 1 : n;
        
        for (int i = start; ; i += 2) {
            if (isPrime(i)) {
                return i;
            }
        }
    }
    
    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    public int getCollisionCount() {
        return collisions;
    }
    
    public long getMemoryUsage() {
        return (8L * capacity) + (16L * size);
    }
}
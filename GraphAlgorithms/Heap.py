class Entry:
    def __init__(self, value, weight):
        self.value = value
        self.weight = weight
    def getValue(self):
        return self.value
    def getWeight(self):
        return self.weight

class Heap:
    # Use an adjacency list representation
    # uses a key, which is the node id in an integer value
    # and also has a value, which is a Node object which contains a hashset of neighbors
    def __init__(self, l, weights):
        self.heap = [] # Array 
        self.mapping =  # map

        for i in range(0, len(l), 1):
            e = Entry(l[i], weights[i])
            heap.append(e)
            mapping[e.getValue()] = i
        for i in range(len(heap)/2, 0, -1, -1):
            self.minHeapify(i)
    
    def printHeap(self):
        for e in self.heap:
            print("key: " + e.getKey() + " value: " + e.getValue())
            print(mapping[e.getValue()])
        
    
    def size(self):
        return len(self.heap)

    def isEmpty(self):
        return self.heap == 0
    
    def getParent(self, pos):
        return heap[pos/2] # return parent at i/2 position
    
    def getRight(self, int pos)
        if((2*pos+2) >= len(self.heap.size())):
            return None
        return self.heap[(2*pos +2)] # make sure to do 2i+2 because case of 0 index

    def getLeft(self, int pos)
        if((2*pos+1) >= len(self.heap.size())):
            return None
        return self.heap[(2*pos +1)] # make sure to do 2i+2 because case of 0 index
    
    def minHeapify(self, pos):
        curr = self.heap[pos]
        left = self.getLeft(pos)
        right = self.getRight(pos)
        isRightSmallest = False
        smallest = curr.getKey()
        if(left != None and left.getKey() < curr.getKey()):
            smallest = left.getKey() # find smaller child
        if(right != None and right.getKey() < left.getKey()):
            smallest = right.getKey() # find smaller child
            isRightSmallest = True
        
        if(not(smallest is curr.getKey())): 
            if(isRightSmallest):
                # swaps parent with right
                temp = curr
                heap[pos] = right
                heap[2*pos + 2] = temp
                
                # update mappings
                mapping[temp.getValue()] = 2*pos+2
                mapping[right.getValue()] = pos
                self.minHeapify(2*pos + 2)
            else 
                # swaps parent with left
                Entry<V, Key> temp = curr
                heap.set(pos,left)
                heap.set(2*pos + 1, temp)            
                
                # update mappings
                mapping.put(temp.getValue(), 2*pos + 1)
                mapping.put(left.getValue(), pos)
                self.minHeapify(2*pos + 1)
            
    def isLeaf(self, i):
        if(i >= (self.heap.size()/2) and i < self.heap.size()):
            return True
        else 
            return False
        
    def getGraph(self):
        return self.nodes
    
    def getNode(self, nodeID):
        return self.nodes[nodeID]


    def containsValue(self, value):
        return (value in self.mapping)
    
    def add(self, value, key):
        if(key == None):
            return None
        e = Entry(value, key)
        self.heap.append(e)
        self.mapping[e.getValue()] = heap.size()-1
        index = heap.size()-1
        # while current key is smaller than parent
        while(self.heap[index].getKey() < self.heap[index/2].getKey()):
            # swap child with parent
            Entry<V, Key> temp = heap.get(index)
            #update mapping positions
            self.mapping[temp.getValue()] = index/2
            self.mapping[heap.get(index/2).getValue()] = index
            
            self.heap[index] = heap.get(index/2)
            self.heap[index/2] = temp
            index = index/2
    
    def decreaseKey(self, value, newKey):
        if(newKey == None):
            return None
        if(!mapping.containsKey(value)):
            return None
        e = self.heap[mapping.get(value)]
        if(e.getKey() < newKey): 
            return None
        e.setKey(newKey)
        # bubble up
        index = self.mapping[value]
        # while current key is greater than parent
        while(self.heap[index].getKey() < heap[index/2].getKey()):
            # swap child with parent
            Entry<V, Key> temp = heap.get(index)
            #update mapping positions
            mapping.put(temp.getValue(), index/2)
            mapping.put(heap.get(index/2).getValue(), index)
            
            heap.set(index, heap.get(index/2))
            heap.set(index/2, temp)
            index = index/2
        
    

    /**
     * @inheritDoc
     */
    @Override
    public V peek() 
        if(heap.isEmpty()) 
            throw new NoSuchElementException()
        
        return heap.get(0).getValue()
    

    /**
     * @inheritDoc
     */
    @Override
    public V extractMin() 
        if(heap.size() == 0) 
            throw new NoSuchElementException()
        
        Entry<V, Key> first = heap.remove(0)
        # put last element to the front
        if(heap.size() > 0) 
            Entry<V,Key> last = heap.remove(heap.size()-1)
            mapping.put(last.getValue(), 0)
            heap.add(0, last)
            minHeapify(0) # minheapify from the root
        
        if(heap.size() == 1) 
            mapping.put(heap.get(0).getValue(), 0)
        
        return first.getValue()
    

    /**
     * @inheritDoc
     */
    @Override
    public Set<V> values() 
        return Collections.unmodifiableSet(mapping.keySet())
    
    
    public HashMap<V, Integer> getKeys()
        return mapping
    
    public ArrayList<Entry<V,Key>> getArray()
        return heap




class BinaryMinHeapImpl<V, Key extends Comparable<Key>> implements BinaryMinHeap<V, Key> 
    ArrayList<Entry<V, Key>> heap
    HashMap<V, Integer> mapping
    
    # print function for debugging
    public void print() 
        for(Entry<V, Key> e : heap) 
            System.out.println("key: " + e.getKey() + " value: " + e.getValue())
            System.out.println(mapping.get(e.getValue()))
        
    
    
    /**
     * @inheritDoc
     */
    @Override
    public int size() 
        return heap.size()
    

    @Override
    public boolean isEmpty() 
        return heap.isEmpty()
    
    
    public Entry<V, Key> getParent(int pos) 
        return heap.get(pos/2) # return parent at i/2 position
    
    public Entry<V, Key> getRight(int pos) 
        if(2*pos+2 >= heap.size()) 
            return None
        
        return heap.get(2*pos +2) # make sure to do 2i+2 because case of 0 index
    
    
    public Entry<V, Key> getLeft(int pos) 
        if((2*pos+1) >= heap.size()) 
            return None
        
        return heap.get(2*pos+1) # make sure to do 2i+1 because case of 0 index
    
    
    public void minHeapify(int pos) 
        Entry<V, Key> curr = heap.get(pos)
        Entry<V, Key> left = getLeft(pos)
        Entry<V, Key> right = getRight(pos)
        boolean isRightSmallest = false
        Key smallest = curr.getKey()
        if(left != None && left.getKey().compareTo(curr.getKey()) < 0)
            smallest = left.getKey() # find smaller child
        
        if(right != None && right.getKey().compareTo(left.getKey()) < 0)
            smallest = right.getKey() # find smaller child
            isRightSmallest = true
        
        if(!smallest.equals(curr.getKey())) 
            if(isRightSmallest) 
                # swaps parent with right
                Entry<V, Key> temp = curr
                heap.set(pos,right)
                heap.set(2*pos + 2, temp)
                
                # update mappings
                mapping.put(temp.getValue(), 2*pos+2)
                mapping.put(right.getValue(), pos)
                minHeapify(2*pos + 2)
            else 
                # swaps parent with left
                Entry<V, Key> temp = curr
                heap.set(pos,left)
                heap.set(2*pos + 1, temp)            
                
                # update mappings
                mapping.put(temp.getValue(), 2*pos + 1)
                mapping.put(left.getValue(), pos)
                minHeapify(2*pos + 1)
            
        
    
    
    public boolean isLeaf(int i) 
        if(i >= (heap.size()/2) && i < heap.size()) 
            return true
        
        else 
            return false
        
    
    /**
     * @inheritDoc
     */
    @Override
    public boolean containsValue(V value) 
        return mapping.containsKey(value)
    

    /**
     * @inheritDoc
     * 
     */
    @Override
    public void add(V value, Key key) 
        if(key == None) 
            throw new IllegalArgumentException()
        
        Entry<V, Key> e = new Entry<V,Key>(value, key)
        heap.add(e)
        if(mapping.containsKey(value))
            throw new IllegalArgumentException()
        
        mapping.put(e.getValue(), heap.size()-1)
        int index = heap.size()-1
        
        # while current key is smaller than parent
        while(heap.get(index).getKey().compareTo(heap.get(index/2).getKey()) < 0) 
            # swap child with parent
            Entry<V, Key> temp = heap.get(index)
            #update mapping positions
            mapping.put(temp.getValue(), index/2)
            mapping.put(heap.get(index/2).getValue(), index)
            
            heap.set(index, heap.get(index/2))
            heap.set(index/2, temp)
            index = index/2
        
    

    /**
     * @inheritDoc
     */
    @Override
    public void decreaseKey(V value, Key newKey) 
        if(newKey == None) 
            throw new IllegalArgumentException()
        
        if(!mapping.containsKey(value)) 
            throw new NoSuchElementException()
        
        Entry<V, Key> e = heap.get(mapping.get(value))
        if(e.getKey().compareTo(newKey) < 0) 
            throw new IllegalArgumentException()
        
        e.setKey(newKey)
        
        # bubble up
        int index = mapping.get(value)
        # while current key is greater than parent
        
        while(heap.get(index).getKey().compareTo(heap.get(index/2).getKey()) < 0) 
            # swap child with parent
            Entry<V, Key> temp = heap.get(index)
            #update mapping positions
            mapping.put(temp.getValue(), index/2)
            mapping.put(heap.get(index/2).getValue(), index)
            
            heap.set(index, heap.get(index/2))
            heap.set(index/2, temp)
            index = index/2
        
    

    /**
     * @inheritDoc
     */
    @Override
    public V peek() 
        if(heap.isEmpty()) 
            throw new NoSuchElementException()
        
        return heap.get(0).getValue()
    

    /**
     * @inheritDoc
     */
    @Override
    public V extractMin() 
        if(heap.size() == 0) 
            throw new NoSuchElementException()
        
        Entry<V, Key> first = heap.remove(0)
        # put last element to the front
        if(heap.size() > 0) 
            Entry<V,Key> last = heap.remove(heap.size()-1)
            mapping.put(last.getValue(), 0)
            heap.add(0, last)
            minHeapify(0) # minheapify from the root
        
        if(heap.size() == 1) 
            mapping.put(heap.get(0).getValue(), 0)
        
        return first.getValue()
    

    /**
     * @inheritDoc
     */
    @Override
    public Set<V> values() 
        return Collections.unmodifiableSet(mapping.keySet())
    
    
    public HashMap<V, Integer> getKeys()
        return mapping
    
    public ArrayList<Entry<V,Key>> getArray()
        return heap
    


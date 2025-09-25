package dijkstra;

/** A priority queue represented by the min heap.
 *  Used in Dijkstra's algorithm to keep track of the "smallest" vertex (the vertex with the smallest "distance").
 *
 *  FILL IN CODE in insert and reduceKey. Other methods have been provided.
 *  */
public class MinHeap {
    private MinHeapElement[] heap; // heap array
    private int[] positions; // maps the vertex id to the index in the heap array
    private int maxsize; // max capacity
    private int size; // current size of min heap

    // An element of MinHeap, stores a vertexId and a distance
    private class MinHeapElement {
        int vertexId;
        double distance; //used as the priority

        public MinHeapElement(int vertexId, double distance) {
            this.vertexId = vertexId;
            this.distance = distance;
        }

        /**
         * In the minHeap, we will use the distance to decided which vertex is smallest
         * @param otherElem another element of the min heap
         * @return negative value if this element has a smaller distance than otherElem, 0 if distances are equal, and > 0 otherwise
         */
        public int compareTo(MinHeapElement otherElem) {
          if (Math.abs(this.distance - otherElem.distance) < 0.1) {
              return 0;
          }
          else
              if (this.distance < otherElem.distance)
                  return -1;
              else
                  return 1;
        }
    }


    public MinHeap(int maxsize) {
        heap = new MinHeapElement[maxsize + 1];
        positions = new int[maxsize];
        this.maxsize = maxsize;
        this.size = 0;
        heap[0] = new MinHeapElement(-1, Integer.MIN_VALUE);
    }

    /**
     * Insert a new MinHeapElement (vertexId, distance) to the minHeap
     * @param vertexId
     * @param distance
     */
    public void insert(int vertexId, double distance) {
        MinHeapElement node = new MinHeapElement(vertexId, distance);
        size++;
        heap[size] = node;
        positions[vertexId] = size;
        // FILL IN CODE: bubble up the node as needed, if it's less than the parent
        bubbleUp(size);
    }
    private void bubbleUp(int index) {
        while (index > 1) {
            int parent = index / 2;
            if (heap[index].distance < heap[parent].distance) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public boolean contains(int vertexId) {
        return positions[vertexId] != 0;
    }
    /**
     * Should be called when we update the distance ("priority") of a vertex to
     * a lower value. Need to bubble it up (by comparing with the parent, and updating the parent).
     * @param vertexId
     * @param newDistance // assume to be lower than the current distance for this vertex
     */
    public void reduceKey(int vertexId, double newDistance) {
        int pos = positions[vertexId];
        heap[pos].distance = newDistance;
        // Bubble up the heap as needed by repeatedly comparing with the parent
        bubbleUp(pos);

    }


    private int leftChild(int pos) {
        return 2 * pos;
    }

    private int rightChild(int pos) {
        return 2 * pos + 1;
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private boolean isLeaf(int pos) {
        return (pos > size / 2) && (pos <= size);
    }

    private void swap(int pos1, int pos2) {
        // We not only need to swap the vertex in the minHeap, but also need to update the new position information of
        // swapped vertices in the positions array
        int vertexId1 = heap[pos1].vertexId;
        int vertexId2 = heap[pos2].vertexId;
        positions[vertexId1] = pos2;
        positions[vertexId2] = pos1;

        // Swap two vertices in the minHeap
        MinHeapElement tmpNode;
        tmpNode = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = tmpNode;
    }

    /**
     * Removes the smallest element from the top of the min heap and rearranges the minheap
     * @return id of the vertex with the smallest distance.
     */
    public int removeMin() {
        swap(1, size);
        size--;

        if (size != 0) {
            pushdown(1);
        }
        int vertexId = heap[size + 1].vertexId;
        positions[vertexId] = 0; // no longer a valid entry
        return vertexId;
    }

    private void pushdown(int pos) {
        int newPos;
        while (!isLeaf(pos)) {
            newPos = leftChild(pos);
            if (newPos < size && heap[newPos].compareTo(heap[newPos + 1]) > 0) {
                newPos = newPos + 1;
            }
            if (heap[pos].compareTo(heap[newPos]) <= 0) {
                return;
            }
            swap(pos, newPos);
            pos = newPos;
        }
    }
}

package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Note: For more info on the expected behavior of your methods, see
 * the source code for IList.
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(T item) {
        insertAtEdge(this.size, item);
    }

    @Override
    public T remove() {
        if (this.size() == 0) {
            throw new EmptyContainerException();
        }
        return deleteAtEdge(this.size - 1);
    }

    @Override
    public T get(int index) {
        checkValidIndex(index);
        return getReference(index).data;
    }

    @Override
    public void set(int index, T item) {
        checkValidIndex(index);
        if (index == 0 || index == this.size() - 1) {
            deleteAtEdge(index);
            insertAtEdge(index, item);
        } else {
            Node<T> ref = getReference(index);
            deleteAtMiddle(ref.prev);
            insertAtMiddle(ref.prev, item);
        }
    }

    @Override
    public void insert(int index, T item) {
        if (index < 0 || index >= this.size() + 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0 || index == this.size()) {
            insertAtEdge(index, item);
        } else {
            insertAtMiddle(getReference(index - 1), item);
        }
    }

    @Override
    public T delete(int index) {
        checkValidIndex(index);
        if (index == 0 || index == this.size() - 1) {
            return deleteAtEdge(index);
        } else {
            return deleteAtMiddle(getReference(index - 1));
        }
    }

    @Override
    public int indexOf(T item) {
        Iterator<T> itr = this.iterator();
        int i = 0;
        while (itr.hasNext()) {
          T refer = itr.next();
          if ((refer == null && item == null) || (refer != null && refer.equals(item))) {
              return i;
          }
          i++;
        }
        return -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(T other) {
        return this.indexOf(other) != -1;
    }

    private Node<T> getReference(int index) {
        checkValidIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = front;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = back;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    // pre:  index == 0 or index == size
    // post: insert a new node next to the left of front or to the right of end
    //       (or both if the list is empty), update front and/or back, increase size
    private void insertAtEdge(int index, T item) {
        if (this.size == 0) {
            this.back = new Node<T>(item);
            this.front = this.back;
        } else if (index == this.size()) {
            back.next = new Node<T>(back, item, null);
            back = back.next;
        } else { // index == 0
            front.prev = new Node<T>(null, item, front);
            front = front.prev;
        }
        this.size++;
    }

    // pre:   ref != null and ref.next has index from 1 to size-2
    // post:  insert a new node at location ref.next
    private void insertAtMiddle(Node<T> ref, T item) {
        ref.next = new Node<T>(ref, item, ref.next);
        ref.next.next.prev = ref.next;
        this.size++;
    }

    // pre:  index == 0 or index == size-1
    // post: delete node at front or end (or both if size==1), decrease size by 1
    private T deleteAtEdge(int index) {
        T result;
        if (this.size == 1) {
            result = this.front.data;
            this.front = null;
            this.back = null;
        } else if (index == this.size() - 1) {
            result = this.back.data;
            this.back = back.prev;
            this.back.next.prev = null;
            this.back.next = null;
        } else { // index == 0
            result = this.front.data;
            this.front = front.next;
            this.front.prev.next = null;
            this.front.prev = null;
        }
        this.size--;
        return result;
    }

    // pre:  ref != null and ref.next has a index from 1 to size-2
    // post: delete the node at ref.next, decrease size by 1
    private T deleteAtMiddle(Node<T> ref) {
        Node<T> toDelete = ref.next;
        T answer = toDelete.data;
        ref.next = toDelete.next;
        toDelete.next.prev = ref;
        this.size--;
        return answer;
    }

    private void checkValidIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }

    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }

        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {
            return this.current != null;
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public T next() {
          if (!hasNext()) {
              throw new NoSuchElementException();
          }
          T result = current.data;
          this.current = current.next;
          return result;
        }
    }
}

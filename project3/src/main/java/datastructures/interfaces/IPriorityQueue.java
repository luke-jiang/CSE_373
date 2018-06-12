package datastructures.interfaces;

import misc.exceptions.EmptyContainerException;

/**
 * Represents a queue where the elements are ordered such that the
 * front element is always the "smallest", as defined by the
 * element's compareTo method.
 */
public interface IPriorityQueue<T extends Comparable<T>> {
    /**
     * Removes and return the smallest element in the queue.
     *
     * If two elements within the queue are considered "equal"
     * according to their compareTo method, this method may break
     * the tie arbitrarily and return either one.
     *
     * @throws EmptyContainerException  if the queue is empty
     */
    T removeMin();

    /**
     * Returns, but does not remove, the smallest element in the queue.
     *
     * This method must break ties in the same way the removeMin
     * method breaks ties.
     *
     * @throws EmptyContainerException  if the queue is empty
     */
    T peekMin();

    /**
     * Inserts the given item into the queue.
     *
     * @throws IllegalArgumentException  if the item is null
     */
    void insert(T item);

    /**
     * Returns the number of elements contained within this queue.
     */
    int size();

    /**
     * Returns 'true' if this queue is empty, and false otherwise.
     */
    default boolean isEmpty() {
        return this.size() == 0;
    }
}

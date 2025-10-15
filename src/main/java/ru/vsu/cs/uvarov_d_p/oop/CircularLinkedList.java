package ru.vsu.cs.uvarov_d_p.oop;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularLinkedList<T> implements Iterable<T> {

    private Node<T> head = null;
    private int count = 0;

    public void checkEmpty() throws SimpleLinkedListException {
        if (isEmpty()) {
            throw new SimpleLinkedListException("List is empty");
        }
    }

    public Node<T> getTail() {
        if (head == null) return null;
        return head.prev;
    }

    public T getFirst() throws SimpleLinkedListException {
        checkEmpty();
        return head.value;
    }

    public T getLast() throws SimpleLinkedListException {
        checkEmpty();
        return getTail().value;
    }

    public Node<T> getItem(int index) throws SimpleLinkedListException {
        if (index < 0 || index >= count) {
            throw new SimpleLinkedListException("Incorrect index");
        }

        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    public T get(int index) throws SimpleLinkedListException {
        return getItem(index).value;
    }

    public void addFirst(T value) {
        Node<T> newItem = new Node<>(value);

        if (isEmpty()) {
            newItem.next = newItem;
            newItem.prev = newItem;
            head = newItem;
        } else {
            Node<T> tail = getTail();
            newItem.next = head;
            newItem.prev = tail;
            head.prev = newItem;
            tail.next = newItem;
            head = newItem;
        }
        count++;
    }

    public void addLast(T value) {
        if (isEmpty()) {
            addFirst(value);
        } else {
            Node<T> tail = getTail();
            Node<T> newItem = new Node<>(value, head, tail);
            tail.next = newItem;
            head.prev = newItem;
            count++;
        }
    }

    public void insert(int index, T value) throws SimpleLinkedListException {
        if (index < 0 || index > count) {
            throw new SimpleLinkedListException("Incorrect index");
        }
        if (index == 0) {
            addFirst(value);
        } else if (index == count) {
            addLast(value);
        } else {
            Node<T> nextItem = getItem(index);
            Node<T> prevItem = nextItem.prev;
            Node<T> newItem = new Node<>(value, nextItem, prevItem);
            prevItem.next = newItem;
            nextItem.prev = newItem;
            count++;
        }
    }

    public T removeFirst() throws SimpleLinkedListException {
        checkEmpty();

        T value = head.value;

        if (count == 1) {
            head = null;
        } else {
            Node<T> tail = getTail();
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }

        count--;
        return value;
    }

    public T removeLast() throws SimpleLinkedListException {
        checkEmpty();

        if (count == 1) {
            return removeFirst();
        }

        Node<T> tail = getTail();
        T value = tail.value;

        Node<T> newTail = tail.prev;
        newTail.next = head;
        head.prev = newTail;

        count--;
        return value;
    }

    public T remove(int index) throws SimpleLinkedListException {
        if (index < 0 || index >= count) {
            throw new SimpleLinkedListException("Incorrect index");
        }
        if (index == 0) {
            return removeFirst();
        } else if (index == count - 1) {
            return removeLast();
        } else {
            Node<T> item = getItem(index);
            item.prev.next = item.next;
            item.next.prev = item.prev;
            count--;
            return item.value;
        }
    }

    public boolean contains(T value) {
        if (isEmpty()) return false;

        Node<T> current = head;
        for (int i = 0; i < count; i++) {
            if (value == null ? current.value == null : value.equals(current.value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void clear() {
        if (head != null) {
            Node<T> current = head;
            for (int i = 0; i < count; i++) {
                Node<T> next = current.next;
                current.next = null;
                current.prev = null;
                current = next;
            }
            head = null;
        }
        count = 0;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            private int position = 0;

            @Override
            public boolean hasNext() {
                return position < count;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.next;
                position++;
                return value;
            }
        };
    }

    public Iterable<T> circularIterator() {
        return () -> new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return !isEmpty();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
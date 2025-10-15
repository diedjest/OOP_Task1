package ru.vsu.cs.uvarov_d_p.oop.linked_list;

import java.util.Iterator;

public class DoublyLinkedList<T> implements Iterable<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int count = 0;

    private void checkEmpty() throws SimpleLinkedListException {
        if (isEmpty()) {
            throw new SimpleLinkedListException("List is empty");
        }
    }

    public T getFirst() throws SimpleLinkedListException {
        checkEmpty();
        return head.value;
    }

    public T getLast() throws SimpleLinkedListException {
        checkEmpty();
        return tail.value;
    }

    private Node<T> getItem(int index) throws SimpleLinkedListException {
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
        Node<T> newItem = new Node<>(value, head, null);
        if (head != null) {
            head.prev = newItem;
        }
        head = newItem;
        if (tail == null) {
            tail = head;
        }
        count++;
    }

    public void addLast(T value) {
        Node<T> newItem = new Node<>(value, null, tail);
        if (tail != null) {
            tail.next = newItem;
        }
        tail = newItem;
        if (head == null) {
            head = tail;
        }
        count++;
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
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        count--;
        return value;
    }

    public T removeLast() throws SimpleLinkedListException {
        checkEmpty();
        T value = tail.value;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        count--;
        return value;
    }

    public T remove(int index) throws SimpleLinkedListException {
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
        Node<T> current = head;
        while (current != null) {
            if (value == null ? current.value == null : value.equals(current.value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void clear() {
        head = tail = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T value = curr.value;
                curr = curr.next;
                return value;
            }
        };
    }
}


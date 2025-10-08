package ru.vsu.cs.uvarov_d_p.oop;

public class Node<T> {
    T value;
    Node<T> next;
    Node<T> prev;

    public Node(T value, Node<T> next, Node<T> prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

    public Node(T value) {
        this(value, null, null);;
    }
}

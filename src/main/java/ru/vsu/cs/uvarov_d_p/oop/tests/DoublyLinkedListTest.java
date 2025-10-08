package ru.vsu.cs.uvarov_d_p.oop.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {

    @Test
    public void testEmptyList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddFirst() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);

        assertEquals(2, list.size());
        assertEquals(2, list.getFirst());
        assertEquals(1, list.getLast());
    }

    @Test
    public void testAddLast() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);

        assertEquals(2, list.size());
        assertEquals(1, list.getFirst());
        assertEquals(2, list.getLast());
    }

    @Test
    public void testInsert() throws SimpleLinkedListException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);
        list.addLast(3);
        list.insert(1, 2);

        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testRemoveFirst() throws SimpleLinkedListException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, list.removeFirst());
        assertEquals(2, list.size());
        assertEquals(2, list.getFirst());
    }

    @Test
    public void testRemoveLast() throws SimpleLinkedListException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(3, list.removeLast());
        assertEquals(2, list.size());
        assertEquals(2, list.getLast());
    }

    @Test
    public void testRemoveByIndex() throws SimpleLinkedListException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(2, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    public void testGetByIndex() throws SimpleLinkedListException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    public void testContains() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("apple");
        list.addLast("banana");
        list.addLast("orange");

        assertTrue(list.contains("banana"));
        assertFalse(list.contains("grape"));
        assertTrue(list.contains("apple"));
    }

    @Test
    public void testClear() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testIterator() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        int sum = 0;
        for (Integer value : list) {
            sum += value;
        }

        assertEquals(6, sum);
    }

    @Test
    public void testEmptyListExceptions() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        assertThrows(SimpleLinkedListException.class, list::getFirst);
        assertThrows(SimpleLinkedListException.class, list::getLast);
        assertThrows(SimpleLinkedListException.class, () -> list.get(0));
        assertThrows(SimpleLinkedListException.class, list::removeFirst);
        assertThrows(SimpleLinkedListException.class, list::removeLast);
    }

    @Test
    public void testInvalidIndexExceptions() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);

        assertThrows(SimpleLinkedListException.class, () -> list.get(-1));
        assertThrows(SimpleLinkedListException.class, () -> list.get(1));
        assertThrows(SimpleLinkedListException.class, () -> list.insert(-1, 0));
        assertThrows(SimpleLinkedListException.class, () -> list.insert(2, 0));
        assertThrows(SimpleLinkedListException.class, () -> list.remove(-1));
        assertThrows(SimpleLinkedListException.class, () -> list.remove(1));
    }

    @Test
    public void testSingleElementList() throws SimpleLinkedListException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addFirst(42);

        assertEquals(1, list.size());
        assertEquals(42, list.getFirst());
        assertEquals(42, list.getLast());
        assertEquals(42, list.get(0));
        assertTrue(list.contains(42));

        assertEquals(42, list.removeFirst());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testMultipleOperations() throws SimpleLinkedListException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        // Добавляем элементы
        for (int i = 0; i < 5; i++) {
            list.addLast(i);
        }

        assertEquals(5, list.size());
        assertEquals(0, list.getFirst());
        assertEquals(4, list.getLast());

        // Удаляем из середины
        assertEquals(2, list.remove(2));
        assertEquals(4, list.size());

        // Добавляем в начало
        list.addFirst(-1);
        assertEquals(-1, list.getFirst());

        // Очищаем
        list.clear();
        assertTrue(list.isEmpty());
    }
}

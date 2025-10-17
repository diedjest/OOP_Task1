package ru.vsu.cs.uvarov_d_p.oop.tests;

import org.junit.Test;
import ru.vsu.cs.uvarov_d_p.oop.linked_list.CircularLinkedList;
import ru.vsu.cs.uvarov_d_p.oop.linked_list.Node;
import ru.vsu.cs.uvarov_d_p.oop.linked_list.SimpleLinkedListException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CircularLinkedListTest {

    @Test
    public void testEmptyList() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddFirst() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);

        assertEquals(2, list.size());
        assertEquals(2, (int) list.getFirst());
        assertEquals(1, (int) list.getLast());
    }

    @Test
    public void testAddLast() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);
        list.addLast(2);

        assertEquals(2, list.size());
        assertEquals(1,(int) list.getFirst());
        assertEquals(2, (int) list.getLast());
    }

    @Test
    public void testCircularStructure() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, (int) list.getFirst());
        assertEquals(3, (int) list.getLast());

        Node<Integer> lastNode = list.getItem(2);
        assertEquals(list.getFirst(), lastNode.next.value);

        Node<Integer> firstNode = list.getItem(0);
        assertEquals(list.getLast(), firstNode.prev.value);
    }

    @Test
    public void testInsert() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);
        list.addLast(3);
        list.insert(1, 2);

        assertEquals(3, list.size());
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(3, (int) list.get(2));
    }

    @Test
    public void testRemoveFirst() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, (int) list.removeFirst());
        assertEquals(2, list.size());
        assertEquals(2, (int) list.getFirst());
        assertEquals(3, (int) list.getLast());

        assertEquals(list.getFirst(), list.getItem(1).prev.value);
        assertEquals(list.getLast(), list.getItem(0).next.value);
    }

    @Test
    public void testRemoveLast() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(3, (int) list.removeLast());
        assertEquals(2, list.size());
        assertEquals(1, (int) list.getFirst());
        assertEquals(2, (int) list.getLast());

        assertEquals(list.getFirst(), list.getItem(1).prev.value);
        assertEquals(list.getLast(), list.getItem(0).next.value);
    }

    @Test
    public void testRemoveByIndex() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(2, (int) list.remove(1));
        assertEquals(2, list.size());
        assertEquals(1, (int) list.get(0));
        assertEquals(3, (int) list.get(1));
    }

    @Test
    public void testSingleElementCircular() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addFirst(42);

        assertEquals(1, list.size());
        assertEquals(42, (int) list.getFirst());
        assertEquals(42, (int) list.getLast());

        Node<Integer> node = list.getItem(0);
        assertEquals(node, node.next);
        assertEquals(node, node.prev);

        assertEquals(42, (int) list.removeFirst());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testContains() {
        CircularLinkedList<String> list = new CircularLinkedList<>();
        list.addLast("apple");
        list.addLast("banana");
        list.addLast("orange");

        assertTrue(list.contains("banana"));
        assertFalse(list.contains("grape"));
        assertTrue(list.contains("apple"));
    }

    @Test
    public void testClear() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testIterator() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
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
    public void testCircularIterator() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);
        list.addLast(2);

        int count = 0;
        int sum = 0;
        for (Integer value : list.circularIterator()) {
            sum += value;
            count++;
            if (count >= 6) break;
        }

        assertEquals(9, sum);
        assertEquals(6, count);
    }

    @Test
    public void testEmptyListExceptions() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();

        assertThrows(SimpleLinkedListException.class, list::getFirst);
        assertThrows(SimpleLinkedListException.class, list::getLast);
        assertThrows(SimpleLinkedListException.class, () -> list.get(0));
        assertThrows(SimpleLinkedListException.class, list::removeFirst);
        assertThrows(SimpleLinkedListException.class, list::removeLast);
    }

    @Test
    public void testInvalidIndexExceptions() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.addLast(1);

        assertThrows(SimpleLinkedListException.class, () -> list.get(-1));
        assertThrows(SimpleLinkedListException.class, () -> list.get(1));
        assertThrows(SimpleLinkedListException.class, () -> list.insert(-1, 0));
        assertThrows(SimpleLinkedListException.class, () -> list.insert(2, 0));
        assertThrows(SimpleLinkedListException.class, () -> list.remove(-1));
        assertThrows(SimpleLinkedListException.class, () -> list.remove(1));
    }

    @Test
    public void testComplexScenario() throws SimpleLinkedListException {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();

        for (int i = 0; i < 5; i++) {
            list.addLast(i);
        }

        assertEquals(5, list.size());
        assertEquals(0, (int) list.getFirst());
        assertEquals(4, (int) list.getLast());

        assertEquals(2, (int) list.remove(2));
        assertEquals(4, list.size());

        list.addFirst(-1);
        assertEquals(-1, (int) list.getFirst());

        assertEquals(list.getFirst(), list.getItem(4).next.value);
        assertEquals(list.getLast(), list.getItem(0).prev.value);

        list.clear();
        assertTrue(list.isEmpty());
    }
}

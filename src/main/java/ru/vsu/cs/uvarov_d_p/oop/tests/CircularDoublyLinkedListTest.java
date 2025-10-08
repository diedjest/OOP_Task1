package ru.vsu.cs.uvarov_d_p.oop.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CircularDoublyLinkedListTest {

    @Test
    public void testEmptyList() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddFirst() throws SimpleLinkedListException {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);

        assertEquals(2, list.size());
        assertEquals(2, list.getFirst());
        assertEquals(1, list.getLast());
    }

    @Test
    public void testAddLast() throws SimpleLinkedListException {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);

        assertEquals(2, list.size());
        assertEquals(1, list.getFirst());
        assertEquals(2, list.getLast());
    }

    @Test
    public void testCircularStructure() throws SimpleLinkedListException {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        // Проверяем, что список действительно циклический
        assertEquals(1, list.getFirst());
        assertEquals(3, list.getLast());

        // После последнего элемента должен быть первый
        Node<Integer> lastNode = list.getItem(2);
        assertEquals(list.getFirst(), lastNode.next.value);

        // Перед первым элементом должен быть последний
        Node<Integer> firstNode = list.getItem(0);
        assertEquals(list.getLast(), firstNode.prev.value);
    }

    @Test
    public void testInsert() throws SimpleLinkedListException {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
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
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, list.removeFirst());
        assertEquals(2, list.size());
        assertEquals(2, list.getFirst());
        assertEquals(3, list.getLast());

        // Проверяем циклическую структуру после удаления
        assertEquals(list.getFirst(), list.getItem(1).prev.value);
        assertEquals(list.getLast(), list.getItem(0).next.value);
    }

    @Test
    public void testRemoveLast() throws SimpleLinkedListException {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(3, list.removeLast());
        assertEquals(2, list.size());
        assertEquals(1, list.getFirst());
        assertEquals(2, list.getLast());

        // Проверяем циклическую структуру после удаления
        assertEquals(list.getFirst(), list.getItem(1).prev.value);
        assertEquals(list.getLast(), list.getItem(0).next.value);
    }

    @Test
    public void testRemoveByIndex() throws SimpleLinkedListException {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(2, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    public void testSingleElementCircular() throws SimpleLinkedListException {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addFirst(42);

        assertEquals(1, list.size());
        assertEquals(42, list.getFirst());
        assertEquals(42, list.getLast());

        // Единственный элемент должен ссылаться сам на себя
        Node<Integer> node = list.getItem(0);
        assertEquals(node, node.next);
        assertEquals(node, node.prev);

        assertEquals(42, list.removeFirst());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testContains() {
        CircularDoublyLinkedList<String> list = new CircularDoublyLinkedList<>();
        list.addLast("apple");
        list.addLast("banana");
        list.addLast("orange");

        assertTrue(list.contains("banana"));
        assertFalse(list.contains("grape"));
        assertTrue(list.contains("apple"));
    }

    @Test
    public void testClear() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testIterator() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
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
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);

        // Тестируем циклический итератор (ограничиваем количество итераций)
        int count = 0;
        int sum = 0;
        for (Integer value : list.circularIterator()) {
            sum += value;
            count++;
            if (count >= 6) break; // 3 полных цикла
        }

        assertEquals(9, sum); // (1+2) * 3 = 9
        assertEquals(6, count);
    }

    @Test
    public void testDescendingIterator() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        StringBuilder result = new StringBuilder();
        for (Integer value : list.descendingIterator()) {
            result.append(value);
        }

        assertEquals("321", result.toString());
    }

    @Test
    public void testEmptyListExceptions() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();

        assertThrows(SimpleLinkedListException.class, list::getFirst);
        assertThrows(SimpleLinkedListException.class, list::getLast);
        assertThrows(SimpleLinkedListException.class, () -> list.get(0));
        assertThrows(SimpleLinkedListException.class, list::removeFirst);
        assertThrows(SimpleLinkedListException.class, list::removeLast);
    }

    @Test
    public void testInvalidIndexExceptions() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
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
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();

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

        // Проверяем циклическую структуру
        assertEquals(list.getFirst(), list.getItem(3).next.value);
        assertEquals(list.getLast(), list.getItem(0).prev.value);

        // Очищаем
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testToString() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        assertEquals("[]", list.toString());

        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals("[1, 2, 3]", list.toString());
    }
}

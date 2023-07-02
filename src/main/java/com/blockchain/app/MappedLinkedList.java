package com.blockchain.app;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MappedLinkedList<T> {

    private Map<T, ListIterator<T>> map;
    private List<T> list;

    MappedLinkedList() {
        map = new HashMap<>();
        list = new LinkedList<>();
    }

    MappedLinkedList(MappedLinkedList<T> mappedLinkedList) {
        map = new HashMap<>();
        list = new LinkedList<>();

        for (T entry : mappedLinkedList.list) {
            list.add(entry);
            map.put(entry, list.listIterator(list.size() - 1));
        }
    }

    public void insert(T element) {
        list.add(element);
        map.put(element, list.listIterator(list.size() - 1));
    }

    public ListIterator<T> searchInList(T element) {
        return map.get(element);
    }

    public T get(int i) {
        return list.get(i);
    }

    public T getLast() {
        return this.get(this.size() - 1);
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(T entry : this.list) {
            sb.append(entry);
            sb.append("\n");
        }

        return sb.toString();
    }
}

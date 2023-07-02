package com.blockchain.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class MappedLinkedList_Test {
    @Test
    public void shouldCreateMappedLinkedList()
    {
        MappedLinkedList<Integer> m = new MappedLinkedList<>();

        m.insert(4);

        m.insert(0);

        m.insert(12);

        String expectedString = "4\n0\n12\n";

        assertTrue(m.toString().equals(expectedString));
    }

    @Test
    public void shouldFindWhenSearching()
    {
        MappedLinkedList<Integer> m = new MappedLinkedList<>();

        m.insert(4);

        m.insert(0);

        m.insert(12);

        System.out.println(m.searchInList(0));

        assertTrue(!m.searchInList(0).equals(null));
    }
}

package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    private Entry<String> root;
    private List <Entry<String>> entries = new ArrayList<>();

    public CustomTree() {
        root = new Entry<>("0");
        entries.add(root);
    }

    @Override
    public boolean add(String s) {
        Entry<String> entry = new Entry<>(s);
        if (!enumeration(entry)){
            for (Entry<String> e:entries) {
                if (e.leftChild == null && e.rightChild == null){
                    e.availableToAddLeftChildren = true;
                    e.availableToAddRightChildren = true;
                }
            }
            enumeration(entry);
        }

        return true;

    }

    private boolean enumeration(Entry<String> entry) {
        for (Entry<String> e:entries){
            if (e.isAvailableToAddChildren()){
                entries.add(entry);
                entry.parent = e;
                if (e.availableToAddLeftChildren == true){
                    e.leftChild = entry;
                    e.availableToAddLeftChildren = false;
                    return true;

                }else {
                    e.rightChild = entry;
                    e.availableToAddRightChildren = false;
                    return true;

                }
            }
        }
        return false;
    }

    public String getParent(String s){
        for (Entry<String> e:entries) {
            if (e.elementName.equals(s)){
                return e.parent.elementName;
            }
        }
        return null;
    }

    public boolean remove(Object o){
        if (!(o instanceof String)){
            throw new UnsupportedOperationException();
        }
        String name = (String) o;

        for (Entry<String> e:entries) {
            if (e.elementName.equals(name)){
                removeChild(e);
                break;
            }
        }

        Iterator<Entry<String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            if(iterator.next().delete){
                iterator.remove();
            }
        }

        return true;

    }

    private void removeChild(Entry<String> entry) {
      //  entry.parent.notChild = true;

        while (true) {
            if (entry.availableToAddLeftChildren && entry.availableToAddRightChildren){
                entry.delete = true;
                break;
            }


            if (!entry.availableToAddRightChildren) {
                entry.availableToAddRightChildren = true;
                removeChild(entry.rightChild);

            }

            if (!entry.availableToAddLeftChildren) {
                entry.availableToAddLeftChildren = true;
                removeChild(entry.leftChild);

            }
        }
        if (entry.parent.leftChild == entry){
            entry.parent.leftChild = null;
        }else{
            entry.parent.rightChild = null;
        }



    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return entries.size() - 1;
    }



    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    static class Entry<T> implements Serializable{
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        boolean delete;
        boolean notChild;
        Entry<T> parent, leftChild, rightChild;


        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        boolean isAvailableToAddChildren(){
            return this.availableToAddRightChildren || this.availableToAddLeftChildren;
        }


    }
}

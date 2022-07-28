package com;

import com.model.Product;

import java.time.LocalDateTime;
import java.util.Stack;

public class BinaryTree<T extends Product> {


    private double price = 0.0;
    private int size;

    private Stack<T> treeStack;
    BinaryTree.Node<T> root;
    BinaryTree.Node<T> current;

    private static class Node<T> {

        Product item;
        BinaryTree.Node<T> left;
        BinaryTree.Node<T> right;

        LocalDateTime time;
        int version;

        Node(BinaryTree.Node<T> left, Product element, BinaryTree.Node<T> right, LocalDateTime time, int version) {
            this.item = element;
            this.left = right;
            this.right = left;
            this.time = time;
            this.version = version;
        }
    }

    public void add(T e, int version) {
        LocalDateTime time = LocalDateTime.now();
        ProductComporator comporator = new ProductComporator();
        if (root == null) {
            root = new Node<>(null, e, null, time, version);
            current = root;
            size++;
            return;
        }
        if (comporator.compare(e, current.item) > 0) {
            if (current.right == null) {
                current.right = new Node<>(null, e, null, time, version);
                size++;
                current = root;
                return;
            } else {
                current = current.right;
                add(e, version);
            }

        } else {
            if (current.left == null) {
                current.left = new Node<>(null, e, null, time, version);
                current = root;
                size++;
                return;
            } else {
                current = current.left;
                add(e, version);
            }
        }
    }

    public double countLeftNodes() {
        price = 0.0;
        count(root.left);
        return price;
    }

    public double countRightNodes() {
        price = 0.0;
        count(root.right);
        return price;
    }

    private void count(Node<T> t) {
        if (t == null) {
            return;
        }
        price += t.item.getPrice();
        count(t.right);
        count(t.left);
    }

    public void printNodes() {
        Stack<T> treeStack = new Stack<>();
        count(root);
    }
}

package com;

import com.model.Product;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Stack;

public class BinaryTree<T extends Product> {


    private double price = 0.0;
    private int size;

    private BinaryTree.Node<T> root;
    private BinaryTree.Node<T> current;

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
    private void count(Stack<Node<T>> stack,Node<T> t) {
        if (t == null) {
            return;
        }
        stack.add(t);
        count(stack,t.right);
        count(stack,t.left);
    }

    public void printNodes() {
        Stack<Node<T>> tree = new Stack<>();
        count(tree,root);

        int left = 20;
        int n = 3;
        int right = 40;
        int i = 1;
        System.out.println(StringUtils.repeat(" ", 30) + tree.get(0).item.getPrice());
        while(i<tree.size()) {
            current = tree.get(i);
            Node<T> previous = tree.get(i-1);
            if(tree.get(i).item.getPrice() > root.item.getPrice()) {
                System.out.println(StringUtils.repeat(" ", right) + tree.get(i).item.getPrice());
            }
            else  {
                if(current.item.getPrice() > previous.item.getPrice()) {
                    System.out.println(StringUtils.repeat(" ", left + n) + tree.get(i).item.getPrice());
                }
                else {
                    System.out.println(StringUtils.repeat(" ", left-n) + tree.get(i).item.getPrice());
                }
                n += 2;
            }

            i++;
        }

    }
}

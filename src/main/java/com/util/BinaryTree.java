package com.util;

import com.model.Product;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Stack;

public class BinaryTree<T extends Product> {


    private double price = 0.0;
    private int size;

    private int nodeDepth = 1;

    private BinaryTree.Node<T> root;
    private BinaryTree.Node<T> current;

    private static class Node<T> {

        Product item;
        BinaryTree.Node<T> left;
        BinaryTree.Node<T> right;

        LocalDateTime time;
        int version;

        int depth;

        Node(BinaryTree.Node<T> left, Product element, BinaryTree.Node<T> right, LocalDateTime time, int version,int depth) {
            this.item = element;
            this.left = right;
            this.right = left;
            this.time = time;
            this.version = version;
            this.depth = depth;
        }
    }

    public void add(T e, int version) {
        LocalDateTime time = LocalDateTime.now();
        ProductComporator comporator = new ProductComporator();
        if (root == null) {
            root = new Node<>(null, e, null, time, version,0);
            current = root;
            size++;
            return;
        }
        if (comporator.compare(e, current.item) > 0) {
            if (current.right == null) {
                current.right = new Node<>(null, e, null, time, version,nodeDepth);
                size++;
                current = root;
                nodeDepth = 1;
                return;
            } else {
                current = current.right;
                nodeDepth++;
                add(e, version);
            }

        } else {
            if (current.left == null) {
                current.left = new Node<>(null, e, null, time, version,nodeDepth);
                current = root;
                size++;
                nodeDepth = 1;
                return;
            } else {
                current = current.left;
                nodeDepth++;
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

        int height = 10;
        int n = 0;
        int right = 15;
        int maxDepth = 0;
        int currentDepth = 0;

        for(int i = 0;i<tree.size();i++) {
            if(tree.get(i).depth > maxDepth) {
                maxDepth = tree.get(i).depth;
            }
        }

        System.out.print(StringUtils.repeat(" ", height+5) + root.item.getPrice());
        while (currentDepth < maxDepth) {
            System.out.print(StringUtils.repeat(" ", height));
            for(int i = tree.size() - 1;i>0;i--) {
                if(currentDepth == tree.get(i).depth) {
                    System.out.print(tree.get(i).item.getPrice() + StringUtils.repeat(" ", right));
                }
            }
            height -= 3;
            right -= 2;
            System.out.println();
            currentDepth++;
        }
    }
}

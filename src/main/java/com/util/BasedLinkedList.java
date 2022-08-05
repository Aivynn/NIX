package com.util;


import com.model.Product;
import com.service.PhoneService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
public class BasedLinkedList<E extends Product> implements Iterable<Product> {


    Set<Integer> versions = new HashSet<>();
    Date date = new Date();

    private int nextIndex;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    private BasedLinkedList.Node<E> first;
    private BasedLinkedList.Node<E> last;

    int size = 0;

    void linkLast(E e, int version) {
        LocalDateTime time = LocalDateTime.now();
        final BasedLinkedList.Node<E> l = last;
        final BasedLinkedList.Node<E> newNode = new BasedLinkedList.Node<E>(l, e, null,time,version);
        last = newNode;
        if (l == null) {
            first = newNode;
            LOGGER.info("Product was created");

        } else {
            l.next = newNode;
            LOGGER.info("Product was created");
        }
        size++;
    }

    @Override
    public Iterator<Product> iterator() {
        Node<E> current = first;
        Iterator<Product> it = new Iterator<Product>() {

            @Override
            public boolean hasNext() {
                if(nextIndex < size) {
                    return true;
                }
                else {
                    nextIndex = 0;
                    return false;
                }
            }

            @Override
            public Product next() {
                nextIndex++;
                return current.next.item;
            }
        };
        return it;
    }

    private static class Node<E> {

        Product item;
        BasedLinkedList.Node<E> next;
        BasedLinkedList.Node<E> prev;

        LocalDateTime time;
        int version;
        Node(BasedLinkedList.Node<E> prev, Product element, BasedLinkedList.Node<E> next, LocalDateTime time, int version) {
            this.item = element;
            this.next = next;
            this.prev = prev;
            this.time = time;
            this.version = version;

        }
    }

    public boolean add(E e,int version) {
        versions.add(version);
        linkLast(e,version);
        return true;
    }

    public Node<E> findByVersion(int version) {
        Node<E> current = first;
        int i = 0;
        while (i < size) {
            if(current.version == version) {
                System.out.println(current.item);
                return current;
            }
            i++;
            current = current.next;
        }
        throw new IllegalArgumentException("No such version,try again");
    }
    public void updatePriceByVersion(int version,int price) {
        Product product = findByVersion(version).item;
        System.out.println(product.getPrice());
        product.setPrice((double) price);
        System.out.println(product.getPrice());
    }
    public void showAllVersions() {
        System.out.println(versions);
    }

    public boolean deleteByVersion(int version) {
        Node<E> current = findByVersion(version);
        if(current.equals(first)) {
            versions.remove(version);
            first = current.next;
            first.prev = null;
            size--;
            return true;
        }
        if(current.equals(last)) {
            versions.remove(version);
            last = current.prev;
            last.next = null;
            size--;
            return true;
        }
        Node<E> nodePrev = findByVersion(version).prev;
        Node<E> nodeNext = findByVersion(version).next;

        nodePrev.next = nodeNext;
        nodeNext.prev = nodePrev;
        size--;
        return true;
    }

    public void addToHead(Product p, int version) {
        LocalDateTime time = LocalDateTime.now();
        final BasedLinkedList.Node<E> f = first;
        final BasedLinkedList.Node<E> newNode = new BasedLinkedList.Node<>(null, p, f,time,version);
        first.prev = newNode;
        versions.add(version);
        first = newNode;
        size++;
    }

    public void showFirst() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println(dtf.format(first.time));

    }

    public void showLast() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println(dtf.format(last.time));
    }

}

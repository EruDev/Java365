package com.github.java.collections;

import com.github.java.collections.model.Employee;

import java.util.*;

/**
 * List 相关例子
 *
 * @author pengfei.zhao
 * @date 2020/10/11 15:21
 */
public class ListDemo {

    static class CardDeck {
        public static void deal(int numHands, int cardPerHand) {
            // 一副扑克牌
            String[] suit = new String[]{
                    "spades", "hearts",
                    "diamonds", "clubs"
            };
            String[] rank = new String[]{
                    "ace", "2", "3", "4",
                    "5", "6", "7", "8", "9", "10",
                    "jack", "queen", "king"
            };

            List<String> deck = new ArrayList<>();
            for (int i = 0; i < suit.length; i++) {
                for (int j = 0; j < rank.length; j++) {
                    deck.add(rank[j] + " of " + suit[i]);
                }
            }
            // 打乱扑克
            Collections.shuffle(deck);

            if (numHands * cardPerHand > deck.size()) {
                System.out.println("Not enough cards.");
                return;
            }

            for (int i = 0; i < numHands; i++) {
                System.out.println(dealHand(deck, cardPerHand));
            }
        }
    }

    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>(
                Arrays.asList(
                        new Employee("XiaoFang"),
                        new Employee("XiaoChen"),
                        new Employee("XiaoZhao"),
                        new Employee("XiaoHong"),
                        new Employee("XiaoYang"))
        );
        System.out.println("==============Begin Swap================");
        System.out.println(employeeList);
        swap(employeeList, 1, 2);
        System.out.println("==============After Swap================");
        System.out.println(employeeList);

        shuffle(employeeList, new Random());
        System.out.println(employeeList);

        System.out.println("==============Rang View=================");
        rangView();
        dealHand(new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e")), 2);

        System.out.println("==============Card Deck=================");
        CardDeck.deal(4, 5);
    }

    /**
     * 交换
     *
     * @param list list
     * @param i    i
     * @param j    j
     * @param <E>  E
     */
    public static <E> void swap(List<E> list, int i, int j) {
        E tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    /**
     * 随机打乱
     *
     * @param list   list
     * @param random random
     */
    public static void shuffle(List<?> list, Random random) {
        for (int i = list.size(); i > 1; i--) {
            swap(list, i - 1, random.nextInt(i));
        }
    }

    /**
     * 范围查看操作
     */
    public static void rangView() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "e", "a"));
        //list.subList(0, 2).clear();
        System.out.println(list);

        int exist = list.subList(0, 7).indexOf("a");
        int lastIndex = list.subList(0, 7).lastIndexOf("a");
        System.out.println(exist);
        System.out.println(lastIndex);
    }

    public static <E> List<E> dealHand(List<E> deck, int n) {
        int deckSize = deck.size();
        List<E> handView = deck.subList(deckSize - n, deckSize);
        List<E> hand = new ArrayList<>(handView);
        handView.clear();
        return hand;
    }
}

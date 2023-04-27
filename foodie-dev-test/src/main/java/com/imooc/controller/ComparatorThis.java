package com.imooc.controller;

import com.google.common.collect.Lists;
import com.imooc.entity.Apple;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.Comparator;
import java.util.List;

/**
 * @author Mengdl
 * @date 2022/01/28
 */
public class ComparatorThis {

    public static void main(String[] args) {

        List<Apple> inventory = Lists.newArrayList();
        inventory.sort(Comparator.comparing(Apple::getWeight, Comparator.reverseOrder()));
        Thread t = new Thread(() -> System.out.println("Hello world"));

    }

    public static void Button(){
        Button button = new Button("Send");
//        button.setOnAction((ActionEvent event) -> label.setText("Sent!!"));
    }



}

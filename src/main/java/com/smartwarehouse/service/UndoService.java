package com.smartwarehouse.service;

import com.smartwarehouse.datastructure.CustomStack;

public class UndoService {

    private final CustomStack<String> actions = new CustomStack<>();

    public void recordAction(String action) {
        actions.push(action);
    }

    public void undoLast() {
        String action = actions.pop();
        if (action == null) {
            System.out.println("No operation to undo.");
            return;
        }
        System.out.println("Undo action recorded: " + action);
    }
}

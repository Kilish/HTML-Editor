package com.javarush.task.task32.task3209.actions;

import com.javarush.task.task32.task3209.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Мои документы on 28.05.2017.
 */
public class RedoAction extends AbstractAction {
    private View view;
    public RedoAction(View view){
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        view.redo();
    }
}

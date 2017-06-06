package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.UndoListener;
import javafx.scene.web.HTMLEditor;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

/**
 * Created by Мои документы on 28.05.2017.
 */
public class Controller {
    private View view;

    public HTMLDocument getDocument() {
        return document;
    }

    private HTMLDocument document;
    private File currentFile;
    public Controller(View view){
        this.view = view;
    }


    public static void main(String[] args){
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();

    }

    // Инициализация
    public void init(){createNewDocument();}
    public void exit(){
        System.exit(0);
    }
    //Сбрасывает текущий документ
    public void resetDocument(){
        UndoListener undoListener = view.getUndoListener();
        if(document != null){
            document.removeUndoableEditListener(undoListener);
        }
        HTMLDocument htmlDocument = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        htmlDocument.addUndoableEditListener(view.getUndoListener());
        view.update();
    }
    // Записывает переданный текст с html тегами в документ document
    public void setPlainText(String text){
        resetDocument();
        StringReader stringReader = new StringReader(text);
        HTMLEditorKit htmlEditor = new HTMLEditorKit();
        try{
            htmlEditor.read(stringReader,document,0);
        } catch(Exception e){
            ExceptionHandler.log(e);
        }
    }
    //Получает текст из документа со всеми html тегами
    public String getPlainText(){
        try{
            int length = document.getLength();
            StringWriter stringWriter = new StringWriter();
            HTMLEditorKit htmlEditor = new HTMLEditorKit();
            htmlEditor.write(stringWriter,document,0,length);
            return stringWriter.toString();
        } catch(Exception e){
            ExceptionHandler.log(e);
            return null;
        }
    }
    //Создание нового документа
    public void createNewDocument(){
        if(!view.isHtmlTabSelected()){
            view.selectHtmlTab();
            this.resetDocument();
            view.setTitle("HTML редактор");
            view.resetUndo();
            currentFile = null;
        }
    }
    public void openDocument(){
        if(!view.isHtmlTabSelected()){
            try{
                view.selectHtmlTab();
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new HTMLFileFilter());
                jFileChooser.setDialogTitle("Open File");
                int result = jFileChooser.showOpenDialog(view);
                if(result == JFileChooser.APPROVE_OPTION){
                    resetDocument();
                    view.resetUndo();
                    currentFile = jFileChooser.getSelectedFile();
                    view.setTitle(currentFile.getName());
                    FileReader fileReader = new FileReader(currentFile);
                    HTMLEditorKit htmlEditor = new HTMLEditorKit();
                    htmlEditor.read(fileReader,document,0);
                }

            } catch (Exception e){
                ExceptionHandler.log(e);
            }
        }
    }
    public void saveDocument(){
        if (currentFile == null) {
            saveDocumentAs();
        }
        else {
            //Переключать представление на html вкладку
            view.selectHtmlTab();

            //Создавать FileWriter на базе currentFile
            try (FileWriter fileWriter = new FileWriter(currentFile)) {
                //Переписывать данные из документа document в объекта FileWriter-а аналогично тому, как мы это делали в методе getPlainText()
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            }
            catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }
    //Сохранение файла
    public void saveDocumentAs()  {
        if(!view.isHtmlTabSelected()){
            try{
                view.selectHtmlTab();
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new HTMLFileFilter());
                jFileChooser.setDialogTitle("Save File");
                int result = jFileChooser.showSaveDialog(view);
                if(result == JFileChooser.APPROVE_OPTION){
                    currentFile = jFileChooser.getSelectedFile();
                    view.setTitle(currentFile.getName());
                    FileWriter fileWriter = new FileWriter(currentFile);
                    HTMLEditorKit htmlEditor = new HTMLEditorKit();
                    htmlEditor.write(fileWriter,document,0,document.getLength());
                }

            } catch (Exception e){
                ExceptionHandler.log(e);
            }
        }
    }
}

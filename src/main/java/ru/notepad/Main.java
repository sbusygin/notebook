package ru.notepad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private final String NAME = "Новый файл"; // Название нового созданного файла

    private JFileChooser f = new JFileChooser(); //Класс для окна сохранения

    private JTabbedPane tabs = new JTabbedPane(); // класс для создания вкладок

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    public Main () {
        // Создание меню

        // Создание конейтенра под меню
        JMenuBar menu = new JMenuBar();

        // Создание пункта меню
        JMenu file = new JMenu("Файл");

        // Создание подпунктов меню
        JMenuItem newFile = new JMenuItem("Создать файл");
        JMenuItem openFile = new JMenuItem("Открыть файл");
        JMenuItem saveFile = new JMenuItem("Сохранить файл");

        // Добавление подпукнта меню в пукнт
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);

        // Добавление пункта в сам контейнер меню
        menu.add(file);


        // Инициализация окна
        JFrame window = new JFrame("Notepad");
        window.setSize(800, 600);  //установка размера окна
        window.setJMenuBar(menu); // добавления меню созданного выше в окно
        window.add(tabs); // добавление вкладок
        window.setResizable(false); // делаем окно не изменяемым
        window.setLocationRelativeTo(null); // локация появления окна по умолчанию по середине экрана
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // при нажатии на крести выход
        window.setVisible(true); // устанавливаем что окно будет видимым


        // слушатель для создания файла - что будет происхоидть когда будем нажимать на создать файл
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea text = new JTextArea();

                Scroll scroll = new Scroll(text,false,"");

                tabs.add(NAME, scroll);
            }
        });


        // слушатель для сохранения файла - что будет происхоидть когда будем нажимать на сохранить файл
        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scroll text = (Scroll) tabs.getSelectedComponent();
                String output = text.getText();


                if (tabs.countComponents() != 0) {
                    if (text.isOpened()) {

                        try {
                            FileOutputStream writer = new FileOutputStream(text.getPath());
                            writer.write(output.getBytes());

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        f.showSaveDialog(null); //Окно сохранения будет появлятся в центре экрана
                        File file = f.getSelectedFile();
                        try {
                            FileOutputStream writer = new FileOutputStream(file);
                            writer.write(output.getBytes());

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }

                }
            }
        });

        // слушатель для открытия файла - что будет происхоидть когда будем нажимать на открыть файл
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    f.showOpenDialog(null); //Окно выбора файла для открытия будет появлятся в центре экрана
                    File file = f.getSelectedFile();
                    String input = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

                    JTextArea text = new JTextArea(input);

                    Scroll scroll = new Scroll(text, true, file.getAbsolutePath());

                    tabs.addTab(file.getName(), scroll);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
    }
}

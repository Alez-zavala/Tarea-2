package com.mycompany.analisisdetexto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AnalisisDeTexto extends JFrame {
    private JTextArea inputTextArea;
    private JTextField lengthField, wordCountField, firstLetterField, lastLetterField, centralLetterField;
    private JTextField firstWordField, centralWordField, lastWordField;
    private JTextField countAField, countEField, countIField, countOField, countUField;
    private JTextField evenWordsField, oddWordsField;
    private JTextArea translationArea;

    public AnalisisDeTexto() {
        setTitle("PROGRAMACION II");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem abrirItem = new JMenuItem("Abrir...");
        JMenuItem guardarItem = new JMenuItem("Guardar");
        JMenuItem guardarComoItem = new JMenuItem("Guardar como...");
        JMenuItem salirItem = new JMenuItem("Salir");
        
        abrirItem.addActionListener(new AbrirArchivoListener());
        guardarItem.addActionListener(new GuardarArchivoListener());
        guardarComoItem.addActionListener(new GuardarComoArchivoListener());
        salirItem.addActionListener(e -> System.exit(0));
        
        archivoMenu.add(abrirItem);
        archivoMenu.add(guardarItem);
        archivoMenu.add(guardarComoItem);
        archivoMenu.add(salirItem);

        // Menú Editar
        JMenu editarMenu = new JMenu("Editar");
        JMenuItem copiarItem = new JMenuItem("Copiar");
        JMenuItem cortarItem = new JMenuItem("Cortar");
        JMenuItem pegarItem = new JMenuItem("Pegar");
        JMenuItem buscarItem = new JMenuItem("Buscar");
        JMenuItem reemplazarItem = new JMenuItem("Reemplazar");
        
        copiarItem.addActionListener(e -> inputTextArea.copy());
        cortarItem.addActionListener(e -> inputTextArea.cut());
        pegarItem.addActionListener(e -> inputTextArea.paste());
        buscarItem.addActionListener(new BuscarTextoListener());
        reemplazarItem.addActionListener(new ReemplazarTextoListener());

        editarMenu.add(copiarItem);
        editarMenu.add(cortarItem);
        editarMenu.add(pegarItem);
        editarMenu.add(buscarItem);
        editarMenu.add(reemplazarItem);

        // Añadir menús a la barra de menú
        menuBar.add(archivoMenu);
        menuBar.add(editarMenu);

        // Añadir la barra de menú a la ventana
        setJMenuBar(menuBar);

        // Panel de entrada de texto
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("MANEJO DE CADENAS"));
        inputTextArea = new JTextArea(5, 40);
        inputTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        inputPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón para procesar
        JButton processButton = new JButton("Procesar");
        processButton.addActionListener(new ProcessButtonListener());
        inputPanel.add(processButton, BorderLayout.SOUTH);

        // Panel de resultados
        JPanel resultsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Filas de resultados organizadas en el GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        resultsPanel.add(new JLabel("Longitud del texto:"), gbc);
        gbc.gridx = 1;
        lengthField = new JTextField(10);
        lengthField.setEditable(false);
        resultsPanel.add(lengthField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        resultsPanel.add(new JLabel("Total de palabras:"), gbc);
        gbc.gridx = 1;
        wordCountField = new JTextField(10);
        wordCountField.setEditable(false);
        resultsPanel.add(wordCountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        resultsPanel.add(new JLabel("Primera letra del texto:"), gbc);
        gbc.gridx = 1;
        firstLetterField = new JTextField(10);
        firstLetterField.setEditable(false);
        resultsPanel.add(firstLetterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        resultsPanel.add(new JLabel("Última letra del texto:"), gbc);
        gbc.gridx = 1;
        lastLetterField = new JTextField(10);
        lastLetterField.setEditable(false);
        resultsPanel.add(lastLetterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        resultsPanel.add(new JLabel("Letra central del texto:"), gbc);
        gbc.gridx = 1;
        centralLetterField = new JTextField(10);
        centralLetterField.setEditable(false);
        resultsPanel.add(centralLetterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        resultsPanel.add(new JLabel("Primera palabra:"), gbc);
        gbc.gridx = 1;
        firstWordField = new JTextField(10);
        firstWordField.setEditable(false);
        resultsPanel.add(firstWordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        resultsPanel.add(new JLabel("Palabra central:"), gbc);
        gbc.gridx = 1;
        centralWordField = new JTextField(10);
        centralWordField.setEditable(false);
        resultsPanel.add(centralWordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        resultsPanel.add(new JLabel("Última palabra:"), gbc);
        gbc.gridx = 1;
        lastWordField = new JTextField(10);
        lastWordField.setEditable(false);
        resultsPanel.add(lastWordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        resultsPanel.add(new JLabel("Repeticiones de 'A', 'a' ó 'á':"), gbc);
        gbc.gridx = 1;
        countAField = new JTextField(10);
        countAField.setEditable(false);
        resultsPanel.add(countAField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        resultsPanel.add(new JLabel("Repeticiones de 'E', 'e' ó 'é':"), gbc);
        gbc.gridx = 1;
        countEField = new JTextField(10);
        countEField.setEditable(false);
        resultsPanel.add(countEField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        resultsPanel.add(new JLabel("Repeticiones de 'I', 'i' ó 'í':"), gbc);
        gbc.gridx = 1;
        countIField = new JTextField(10);
        countIField.setEditable(false);
        resultsPanel.add(countIField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        resultsPanel.add(new JLabel("Repeticiones de 'O', 'o' ó 'ó':"), gbc);
        gbc.gridx = 1;
        countOField = new JTextField(10);
        countOField.setEditable(false);
        resultsPanel.add(countOField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        resultsPanel.add(new JLabel("Repeticiones de 'U', 'u' ó 'ú':"), gbc);
        gbc.gridx = 1;
        countUField = new JTextField(10);
        countUField.setEditable(false);
        resultsPanel.add(countUField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 13;
        resultsPanel.add(new JLabel("Palabras con cantidad de caracteres par:"), gbc);
        gbc.gridx = 1;
        evenWordsField = new JTextField(10);
        evenWordsField.setEditable(false);
        resultsPanel.add(evenWordsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        resultsPanel.add(new JLabel("Palabras con cantidad de caracteres impar:"), gbc);
        gbc.gridx = 1;
        oddWordsField = new JTextField(10);
        oddWordsField.setEditable(false);
        resultsPanel.add(oddWordsField, gbc);

        // Panel de traducción a clave Murciélago
        JPanel translationPanel = new JPanel(new BorderLayout());
        translationPanel.setBorder(BorderFactory.createTitledBorder("TRADUCCIÓN A CLAVE MURCIELAGO"));
        translationArea = new JTextArea(3, 40);
        translationArea.setLineWrap(true);
        translationPanel.add(new JScrollPane(translationArea), BorderLayout.CENTER);

        // Añadir paneles al JFrame
        add(inputPanel, BorderLayout.NORTH);
        add(resultsPanel, BorderLayout.CENTER);
        add(translationPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Centra la ventana
        setVisible(true);
    }

    private class ProcessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = inputTextArea.getText();
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(AnalisisDeTexto.this, "Por favor, ingrese un texto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Análisis del texto
            lengthField.setText(String.valueOf(text.length()));
            String[] words = text.split("\\s+");
            wordCountField.setText(String.valueOf(words.length));

            if (text.length() > 0) {
                firstLetterField.setText(String.valueOf(text.charAt(0)));
                lastLetterField.setText(String.valueOf(text.charAt(text.length() - 1)));
                centralLetterField.setText(String.valueOf(text.charAt(text.length() / 2)));
            }

            if (words.length > 0) {
                firstWordField.setText(words[0]);
                lastWordField.setText(words[words.length - 1]);
                centralWordField.setText(words[words.length / 2]);
            }

            int countA = 0, countE = 0, countI = 0, countO = 0, countU = 0;
            int evenWords = 0, oddWords = 0;

            for (String word : words) {
                if (word.length() % 2 == 0) {
                    evenWords++;
                } else {
                    oddWords++;
                }

                for (char c : word.toCharArray()) {
                    switch (c) {
                        case 'A': case 'a': case 'á': countA++; break;
                        case 'E': case 'e': case 'é': countE++; break;
                        case 'I': case 'i': case 'í': countI++; break;
                        case 'O': case 'o': case 'ó': countO++; break;
                        case 'U': case 'u': case 'ú': countU++; break;
                    }
                }
            }

            countAField.setText(String.valueOf(countA));
            countEField.setText(String.valueOf(countE));
            countIField.setText(String.valueOf(countI));
            countOField.setText(String.valueOf(countO));
            countUField.setText(String.valueOf(countU));
            evenWordsField.setText(String.valueOf(evenWords));
            oddWordsField.setText(String.valueOf(oddWords));

            // Traducción a Clave Murciélago
            translationArea.setText(translateToMurcielago(text));
        }

        private String translateToMurcielago(String text) {
            // Mapa de sustitución específico
            String[][] mapping = {
                {"a", "7"}, {"A", "7"},
                {"e", "5"}, {"E", "5"},
                {"i", "4"}, {"I", "4"},
                {"o", "9"}, {"O", "9"},
                {"u", "1"}, {"U", "1"},
                {" ", " "}, // Mantenemos los espacios
                {"l", "6"}, {"L", "6"},
                {"c", "3"}, {"C", "3"},
                {"s", "s"}, {"S", "5"},
                {"d", "d"}, {"D", "0"},
                {"p", "p"}, {"P", "2"},
                {"r", "2"}, {"R", "8"},
                {"m", "0"}, {"M", "0"},
                {"g", "8"}, {"G", "9"},
                {"j", "j"}, {"J", "9"},
                {"b", "2"}, {"B", "2"},
                {"f", "6"}, {"F", "6"},
                {"h", "8"}, {"H", "8"},
                {"N", "5"}, {"n", " r"},
                {"ó", "n"}, {"o", "9"}
                
            };

            StringBuilder translation = new StringBuilder();
            for (char c : text.toCharArray()) {
                boolean translated = false;
                for (String[] pair : mapping) {
                    if (pair[0].equals(String.valueOf(c))) {
                        translation.append(pair[1]);
                        translated = true;
                        break;
                    }
                }
                if (!translated) {
                    translation.append(c);
                }
            }

            return translation.toString();
        }
    }

    private class AbrirArchivoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(AnalisisDeTexto.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    String content = new String(Files.readAllBytes(Paths.get(selectedFile.getPath())));
                    inputTextArea.setText(content);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(AnalisisDeTexto.this, "No se pudo leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class GuardarArchivoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(AnalisisDeTexto.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(inputTextArea.getText());
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(AnalisisDeTexto.this, "No se pudo guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class GuardarComoArchivoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(AnalisisDeTexto.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(inputTextArea.getText());
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(AnalisisDeTexto.this, "No se pudo guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class BuscarTextoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = JOptionPane.showInputDialog(AnalisisDeTexto.this, "Buscar:");
            if (searchText != null && !searchText.isEmpty()) {
                String text = inputTextArea.getText();
                int index = text.indexOf(searchText);
                if (index != -1) {
                    inputTextArea.setCaretPosition(index);
                    inputTextArea.select(index, index + searchText.length());
                } else {
                    JOptionPane.showMessageDialog(AnalisisDeTexto.this, "Texto no encontrado.", "Buscar", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private class ReemplazarTextoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel(new GridLayout(2, 2));
            JTextField findField = new JTextField();
            JTextField replaceField = new JTextField();
            panel.add(new JLabel("Buscar:"));
            panel.add(findField);
            panel.add(new JLabel("Reemplazar con:"));
            panel.add(replaceField);

            int result = JOptionPane.showConfirmDialog(AnalisisDeTexto.this, panel, "Reemplazar", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String findText = findField.getText();
                String replaceText = replaceField.getText();
                String text = inputTextArea.getText();
                text = text.replace(findText, replaceText);
                inputTextArea.setText(text);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AnalisisDeTexto::new);
    }
}

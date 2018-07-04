package com.aayush.view;

import com.aayush.model.Enigma;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import static com.aayush.util.Constants.*;

public class MainFrame extends JFrame {
    private ConfigPanel configPanel;
    private TextPanel textPanel;

    private Enigma enigma;
    private String machineType;

    private final String[][] ENIGMA_ROTORS = {I, II, III, IV, V, VI, VII, VIII};
    private final String[] GREEK_ROTORS = {BETA, GAMMA};
    private final String[] ENIGMA_REFLECTORS = {B, C};
    private final String[] GREEK_REFLECTORS = {B_THIN, C_THIN};

    public MainFrame() {
        super("Enigma Machine");

        initComponents();
        setupComponents();
        addToView();

        setMinimumSize(new Dimension(600, 480));
        setSize(1200, 700);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(50, 50);
        setVisible(true);
    }

    private void addToView() {
        setLayout(new BorderLayout());

        add(configPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
    }

    private void initComponents() {
        configPanel = new ConfigPanel();
        textPanel = new TextPanel();
    }

    private void runEnigma() {
        machineType = String.valueOf(configPanel.getMachineComboBox().getSelectedItem());

        if(Enigma.isNotDistinct(ENIGMA_ROTORS[configPanel.getLeftRotorComboBox().getSelectedIndex()],
                ENIGMA_ROTORS[configPanel.getMidRotorComboBox().getSelectedIndex()],
                ENIGMA_ROTORS[configPanel.getRightRotorComboBox().getSelectedIndex()])) {
            textPanel.getInputField().setEditable(false);
            JOptionPane.showMessageDialog(this, "Select distinct rotors!",
                    "Enigma Machine Configuration Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            textPanel.getInputField().setEditable(true);
            textPanel.getOutputField().setText("");
        }

        if("M4".equals(machineType)){
            enigma = new Enigma(GREEK_ROTORS[configPanel.getGreekRotorComboBox().getSelectedIndex()],
                    ENIGMA_ROTORS[configPanel.getLeftRotorComboBox().getSelectedIndex()],
                    ENIGMA_ROTORS[configPanel.getMidRotorComboBox().getSelectedIndex()],
                    ENIGMA_ROTORS[configPanel.getRightRotorComboBox().getSelectedIndex()],
                    GREEK_REFLECTORS[configPanel.getReflectorComboBox().getSelectedIndex()]);
        }
        else{
            enigma = new Enigma(ENIGMA_ROTORS[configPanel.getLeftRotorComboBox().getSelectedIndex()],
                    ENIGMA_ROTORS[configPanel.getMidRotorComboBox().getSelectedIndex()],
                    ENIGMA_ROTORS[configPanel.getRightRotorComboBox().getSelectedIndex()],
                    ENIGMA_REFLECTORS[configPanel.getReflectorComboBox().getSelectedIndex()]);
        }

        if("M4".equals(machineType)) {
            enigma.getGreekRotor()
                    .setRingHead(String.valueOf(configPanel.getGreekRingComboBox()).charAt(0));
            enigma.getGreekRotor()
                    .setRotorHead(String.valueOf(configPanel.getGreekGroundComboBox()).charAt(0));
        }
        enigma.getLeftRotor()
                .setRingHead(String.valueOf(configPanel.getLeftRingComboBox()).charAt(0));
        enigma.getLeftRotor()
                .setRotorHead(String.valueOf(configPanel.getLeftGroundComboBox()).charAt(0));
        enigma.getMidRotor()
                .setRingHead(String.valueOf(configPanel.getMidRingComboBox()).charAt(0));
        enigma.getMidRotor()
                .setRotorHead(String.valueOf(configPanel.getMidGroundComboBox()).charAt(0));
        enigma.getRightRotor()
                .setRingHead(String.valueOf(configPanel.getRightRingComboBox()).charAt(0));
        enigma.getRightRotor()
                .setRotorHead(String.valueOf(configPanel.getRightGroundComboBox()).charAt(0));

        enigma.resetPlugboard();
        Scanner in = new Scanner(configPanel.getPlugboardInput().getText());
        while(in.hasNext()) {
            String wire = in.next();
            if(wire.length() == 2) {
                char src = wire.charAt(0);
                char dest = wire.charAt(1);
                if(enigma.isNotPlugged(src) && enigma.isNotPlugged(dest) && src != dest){
                    enigma.addPlugboardWire(src, dest);
                }
            }
        }
        in.close();
    }

    private void setupComponents() {
        DocumentFilter filter = new DocumentFilter(){
            public void replace(FilterBypass fb, int offset, int length, String text,
                                AttributeSet attrs) throws BadLocationException {
                for (int i = 0; i < text.length(); i++){
                    if( (text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' ||
                            text.charAt(i) > 'Z') && text.charAt(0) != ' ')
                        return;
                }
                super.replace(fb, offset, length, text.toUpperCase(), attrs);
            }
        };

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                Runtime.getRuntime().gc();
            }
        });

        configPanel.setDocumentFilter(filter);
        textPanel.setDocumentFilter(filter);
        textPanel.addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                enigma.resetMachine();
                textPanel.getOutputField()
                        .setText(enigma.print(textPanel.getInputField().getText()));
                updateStates();
            }

            public void insertUpdate(DocumentEvent e) {
                enigma.resetMachine();
                textPanel.getOutputField()
                        .setText(enigma.print(textPanel.getInputField().getText()));
                updateStates();
            }
            public void changedUpdate(DocumentEvent e) {}
        });

        configPanel.addActionListener(e -> {
            textPanel.getInputField().setText("");
            configPanel.getLeftStateField().setText("");
            configPanel.getRightStateField().setText("");
            configPanel.getMidStateField().setText("");
            configPanel.getGreekStateField().setText("");
            runEnigma();
        });
    }

    private void updateStates() {
        if("M4".equals(machineType)){
            configPanel.getGreekStateField().setText(enigma.getGreekRotor().getRotorHead() + "");
        }
        configPanel.getLeftStateField().setText(enigma.getLeftRotor().getRotorHead() + "");
        configPanel.getMidStateField().setText(enigma.getMidRotor().getRotorHead() + "");
        configPanel.getRightStateField().setText(enigma.getRightRotor().getRotorHead() + "");
    }
}

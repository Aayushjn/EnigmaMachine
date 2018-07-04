package com.aayush.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;

class TextPanel extends JPanel {
    private JFormattedTextField inputField;
    private JTextField outputField;

    TextPanel() {
        initComponents();
        setupComponents();
        addToView();
    }

    void addDocumentListener(DocumentListener listener) {
        inputField.getDocument().addDocumentListener(listener);
    }

    private void addToView() {
        Border outerBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border innerBorder = BorderFactory.createTitledBorder("Enigma");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        Insets rightPadding = new Insets(0, 0, 0, 10);
        Insets noPadding = new Insets(0, 0, 0, 0);
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        // <============ First row ============>
        gc.gridy = 0;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        add(new JLabel("Input:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = noPadding;
        add(inputField, gc);

        // <============ Next row ============>
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        add(new JLabel("Output:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = noPadding;
        add(outputField, gc);
    }

    private void initComponents() {
        inputField = new JFormattedTextField();
        outputField = new JTextField();
    }

    private void setupComponents() {
        inputField.setEditable(false);
        inputField.setPreferredSize(new Dimension(600, 75));

        outputField.setEditable(false);
        outputField.setPreferredSize(new Dimension(600, 75));
    }

    void setDocumentFilter(DocumentFilter filter) {
        ((AbstractDocument) inputField.getDocument()).setDocumentFilter(filter);
    }

    JFormattedTextField getInputField() {
        return inputField;
    }

    JTextField getOutputField() {
        return outputField;
    }
}


package com.aayush.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;

class ConfigPanel extends JPanel {
    private static final String[] MACHINE_CHOICES = {"M3", "M4"};
    private static final String[] M3_REFLECTOR_CHOICES = {"B", "C"};
    private static final String[] M4_REFLECTOR_CHOICES = {"B (thin)", "C (thin)"};
    private static final String[] M3_ROTOR_CHOICES = {"I", "II", "III", "IV", "V", "VI", "VII",
            "VIII"};
    private static final String[] M4_ROTOR_CHOICES = {"Beta", "Gamma"};
    private static final String[] RING_CHOICES = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private JComboBox<String> machineComboBox;
    private JComboBox<String> reflectorComboBox;
    private JComboBox<String> leftRotorComboBox;
    private JComboBox<String> midRotorComboBox;
    private JComboBox<String> rightRotorComboBox;
    private JComboBox<String> greekRotorComboBox;
    private JComboBox<String> leftRingComboBox;
    private JComboBox<String> midRingComboBox;
    private JComboBox<String> rightRingComboBox;
    private JComboBox<String> greekRingComboBox;
    private JComboBox<String> leftGroundComboBox;
    private JComboBox<String> midGroundComboBox;
    private JComboBox<String> rightGroundComboBox;
    private JComboBox<String> greekGroundComboBox;

    private JTextField greekStateField;
    private JTextField leftStateField;
    private JTextField midStateField;
    private JTextField rightStateField;

    private JLabel greekStateLabel;

    private JFormattedTextField plugboardInput;

    private JButton saveButton;

    private Hashtable<String, String[]> reflectorItems;
    private Hashtable<String, String[]> wheelOrderItems;

    ConfigPanel() {
        initComponents();
        setupComponents();
        addToView();
    }

    void addActionListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    private void addToView() {
        Border outerBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border innerBorder = BorderFactory.createTitledBorder("Configuration");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        setLayout(new GridBagLayout());

        JPanel panel;

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
        add(new JLabel("Machine Type:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = noPadding;
        add(machineComboBox, gc);

        // <============ Next row ============>
        gc.gridy += 2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        add(new JLabel("Reflector:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = rightPadding;
        add(reflectorComboBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = noPadding;
        add(new JLabel("Left State:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = noPadding;
        add(leftStateField, gc);

        // <============ Next row ============>
        gc.gridy += 2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        add(new JLabel("Rotor:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = rightPadding;
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(leftRotorComboBox);
        panel.add(midRotorComboBox);
        panel.add(rightRotorComboBox);
        panel.add(greekRotorComboBox);
        add(panel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = noPadding;
        add(new JLabel("Mid State:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = noPadding;
        add(midStateField, gc);

        // <============ Next row ============>
        gc.gridy += 2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        add(new JLabel("Ring Setting:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = rightPadding;
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(leftRingComboBox);
        panel.add(midRingComboBox);
        panel.add(rightRingComboBox);
        panel.add(greekRingComboBox);
        add(panel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = noPadding;
        add(new JLabel("Right State:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = noPadding;
        add(rightStateField, gc);

        // <============ Next row ============>
        gc.gridy += 2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        add(new JLabel("Ground Setting:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = rightPadding;
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(leftGroundComboBox);
        panel.add(midGroundComboBox);
        panel.add(rightGroundComboBox);
        panel.add(greekGroundComboBox);
        add(panel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = noPadding;
        add(greekStateLabel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = noPadding;
        add(greekStateField, gc);

        // <============ Next row ============>
        gc.gridy += 2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        add(new JLabel("Plugboard Input:"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = rightPadding;
        add(plugboardInput, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = noPadding;
        add(saveButton, gc);
    }

    private void initComponents() {
        reflectorItems = new Hashtable<>();
        wheelOrderItems = new Hashtable<>();

        DefaultComboBoxModel<String> machineModel = new DefaultComboBoxModel<>(MACHINE_CHOICES);
        machineComboBox = new JComboBox<>(machineModel);

        reflectorItems.put(MACHINE_CHOICES[0], M3_REFLECTOR_CHOICES);
        reflectorItems.put(MACHINE_CHOICES[1], M4_REFLECTOR_CHOICES);
        reflectorComboBox = new JComboBox<>(M3_REFLECTOR_CHOICES);

        wheelOrderItems.put(MACHINE_CHOICES[0], M3_ROTOR_CHOICES);
        wheelOrderItems.put(MACHINE_CHOICES[1], M4_ROTOR_CHOICES);
        leftRotorComboBox = new JComboBox<>(M3_ROTOR_CHOICES);
        midRotorComboBox = new JComboBox<>(M3_ROTOR_CHOICES);
        rightRotorComboBox = new JComboBox<>(M3_ROTOR_CHOICES);
        greekRotorComboBox = new JComboBox<>(M4_ROTOR_CHOICES);

        leftRingComboBox = new JComboBox<>(RING_CHOICES);
        midRingComboBox = new JComboBox<>(RING_CHOICES);
        rightRingComboBox = new JComboBox<>(RING_CHOICES);
        greekRingComboBox = new JComboBox<>(RING_CHOICES);

        leftGroundComboBox = new JComboBox<>(RING_CHOICES);
        midGroundComboBox = new JComboBox<>(RING_CHOICES);
        rightGroundComboBox = new JComboBox<>(RING_CHOICES);
        greekGroundComboBox = new JComboBox<>(RING_CHOICES);

        leftStateField = new JTextField(10);
        midStateField = new JTextField(10);
        rightStateField = new JTextField(10);
        greekStateField = new JTextField(10);

        greekStateLabel = new JLabel("Greek State:");

        plugboardInput = new JFormattedTextField();

        saveButton = new JButton("Save");
    }

    private void setupComponents() {
        // Set default selections to prevent NullPointerException
        machineComboBox.setSelectedIndex(0);

        reflectorComboBox.setSelectedIndex(0);

        leftRotorComboBox.setSelectedIndex(0);
        midRotorComboBox.setSelectedIndex(0);
        rightRotorComboBox.setSelectedIndex(0);
        greekRotorComboBox.setSelectedIndex(0);

        leftRingComboBox.setSelectedIndex(0);
        midRingComboBox.setSelectedIndex(0);
        rightRingComboBox.setSelectedIndex(0);
        greekRingComboBox.setSelectedIndex(0);

        leftGroundComboBox.setSelectedIndex(0);
        midGroundComboBox.setSelectedIndex(0);
        rightGroundComboBox.setSelectedIndex(0);
        greekGroundComboBox.setSelectedIndex(0);

        greekRotorComboBox.setVisible(false);
        greekRingComboBox.setVisible(false);
        greekGroundComboBox.setVisible(false);
        greekStateLabel.setVisible(false);
        greekStateField.setVisible(false);
        leftStateField.setEditable(false);
        midStateField.setEditable(false);
        rightStateField.setEditable(false);
        greekStateField.setEditable(false);

        machineComboBox.addActionListener(e -> {
            String machineType = (String) machineComboBox.getSelectedItem();
            String[] reflector;
            String[] rotor;
            if (machineType != null) {
                reflector = reflectorItems.get(machineType);
                rotor = wheelOrderItems.get(machineType);

                if (reflector == null || rotor == null) {
                    reflectorComboBox.setModel(new DefaultComboBoxModel<>());
                    leftRotorComboBox.setModel(new DefaultComboBoxModel<>());
                    midRotorComboBox.setModel(new DefaultComboBoxModel<>());
                    rightRotorComboBox.setModel(new DefaultComboBoxModel<>());
                    greekRotorComboBox.setModel(new DefaultComboBoxModel<>());
                    leftRingComboBox.setModel(new DefaultComboBoxModel<>());
                    midRingComboBox.setModel(new DefaultComboBoxModel<>());
                    rightRingComboBox.setModel(new DefaultComboBoxModel<>());
                    rightRingComboBox.setModel(new DefaultComboBoxModel<>());
                    leftGroundComboBox.setModel(new DefaultComboBoxModel<>());
                    midGroundComboBox.setModel(new DefaultComboBoxModel<>());
                    rightGroundComboBox.setModel(new DefaultComboBoxModel<>());
                    greekGroundComboBox.setModel(new DefaultComboBoxModel<>());
                }
                else {
                    reflectorComboBox.setModel(new DefaultComboBoxModel<>(reflector));
                    leftRotorComboBox.setModel(new DefaultComboBoxModel<>(M3_ROTOR_CHOICES));
                    midRotorComboBox.setModel(new DefaultComboBoxModel<>(M3_ROTOR_CHOICES));
                    rightRotorComboBox.setModel(new DefaultComboBoxModel<>(M3_ROTOR_CHOICES));
                    greekRotorComboBox.setModel(new DefaultComboBoxModel<>(rotor));
                    if ("M4".equals(machineType)){
                        greekRotorComboBox.setVisible(true);
                    }
                    else {
                        greekRotorComboBox.setVisible(false);
                    }
                    leftRingComboBox.setModel(new DefaultComboBoxModel<>(RING_CHOICES));
                    midRingComboBox.setModel(new DefaultComboBoxModel<>(RING_CHOICES));
                    rightRingComboBox.setModel(new DefaultComboBoxModel<>(RING_CHOICES));
                    greekRingComboBox.setModel(new DefaultComboBoxModel<>(RING_CHOICES));
                    if ("M4".equals(machineType)){
                        greekRingComboBox.setVisible(true);
                    }
                    else {
                        greekRingComboBox.setVisible(false);
                    }
                    leftGroundComboBox.setModel(new DefaultComboBoxModel<>(RING_CHOICES));
                    midGroundComboBox.setModel(new DefaultComboBoxModel<>(RING_CHOICES));
                    rightGroundComboBox.setModel(new DefaultComboBoxModel<>(RING_CHOICES));
                    greekGroundComboBox.setModel(new DefaultComboBoxModel<>(RING_CHOICES));
                    if ("M4".equals(machineType)){
                        greekGroundComboBox.setVisible(true);
                    }
                    else {
                        greekGroundComboBox.setVisible(false);
                    }
                }
            }

            if("M4".equals(machineType)) {
                greekStateLabel.setVisible(true);
                greekStateField.setVisible(true);
            }
            else {
                greekStateLabel.setVisible(false);
                greekStateField.setVisible(false);
            }
        });

        plugboardInput.setPreferredSize(new Dimension(500, 30));
    }

    void setDocumentFilter(DocumentFilter filter) {
        ((AbstractDocument) plugboardInput.getDocument()).setDocumentFilter(filter);
    }

    JComboBox<String> getMachineComboBox() {
        return machineComboBox;
    }

    JComboBox<String> getReflectorComboBox() {
        return reflectorComboBox;
    }

    JComboBox<String> getLeftRotorComboBox() {
        return leftRotorComboBox;
    }

    JComboBox<String> getMidRotorComboBox() {
        return midRotorComboBox;
    }

    JComboBox<String> getRightRotorComboBox() {
        return rightRotorComboBox;
    }

    JComboBox<String> getGreekRotorComboBox() {
        return greekRotorComboBox;
    }

    JComboBox<String> getLeftRingComboBox() {
        return leftRingComboBox;
    }

    JComboBox<String> getMidRingComboBox() {
        return midRingComboBox;
    }

    JComboBox<String> getRightRingComboBox() {
        return rightRingComboBox;
    }

    JComboBox<String> getGreekRingComboBox() {
        return greekRingComboBox;
    }

    JComboBox<String> getLeftGroundComboBox() {
        return leftGroundComboBox;
    }

    JComboBox<String> getMidGroundComboBox() {
        return midGroundComboBox;
    }

    JComboBox<String> getRightGroundComboBox() {
        return rightGroundComboBox;
    }

    JComboBox<String> getGreekGroundComboBox() {
        return greekGroundComboBox;
    }

    JTextField getGreekStateField() {
        return greekStateField;
    }

    JTextField getLeftStateField() {
        return leftStateField;
    }

    JTextField getMidStateField() {
        return midStateField;
    }

    JTextField getRightStateField() {
        return rightStateField;
    }

    JFormattedTextField getPlugboardInput() {
        return plugboardInput;
    }
}

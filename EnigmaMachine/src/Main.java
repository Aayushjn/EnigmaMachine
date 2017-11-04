import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.*;

import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

import machine.Enigma;


public class Main extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;			

	private JLabel machineLabel;								//JLabel is for titles
	private JLabel reflectorLabel;
	private JLabel wheelOrderLabel;
	private JLabel ringSettingLabel;
	private JLabel groundSettingLabel;
	private JLabel plugboardLabel;
	private JLabel inputLabel;
	private JLabel outputLabel;
	private JLabel leftStateLabel;
	private JLabel midStateLabel;
	private JLabel rightStateLabel;
	private JLabel greekStateLabel;
	
	private JButton saveButton;
	
	private JComboBox<String> machineComboBox;					//JComboBox is for menus
	private JComboBox<String> reflectorComboBox;
	private JComboBox<String> leftRotorComboBox;
	private JComboBox<String> midRotorComboBox;
	private JComboBox<String> rightRotorComboBox;
	private JComboBox<String> greekRotorComboBox;
	private JComboBox<String> leftRingComboBox;
	private JComboBox<String> midRingComboBox;
	private JComboBox<String> rightRingComboBox;
	private JComboBox<String> greekRingComboBox;				//4th rotor (only used in M4)
	private JComboBox<String> leftGroundComboBox;
	private JComboBox<String> midGroundComboBox;
	private JComboBox<String> rightGroundComboBox;
	private JComboBox<String> greekGroundComboBox;
	
	private JTextField outputField;
	private JFormattedTextField inputField;						//JFormattedTextField for allowing specific characters only
	private JFormattedTextField plugboardInput;
	private JTextField greekStateField;
	private JTextField leftStateField;
	private JTextField midStateField;
	private JTextField rightStateField;
	
	private Component verticalStrut;							//For vertical gaps
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	private Component verticalGlue;								//For relative (to vertical struts) vertical gaps
	private Component verticalGlue_1;
	private Component verticalGlue_2;
	private Component verticalGlue_3;
	
	//Hashtables for dynamic choices
	private Hashtable<String, String[]> wheelOrderItems = new Hashtable<String, String[]>();
	private Hashtable<String, String[]> reflectorItems = new Hashtable<String, String[]>();
	private static String[] wheelOrderChoices1 = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII"};
	private static String[] wheelOrderChoices2 = {"Beta", "Gamma"};
	private static String[] ringSettingChoice = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	private Enigma enigma;
	private String mType;
	private String[][] ENIGMA_ROTORS = {Enigma.I, Enigma.II, Enigma.III, Enigma.IV, Enigma.V, Enigma.VI, Enigma.VII, Enigma.VIII};
	private String[] GREEK_ROTORS = {Enigma.BETA, Enigma.GAMMA};
	private String[] ENIGMA_REFLECTORS = {Enigma.B, Enigma.C}; 
	private String[] GREEK_REFLECTORS = {Enigma.B_THIN, Enigma.C_THIN};
	
	/**
	 * Constructor that builds the GUI for the Enigma Machine
	 */
	public Main(){
		setBackground(new Color(192, 192, 192));
		//Create box layout for menus
		Box labelBox = Box.createVerticalBox();				//Left-side label box
		Box box1 = Box.createHorizontalBox();
		Box choiceBox = Box.createVerticalBox();			//Right-side choice box
		Box wheelOrderBox = Box.createHorizontalBox();		//Horizontal box for multiple menus
		Box ringSettingBox = Box.createHorizontalBox();
		Box groundSettingBox = Box.createHorizontalBox();
		box1.add(labelBox);
		box1.add(choiceBox);

		//Machine type
		machineLabel = new JLabel("Enigma type: ");
		machineLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		machineLabel.setVisible(true);
		String[] machineChoices = {"M3", "M4"};
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(machineChoices);
		machineComboBox = new JComboBox(comboModel);
		machineComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		machineComboBox.setVisible(true);
		machineComboBox.addActionListener(this);
		labelBox.add(machineLabel);
		choiceBox.add(machineComboBox);
		
		//Reflector type
		reflectorLabel = new JLabel("Reflector: ");
		reflectorLabel.setFont(new Font("", Font.PLAIN, 20));
		reflectorLabel.setVisible(true);
		String[] reflectorChoices1 = {"B", "C"};
		reflectorItems.put(machineChoices[0], reflectorChoices1);
		String[] reflectorChoices2 = {"B (thin)", "C (thin)"};
		reflectorItems.put(machineChoices[1], reflectorChoices2);
		reflectorComboBox = new JComboBox<String>(reflectorChoices1);
		reflectorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		verticalStrut = Box.createVerticalStrut(20);
		labelBox.add(verticalStrut);
		labelBox.add(reflectorLabel);
		
		verticalGlue = Box.createVerticalGlue();
		choiceBox.add(verticalGlue);
		choiceBox.add(reflectorComboBox);

		//Wheel order
		wheelOrderLabel = new JLabel("Wheel Order: ");
		wheelOrderLabel.setFont(new Font("", Font.PLAIN, 20));
		wheelOrderLabel.setVisible(true);
		leftRotorComboBox = new JComboBox<String>(wheelOrderChoices1);
		leftRotorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		wheelOrderBox.add(leftRotorComboBox);
		midRotorComboBox = new JComboBox<String>(wheelOrderChoices1);
		midRotorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		wheelOrderBox.add(midRotorComboBox);
		rightRotorComboBox = new JComboBox<String>(wheelOrderChoices1);
		rightRotorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		wheelOrderBox.add(rightRotorComboBox);
		greekRotorComboBox = new JComboBox<String>(wheelOrderChoices2);
		greekRotorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		wheelOrderBox.add(greekRotorComboBox);
		greekRotorComboBox.setVisible(false);
		wheelOrderItems.put(machineChoices[0], wheelOrderChoices1);
		wheelOrderItems.put(machineChoices[1], wheelOrderChoices2);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		labelBox.add(verticalStrut_1);
		labelBox.add(wheelOrderLabel);
		
		verticalGlue_1 = Box.createVerticalGlue();
		choiceBox.add(verticalGlue_1);
		choiceBox.add(wheelOrderBox);

		//Ring settings
		ringSettingLabel = new JLabel("Ring Settings: ");
		ringSettingLabel.setFont(new Font("", Font.PLAIN, 20));
		ringSettingLabel.setVisible(true);
		leftRingComboBox = new JComboBox<String>(ringSettingChoice);
		leftRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ringSettingBox.add(leftRingComboBox);
		midRingComboBox = new JComboBox<String>(ringSettingChoice);
		midRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ringSettingBox.add(midRingComboBox);
		rightRingComboBox = new JComboBox<String>(ringSettingChoice);
		rightRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ringSettingBox.add(rightRingComboBox);
		greekRingComboBox = new JComboBox<String>(ringSettingChoice);
		greekRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ringSettingBox.add(greekRingComboBox);
		greekRingComboBox.setVisible(false);
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		labelBox.add(verticalStrut_2);
		labelBox.add(ringSettingLabel);
		
		verticalGlue_2 = Box.createVerticalGlue();
		choiceBox.add(verticalGlue_2);
		choiceBox.add(ringSettingBox);
		
		//Ground settings
		groundSettingLabel = new JLabel("Ground Settings: ");
		groundSettingLabel.setFont(new Font("", Font.PLAIN, 20));
		groundSettingLabel.setVisible(true);
		leftGroundComboBox = new JComboBox<String>(ringSettingChoice);
		leftGroundComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		leftRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		groundSettingBox.add(leftGroundComboBox);
		midGroundComboBox = new JComboBox<String>(ringSettingChoice);
		midGroundComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		midRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		groundSettingBox.add(midGroundComboBox);
		rightGroundComboBox = new JComboBox<String>(ringSettingChoice);
		rightGroundComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rightRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		groundSettingBox.add(rightGroundComboBox);
		greekGroundComboBox = new JComboBox<String>(ringSettingChoice);
		greekGroundComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		greekRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		groundSettingBox.add(greekGroundComboBox);
		greekGroundComboBox.setVisible(false);
		
		verticalStrut_3 = Box.createVerticalStrut(20);
		labelBox.add(verticalStrut_3);
		labelBox.add(groundSettingLabel);
		
		verticalGlue_3 = Box.createVerticalGlue();
		choiceBox.add(verticalGlue_3);
		choiceBox.add(groundSettingBox);
		
		plugboardLabel = new JLabel("Plugboard Connections: ");
		plugboardLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		//Plugboard field
		plugboardInput = new JFormattedTextField();
		plugboardInput.setFont(new Font("Tahoma", Font.PLAIN, 15));
		((AbstractDocument) plugboardInput.getDocument()).setDocumentFilter(new DocumentFilter(){
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				for (int i = 0; i < text.length(); i++) 
					if( (text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' || text.charAt(i) > 'Z') && text.charAt(0) != ' ')
						return;
				super.replace(fb, offset, length, text.toUpperCase(), attrs);
				}
			});
		
		inputLabel = new JLabel("Input: ");
		inputLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		inputField = new JFormattedTextField();
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		inputField.setEditable(false);
		//Following lines allow only alphabets to be typed and auto-"CAPS" them
		((AbstractDocument) inputField.getDocument()).setDocumentFilter(new DocumentFilter(){
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				for (int i = 0; i < text.length(); i++) 
					if( (text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' || text.charAt(i) > 'Z') && text.charAt(0) != ' ')
						return;
				super.replace(fb, offset, length, text.toUpperCase(), attrs);
			}
		});
		inputField.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				enigma.resetMachine();
				outputField.setText(enigma.print(inputField.getText()));
				updateStates();
			}
			
			public void insertUpdate(DocumentEvent e) {
				enigma.resetMachine();
				outputField.setText(enigma.print(inputField.getText()));
				updateStates();
			}
			public void changedUpdate(DocumentEvent e) {}
		});
		
		outputLabel = new JLabel("Output: ");
		outputLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		outputField = new JTextField();
		outputField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		outputField.setColumns(10);
		outputField.setEditable(false);
		
		//Button to build machine
		saveButton = new JButton("SAVE");
		saveButton.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 15));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputField.setText("");
				leftStateField.setText("");
				rightStateField.setText("");
				midStateField.setText("");
				greekStateField.setText("");
				runEnigma();
			}
		});
		
		leftStateLabel = new JLabel("Left State:");
		leftStateLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		midStateLabel = new JLabel("Middle State:");
		midStateLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		rightStateLabel = new JLabel("Right State: ");
		rightStateLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		greekStateLabel = new JLabel("Greek State:");
		greekStateLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		greekStateLabel.setVisible(false);
		
		greekStateField = new JTextField();
		greekStateField.setEditable(false);
		greekStateField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		greekStateField.setColumns(10);
		greekStateField.setVisible(false);
		
		leftStateField = new JTextField();
		leftStateField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		leftStateField.setEditable(false);
		leftStateField.setColumns(10);
		
		midStateField = new JTextField();
		midStateField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		midStateField.setEditable(false);
		midStateField.setColumns(10);
		
		rightStateField = new JTextField();
		rightStateField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rightStateField.setEditable(false);
		rightStateField.setColumns(10);
		
		//GUI Layout settings
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(236)
					.addComponent(box1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(90)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rightStateLabel)
						.addComponent(midStateLabel, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(greekStateLabel, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
							.addGap(36)
							.addComponent(greekStateField, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(leftStateLabel)
							.addGap(66)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(midStateField, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
								.addComponent(rightStateField, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
								.addComponent(leftStateField, 0, 0, Short.MAX_VALUE))))
					.addContainerGap(336, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(plugboardLabel)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(outputLabel)
								.addComponent(inputLabel))
							.addGap(42)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(outputField, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
						.addComponent(inputField, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
						.addComponent(plugboardInput, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(saveButton)
					.addGap(98)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(47)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(box1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(greekStateLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(greekStateField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(leftStateLabel)
								.addComponent(leftStateField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(midStateLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(midStateField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rightStateLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(rightStateField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))))
					.addGap(92)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(plugboardInput, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(plugboardLabel)
						.addComponent(saveButton))
					.addGap(66)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(inputField, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
						.addComponent(inputLabel))
					.addGap(54)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(outputField, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addComponent(outputLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(72))
		);
		setLayout(groupLayout);
	}

	/*
	 * Works on ActionListener of machineComboBox
	 * Toggles between M3 and M4 options
	 */
	public void actionPerformed(ActionEvent e){
		String item = (String)machineComboBox.getSelectedItem();
		Object o1 = reflectorItems.get(item);
		if(o1 == null)
			reflectorComboBox.setModel(new DefaultComboBoxModel());
 		else
			reflectorComboBox.setModel(new DefaultComboBoxModel((String[])o1));
		o1 = wheelOrderItems.get(item);
		if(o1 == null)
			leftRotorComboBox.setModel(new DefaultComboBoxModel());
		else
			leftRotorComboBox.setModel(new DefaultComboBoxModel((String[])o1));
		if(o1 == null)
			midRotorComboBox.setModel(new DefaultComboBoxModel());
		else
			midRotorComboBox.setModel(new DefaultComboBoxModel(wheelOrderChoices1));
		if(o1 == null)
			rightRotorComboBox.setModel(new DefaultComboBoxModel());
		else
			rightRotorComboBox.setModel(new DefaultComboBoxModel(wheelOrderChoices1));
		if(o1 == null)
			greekRotorComboBox.setModel(new DefaultComboBoxModel());
		else{
			greekRotorComboBox.setModel(new DefaultComboBoxModel(wheelOrderChoices1));
			if("M4".equals(item))
				greekRotorComboBox.setVisible(true);
			else
				greekRotorComboBox.setVisible(false);
		}
		if(o1 == null)
			leftRingComboBox.setModel(new DefaultComboBoxModel());
		else
			leftRingComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
		if(o1 == null)
			midRingComboBox.setModel(new DefaultComboBoxModel());
		else
			midRingComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
		if(o1 == null)
			rightRingComboBox.setModel(new DefaultComboBoxModel());
		else
			rightRingComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
		if(o1 == null)
			rightRingComboBox.setModel(new DefaultComboBoxModel());
		else{
			greekRingComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
			if("M4".equals(item))
				greekRingComboBox.setVisible(true);
			else
				greekRingComboBox.setVisible(false);
		}
		if(o1 == null)
			leftGroundComboBox.setModel(new DefaultComboBoxModel());
		else
			leftGroundComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
		if(o1 == null)
			midGroundComboBox.setModel(new DefaultComboBoxModel());
		else
			midGroundComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
		if(o1 == null)
			rightGroundComboBox.setModel(new DefaultComboBoxModel());
		else
			rightGroundComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
		if(o1 == null)
			rightGroundComboBox.setModel(new DefaultComboBoxModel());
		else{
			greekGroundComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
			if("M4".equals(item))
				greekGroundComboBox.setVisible(true);
			else
				greekGroundComboBox.setVisible(false);
		}
		
		if("M4".equals(item)) {
			greekStateLabel.setVisible(true);
			greekStateField.setVisible(true);
		}
		else {
			greekStateLabel.setVisible(false);
			greekStateField.setVisible(false);
		}
	}
	
	/**
	 * Builds the Enigma machine based on the options selected in the GUI
	 */
	private void runEnigma() {
		mType = machineComboBox.getSelectedItem().toString();
		
		if("M3".equals(mType)) {
			if(!Enigma.isDistinct(ENIGMA_ROTORS[leftRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[midRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[rightRotorComboBox.getSelectedIndex()])) {
				inputField.setEditable(false);
				outputField.setText("SELECT DISTINCT ROTORS!");
			}
			else {
				inputField.setEditable(true);
				outputField.setText("");
			}
		}
		else {
			if(!Enigma.isDistinct(ENIGMA_ROTORS[midRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[rightRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[greekRotorComboBox.getSelectedIndex()])) {
				inputField.setEditable(false);
				outputField.setText("SELECT DISTINCT ROTORS!");
			}
			else {
				inputField.setEditable(true);
				outputField.setText("");
			}
		}
			
						
		if("M4".equals(mType))
			enigma = new Enigma(GREEK_ROTORS[leftRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[midRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[rightRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[greekRotorComboBox.getSelectedIndex()], GREEK_REFLECTORS[reflectorComboBox.getSelectedIndex()]);
		else
			enigma = new Enigma(ENIGMA_ROTORS[leftRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[midRotorComboBox.getSelectedIndex()], ENIGMA_ROTORS[rightRotorComboBox.getSelectedIndex()], ENIGMA_REFLECTORS[reflectorComboBox.getSelectedIndex()]);
		
		if("M4".equals(mType)) {
			enigma.getGreekRotor().setRingHead((char)leftRingComboBox.getSelectedItem().toString().charAt(0));
			enigma.getGreekRotor().setRotorHead((char)leftGroundComboBox.getSelectedItem().toString().charAt(0));
		}
		enigma.getLeftRotor().setRingHead(midRingComboBox.getSelectedItem().toString().charAt(0));
		enigma.getLeftRotor().setRotorHead(midGroundComboBox.getSelectedItem().toString().charAt(0));
		enigma.getMidRotor().setRingHead(rightRingComboBox.getSelectedItem().toString().charAt(0));
		enigma.getMidRotor().setRotorHead(rightGroundComboBox.getSelectedItem().toString().charAt(0));
		enigma.getRightRotor().setRingHead(greekRingComboBox.getSelectedItem().toString().charAt(0));
		enigma.getRightRotor().setRotorHead(greekGroundComboBox.getSelectedItem().toString().charAt(0));
		
		enigma.resetPlugboard();
		Scanner in = new Scanner(plugboardInput.getText());
		while(in.hasNext()) {
			String wire = in.next();
			if(wire.length() == 2) {
				char src = wire.charAt(0);
				char dest = wire.charAt(1);
				if(!enigma.isPlugged(src) && !enigma.isPlugged(dest) && src != dest)
					enigma.addPlugboardWire(src, dest);
			}
		}
		in.close();
	}
	
	/**
	 * Updates the alphabet cycle for all stateField(s)
	 */
	private void updateStates() {
		if("M4".equals(mType))
			greekStateField.setText(enigma.getGreekRotor().getRotorHead() + "");
		leftStateField.setText(enigma.getLeftRotor().getRotorHead() + "");
		midStateField.setText(enigma.getMidRotor().getRotorHead() + "");
		rightStateField.setText(enigma.getRightRotor().getRotorHead() + "");
	}

	/**
	 * Creates and runs the GUI
	 */
	private static void createAndShowUI(){
		JFrame frame = new JFrame("Enigma Machine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Main());
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				createAndShowUI();
				System.gc();
			}
		});
	}
}
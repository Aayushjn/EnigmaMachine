import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.*;


public class Test extends JPanel implements ActionListener{

	private JComboBox<String> machineComboBox;
	private JComboBox<String> reflectorComboBox;
	private JComboBox<String> leftRotorComboBox;
	private JComboBox<String> midRotorComboBox;
	private JComboBox<String> rightRotorComboBox;
	private JComboBox<String> splRotorComboBox;
	private JComboBox<String> leftRingComboBox;
	private JComboBox<String> midRingComboBox;
	private JComboBox<String> rightRingComboBox;
	private JComboBox<String> splRingComboBox;
	
	private Hashtable<String, String[]> wheelOrderItems = new Hashtable<String, String[]>();
	private Hashtable<String, String[]> reflectorItems = new Hashtable<String, String[]>();
	private static String[] wheelOrderChoices1 = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII"};
	private static String[] ringSettingChoice = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	private Component verticalStrut;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalGlue;
	private Component verticalGlue_1;
	private JLabel plugboardLabel;
	private JLabel outputLabel;
	private JTextField outputField;
	private JFormattedTextField inputField;
	private JFormattedTextField plugboardInput;

	public Test(){
		setBackground(new Color(192, 192, 192));
		Box labelBox = Box.createVerticalBox();				//Left-side label box
		Box box = Box.createHorizontalBox();
		Box choiceBox = Box.createVerticalBox();			//Right-side choice box
		Box wheelOrderBox = Box.createHorizontalBox();		//Horizontal box for multiple menus	
		Box ringSettingBox = Box.createHorizontalBox();
		box.add(labelBox);
		box.add(choiceBox);

		//JLabel is a label I used for every JComboBox
		JLabel machineLabel = new JLabel("Enigma type: ");
		machineLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		machineLabel.setVisible(true);
		String[] machineChoices = {"Select type", "M3", "M4"};
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(machineChoices);
		machineComboBox = new JComboBox(comboModel);
		machineComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		machineComboBox.setVisible(true);
		//ActionListener allows for dynamic switching
		machineComboBox.addActionListener(this);
		labelBox.add(machineLabel);
		choiceBox.add(machineComboBox);
		
		JLabel reflectorLabel = new JLabel("Reflector: ");
		reflectorLabel.setFont(new Font("", Font.PLAIN, 20));
		reflectorLabel.setVisible(true);
		reflectorComboBox = new JComboBox<String>();
		reflectorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		String[] reflectorChoices1 = {"B", "C"};
		reflectorItems.put(machineChoices[1], reflectorChoices1);
		String[] reflectorChoices2 = {"B (thin)", "C (thin)"};
		reflectorItems.put(machineChoices[2], reflectorChoices2);
		
		verticalStrut = Box.createVerticalStrut(20);
		labelBox.add(verticalStrut);
		labelBox.add(reflectorLabel);
		
		verticalGlue = Box.createVerticalGlue();
		choiceBox.add(verticalGlue);
		choiceBox.add(reflectorComboBox);

		JLabel wheelOrderLabel = new JLabel("Wheel Order: ");
		wheelOrderLabel.setFont(new Font("", Font.PLAIN, 20));
		wheelOrderLabel.setVisible(true);
		leftRotorComboBox = new JComboBox<String>();
		leftRotorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		midRotorComboBox = new JComboBox<String>();
		midRotorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rightRotorComboBox = new JComboBox<String>();
		rightRotorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		splRotorComboBox = new JComboBox<String>();
		splRotorComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		wheelOrderItems.put(machineChoices[1], wheelOrderChoices1);
		String[] wheelOrderChoices2 = {"Beta", "Gamma"};
		wheelOrderItems.put(machineChoices[2], wheelOrderChoices2);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		labelBox.add(verticalStrut_1);
		labelBox.add(wheelOrderLabel);
		wheelOrderBox.add(leftRotorComboBox);
		wheelOrderBox.add(midRotorComboBox);
		wheelOrderBox.add(rightRotorComboBox);
		wheelOrderBox.add(splRotorComboBox);
		//IVth sub-menu is hidden by default
		splRotorComboBox.setVisible(false);
		
		verticalGlue_1 = Box.createVerticalGlue();
		choiceBox.add(verticalGlue_1);
		choiceBox.add(wheelOrderBox);

		JLabel ringSettingLabel = new JLabel("Ring Settings: ");
		ringSettingLabel.setFont(new Font("", Font.PLAIN, 20));
		ringSettingLabel.setVisible(true);
		leftRingComboBox = new JComboBox<String>();
		leftRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		midRingComboBox = new JComboBox<String>();
		midRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rightRingComboBox = new JComboBox<String>();
		rightRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		splRingComboBox = new JComboBox<String>();
		splRingComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		labelBox.add(verticalStrut_2);
		labelBox.add(ringSettingLabel);
		ringSettingBox.add(leftRingComboBox);
		ringSettingBox.add(midRingComboBox);
		ringSettingBox.add(rightRingComboBox);
		ringSettingBox.add(splRingComboBox);
		splRingComboBox.setVisible(false);
		verticalGlue_1 = Box.createVerticalGlue();
		choiceBox.add(verticalGlue_1);
		choiceBox.add(ringSettingBox);
		
		plugboardLabel = new JLabel("Plugboard Connections: ");
		plugboardLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel inputLabel = new JLabel("Input: ");
		inputLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		outputLabel = new JLabel("Output: ");
		outputLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		outputField = new JTextField();
		outputField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		outputField.setColumns(10);
		outputField.setEditable(false);
		
		JButton button = new JButton("SAVE");
		button.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 15));
		button.addActionListener(this);
		
		inputField = new JFormattedTextField();
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		((AbstractDocument) inputField.getDocument()).setDocumentFilter(new DocumentFilter(){
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				for (int i = 0; i < text.length(); i++) 
					if( (text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' || text.charAt(i) > 'Z') && text.charAt(0) != ' ')
						return;
				super.replace(fb, offset, length, text.toUpperCase(), attrs);
			}
		});
		
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
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(485)
							.addComponent(box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(397)
							.addComponent(plugboardLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(plugboardInput, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(41)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(outputLabel)
								.addComponent(inputLabel))
							.addGap(17)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(outputField, GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
								.addComponent(inputField, GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE))))
					.addGap(120))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(119)
					.addComponent(box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(45)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(plugboardLabel)
						.addComponent(button)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(plugboardInput, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
					.addGap(90)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(inputLabel)
						.addComponent(inputField, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(66)
							.addComponent(outputLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(54)
							.addComponent(outputField, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
					.addGap(167))
		);
		setLayout(groupLayout);

		//TODO: Add Enigma functionality


	}

	//Change menus based on type of machine
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
			rightRotorComboBox.setModel(new DefaultComboBoxModel());
		else{
			splRotorComboBox.setModel(new DefaultComboBoxModel(wheelOrderChoices1));
			if("M4".equals(item))
				splRotorComboBox.setVisible(true);
			else
				splRotorComboBox.setVisible(false);
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
			splRingComboBox.setModel(new DefaultComboBoxModel(ringSettingChoice));
			if("M4".equals(item))
				splRingComboBox.setVisible(true);
			else
				splRingComboBox.setVisible(false);
		}
	}

	private static void createAndShowUI(){
		JFrame frame = new JFrame("Enigma Machine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Test());
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				createAndShowUI();
			}
		});
	}
}
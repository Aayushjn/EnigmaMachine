package machine;

public class Enigma {
	//Machine configuration
	private String machineType;
	private Rotor rightRotor;
	private Rotor midRotor;
	private Rotor leftRotor;
	private Rotor greekRotor;
	private Reflector reflector;
	private int[] plugboard;
	
	//Available rotors
	public static final String[] I = {"EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"};
	public static final String[] II = {"AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"};
	public static final String[] III = {"BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"};
	public static final String[] IV = {"ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"};
	public static final String[] V = {"VZBRGITYUPSDNHLXAWMJQOFECK", "Z"};
	public static final String[] VI = {"JPGVOUMFYQBENHZRDKASXLICTW", "Z/M"};
	public static final String[] VII = {"NZJHGRCXMYSWBOUFAIVLPEKQDT", "Z/M"};
	public static final String[] VIII = {"FKQHTLXOCBJSPDZRAMEWNIUYGV", "Z/M"};
	//Greek rotors for M4 machine
	public static final String BETA = "LEYJVCNIXWPBQMDRTAKZGFUHOS";
	public static final String GAMMA = "FSOKANUERHMBTIYCWLQPZXVGJD";
	
	//Available reflectors
	public static final String B = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
	public static final String C = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
	//Thin reflectors for M4 machine
	public static final String B_THIN = "ENKQAUYWJICOPBLMDXZVFTHRGS";
	public static final String C_THIN = "RDOBJNTKVEHMLFCWZAXGYIPSUQ";
	
	
	//Build M3 enigma
	public Enigma(String[] left, String[] mid, String[] right, String ref) {
		//Check for 1 notch or 2 notches
		if("VI".equals(left[0]) || "VII".equals(left[0]) || "VIII".equals(left[0]))
			this.leftRotor = new Rotor(left[0], left[1].charAt(0), left[1].charAt(2));
		else
			this.leftRotor = new Rotor(left[0], left[1].charAt(0));
		if("VI".equals(mid[0]) || "VII".equals(mid[0]) || "VIII".equals(mid[0]))
			this.midRotor = new Rotor(mid[0], mid[1].charAt(0), mid[1].charAt(2));
		else
			this.midRotor = new Rotor(mid[0], mid[1].charAt(0));
		if("VI".equals(right[0]) || "VII".equals(right[0]) || "VIII".equals(right[0]))
			this.rightRotor = new Rotor(right[0], right[1].charAt(0), right[1].charAt(2));
		else
			this.rightRotor = new Rotor(right[0], right[1].charAt(0));
		this.reflector = new Reflector(ref);
		
		plugboard = new int[26];
		resetPlugboard();
	}
	
	//Build M4 enigma
	public Enigma(String greek, String[] left, String[] mid, String[] right, String ref) {		
		this.greekRotor = new Rotor(greek);
		if("VI".equals(left[0]) || "VII".equals(left[0]) || "VIII".equals(left[0]))
			this.leftRotor = new Rotor(left[0], left[1].charAt(0), left[1].charAt(2));
		else
			this.leftRotor = new Rotor(left[0], left[1].charAt(0));
		if("VI".equals(mid[0]) || "VII".equals(mid[0]) || "VIII".equals(mid[0]))
			this.midRotor = new Rotor(mid[0], mid[1].charAt(0), mid[1].charAt(2));
		else
			this.midRotor = new Rotor(mid[0], mid[1].charAt(0));
		if("VI".equals(right[0]) || "VII".equals(right[0]) || "VIII".equals(right[0]))
			this.rightRotor = new Rotor(right[0], right[1].charAt(0), right[1].charAt(2));
		else
			this.rightRotor = new Rotor(right[0], right[1].charAt(0));
		this.reflector = new Reflector(ref);
		
		plugboard = new int[26];
		resetPlugboard();
	}
	
	//Check if selected rotors are distinct
	public static boolean isDistinct(String[] left, String[] mid, String[] right) {
		if(left[0].equals(mid[0]) || left[0].equals(right[0]) || mid[0].equals(right[0]))
			return false;
		return true;
	}
	
	//Returns the encrypted string
	public String print(String text) {
		String output = "";
		for(int i = 0;i < text.length();i++)
			if(text.charAt(i) >= 'A' && text.charAt(i) <= 'Z')
				output += rotorEncryption(text.charAt(i));
			else if(text.charAt(i) == ' ' || text.charAt(i) == '\n')
				output += text.charAt(i);
		return output;
	}
	
	//Encrypt individual characters of the plaintext and return them
	private char rotorEncryption(char inputChar) {
		//Rotate conditions
		if(midRotor.getNotch() == midRotor.getRotorHead()) {
			leftRotor.rotate();
			midRotor.rotate();
		}
		if(rightRotor.getNotch() == rightRotor.getRotorHead())
			midRotor.rotate();
		
		//Right rotor rotates every time
		rightRotor.rotate();
		
		int input = inputChar - 'A';
		
		//Pass by plugboard
		if(plugboard[input] != -1)
			input = plugboard[input];
		
		//Pass 1
		int output = rightRotor.convertForward(input);
		output = midRotor.convertForward(output);
		output = leftRotor.convertForward(output);
		if("M4".equals(machineType))
			output = greekRotor.convertForward(output);
		output = reflector.convertForward(output);
		
		//Pass 2
		if("M4".equals(machineType))
			output = greekRotor.convertBackward(output);
		output = leftRotor.convertBackward(output);
		output = midRotor.convertBackward(output);
		output = rightRotor.convertBackward(output);
		
		//2nd pass through plugboard
		if(plugboard[output] != -1)
			output = plugboard[output];
		
		return (char)(output + 'A');
	}
	
	//Returns the Greek rotor
	public Rotor getGreekRotor() {
		return greekRotor;
	}
	
	//Returns the left rotor
	public Rotor getLeftRotor() {
		return leftRotor;
	}
	
	//Returns the middle rotor
	public Rotor getMidRotor() {
		return midRotor;
	}
	
	//Returns the right rotor
	public Rotor getRightRotor() {
		return rightRotor;
	}
	
	//Make a plugboard connection
	public void addPlugboardWire(char a, char b) {
		this.plugboard[a - 'A'] = b - 'A';
		this.plugboard[b - 'A'] = a - 'A';
	}
	
	//Remove a plugboard connection
	public void removePlugboardWire(char c) {
		this.plugboard[this.plugboard[c - 'A']] = -1;
		this.plugboard[c - 'A'] = -1;
	}
	
	//Check if a letter is already connected
	public boolean isPlugged(char c) {
		return plugboard[c - 'A'] != -1;
	}
	
	//Reset all plugboard values
	public void resetPlugboard() {
		for(int wire = 0;wire < 26;wire++)
			this.plugboard[wire] = -1;
	}
	
	//Reset all rotors
	public void resetMachine() {
		leftRotor.reset();
		midRotor.reset();
		rightRotor.reset();
		if("M4".equals(machineType))
			greekRotor.reset();
	}
}
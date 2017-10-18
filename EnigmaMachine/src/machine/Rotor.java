package machine;

public class Rotor {
	private int rotorHead;
	private int ringHead;
	private int[] cipher = new int[26];						//Used for 1st pass during encryption
	private int[] revCipher = new int[26];					//Used for 2nd pass during encryption
	private char notch1;
	private char notch2 = '?';								//Used only for rotors VI, VII and VIII
	private int position;
	
	//Used for rotors VI, VII and VIII
	protected Rotor(String str, char notch1, char notch2) {
		this.notch1 = notch1;
		this.notch2 = notch2;
		for(int i = 0;i < 26;i++) {
			int src = (char)('A' + i);
			int dest = str.charAt(i);
			if(src < dest)
				cipher[i] = dest - src;
			else
				cipher[i] = (26 - (src - dest)) % 26;
			revCipher[(cipher[i] + i) % 26] = cipher[i];
		}
	}
	
	//Used for rotors I, II, III, IV and V
	protected Rotor(String str, char notch) {
		notch1 = notch;
		for(int i = 0;i < 26;i++) {
			int src = (char)('A' + i);
			int dest = str.charAt(i);
			if(src < dest)
				cipher[i] = dest - src;
			else
				cipher[i] = (26 - (src - dest)) % 26;
			revCipher[(cipher[i] + i) % 26] = cipher[i];
		}
	}
	
	//Used for Greek rotors
	protected Rotor(String str) {
		notch1 = '\0';
		for(int i = 0;i < 26;i++) {
			int src = (char)('A' + i);
			int dest = str.charAt(i);
			if(src < dest)
				cipher[i] = dest - src;
			else
				cipher[i] = (26 - (src - dest)) % 26;
			revCipher[(cipher[i] + i) % 26] = cipher[i];
		}
	}
	
	//Initialize rotorHead and position
	public void setRotorHead(char c) {
		rotorHead = c - 'A';
		position = 0;
	}
	
	//Initialize ringHead
	public void setRingHead(char c) {
		ringHead = c - 'A';
	}
	
	//Returns notch (works only for M3 machine)
	public char getNotch() {
		//Checking for rotors VI, VII and VIII
		if(this.notch2 == '?')
			return this.notch1;
		else {
			if((this.getRotorHead() - this.notch1) < (this.getRotorHead() - this.notch2) || (this.notch1 - this.getRotorHead()) < (this.notch2 - this.getRotorHead()))
				return this.notch1;
			else
				return this.notch2;
		}
	}
	
	//Returns character at rotorHead
	public char getRotorHead() {
		return (char)('A' + (rotorHead + position) % 26);
	}
	
	//Returns character at ringHead
	public char getRingHead() {
		return (char)('A' + (ringHead + position) % 26);
	}
	
	//Rotate after encrypting a letter
	protected void rotate() {
		position = (position + 1) % 26;
	}
	
	//Pass 1 encryption
	protected int convertForward(int pos) {
		int diff;
		if(rotorHead >= ringHead)
			diff = rotorHead - ringHead;
		else
			diff = 26 - (rotorHead - ringHead);
		return (pos + cipher[(pos + position + diff) % 26]) % 26;
	}
	
	//Pass 2 encryption
	protected int convertBackward(int pos) {
		int diff;
		if(rotorHead >= ringHead)
			diff = rotorHead - ringHead;
		else
			diff = 26 - (rotorHead - ringHead);
		int jump = pos - revCipher[(pos + position + diff) % 26];
		if(jump > 0)
			return (jump % 26);
		else
			return ((jump + 26) % 26);
	}
	
	//Reset rotor position
	public void reset() {
		position = 0;
	}
}
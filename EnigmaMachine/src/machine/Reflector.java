package machine;

public class Reflector {
	private int[] reflection = new int[26];
	
	/**
	 * Builds reflector
	 * @param reflector Reflector
	 */
	public Reflector(String reflector) {
		for(int i = 0;i < 26;i++) {
			int src = (char)('A' + i);
			int dest = reflector.charAt(i);
			if(src < dest)
				reflection[i] = dest - src;
			else
				reflection[i] = (26 - (src - dest)) % 26;
		}
	}
	
	/**
	 * Performs a "forward rotate" on the reflector
	 * @param pos Position to be moved forward
	 * @return Returns rotated position
	 */
	protected int convertForward(int pos) {
		return ((pos + reflection[pos]) % 26);
	}
}
package com.aayush.model;

public class Rotor {
    private int rotorHead;
    private int ringHead;
    //Used for 1st pass during encryption
    private final int[] cipher = new int[26];
    //Used for 2nd pass during encryption
    private final int[] revCipher = new int[26];
    private final char notch1;
    //Used only for rotors VI, VII and VIII
    private char notch2 = '?';
    private int position;

    /**
     * Used to build rotors VI, VII and VII
     * @param str    Rotor string
     * @param notch1 1st turnover notch
     * @param notch2 2nd turnover notch
     */
    Rotor(String str, char notch1, char notch2) {
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

    /**
     * Used to build rotors I to V
     * @param str	Rotor string
     * @param notch Turnover notch
     */
    Rotor(String str, char notch) {
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

    /**
     * Used to build Greek rotors
     * @param str Rotor string
     */
    Rotor(String str) {
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

    /**
     * Initialize ring head
     * @param c Letter on the ring
     */
    public void setRingHead(char c) {
        ringHead = c - 'A';
    }

    /**
     * Returns notch
     * @return char Returns notch of current rotor
     */
    char getNotch() {
        //Checking for rotors VI, VII and VIII
        if(this.notch2 == '?')
            return this.notch1;
        else {
            if(Math.abs(this.getRotorHead() - this.notch1) <
                    Math.abs(this.getRotorHead() - this.notch2))
                return this.notch1;
            else
                return this.notch2;
        }
    }

    /**
     * Returns the letter on rotor head
     * @return char This returns the letter on the rotor head
     */
    public char getRotorHead() {
        return (char)('A' + (rotorHead + position) % 26);
    }

    /**
     * Initialize rotor head and rotor position
     * @param c Letter on the rotor
     */
    public void setRotorHead(char c) {
        rotorHead = c - 'A';
        position = 0;
    }

    /**
     * Rotates rotor after encrypting letter
     */
    void rotate() {
        position = (position + 1) % 26;
    }

    /**
     * Performs a "forward rotate" on the rotor
     * @param pos Position to be moved forward
     * @return Returns rotated position
     */
    int convertForward(int pos) {
        int diff;
        if(rotorHead >= ringHead)
            diff = rotorHead - ringHead;
        else
            diff = 26 - (rotorHead - ringHead);
        return (pos + cipher[(pos + position + diff) % 26]) % 26;
    }

    /**
     * Performs a "backward rotate" on the rotor (Occurs during 2nd pass)
     * @param pos Position to be moved backward
     * @return Returns rotated position
     */
    int convertBackward(int pos) {
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

    /**
     * Resets rotor position
     */
    void reset() {
        position = 0;
    }
}

package com.aayush.model;

import static com.aayush.util.Constants.VI;
import static com.aayush.util.Constants.VII;
import static com.aayush.util.Constants.VIII;

public class Enigma {
    private final String machineType;
    private final Rotor rightRotor;
    private final Rotor midRotor;
    private final Rotor leftRotor;
    private Rotor greekRotor;
    private final Reflector reflector;
    private final int[] plugboard;

    /**
     * Builds an M3 Enigma machine
     * @param left  Left rotor
     * @param mid   Middle rotor
     * @param right Right rotor
     * @param ref   Reflector
     */
    public Enigma(String[] left, String[] mid, String[] right, String ref) {
        this.machineType = "M3";
        //Check for 1 notch or 2 notches
        if(VI[0].equals(left[0]) || VII[0].equals(left[0]) || VIII[0].equals(left[0]))
            this.leftRotor = new Rotor(left[0], left[1].charAt(0), left[1].charAt(2));
        else
            this.leftRotor = new Rotor(left[0], left[1].charAt(0));
        if(VI[0].equals(mid[0]) || VII[0].equals(mid[0]) || VIII[0].equals(mid[0]))
            this.midRotor = new Rotor(mid[0], mid[1].charAt(0), mid[1].charAt(2));
        else
            this.midRotor = new Rotor(mid[0], mid[1].charAt(0));

        if(VI[0].equals(right[0]) || VII[0].equals(right[0]) || VIII[0].equals(right[0]))
            this.rightRotor = new Rotor(right[0], right[1].charAt(0), right[1].charAt(2));
        else
            this.rightRotor = new Rotor(right[0], right[1].charAt(0));
        this.reflector = new Reflector(ref);

        plugboard = new int[26];
        resetPlugboard();
    }

    /**
     * Builds an M4 Enigma machine
     * @param greek Greek rotor
     * @param left	Left rotor
     * @param mid	Middle rotor
     * @param right	Right rotor
     * @param ref	Reflector
     */
    public Enigma(String greek, String[] left, String[] mid, String[] right, String ref) {
        this.machineType = "M4";
        this.greekRotor = new Rotor(greek);
        if(VI[0].equals(left[0]) || VII[0].equals(left[0]) || VIII[0].equals(left[0]))
            this.leftRotor = new Rotor(left[0], left[1].charAt(0), left[1].charAt(2));
        else
            this.leftRotor = new Rotor(left[0], left[1].charAt(0));
        if(VI[0].equals(mid[0]) || VII[0].equals(mid[0]) || VIII[0].equals(mid[0]))
            this.midRotor = new Rotor(mid[0], mid[1].charAt(0), mid[1].charAt(2));
        else
            this.midRotor = new Rotor(mid[0], mid[1].charAt(0));
        if(VI[0].equals(right[0]) || VII[0].equals(right[0]) || VIII[0].equals(right[0]))
            this.rightRotor = new Rotor(right[0], right[1].charAt(0), right[1].charAt(2));
        else
            this.rightRotor = new Rotor(right[0], right[1].charAt(0));
        this.reflector = new Reflector(ref);

        plugboard = new int[26];
        resetPlugboard();
    }

    /**
     * Checks whether selected rotors are distinct
     * @param left	Left rotor
     * @param mid	Middle rotor
     * @param right Right rotor
     * @return boolean This returns true if selected rotors are distinct, and false otherwise
     */
    public static boolean isNotDistinct(String[] left, String[] mid, String[] right) {
        return left[0].equals(mid[0]) || left[0].equals(right[0]) || mid[0].equals(right[0]);
    }

    /**
     * Gives the encrypted string of input string
     * @param text Input string to be encrypted
     * @return String This returns the encrypted string
     */
    public String print(String text) {
        final StringBuilder sb = new StringBuilder();
        String output;
        for(int i = 0;i < text.length();i++)
            if(text.charAt(i) >= 'A' && text.charAt(i) <= 'Z')
                sb.append(rotorEncryption(text.charAt(i)));
            else if(text.charAt(i) == ' ' || text.charAt(i) == '\n')
                sb.append(text.charAt(i));
        output = sb.toString();
        return (output.substring(output.length() - text.length(), output.length()));
    }

    /**
     * Gives encryption of individual characters
     * @param inputChar Character to be encrypted
     * @return char Returns encrypted character
     */
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

    /**
     * Return the Greek rotor
     * @return Rotor This returns the Greek rotor
     */
    public Rotor getGreekRotor() {
        return greekRotor;
    }

    /**
     * Return the left rotor
     * @return Rotor This returns the left rotor
     */
    public Rotor getLeftRotor() {
        return leftRotor;
    }

    /**
     * Return the middle rotor
     * @return Rotor This returns the middle rotor
     */
    public Rotor getMidRotor() {
        return midRotor;
    }

    /**
     * Return the right rotor
     * @return Rotor This returns the right rotor
     */
    public Rotor getRightRotor() {
        return rightRotor;
    }

    /**
     * Links two letters to form a plugboard connection
     * @param a First parameter of addPlugboardWire method
     * @param b Second parameter of addPlugboardWire method
     */
    public void addPlugboardWire(char a, char b) {
        this.plugboard[a - 'A'] = b - 'A';
        this.plugboard[b - 'A'] = a - 'A';
    }

    /**
     * Check whether a character is already connected
     * @param c Letter to be checked
     * @return boolean This returns true if letter is already connected, else returns false
     */
    public boolean isNotPlugged(char c) {
        return plugboard[c - 'A'] == -1;
    }

    /**
     * Resets all plugboard connections
     */
    public void resetPlugboard() {
        for(int wire = 0;wire < 26;wire++)
            this.plugboard[wire] = -1;
    }

    /**
     * Resets all rotors of the machine
     */
    public void resetMachine() {
        leftRotor.reset();
        midRotor.reset();
        rightRotor.reset();
        if("M4".equals(machineType))
            greekRotor.reset();
    }
}

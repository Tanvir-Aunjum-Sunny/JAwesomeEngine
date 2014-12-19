package input;

public class KeyInput extends Input {
	public KeyInput(String componentname, int eventtype) {
		super(KEYBOARD_EVENT, componentname, eventtype);
	}

	public static final int KEY_PRESSED = 0;
	public static final int KEY_DOWN = 1;
	public static final int KEY_RELEASED = 2;
}
package display;

public abstract class Display {
	protected int width, height;
	protected int positionX, positionY;

	public abstract void bindMouse();

	public abstract void clear();

	public abstract void close();

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public abstract boolean isCloseRequested();

	public abstract boolean isMouseBound();

	public abstract void open(DisplayMode displaymode, PixelFormat pixelformat);

	public abstract void pollInputs();

	public abstract void resetMouse();

	public abstract void swap();

	public abstract void unbindMouse();
}
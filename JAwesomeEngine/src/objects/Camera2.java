package objects;

import matrix.Matrix4f;
import vector.Vector2f;

public class Camera2 extends GameObject2 implements Camera {
	public Camera2() {
		super();
	}

	public Camera2(Vector2f pos) {
		super(pos);
	}

	@Override
	public void updateBuffer() {
		Matrix4f mat = new Matrix4f();
		mat.setSubMatrix2(rotation.toMatrixf());
		mat.scale(getScale());
		mat.translate(getTranslation());
		mat.invert();
		mat.store(buf);
		buf.flip();
	}
}
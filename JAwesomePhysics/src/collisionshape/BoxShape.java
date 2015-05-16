package collisionshape;

import math.QuatMath;
import math.VecMath;
import matrix.Matrix3f;
import objects.CollisionShape;
import objects.CollisionShape3;
import objects.InertiaCalculator;
import objects.SupportCalculator;
import quaternion.Quaternionf;
import shapedata.BoxStructure;
import vector.Vector3f;

public class BoxShape extends CollisionShape3 implements BoxStructure {
	protected class BoxInertia implements InertiaCalculator<Quaternionf> {
		@Override
		public Quaternionf calculateInertia(float mass) {
			float fmass = mass / 12f;
			Matrix3f inertiaMatrix = new Matrix3f(fmass
					* (halfsize.y * halfsize.y * 4 + halfsize.z * halfsize.z
							* 4), 0, 0, 0, fmass
					* (halfsize.x * halfsize.x * 4 + halfsize.z * halfsize.z
							* 4), 0, 0, 0, fmass
					* (halfsize.x * halfsize.x * 4 + halfsize.y * halfsize.y
							* 4));
			return inertiaMatrix.toQuaternionDiagonalf();
		}
	}

	protected class BoxSupport implements SupportCalculator<Vector3f> {
		private CollisionShape<Vector3f, Quaternionf, Quaternionf> collisionshape;

		public BoxSupport(CollisionShape<Vector3f, Quaternionf, Quaternionf> cs) {
			collisionshape = cs;
		}

		@Override
		public Vector3f supportPointLocal(Vector3f direction) {
			Vector3f v = QuatMath.transform(
					collisionshape.getInverseRotation(), direction);
			return VecMath.multiplication(new Vector3f(v.x < 0 ? -1 : 1,
					v.y < 0 ? -1 : 1, v.z < 0 ? -1 : 1), halfsize);
		}

		@Override
		public Vector3f supportPointLocalNegative(Vector3f direction) {
			Vector3f v = QuatMath.transform(
					collisionshape.getInverseRotation(), direction);
			return VecMath.multiplication(new Vector3f(v.x < 0 ? 1 : -1,
					v.y < 0 ? 1 : -1, v.z < 0 ? 1 : -1), halfsize);
		}
	}

	Vector3f halfsize;

	public BoxShape(float x, float y, float z, float halfsizex,
			float halfsizey, float halfsizez) {
		super();
		translate(x, y, z);
		halfsize = new Vector3f(halfsizex, halfsizey, halfsizez);
		init();
	}

	public BoxShape(float x, float y, float z, Vector3f halfsize) {
		super();
		translate(x, y, z);
		this.halfsize = halfsize;
		init();
	}

	public BoxShape(Vector3f pos, float halfsizex, float halfsizey,
			float halfsizez) {
		super();
		translate(pos);
		halfsize = new Vector3f(halfsizex, halfsizey, halfsizez);
		init();
	}

	public BoxShape(Vector3f pos, Vector3f halfsize) {
		super();
		translate(pos);
		this.halfsize = halfsize;
		init();
	}

	@Override
	public InertiaCalculator<Quaternionf> createInertiaCalculator() {
		return new BoxInertia();
	}

	@Override
	public SupportCalculator<Vector3f> createSupportCalculator(
			CollisionShape<Vector3f, Quaternionf, Quaternionf> cs) {
		return new BoxSupport(cs);
	}

	@Override
	public Vector3f getHalfSize() {
		return halfsize;
	}

	@Override
	public Vector3f getSize() {
		return VecMath.scale(halfsize, 2);
	}

	private void init() {
		float diag = (float) Math.sqrt(halfsize.x * halfsize.x + halfsize.y
				* halfsize.y + halfsize.z * halfsize.z);
		setAABB(new Vector3f(-diag, -diag, -diag), new Vector3f(diag, diag,
				diag));
		supportcalculator = createSupportCalculator(this);
	}
}
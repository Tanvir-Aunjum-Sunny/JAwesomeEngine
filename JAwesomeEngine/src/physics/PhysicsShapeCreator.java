package physics;

import java.util.ArrayList;
import java.util.HashSet;

import collisionshape.BoxShape;
import collisionshape.CapsuleShape;
import collisionshape.ConvexShape;
import collisionshape.CylinderShape;
import collisionshape.EllipsoidShape;
import collisionshape.PlaneShape;
import collisionshape2d.ConvexShape2;
import collisionshape2d.EllipseShape;
import collisionshape2d.QuadShape;
import convexhull.ConvexHull3;
import convexhull.Quickhull2;
import math.VecMath;
import objects.ShapedObject2;
import objects.ShapedObject3;
import shapedata.BoxStructure;
import shapedata.CapsuleStructure;
import shapedata.CylinderStructure;
import shapedata.EllipsoidStructure;
import shapedata.PlaneStructure;
import shapedata2d.EllipseStructure;
import shapedata2d.QuadStructure;
import vector.Vector2f;
import vector.Vector3f;

public class PhysicsShapeCreator {
	public static BoxShape create(BoxStructure box) {
		return new BoxShape(box.getTranslation(), box.getHalfSize());
	}

	public static EllipsoidShape create(EllipsoidStructure capsule) {
		return new EllipsoidShape(capsule.getTranslation(), capsule.getRadiusX(), capsule.getRadiusY(),
				capsule.getRadiusZ());
	}

	public static CylinderShape create(CylinderStructure cylinder) {
		return new CylinderShape(cylinder.getTranslation(), cylinder.getRadius(), cylinder.getHalfHeight());
	}

	public static CapsuleShape create(CapsuleStructure capsule) {
		return new CapsuleShape(capsule.getTranslation(), capsule.getRadius(), capsule.getHalfHeight());
	}

	public static EllipseShape create(EllipseStructure ellipse) {
		return new EllipseShape(ellipse.getTranslation(), ellipse.getRadius(), ellipse.getHalfHeight());
	}

	public static QuadShape create(QuadStructure quad) {
		return new QuadShape(quad.getTranslation(), quad.getHalfSize());
	}

	public static PlaneShape create(PlaneStructure plane) {
		return new PlaneShape(plane.getTranslation(), plane.getHalfSize());
	}

	public static ConvexShape createHull(ShapedObject3 shapedobject) {
		Vector3f min = new Vector3f();
		Vector3f max = new Vector3f();
		VecMath.minMaxVectors(shapedobject.getVertices(), min, max);
		Vector3f center = new Vector3f();
		VecMath.addition(min, VecMath.scale(VecMath.subtraction(max, min, center), 0.5f, center), center);
		center.negate();
		HashSet<Vector3f> vert = new HashSet<Vector3f>(shapedobject.getVertices());
		for (Vector3f v : vert) {
			v.translate(center);
		}
		shapedobject.prerender();
		ConvexShape hull = ConvexHull3.computeConvexHull(new ArrayList<Vector3f>(shapedobject.getVertices()));
		hull.translateTo(shapedobject.getTranslation());
		return hull;
	}

	public static ConvexShape2 createHull(ShapedObject2 shapedobject) {
		Vector2f min = new Vector2f();
		Vector2f max = new Vector2f();
		VecMath.minMaxVectors(shapedobject.getVertices(), min, max);
		Vector2f center = new Vector2f();
		VecMath.addition(min, VecMath.scale(VecMath.subtraction(max, min, center), 0.5f, center), center);
		center.negate();
		HashSet<Vector2f> vert = new HashSet<Vector2f>(shapedobject.getVertices());
		for (Vector2f v : vert) {
			v.translate(center);
		}
		shapedobject.prerender();
		ConvexShape2 hull = Quickhull2.computeConvexHull(new ArrayList<Vector2f>(shapedobject.getVertices()));
		hull.translateTo(shapedobject.getTranslation());
		return hull;
	}
}
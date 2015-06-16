package physicsConvexTest;

import game.StandardGame;
import gui.Font;
import integration.VerletIntegration;

import java.awt.Color;

import loader.FontLoader;
import loader.ModelLoader;
import manifold.SimpleManifoldManager;
import math.VecMath;
import narrowphase.EPA;
import narrowphase.GJK;
import objects.RigidBody3;
import objects.ShapedObject;
import physics.PhysicsDebug;
import physics.PhysicsShapeCreator;
import physics.PhysicsSpace;
import positionalcorrection.ProjectionCorrection;
import quaternion.Quaternionf;
import resolution.ImpulseResolution;
import shape.Box;
import shape.Sphere;
import utils.Debugger;
import vector.Vector3f;
import broadphase.SAP;
import display.DisplayMode;
import display.GLDisplay;
import display.PixelFormat;
import display.VideoSettings;

public class ConvexTest extends StandardGame {
	PhysicsSpace space;
	RigidBody3 rb1;
	int tempdelta = 0;
	boolean impulseapplied = false;
	Debugger debugger;
	PhysicsDebug physicsdebug;

	@Override
	public void init() {
		initDisplay(new GLDisplay(), new DisplayMode(), new PixelFormat(),
				new VideoSettings());
		display.bindMouse();
		cam.setFlyCam(true);
		cam.translateTo(0f, 0f, 5);
		cam.rotateTo(0, 0);

		space = new PhysicsSpace(new VerletIntegration(), new SAP(), new GJK(
				new EPA()), new ImpulseResolution(), new ProjectionCorrection(
				0.01f), new SimpleManifoldManager<Vector3f>());
		space.setGlobalGravitation(new Vector3f(0, -8f, 0));

		Font font = FontLoader.loadFont("res/fonts/DejaVuSans.ttf");
		debugger = new Debugger(inputs, font, cam);
		physicsdebug = new PhysicsDebug(inputs, font, space);

		Box ground = new Box(0, -5, 0, 10, 1, 10);
		RigidBody3 rb = new RigidBody3(PhysicsShapeCreator.create(ground));

		space.addRigidBody(ground, rb);
		addObject(ground);

		ShapedObject bunny = ModelLoader.load("res/models/bunny.mobj");
		RigidBody3 bunnyBody = new RigidBody3(
				PhysicsShapeCreator.createHull(bunny));

		space.addRigidBody(bunny, bunnyBody);
		addObject(bunny);
	}

	@Override
	public void render() {
		debugger.render3d();
		debugger.begin();
		renderScene();
		physicsdebug.render3d();
	}

	@Override
	public void render2d() {
		render2dScene();
		debugger.end();
		debugger.render2d(fps, objects.size(), objects2d.size());
	}

	@Override
	public void update(int delta) {
		// System.out.println(rb1.getLinearVelocity());
		if (tempdelta > 200) {
			if (inputs.isMouseButtonDown("0")) {
				Box q = new Box(cam.getTranslation(), 0.5f, 0.5f, 0.5f);
				q.setColor(Color.BLUE);
				RigidBody3 rb = new RigidBody3(PhysicsShapeCreator.create(q));
				rb.setMass(0.1f);
				rb.setInertia(new Quaternionf(0.1f, 0, 0, 0));
				space.addRigidBody(q, rb);
				rb.applyCentralImpulse(VecMath.scale(cam.getDirection(), 3));
				addObject(q);
				tempdelta = 0;
			}
			if (inputs.isMouseButtonDown("1")) {
				Sphere c = new Sphere(cam.getTranslation(), 0.5f, 36, 36);
				c.setColor(Color.RED);
				RigidBody3 rb = new RigidBody3(PhysicsShapeCreator.create(c));
				rb.setMass(0.1f);
				rb.setInertia(new Quaternionf(0.03f, 0, 0, 0));
				space.addRigidBody(c, rb);
				rb.applyCentralImpulse(VecMath.scale(cam.getDirection(), 3));
				addObject(c);
				tempdelta = 0;
			}
		} else {
			tempdelta += delta;
		}

		debugger.update();
		space.update(delta);
		physicsdebug.update();

		if (display.isMouseBound())
			cam.update(delta);
	}
}
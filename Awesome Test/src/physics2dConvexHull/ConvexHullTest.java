package physics2dConvexHull;

import game.StandardGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import objects.ShapedObject2;
import utils.GLConstants;
import vector.Vector2f;
import convexhull.Quickhull2;
import display.DisplayMode;
import display.GLDisplay;
import display.PixelFormat;
import display.VideoSettings;

public class ConvexHullTest extends StandardGame {

	@Override
	public void init() {
		initDisplay(new GLDisplay(), new DisplayMode(),
				new PixelFormat().withSamples(0), new VideoSettings());
		cam.setFlyCam(true);
		cam.translateTo(0.5f, 0f, 5);
		cam.rotateTo(0, 0);

		List<Vector2f> pointcloud = new ArrayList<Vector2f>();
		for (int i = 0; i < 100; i++) {
			pointcloud.add(new Vector2f(Math.random() * 500 + 20,
					Math.random() * 500 + 20));
		}
		add2dObject(new PointCloud(pointcloud));

		List<Vector2f> convexHull = Quickhull2.computeConvexHull(pointcloud)
				.getVertices();
		add2dObject(new ConvexHull(convexHull));
		System.out.println("Point Count: " + pointcloud.size()
				+ "; Hull Size: " + convexHull.size());
	}

	@Override
	public void render() {
		renderScene();
	}

	@Override
	public void render2d() {
		render2dScene();
	}

	@Override
	public void update(int delta) {
		cam.update(delta);
	}

	private class PointCloud extends ShapedObject2 {
		public PointCloud(List<Vector2f> points) {
			setRenderMode(GLConstants.POINTS);
			for (int i = 0; i < points.size(); i++) {
				addVertex(points.get(i), Color.GRAY, new Vector2f(0, 0));
				addIndex(i);
			}
			prerender();
		}
	}

	private class ConvexHull extends ShapedObject2 {
		public ConvexHull(List<Vector2f> hull) {
			setRenderMode(GLConstants.LINE_LOOP);
			for (int i = 0; i < hull.size(); i++) {
				addVertex(hull.get(i), Color.GRAY, new Vector2f(0, 0));
				addIndex(i);
			}
			prerender();
		}
	}
}

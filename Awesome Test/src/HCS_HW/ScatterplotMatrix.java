package HCS_HW;

import java.util.ArrayList;
import java.util.List;

import display.DisplayMode;
import display.GLDisplay;
import display.PixelFormat;
import display.VideoSettings;
import game.StandardGame;
import gui.Color;
import gui.Font;
import gui.Text;
import loader.FontLoader;
import loader.ShaderLoader;
import objects.ShapedObject2;
import shader.Shader;
import shape2d.Circle;
import shape2d.Quad;
import sound.NullSoundEnvironment;
import utils.GLConstants;
import vector.Vector2f;
import vector.Vector4f;

public class ScatterplotMatrix extends StandardGame {
	final int NUM_MARKEN = 10;
	final int MAX_SPEED = 350;
	final int MAX_PS = 750;
	final int MAX_GAENGE = 8;
	final int GRIDSIZE = 100;

	final int OFFSET_X = 140;
	final int OFFSET_Y = 50;

	public static void main(String[] args) {
		ScatterplotMatrix test = new ScatterplotMatrix();
		test.start();
	}

	@Override
	public void init() {
		int sizeX = 4 * GRIDSIZE + OFFSET_X;
		int sizeY = 4 * GRIDSIZE + OFFSET_Y;
		initDisplay(new GLDisplay(), new DisplayMode(sizeX, sizeY, "HCS Scatterplot Matrix", true),
				new PixelFormat().withSamples(0), new VideoSettings(sizeX, sizeY), new NullSoundEnvironment());

		List<PlotData> plotdata = new ArrayList<PlotData>();
		plotdata.add(new PlotData("VW Golf", 220, 110, 5));
		plotdata.add(new PlotData("BMW Z4", 235, 184, 6));
		plotdata.add(new PlotData("Audi TT", 228, 180, 6));
		plotdata.add(new PlotData("Trabant", 108, 26, 4));
		plotdata.add(new PlotData("Ferrari F138", 350, 750, 8));
		plotdata.add(new PlotData("Fiat Punto", 182, 105, 5));
		plotdata.add(new PlotData("Mitsubishi Lancer", 250, 280, 6));
		plotdata.add(new PlotData("Maserati Ghibli", 263, 330, 7));
		plotdata.add(new PlotData("Maybach S600", 250, 530, 7));
		plotdata.add(new PlotData("Mini Cooper", 175, 75, 5));

		Font font = FontLoader.loadFont("res/fonts/DejaVuSans.ttf");
		int ps = plotdata.size();

		int colorshaderID = ShaderLoader.loadShaderFromFile("res/shaders/colorshader.vert",
				"res/shaders/colorshader.frag");

		Shader whiteshader = new Shader(colorshaderID);
		whiteshader.addArgumentName("u_color");
		whiteshader.addArgument(new Vector4f(1f, 1f, 1f, 1f));
		whiteshader.addObject(new Quad(sizeX / 2f, sizeY / 2f, sizeX / 2f, sizeY / 2f));
		addShader2d(whiteshader);

		Shader blackshader = new Shader(
				ShaderLoader.loadShaderFromFile("res/shaders/colorshader.vert", "res/shaders/colorshader.frag"));
		blackshader.addArgumentName("u_color");
		blackshader.addArgument(new Vector4f(0f, 0f, 0f, 1f));
		addShader2d(blackshader);

		Text t11 = new Text("Automarke", 150, 20, font, 10);
		Text t12 = new Text("Automarke", 40, 95, font, 10);
		Text t21 = new Text("Hoechstgeschwindigkeit", 230, 20, font, 10);
		Text t22 = new Text("Hoechstgeschwindigkeit", 10, 195, font, 10);
		Text t31 = new Text("PS", 380, 20, font, 10);
		Text t32 = new Text("PS", 60, 295, font, 10);
		Text t41 = new Text("Anzahl Gaenge", 450, 20, font, 10);
		Text t42 = new Text("Anzahl Gaenge", 30, 395, font, 10);
		blackshader.addObject(t11);
		blackshader.addObject(t12);
		blackshader.addObject(t21);
		blackshader.addObject(t22);
		blackshader.addObject(t31);
		blackshader.addObject(t32);
		blackshader.addObject(t41);
		blackshader.addObject(t42);

		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 4; b++) {
				for (int c = 0; c < ps; c++) {
					PlotData pd = plotdata.get(c);
					Circle s = new Circle(a * GRIDSIZE + getValue(pd, a, GRIDSIZE) + OFFSET_X,
							b * GRIDSIZE + getValue(pd, b, GRIDSIZE) + OFFSET_Y, 2f, 12);
					s.setColor(Color.BLACK);
					blackshader.addObject(s);
				}
			}
		}

		ShapedObject2 gridLines = new ShapedObject2();
		gridLines.setRenderMode(GLConstants.LINES);
		for (int a = 0; a < 4; a++) {
			gridLines.addVertex(new Vector2f(a * GRIDSIZE + OFFSET_X, OFFSET_Y), Color.BLACK);
			gridLines.addVertex(new Vector2f(a * GRIDSIZE + OFFSET_X, 4 * GRIDSIZE + OFFSET_Y), Color.BLACK);
			gridLines.addVertex(new Vector2f(OFFSET_X, a * GRIDSIZE + OFFSET_Y), Color.BLACK);
			gridLines.addVertex(new Vector2f(4 * GRIDSIZE + OFFSET_X, a * GRIDSIZE + OFFSET_Y), Color.BLACK);
			gridLines.addIndices(a * 4, a * 4 + 1, a * 4 + 2, a * 4 + 3);
		}
		gridLines.prerender();
		blackshader.addObject(gridLines);
	}

	public float getValue(PlotData data, int whichValue, float gridsize) {
		float sizePerPiece;
		switch (whichValue) {
		case 0:
			sizePerPiece = gridsize / (NUM_MARKEN + 1);
			if (data.marke.equals("VW Golf")) {
				return 1 * sizePerPiece;
			}
			if (data.marke.equals("BMW Z4")) {
				return 2 * sizePerPiece;
			}
			if (data.marke.equals("Audi TT")) {
				return 3 * sizePerPiece;
			}
			if (data.marke.equals("Trabant")) {
				return 4 * sizePerPiece;
			}
			if (data.marke.equals("Ferrari F138")) {
				return 5 * sizePerPiece;
			}
			if (data.marke.equals("Fiat Punto")) {
				return 6 * sizePerPiece;
			}
			if (data.marke.equals("Mitsubishi Lancer")) {
				return 7 * sizePerPiece;
			}
			if (data.marke.equals("Maserati Ghibli")) {
				return 8 * sizePerPiece;
			}
			if (data.marke.equals("Maybach S600")) {
				return 9 * sizePerPiece;
			}
			if (data.marke.equals("Mini Cooper")) {
				return 10 * sizePerPiece;
			}
			break;
		case 1:
			sizePerPiece = gridsize / (MAX_SPEED + 1);
			return data.speed * sizePerPiece;
		case 2:
			sizePerPiece = gridsize / (MAX_PS + 1);
			return data.ps * sizePerPiece;
		case 3:
			sizePerPiece = gridsize / (MAX_GAENGE + 1);
			return data.gaenge * sizePerPiece;
		}
		return 0;
	}

	@Override
	public void render() {
		render3dLayer();
	}

	@Override
	public void render2d() {
		render2dLayer();
	}

	@Override
	public void update(int delta) {
		cam.update(delta);
	}

	private class PlotData {
		public String marke;
		public int speed, ps, gaenge;

		public PlotData(String m, int s, int p, int g) {
			marke = m;
			speed = s;
			ps = p;
			gaenge = g;
		}
	}

	@Override
	public void renderInterface() {

	}
}

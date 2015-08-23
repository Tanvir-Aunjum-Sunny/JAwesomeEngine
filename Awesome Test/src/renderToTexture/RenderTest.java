package renderToTexture;

import display.DisplayMode;
import display.GLDisplay;
import display.PixelFormat;
import display.VideoSettings;
import game.StandardGame;
import loader.FontLoader;
import loader.ShaderLoader;
import objects.Camera;
import shader.Shader;
import shape.Box;
import texture.FramebufferObject;
import texture.Texture;
import utils.Debugger;
import utils.ViewFrustum;
import vector.Vector3f;

public class RenderTest extends StandardGame {
	FramebufferObject rtt;
	Debugger debugger;

	@Override
	public void init() {
		initDisplay(new GLDisplay(), new DisplayMode(), new PixelFormat(), new VideoSettings());
		display.bindMouse();
		cam.setFlyCam(true);
		cam.translateTo(0, 2, 20);
		cam.rotateTo(0, 0);

		Shader defaultshader = new Shader(
				ShaderLoader.loadShaderFromFile("res/shaders/defaultshader.vert", "res/shaders/defaultshader.frag"));
		addShader(defaultshader);
		Shader defaultshader2 = new Shader(
				ShaderLoader.loadShaderFromFile("res/shaders/defaultshader.vert", "res/shaders/defaultshader.frag"));
		addShader2d(defaultshader2);

		debugger = new Debugger(inputs, defaultshader, defaultshader2, FontLoader.loadFont("res/fonts/DejaVuSans.ttf"),
				cam);

		// defaultshader.addObject(ModelLoader.load("res/models/bunny.mobj"));
		defaultshader.addObject(new Box(0, 0, -20, 100, 100, 1));

		rtt = new FramebufferObject(this, 800, 800, 0, new Camera(new Vector3f(0, 0, 12), 0, 0),
				new ViewFrustum(settings.getResolutionX() / (float) settings.getResolutionY(), settings.getZNear(),
						settings.getZFar(), 90));
		rtt.updateTexture();

		Shader screenshader = new Shader(
				ShaderLoader.loadShaderFromFile("res/shaders/textureshader.vert", "res/shaders/textureshader.frag"));
		screenshader.addArgumentName("u_texture");
		screenshader.addArgument(new Texture(framebuffer.getColorTextureID()));// rtt.getColorTextureID()));
		addShader(screenshader);

		ViewFrustum vf1 = new ViewFrustum(settings.getResolutionX() / (float) settings.getResolutionY(),
				settings.getZNear(), settings.getZFar(), settings.getFOVy());
		ViewFrustum vf2 = new ViewFrustum(settings.getResolutionX() / (float) settings.getResolutionY(),
				settings.getZNear(), settings.getZFar(), 90);
		System.out.println(vf1.getMatrix());
		System.out.println(vf2.getMatrix());

		Box screen = new Box(2, 3, 12, 1, 1, 0.1f);
		screen.setRenderHints(false, true, false);
		screenshader.addObject(screen);
	}

	@Override
	public void render() {
		debugger.begin();
		renderScene();
	}

	@Override
	public void render2d() {
		render2dScene();
		debugger.end();
	}

	@Override
	public void update(int delta) {
		rtt.updateTexture();
		debugger.update(fps, 0, 0);
		cam.update(delta);
	}

}

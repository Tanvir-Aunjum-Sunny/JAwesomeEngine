package shader4_Cube;

import game.StandardGame;
import gui.DisplayMode;
import gui.GLDisplay;
import gui.PixelFormat;
import gui.VideoSettings;
import loader.ShaderLoader;
import loader.TextureLoader;
import shader.Shader;
import shape.Box;
import shape.Sphere;
import texture.CubeEnvironmentMap;
import texture.Texture;
import vector.Vector4f;

public class ShaderTest4 extends StandardGame {
	Texture texture, diffuse, bumpmap;
	CubeEnvironmentMap cubemapper;

	@Override
	public void init() {
		initDisplay(new GLDisplay(), new DisplayMode(), new PixelFormat(),
				new VideoSettings());
		display.bindMouse();
		cam.setFlyCam(true);
		cam.translateTo(0, 0, 5);
		cam.rotateTo(0, 0);

		// Shader Test 1
		Shader colorshader = new Shader(ShaderLoader.loadShaderFromFile(
				"res/shaders/colorshader.vert", "res/shaders/colorshader.frag"));
		colorshader.addArgumentName("color");
		colorshader.addArgument(new Vector4f(1f, 0f, 0f, 1f));

		Box a = new Box(-2, 0, 2, 0.5f, 0.5f, 0.5f);
		a.setShader(colorshader);
		addObject(a);

		// Shader Test 2
		Texture texture = new Texture(
				TextureLoader.loadTexture("res/textures/cobblestone.png"));

		Shader textureshader = new Shader(ShaderLoader.loadShaderFromFile(
				"res/shaders/textureshader.vert",
				"res/shaders/textureshader.frag"));
		textureshader.addArgumentName("texture");
		textureshader.addArgument(texture);

		Box b = new Box(2, 0, 2, 0.5f, 0.5f, 0.5f);
		b.setRenderHints(false, true, false);
		b.setShader(textureshader);
		addObject(b);

		// Shader Test 3
		diffuse = new Texture(
				TextureLoader.loadTexture("res/textures/diffuse.jpg"));
		bumpmap = new Texture(
				TextureLoader.loadTexture("res/textures/normal.jpg"));

		Shader bumpmapshader = new Shader(ShaderLoader.loadShaderFromFile(
				"res/shaders/bumpmapshader.vert",
				"res/shaders/bumpmapshader.frag"));
		bumpmapshader.addArgumentNames("colorTexture", "normalTexture");
		bumpmapshader.addArguments(diffuse, bumpmap);

		Box c = new Box(0, -2, 2, 0.5f, 0.5f, 0.5f);
		c.setRenderHints(false, true, false);
		c.setShader(bumpmapshader);
		addObject(c);

		// Shader Test 4
		Sphere s = new Sphere(0, 0, 0, 0.5f, 32, 32);
		addObject(s);

		cubemapper = new CubeEnvironmentMap(this, s.getTranslation());
		cubemapper.updateTexture();

		// textureshader = new Shader(ShaderLoader.loadShaderFromFile(
		// "res/shaders/textureshader.vert",
		// "res/shaders/textureshader.frag"));
		// textureshader.addArgumentName("texture");
		// textureshader.addArgument(new Texture(cubemapper.getTextureID()));
		// s.setShader(textureshader);

		// /*Shader textureshader2 = new Shader(ShaderLoader.loadShaderPair(
		// "res/shaders/textureshader.vert",
		// "res/shaders/textureshader.frag"));
		// textureshader2.addTextureID(cubemapper.getTextureID(), "colorMap");*/
		//
		// Box d = new Box(0, 1, 3, 0.5f, 0.5f, 0.5f);
		// d.setRenderHints(false, true, false);
		// //d.setShader(textureshader2);
		// addObject(d);
	}

	@Override
	public void render() {
		renderScene();
	}

	@Override
	public void render2d() {

	}

	@Override
	public void update(int delta) {
		// cubemapper.updateTexture();
		cam.update(delta);
	}
}

package objects;

import static org.lwjgl.opengl.GL11.glMultMatrixf;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import shader.Shader;

public abstract class RenderedObject extends DataGameObject implements
		Renderable {
	protected Shader shader;
	protected boolean shadered = false;
	protected boolean shaderactive = true;

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
		shadered = true;
	}

	public void setShaderActive(boolean active) {
		shaderactive = active;
	}

	protected void initRender() {
		if (shadered && shaderactive)
			shader.bind();

		glPushMatrix();
		glTranslatef(rotcenter.x, rotcenter.y, rotcenter.z);
		glMultMatrixf(buf);
		glTranslatef(-rotcenter.x, -rotcenter.y, -rotcenter.z);
	}

	protected void endRender() {
		if (shadered && shaderactive)
			shader.unbind();

		glPopMatrix();
	}
}

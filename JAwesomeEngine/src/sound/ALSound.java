package sound;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_SOURCE_RELATIVE;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSource3f;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceRewind;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcei;

import vector.Vector2f;
import vector.Vector3f;

public class ALSound extends Sound {
	int sourcehandle, bufferhandle;

	public ALSound(int bufferhandle) {
		sourcehandle = alGenSources();
		alSourcei(sourcehandle, AL_BUFFER, bufferhandle);
		alSourcei(sourcehandle, AL_SOURCE_RELATIVE, AL_TRUE);
		alSource3f(sourcehandle, AL_POSITION, 0.0f, 0.0f, 0.0f);
	}

	public void pause() {
		alSourcePause(sourcehandle);
	}

	public void play() {
		alSourcePlay(sourcehandle);
	}

	public void rewind() {
		alSourceRewind(sourcehandle);
	}

	public void stop() {
		alSourceStop(sourcehandle);
	}

	public void setLooping(boolean loop) {
		alSourcei(sourcehandle, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
	}

	@Override
	public void delete() {
		alDeleteSources(sourcehandle);
		alDeleteBuffers(bufferhandle);
	}

	@Override
	public void setSourcePosition(Vector3f position) {
		alSource3f(sourcehandle, AL_POSITION, position.x, position.y, position.z);
	}

	@Override
	public void setSourcePosition(Vector2f position) {
		alSource3f(sourcehandle, AL_POSITION, position.x, position.y, 0);
	}

	@Override
	public void setSourcePosition(float x, float y, float z) {
		alSource3f(sourcehandle, AL_POSITION, x, y, z);
	}

	@Override
	public void setSourcePosition(float x, float y) {
		alSource3f(sourcehandle, AL_POSITION, x, y, 0);
	}

	@Override
	public void setSourcePositionRelative(boolean relative) {
		alSourcei(sourcehandle, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
	}
}
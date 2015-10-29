package anim;

import java.util.ArrayList;
import java.util.List;

import objects.BaseObject;
import objects.Updateable;
import quaternion.Rotation;
import vector.Vector;

public abstract class Skeleton<L extends Vector, A extends Rotation> implements Updateable {
	List<BaseObject<L, A>> bodyparts;
	BaseObject<L, A> attachedTo;
	Animation<L, A> animation;
	float animationTimer = 0;
	L nullvec;
	A nullrot;

	public Skeleton(Animation<L, A> animation, BaseObject<L, A>[] bodypart) {
		bodyparts = new ArrayList<BaseObject<L, A>>();
		for (BaseObject<L, A> part : bodypart) {
			bodyparts.add(part);
		}
		setAnimation(animation);
	}

	public void setAnimation(Animation<L, A> animation) {
		this.animation = animation;
		animationTimer = 0;
	}

	@Override
	public void update(int delta) {
		animationTimer += delta * animation.getSpeed();
		if (animationTimer > 1) {
			if (animation.loops) {
				animationTimer %= 1;
			} else {
				animationTimer = 1;
			}
		}

		if (attachedTo != null) {
			for (BaseObject<L, A> part : bodyparts) {
				part.translateTo(attachedTo.getTranslation());
				part.rotateTo(attachedTo.getRotation());
			}
		} else {
			for (BaseObject<L, A> part : bodyparts) {
				part.translateTo(nullvec);
				part.rotateTo(nullrot);
			}
		}

		updateAnimation(animationTimer);
	}

	public void attachTo(BaseObject<L, A> attach) {
		attachedTo = attach;
	}

	public BaseObject<L, A> attachedTo() {
		return attachedTo;
	}

	public void addBodyPart(BaseObject<L, A> bodypart) {
		bodyparts.add(bodypart);
	}

	protected abstract void updateAnimation(float animationTimer);
}
package braynstorm.entities;

import java.io.File;
import java.io.IOException;

import braynstorm.core.OBJLoader;
import braynstorm.threeD.Model;

public class PlayerEntity implements IEntity {
	private Model	playerModel;

	public PlayerEntity() {
		try {
			scale.x = 1;
			scale.y = 1;
			scale.z = 1;
			this.playerModel = OBJLoader.loadModel(new File("res/models/player.obj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public PlayerEntity translateTo(float x, float y, float z) {
		translation.x = x;
		translation.y = y;
		translation.z = z;
		return this;
	}

	@Override
	public PlayerEntity translateBy(float x, float y, float z) {
		translation.x += x;
		translation.y += y;
		translation.z += z;
		return this;
	}

	@Override
	public PlayerEntity rotateTo(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
		return this;
	}

	@Override
	public PlayerEntity rotateBy(float x, float y, float z) {
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
		return this;
	}

	@Override
	public PlayerEntity scaleTo(float x, float y, float z) {
		scale.x = x;
		scale.y = y;
		scale.z = z;
		return this;
	}

	@Override
	public PlayerEntity scaleBy(float x, float y, float z) {
		scale.x += x;
		scale.y += y;
		scale.z += z;
		return this;
	}

	@Override
	public PlayerEntity render() {
		this.playerModel.renderModel().translate(translation).rotateAbs(rotation).scale(scale);
		return this;
	}

}

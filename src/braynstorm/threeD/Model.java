package braynstorm.threeD;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Model {

	public static Map <String, Model>	models	= new HashMap <String, Model>();

	public Map <String, Object3D>		objects	= new HashMap <String, Object3D>();

	private Vector3f					scale	= new Vector3f(1, 1, 1);
	private Vector3f					trans	= new Vector3f();
	private Vector3f					rot		= new Vector3f();

	public static Model registerModel(String name, Model m) {
		if (models.containsKey(name)) {
			return null;
		} else {
			models.put(name, m);
			return models.get(name);
		}

	}

	public static Model getModel(String name) {
		return models.get(name);
	}

	public Model translate(Vector3f vec) {
		this.trans = vec;
		return this;
	}

	public Model scale(Vector3f vec) {
		this.scale = vec;
		return this;
	}

	public Model scaleModificator(float f) {
		this.scale.x *= f;
		this.scale.y *= f;
		this.scale.z *= f;
		return this;
	}

	public Model rotate(Vector3f vec) {

		if (this.rot.x + vec.x >= 360) {
			this.rot.x = this.rot.x + vec.x - 360;
		} else if (this.rot.x + vec.x < 0) {
			this.rot.x = 360 - this.rot.x + vec.x;
		} else {
			this.rot.x += vec.x;
		}

		if (this.rot.y + vec.y >= 360) {
			this.rot.y = this.rot.y + vec.y - 360;
		} else if (this.rot.y + vec.y < 0) {
			this.rot.y = 360 - this.rot.y + vec.y;
		} else {
			this.rot.y += vec.y;
		}

		if (this.rot.z + vec.z >= 360) {
			this.rot.z = this.rot.z + vec.z - 360;
		} else if (this.rot.z + vec.z < 0) {
			this.rot.z = 360 - this.rot.z + vec.z;
		} else {
			this.rot.z += vec.z;
		}


		return this;
	}

	public Model rotateAbs(Vector3f vec) {
		this.rot = vec;
		return this;
	}


	public Model renderModel() {

		glPushMatrix();
		glTranslatef(this.trans.x, this.trans.y, this.trans.z);
		// System.out.println(this.trans);
		glScalef(this.scale.x, this.scale.y, this.scale.z);
		glRotatef(this.rot.x, 1, 0, 0);
		glRotatef(this.rot.y, 0, 1, 0);
		glRotatef(this.rot.z, 0, 0, 1);

		for (Map.Entry <String, Object3D> entry : this.objects.entrySet()) {
			// entry.getValue().color.x = (float) Math.random();
			// entry.getValue().color.y = (float) Math.random();
			// entry.getValue().color.z = (float) Math.random();
			entry.getValue().renderObject();
		}

		glPopMatrix();
		return this;
	}

	public void addObject(Object3D o) {
		if (!this.objects.containsKey(o.name)) {
			this.objects.put(o.name, o);
		}
	}


}

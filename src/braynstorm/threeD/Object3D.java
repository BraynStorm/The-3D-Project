package braynstorm.threeD;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import braynstorm.core.Axies;

import static org.lwjgl.opengl.GL11.*;

public class Object3D {

	public Vector3f			color			= new Vector3f();
	public String			name;
	public List <Vector3f>	vertexes		= new ArrayList <Vector3f>();
	public List <Vector3f>	normals			= new ArrayList <Vector3f>();
	public List <Vector3f>	textureCoords	= new ArrayList <Vector3f>();
	public List <Face>		faces			= new ArrayList <Face>();
	public float			rotX, rotY, rotZ;


	/* Temporary variables */
	private Vector3f		v, n;


	public void setColor(Vector3f c) {
		this.color = c;
	}

	public Vector3f getDim() {
		Vector3f max = new Vector3f(), min = new Vector3f();

		for (Vector3f v : this.vertexes) {
			if (max.x < v.x) {
				max.x = v.x;
			}

			if (max.y < v.y) {
				max.y = v.y;
			}

			if (max.z < v.z) {
				max.z = v.z;
			}


			if (min.x > v.x) {
				min.x = v.x;
			}

			if (min.y > v.y) {
				min.y = v.y;
			}

			if (min.z > v.z) {
				min.z = v.z;
			}

		}

		return new Vector3f(max.x - min.x, max.y - min.y, max.z - min.z);
	}

	public void renderObject() {
		glPushMatrix();


		glBegin(GL_TRIANGLES);

		glColor3f(this.color.x, this.color.y, this.color.z);

		for (Face face : this.faces) {

			this.v = this.vertexes.get((int) face.vertex.x);
			this.n = this.normals.get((int) face.normal.x);
			glVertex3f(this.v.x, this.v.y, this.v.z);
			GL11.glNormal3f(this.n.x, this.n.y, this.n.z);

			this.v = this.vertexes.get((int) (face.vertex.y));
			this.n = this.normals.get((int) (face.normal.y));
			glVertex3f(this.v.x, this.v.y, this.v.z);
			GL11.glNormal3f(this.n.x, this.n.y, this.n.z);

			this.v = this.vertexes.get((int) (face.vertex.z));
			this.n = this.normals.get((int) (face.normal.z));
			glVertex3f(this.v.x, this.v.y, this.v.z);
			GL11.glNormal3f(this.n.x, this.n.y, this.n.z);


		}
		glEnd();
		glPopMatrix();
	}

	public void rotate(float angle, Axies axis) {
		if (axis == Axies.Y) {
			this.rotY += angle;
		}

	}
}

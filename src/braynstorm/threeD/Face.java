package braynstorm.threeD;

import org.lwjgl.util.vector.Vector3f;

public class Face {

	public Vector3f	vertex;
	public Vector3f	texture;
	public Vector3f	normal;

	public Face(Vector3f vertex2, Vector3f texture2, Vector3f normal2) {
		this.vertex = vertex2;
		this.texture = texture2;
		this.normal = normal2;
	}
}

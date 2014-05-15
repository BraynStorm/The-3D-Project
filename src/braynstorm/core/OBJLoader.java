package braynstorm.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

import braynstorm.threeD.Face;
import braynstorm.threeD.Model;
import braynstorm.threeD.Object3D;

public class OBJLoader {


	public static Model load(File f) throws FileNotFoundException, IOException {
		int vIndex = 0;
		int vnIndex = 0;
		int vtIndex = 0;

		int vCut = 0;
		int vnCut = 0;
		int vtCut = 0;


		Model m = new Model();
		Object3D o = new Object3D();
		Vector3f textureCoord = new Vector3f();

		BufferedReader reader = new BufferedReader(new FileReader(f));

		String line, parts[];
		float x, y, z;


		while ((line = reader.readLine()) != null) {
			parts = line.split(" ");
			if (parts[0] == "#") {
				continue;
			}
			if (parts[0].equals("o")) {

				if (o.name != null && !o.name.equals("")) {
					m.addObject(o);
					o = new Object3D();
					vCut = vIndex;
					vnCut = vnIndex;
					vtCut = vtIndex;
				}
				o.name = parts[1];
				o.setColor(new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()));
			}


			if (parts[0].equals("v")) {

				x = Float.parseFloat(parts[1]);
				y = Float.parseFloat(parts[2]);
				z = Float.parseFloat(parts[3]);

				o.vertexes.add(new Vector3f(x, y, z));
				vIndex++;
			}

			if (parts[0].equals("vt")) {
				x = Float.parseFloat(parts[1]);
				y = Float.parseFloat(parts[2]);

				o.textureCoords.add(new Vector3f(x, y, 0));
				vtIndex++;
			}

			if (parts[0].equals("vn")) {
				x = Float.parseFloat(parts[1]);
				y = Float.parseFloat(parts[2]);
				z = Float.parseFloat(parts[3]);

				o.normals.add(new Vector3f(x, y, z));
				vnIndex++;
			}


			if (parts[0].equals("f")) {

				String[][] ps = new String[3][3];
				ps[0] = parts[1].split("/");
				ps[1] = parts[2].split("/");
				ps[2] = parts[3].split("/");

				int xv = Integer.parseInt(ps[0][0]) - 1;
				int yv = Integer.parseInt(ps[1][0]) - 1;
				int zv = Integer.parseInt(ps[2][0]) - 1;


				int xvn = Integer.parseInt(ps[0][2]) - 1;
				int yvn = Integer.parseInt(ps[1][2]) - 1;
				int zvn = Integer.parseInt(ps[2][2]) - 1;

				// Vertexes
				System.out.println(o.vertexes.size() + " " + xv + " " + yv + " " + zv);

				Vector3f vertex = new Vector3f(xv - vCut, yv - vCut, zv - vCut);

				// Texture coords.
				if (ps[0][1] != null && !ps[0][1].equals("")) {
					int xvt = Integer.parseInt(ps[0][1]) - 1;
					int yvt = Integer.parseInt(ps[1][1]) - 1;
					int zvt = Integer.parseInt(ps[2][1]) - 1;
					// textureCoord = new Vector3f(xvt, yvt, zvt);
				} else {
					textureCoord = new Vector3f();
				}

				// Normals
				Vector3f normal = new Vector3f(xvn - vnCut, yvn - vnCut, zvn - vnCut);
				o.faces.add(new Face(vertex, textureCoord, normal));
			}

		}
		o.setColor(new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()));
		m.addObject(o);

		reader.close();
		return m;
	}

	public static Model loadModel(File file) throws FileNotFoundException, IOException {
		Model m = load(file);


		return m;
	}
}

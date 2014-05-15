package braynstorm.entities;

import org.lwjgl.util.vector.Vector3f;

import braynstorm.threeD.Model;

public interface IEntity {
	static Vector3f	position	= new Vector3f();
	static Vector3f	translation	= new Vector3f();
	static Vector3f	rotation	= new Vector3f();
	static Vector3f	scale		= new Vector3f();

	static Model	entityModel	= new Model();

	static float	hp			= 100;				// LOL

	IEntity translateTo(float x, float y, float z);

	IEntity translateBy(float x, float y, float z);

	IEntity rotateTo(float x, float y, float z);

	IEntity rotateBy(float x, float y, float z);

	IEntity scaleTo(float x, float y, float z);

	IEntity scaleBy(float x, float y, float z);

	IEntity render();


}

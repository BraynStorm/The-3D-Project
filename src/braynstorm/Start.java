package braynstorm;


import java.io.File;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import braynstorm.core.Logger;
import braynstorm.core.OBJLoader;
import braynstorm.entities.PlayerEntity;
import braynstorm.threeD.Model;
import braynstorm.threeD.Object3D;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.util.glu.GLU.*;

public class Start {

	public static boolean	fullscreen		= false;
	public static boolean	resizable		= true;
	public static boolean	vsync			= true;
	public static boolean	running;

	public static Vector3f	playerRotation	= new Vector3f(0, 0, 0);
	public static Vector3f	playerPosition	= new Vector3f(0, 1, 0);
	public static Object3D	playerModel;

	public static Vector3f	tmp				= new Vector3f();


	public static boolean	keyUp;
	public static boolean	keyDown;
	public static boolean	keyLeft;
	public static boolean	keyRight;
	public static boolean	keyFlyUp;
	public static boolean	keyFlyDown;

	public static float		fov				= 90;
	public static boolean	grabbed;
	public static float		zNear			= 0.001f, zFar = 1000;
	public static float		mouseSpeed		= .12f;
	public static float		walkSpeed		= 0.05f;
	public static float		flySpeed		= 0.05f;

	public static int		displayWidth	= 800;
	public static int		displayHeight	= 600;

	public static int		fps				= 60;

	public static float		lastMouseX, lastMouseY;
	private static float	maxLookDown		= -89;
	private static float	maxLookUp		= 89;


	public static void main(String[] args) {

		try {
			if (fullscreen) {
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			} else {
				Display.setResizable(resizable);
				Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
			}
			Display.setTitle("The 3D Project");
			Display.setVSyncEnabled(vsync);
			Display.create();
			running = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			Display.destroy();
			System.exit(1);
		}

		run();
	}

	public static void run() {
		Logger.log("Game started.");

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		glMatrixMode(GL_MODELVIEW);


		glEnable(GL_DEPTH_TEST);
		// glEnable(GL_TEXTURE_2D);
		// glEnable(GL_BLEND);
		// glEnable(GL_ALPHA_TEST);
		// glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);


		try {
			// Model.registerModel("collumn", OBJLoader.loadModel(new File("res/pillar2.obj")));
			Model.registerModel("floor", OBJLoader.loadModel(new File("res/floor.obj")));
			Model.registerModel("ak47", OBJLoader.loadModel(new File("res/akScale.obj")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		PlayerEntity player = new PlayerEntity();
		Model ak47 = Model.getModel("ak47");
		Model floor = Model.getModel("floor");


		Vector3f akRot = new Vector3f();
		Vector3f akPos = new Vector3f();
		while (running) {
			// framesRendered++;
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			floor.renderModel();// .translate(new Vector3f(playerPosition.x, 1f, playerPosition.z));
			// System.out.println(playerPosition);
			// player.translateTo(-playerPosition.x, -playerPosition.y, -playerPosition.z)
			// .rotateTo(playerRotation.x, -playerRotation.y, 0)
			// .scaleTo(1, 1, 1)
			// .render();
			// System.out.println(playerRotation.y);

			akRot.y = -playerRotation.y + 180;

			akPos.x = -playerPosition.x;
			akPos.y = 5;
			akPos.z = -playerPosition.z - 0.1f;

			ak47.renderModel()
				.scale(new Vector3f(1, 1, 1))
				.scaleModificator(0.0001f)
				.rotateAbs(akRot)
				.translate(new Vector3f(-playerPosition.x, 5, -playerPosition.z - .1f));
			glLoadIdentity();

			System.out.println("Player XYZ " + playerPosition.x + " " + playerPosition.y + " " + playerPosition.z);
			System.out.println("AK47   XYZ " + -akPos.x + " " + akPos.y + " " + -akPos.z);

			glRotatef(playerRotation.x, 1, 0, 0);
			glRotatef(playerRotation.y, 0, 1, 0);
			// glRotatef(playerRotation.z, 0, 0, 1); // wtf?
			glTranslatef(playerPosition.x, playerPosition.y, playerPosition.z);


			Mouse.setGrabbed(grabbed);

			if (Display.isCloseRequested()) {
				running = false;
			}


			if (Mouse.isGrabbed()) {

				keyUp = Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
				keyDown = Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
				keyLeft = Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT);
				keyRight = Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
				keyFlyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
				keyFlyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);


				// ROtation
				float[] mDelta = getMouseDelta(true);

				if (playerRotation.y + mDelta[0] >= 360) {
					playerRotation.y = playerRotation.y + mDelta[0] - 360;
				} else if (playerRotation.y + mDelta[0] < 0) {
					playerRotation.y = 360 - playerRotation.y + mDelta[0];
				} else {
					playerRotation.y += mDelta[0];
				}

				if (playerRotation.x - mDelta[1] >= maxLookDown && playerRotation.x - mDelta[1] <= maxLookUp) {
					playerRotation.x += -mDelta[1];
				} else if (playerRotation.x - mDelta[1] < maxLookDown) {
					playerRotation.x = maxLookDown;
				} else if (playerRotation.x - mDelta[1] > maxLookUp) {
					playerRotation.x = maxLookUp;
				}


				// Walking
				float angle = playerRotation.y;
				if (keyUp && keyLeft && !keyRight && !keyDown) {
					angle -= 45;
				}
				if (keyUp && keyRight && !keyLeft && !keyDown) {
					angle += 45;
				}
				if (keyDown && keyLeft && !keyRight && !keyUp) {
					angle -= 135;
				}
				if (keyDown && keyRight && !keyLeft && !keyUp) {
					angle += 135;
				}

				if (keyDown && !keyRight && !keyLeft && !keyUp) {
					angle -= 180;
				}

				if (keyLeft && !keyUp && !keyRight && !keyDown) {
					angle -= 90;
				}

				if (keyRight && !keyUp && !keyLeft && !keyDown) {
					angle += 90;
				}

				if (keyRight && !keyUp && keyLeft && keyDown) {
					angle -= 180;
				}

				if (keyFlyUp) {
					playerPosition.y -= flySpeed;
				}


				if (keyFlyDown) {
					playerPosition.y += flySpeed;
				}

				if (keyUp || keyDown || keyLeft || keyRight) {
					float hyp = walkSpeed;
					float adj = (float) (hyp * Math.cos(Math.toRadians(angle)));
					float op = (float) (hyp * Math.sin(Math.toRadians(angle)));
					playerPosition.z += adj;
					playerPosition.x -= op;
				}


			}


			if (Mouse.isButtonDown(0) && !grabbed) {
				grabbed = true;
				normalizeMouse();
			}

			if (Mouse.isButtonDown(1) && grabbed) {
				grabbed = false;
				normalizeMouse();
			}


			Display.update();
			if (vsync) {
				Display.sync(fps);
			}
		}


	}

	public static void normalizeMouse() {
		lastMouseX = displayWidth / 2;
		lastMouseY = displayHeight / 2;
		Mouse.setCursorPosition((int) lastMouseX, (int) lastMouseY);
	}

	public static float[] getMouseDelta(boolean useSpeed) {
		float[] delta = new float[2];

		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();

		delta[0] = mouseX - lastMouseX;
		delta[1] = mouseY - lastMouseY;

		if (useSpeed) {
			delta[0] *= mouseSpeed;
			delta[1] *= mouseSpeed;
		}

		lastMouseX = mouseX;
		lastMouseY = mouseY;

		if (lastMouseX >= displayWidth - 1 || lastMouseY >= displayHeight - 1) {
			normalizeMouse();
		} else if (lastMouseX == 0 || lastMouseY == 0) {
			normalizeMouse();
		}


		// System.out.println("\nX = " + lastMouseX);
		// System.out.println("Y = " + lastMouseY);
		return delta;
	}
}

package com.max.Alienz2;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class MyInputProcessor implements InputProcessor {
	Ship ship;
	OnScreenJoystick joyStick, joyStick2;
	Camera camera;
	int pointerID;
	boolean pointerPicked;
	
	public MyInputProcessor(Ship ship, OnScreenJoystick joyStick, Camera camera)
	{
		this.ship = ship;
		this.joyStick = joyStick;
		this.camera = camera;
		pointerPicked = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (pointer == pointerID)
		{
			joyStick.resetKnob();
			pointerPicked = false;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (pointerPicked = false)
		{
			pointerID = pointer;
			pointerPicked = true;
		}
		
		if (pointer == pointerID)
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(x, y, 0);
			camera.unproject(touchPos);
			
			joyStick.updateKnobPosition(touchPos.x, touchPos.y);
			ship.setDir(joyStick.angle());
		}
		return true;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

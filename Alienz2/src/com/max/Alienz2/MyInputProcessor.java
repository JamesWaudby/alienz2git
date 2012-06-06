package com.max.Alienz2;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class MyInputProcessor implements InputProcessor {
	Ship ship;
	OnScreenJoystick joyStick, joyStick2;
	Camera camera;
	int pointerID;
	boolean pointerPicked;
	long lastFire;
	long fireTime;
	
	public MyInputProcessor(Ship ship, OnScreenJoystick joyStick, OnScreenJoystick joyStick2, Camera camera)
	{
		this.ship = ship;
		this.joyStick = joyStick;
		this.joyStick2 = joyStick2;
		this.camera = camera;
		pointerPicked = false;
		
		
		fireTime = 250000000;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Input.Keys.SPACE) {
			this.ship.fire();
		}
		return true;
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
			joyStick2.resetKnob();
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
			
			if(x < 400) {
				joyStick.updateKnobPosition(touchPos.x, touchPos.y);
				ship.setDir(joyStick.angle());
				
			}
			else {
				joyStick2.updateKnobPosition(touchPos.x, touchPos.y);
				ship.setDir(joyStick2.angle());
				//camera.translate(1.0f, 0.1f, 1.0f);
				if(TimeUtils.nanoTime() - lastFire > fireTime) {
					ship.fire();
					lastFire = TimeUtils.nanoTime();
				}
			}
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

package com.max.Alienz2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class MyInputProcessor implements InputProcessor {
	Ship ship;
	OnScreenJoystick joyStick, joyStick2;
	Camera camera;

	long lastFire;
	long fireTime;
	
	
	// I used two variables for both sides, might be overkill
	// could potentially just replace the booleans with checks
	// whether or not the pointers = -1
	boolean leftPressed, rightPressed;
	int leftPointer, rightPointer;
	
	Controller controller;
	
	public MyInputProcessor(Ship ship, OnScreenJoystick joyStick, OnScreenJoystick joyStick2, Camera camera, Controller controller)
	{
		this.ship = ship;
		this.joyStick = joyStick;
		this.joyStick2 = joyStick2;
		this.camera = camera;
		this.controller = controller;
				
		// Default back to -1
		leftPointer = -1;
		rightPointer = -1;
		
		leftPressed = false;
		rightPressed = false;
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
		
		// Get the touch input		
		Vector3 touchPos = new Vector3();
		touchPos.set(x, y, 0);
		camera.unproject(touchPos);
		
		// Check the positioning of the touch
		if(touchPos.x < 400) {
			leftPointer = pointer;
			leftPressed = true;
			
			joyStick.setOrigin(touchPos.x, touchPos.y);
			
		}
		else {
			rightPointer = pointer;
			rightPressed = true;
			
			joyStick2.setOrigin(touchPos.x, touchPos.y);
			
		}
		
		return true;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		
		// Get the touch
		Vector3 touchPos = new Vector3();
		touchPos.set(x, y, 0);
		camera.unproject(touchPos);
		
		// Check where the touch was and update accordingly.
		if(pointer == leftPointer) {
			leftPointer = -1;
			leftPressed = false;
			joyStick.resetKnob();
			
			// The user is no longer touching the joystick
			controller.setLeftJoystickDown(false);
			
		}
		else {
			rightPointer = -1;
			rightPressed = false;
			joyStick2.resetKnob();
			
			// The user is no longer touching the joystick
			controller.setRightJoystickDown(false);
		}
			
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		
		// Get the touch position
		Vector3 touchPos = new Vector3();
		touchPos.set(x, y, 0);
		camera.unproject(touchPos);
		
		// Check if it's a left or right drag
		if(leftPointer == pointer && leftPressed == true) {
			
			// Update the joystick
			joyStick.updateKnobPosition(touchPos.x, touchPos.y);
			
			// Set the controller to know the left joystick has been moved
			controller.setLeftJoystickDown(true);
		}
		
		else if(rightPointer == pointer && rightPressed == true) {

			// Update the joystick
			joyStick2.updateKnobPosition(touchPos.x, touchPos.y);
			
			// Set the controller to know the right joystick has been moved
			controller.setRightJoystickDown(true);
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

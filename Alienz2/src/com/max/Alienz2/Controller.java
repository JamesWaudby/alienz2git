package com.max.Alienz2;

import com.badlogic.gdx.utils.TimeUtils;

public class Controller {
	
	Ship ship;
	OnScreenJoystick joyStick, joyStick2;
	private boolean leftJoystickDown, rightJoystickDown;
	
	public Controller(Ship ship, OnScreenJoystick joyStick, OnScreenJoystick joyStick2) {
		
		this.ship = ship;
		
		this.joyStick = joyStick;
		this.joyStick2 = joyStick2;
		
		this.leftJoystickDown = false;
		this.rightJoystickDown = false;
	}
	
	public void update() {
		if(isLeftJoystickDown()) {
			leftJoystick(joyStick.angle());
		}
		if(isRightJoystickDown()) {
			rightJoystick(joyStick2.angle());
		}
	}
	
	public void leftJoystick(float degrees) {
		ship.setDir(degrees);
	}
	
	public void rightJoystick(float degrees) {
		ship.setFireDir(degrees);
		ship.setRotation(degrees);
		
		// Check fire time and fire
		if(TimeUtils.nanoTime() - ship.getLastFire() > ship.getFireTime()) {
			ship.fire();
			ship.setLastFire(TimeUtils.nanoTime());
		}
	}

	public boolean isLeftJoystickDown() {
		return leftJoystickDown;
	}

	public void setLeftJoystickDown(boolean leftJoystickDown) {
		this.leftJoystickDown = leftJoystickDown;
	}

	public boolean isRightJoystickDown() {
		return rightJoystickDown;
	}

	public void setRightJoystickDown(boolean rightJoystickDown) {
		this.rightJoystickDown = rightJoystickDown;
	}
	
	
}

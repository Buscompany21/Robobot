package IS;


import robocode.DeathEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.BulletHitEvent;
import robocode.WinEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;



public class Kobe extends Robot {
    boolean stopIfRobot = false;


	public void run() {
		
        
        // Set colors
		setBodyColor(Color.red);
		setGunColor(Color.black);
		setRadarColor(Color.yellow);
		setBulletColor(Color.green);
		setScanColor(Color.green);

		


		// Move to a corner
		goCorner();

		// Initialize gun turn speed to 3
		int gunIncrement = 3;

		// Spin gun back and forth
		while (true) {
			for (int i = 0; i < 30; i++) {
				turnGunLeft(gunIncrement);
			}
			gunIncrement *= -1;
		}
	}

	
	public void goCorner() {
		double moveDistance = Math.max(getBattleFieldWidth(), getBattleFieldHeight());;
        // We don't want to stop when we're just turning...
		stopIfRobot = false;
		// turn to face the wall to the "right" of our desired corner.
		turnRight(normalRelativeAngleDegrees(90 - getHeading()));
		// Ok, now we don't want to crash into any robot in our way...
		stopIfRobot = true;
		// Move to that wall
		ahead(moveDistance);
		// Turn to face the corner
		turnRight(90);
		// Move to the corner
		ahead(moveDistance);
		
		
        if (getX() == getBattleFieldWidth() & getY() == 0){
            return;
        }
        else if (getY() == 0) {
            doNothing();
            turnRight(normalRelativeAngleDegrees(90 - getHeading()));
            ahead(moveDistance - getX());    
        }
        else if (getX() == getBattleFieldWidth()) {
            doNothing();
            turnRight(90);
            ahead(moveDistance - getY());
        }
        else {
            doNothing();
            stopIfRobot = false;
            // turn to face the wall to the "right" of our desired corner.
            turnRight(normalRelativeAngleDegrees(90 - getHeading()));
            // Ok, now we don't want to crash into any robot in our way...
            stopIfRobot = true;
            // Move to that wall
            ahead(moveDistance);
            // Turn to face the corner
            turnRight(90);
            // Move to the corner
            ahead(moveDistance);
            
        }
        // Turn gun to starting point
        turnGunRight(160);
    }
	

	/**
	 * onScannedRobot:  Stop and fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Should we stop, or just fire?       
        double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

		// If it's close enough, fire!
		if (Math.abs(bearingFromGun) <= 3) {
			turnGunRight(bearingFromGun);
			if (getGunHeat() == 0) {
                smartFire(e.getDistance());
			}

		    else {
			turnGunRight(bearingFromGun);
		    }
            // Generates another scan event if we see a robot.
            // We only need to call this if the gun (and therefore radar)
            // are not turning.  Otherwise, scan is called automatically.
            if (bearingFromGun == 0) {
                scan();
            }
        }
        
       
	}

    public void onBulletHit(BulletHitEvent e){
        fire(4);
    }

	/**
	 * smartFire:  Custom fire method that determines firepower based on distance.
	 *
	 * @param robotDistance the distance to the robot to fire at
	 */
	public void smartFire(double robotDistance) {
		if (robotDistance > 100 || getEnergy() < 20) {
			fire(1);
		} else if (robotDistance > 50 || getEnergy() < 30) {
			fire(2);
		}
          else if (robotDistance > 25 || getEnergy() < 50) {
			fire(3);
		} else {
			fire(4);
		}
	}

	
	public void onWin(WinEvent e) {
		ahead(50);
        turnLeft(900);
        ahead(100);
        back(75);
        turnRight(1000);  
	}
}


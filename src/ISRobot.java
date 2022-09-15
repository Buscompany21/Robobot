package IS;

import robocode.DeathEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;

public class ISRobot extends Robot {
	int others; // Number of other robots in the game
	boolean backwards = false;

	public void run() {
		// Set colors
		setBodyColor(Color.cyan);
		setGunColor(Color.white);
		setRadarColor(Color.magenta);
		setBulletColor(Color.yellow);
		setScanColor(Color.orange);

		// Save # of other bots
		//others = getOthers();
		if (getOthers() > 8) {
			//movement function
		} else if (getOthers() > 1) {
			//movement function
		} else if (getOthers() == 1) {
			// Movement function
		}
		stop();
		// Spin radar around 360 degrees independent of the gun
		setAdjustRadarForRobotTurn(true);
		while (true) {
			turnRadarRight(360);
		}

	}
	// When a robot is scanned, lock on to them
	public void onScannedRobot(ScannedRobotEvent e) {
		// 
		turnGunRight(getRadarHeading() - getGunHeading());
		smartFire(e.getDistance());
		//turnRadarRight(getHeading() - getRadarHeading() + e.getBearing());
		ahead(50);
		turnRight(90);
	}

	public void smartFire(double robotDistance) {
		if (robotDistance > 200 || getEnergy() < 15) {
			fire(1);
		} else if (robotDistance > 50) {
			fire(2);
		} else {
			fire(3);
		}
	}


	// public void onWin(WinEvent e) {
	// 	//Win Dance code goes here
	// }
}

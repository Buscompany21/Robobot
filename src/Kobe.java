package IS;


import robocode.DeathEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.HitWallEvent;
import robocode.HitByBulletEvent;
import robocode.BulletHitEvent;
import robocode.WinEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;



public class Kobe extends Robot {
    boolean stopIfRobot = false;
	boolean reverso = true;


	public void run() {
		
        
        // Set colors
		setBodyColor(Color.red);
		setGunColor(Color.black);
		setRadarColor(Color.yellow);
		setBulletColor(Color.green);
		setScanColor(Color.green);

		
		while (true) {
			ahead(100);
			turnGunRight(360);
			back(50);
			turnGunRight(360);
		}
	}


	public void onScannedRobot(ScannedRobotEvent e) { 
        double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

		if (Math.abs(bearingFromGun) <= 3) {
			turnGunRight(bearingFromGun);
			if (getGunHeat() == 0) {
                smartFire(e.getDistance());
			}

		    else {
			turnGunRight(bearingFromGun);
		    }
            if (bearingFromGun == 0) {
                scan();
            }
        }
        
       
	}

	public void onHitWall(HitWallEvent e) {
		double bearing = e.getBearing();
		turnRight(-bearing);
		ahead(100);
	}


    public void onBulletHit(BulletHitEvent e){
        fire(4);
    }

	public void onHitByBullet(HitByBulletEvent e) {
		double currentEnergy = getEnergy();
		double currentBearing = e.getBearing();
		if (currentEnergy > 100){
			// Check current energy and scan if > 100 to look for enemies to fight
			turnGunRight(360);
		} else {
			// Back off a bit and let current energy recover to dodge incoming bullets
			turnRight(-currentBearing);
			ahead(100);
		}
	}

	/**
	 * @param robotDistance the distance to the robot to fire at
	 */
	public void smartFire(double robotDistance) {
		if (robotDistance > 500) {
			fire(4);
		} else if (robotDistance > 250) {
			fire(3);
		}
          else if (robotDistance > 100) {
			fire(2);
		} else {
			fire(1);
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


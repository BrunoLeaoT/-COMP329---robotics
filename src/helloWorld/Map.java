package helloWorld;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;


public class Map {
	Float map[][];
	public PilotRobot robot;
    public Map(PilotRobot r){
    	robot = r;
    }
    
	public void fillMap(PilotRobot me) {
		int cellSideSize = 18;
		GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
		//lcd.drawRect(x, y, width, height);
		for(int j = 0; j< 7; j ++) {
			for(int i=0;i<6;i++) {
				lcd.drawRect(cellSideSize*i, cellSideSize*j, cellSideSize, cellSideSize);
			}
		lcd.setFont(Font.getSmallFont());
		lcd.drawString("Position robot:", 120 , 0, 0);
		lcd.drawString("X: " + robot.getPose().getX() + "Y: " + robot.getPose().getY() , 110, 20, 0);
		lcd.drawString("H: " + robot.getPose().getHeading() , 110, 20, 0);
		}
	}
}

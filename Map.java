package helloWorld;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;


public class Map {
	public PilotRobot robot;
    public Map(PilotRobot r){
    	robot = r;
    }
    
	public void fillMap(PilotRobot me) {
		int cellSideSize = 18;
		GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
		for(int j = 0; j< 7; j ++) {
			for(int i=0;i<6;i++) {
				lcd.drawRect(cellSideSize*(i), cellSideSize*(6-j), cellSideSize, cellSideSize);
				if(robot.map[j][i].obstacle) {
					lcd.drawRoundRect(cellSideSize*(i) -2, cellSideSize*(6-j) -2,cellSideSize -2, cellSideSize -2, 2, 2);
				}
			}
		lcd.setFont(Font.getSmallFont());
		lcd.drawString("Position robot:", 120 , 0, 0);
		
		lcd.drawString("X: " + robot.getPose().getX() , 110, 20, 0);
		lcd.drawString("Y: " + robot.getPose().getY() , 110, 40, 0);
		lcd.drawString("H: " + robot.getDistance() , 110, 60, 0);
		lcd.drawString("Cell:" + robot.stateCell.y + ","+  robot.stateCell.x, 110, 80, 0);

		}
	}
}

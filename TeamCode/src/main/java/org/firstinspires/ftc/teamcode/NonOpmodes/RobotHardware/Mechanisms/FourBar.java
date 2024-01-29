package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_INTERMEDIATE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_PICKUP;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;


@Config
public class FourBar {
    private static FourBar instance;
    public static FourBar getInstance(){
        if(instance == null){
            instance = new FourBar();
        }
        return instance;
    }
    private Servo v4bL, v4bR;
    private Hardware hardware = new Hardware();
    public enum FourBarState {
        INIT,
        PICKUP,
        INTERMEDIATE,
        DEPOSIT
    }

    FourBarState fourBarState = FourBarState.INIT;


    ElapsedTime servoTime = new ElapsedTime();
    public double[] intermediatePositions;
    public static int delay = 25;
    public static int stepRatio = 150;


    /*public FourBar(HardwareMap hw){
        hardware.initFourBar(hw);
        v4bL = hardware.fourBarL;
        v4bR = hardware.fourBarR;
    }*/

    public void initFourBar(HardwareMap hw){

        hardware.initFourBar(hw);
        v4bL = hardware.fourBarL;
        v4bR = hardware.fourBarR;
    }
    public void setFourBarPosition(double position){
        v4bL.setPosition(position);
        v4bR.setPosition(position);
    }
    public void setFourBarState(FourBarState state){
        fourBarState = state;
        switch (state){
            case INIT:
                setFourBarPositionSlow(FOURBAR_INIT);
                break;
            case PICKUP:
                setFourBarPositionSlow(FOURBAR_PICKUP);
                break;
            case INTERMEDIATE:
                setFourBarPositionSlow(FOURBAR_INTERMEDIATE);
                break;
            case DEPOSIT:
                setFourBarPositionSlow(FOURBAR_DEPOSIT);
                break;
        }
    }



    public synchronized void setFourBarPositionSlow(double targetPosition){

            int delayCounter = 0;

            double currentPosition = getPosition();
            double dPosition = Math.abs(targetPosition - currentPosition);
            double direction = Math.signum(targetPosition - currentPosition);

            int steps = Math.max((int) (dPosition*stepRatio),1);

            intermediatePositions = new double[steps];

            intermediatePositions[0] = currentPosition + (dPosition/steps)*direction;
            for(int i = 1;i < intermediatePositions.length;i++){
                intermediatePositions[i] = intermediatePositions[i-1] + (dPosition/steps)*direction;
            }
            for(int i = 0;i < intermediatePositions.length;i++){
                servoTime.reset();
                while(servoTime.milliseconds() <= delay){
                    delayCounter++;
                }
                setFourBarPosition(intermediatePositions[i]);
            }
        }
    public double getPosition(){
        return v4bL.getPosition();
    }
    public FourBarState getFourBarState(){
        return fourBarState;
    }

    }



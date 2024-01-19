package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@Autonomous(name="BlueLeft")
public class BlueLeft extends LinearOpMode {

    Hardware hardware = new Hardware();
    Intake intake;
    FourBar fourBar;
    Differential diff;
    Claw claw;


    AutoTrajectories autoTrajectories;
    SampleMecanumDrive drive;
    TrajectorySequence scoreSpikeMark, scoreBackBoard, park;


    @Override
    public void runOpMode() throws InterruptedException {
        autoTrajectories = new AutoTrajectories(hardwareMap);
        autoTrajectories.setPath(AutoTrajectories.AutoLocation.BLUE_LEFT, autoTrajectories.spikeMark);
        drive = autoTrajectories.drive;

        scoreSpikeMark = autoTrajectories.scoreSpikeMark;
        scoreBackBoard = autoTrajectories.scoreBackBoard;
        park = autoTrajectories.park;

        hardware.initAuto(hardwareMap);
        fourBar = hardware.fourBarInstance;
        diff = hardware.differentialInstance;
        claw = hardware.clawInstance;

        fourBar.setFourBarState(FourBar.FourBarState.INIT);
        diff.setDiffState(Differential.DiffState.DEPOSIT);
        claw.setClawState(Claw.ClawState.CLOSE_ONE_PIXEL);

        waitForStart();
        if(isStopRequested()){
            return;
        }
        drive.followTrajectorySequence(scoreSpikeMark);
        fourBar.setFourBarState(FourBar.FourBarState.DEPOSIT);
        drive.followTrajectorySequence(scoreBackBoard);
        claw.setClawState(Claw.ClawState.OPEN);







        while(opModeIsActive()){


        }


    }


}

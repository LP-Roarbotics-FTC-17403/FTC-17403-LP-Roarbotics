package org.firstinspires.ftc.teamcode.DecodeFiles;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.CameraSystem;
import org.firstinspires.ftc.teamcode.SubSystems.ColorSensorCode;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Firecracker;
import org.firstinspires.ftc.teamcode.SubSystems.Hammer;
import org.firstinspires.ftc.teamcode.SubSystems.Inhaler;
import org.firstinspires.ftc.teamcode.SubSystems.LED;
import org.firstinspires.ftc.teamcode.SubSystems.MotorClass;

@Autonomous(group = "MSI")
public final class MSI_RedSideGoalNoPattern extends LinearOpMode {

    private Pose2d beginPose;
    private Pose2d launchPose;
    private MecanumDrive drive;
    private CameraSystem camera;
    private Firecracker rightFirecracker;
    private Firecracker leftFirecracker;
    private Inhaler inhaler1;
    private Inhaler inhaler2;
    private Feeder leftFeeder;
    private Feeder rightFeeder;
    private LED leftLight;
    private LED middleLight;
    private LED rightLight;
    private ColorSensorCode leftColor;
    private ColorSensorCode rightColor;
    private MotorClass vroom;

    private Hammer hammer;

    private ElapsedTime timer = new ElapsedTime();

    final double FEED_TIME_SECONDS = 0.80; //The feeder servos run this long when a shot is requested.
    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;

    final double LAUNCHER_CLOSE_TARGET_VELOCITY = 1200; //in ticks/second for the close goal.
    final double LAUNCHER_CLOSE_MIN_VELOCITY = 1175; //minimum required to start a shot for close goal.

    final double LAUNCHER_FAR_TARGET_VELOCITY = 1350; //Target velocity for far goal
    final double LAUNCHER_FAR_MIN_VELOCITY = 1325; //minimum required to start a shot for far goal.

    double launcherTarget = LAUNCHER_CLOSE_TARGET_VELOCITY; //These variables allow
    double launcherMin = LAUNCHER_CLOSE_MIN_VELOCITY;

    final double LEFT_POSITION = 0.2962; //the left and right position for the diverter servo
    final double RIGHT_POSITION = 0;

    int patternNumber = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        beginPose                = new Pose2d(-56, 44, Math.toRadians(126.5));
        launchPose              = new Pose2d(-16,15, Math.toRadians(134));
        drive              = new MecanumDrive(hardwareMap, beginPose);
        camera             = new CameraSystem(hardwareMap);
        rightFirecracker    =  new Firecracker(hardwareMap, "right_launcher");
        leftFirecracker     =  new Firecracker(hardwareMap, "left_launcher");
        inhaler1                 = new Inhaler(hardwareMap, "intake");
        inhaler2                = new Inhaler(hardwareMap, "transfer");
        leftFeeder               = new Feeder(hardwareMap, "left_feeder");
        rightFeeder              = new Feeder(hardwareMap, "right_feeder");
        leftLight                   = new LED(hardwareMap, "left_light");
        middleLight                 = new LED(hardwareMap, "middle_light");
        rightLight                  = new LED(hardwareMap, "right_light");
        leftColor       = new ColorSensorCode(hardwareMap, "left_color_sensor");
        rightColor      = new ColorSensorCode(hardwareMap, "right_color_sensor");
        vroom            = new MotorClass(hardwareMap);
        hammer          = new Hammer(hardwareMap, "diverter");


        boolean reverse = true;
        boolean notReverse = false;
        int detectedID;

        //adjust reverse if needed
        leftFeeder.initialize(reverse);
        rightFeeder.initialize(notReverse);
        leftFirecracker.initialize(notReverse);
        rightFirecracker.initialize(reverse);
        inhaler1.initialize(reverse);
        inhaler2.initialize(reverse);
        vroom.initialize(true);
        camera.cameraOn();


        Action fullCycleTest = drive.actionBuilder(beginPose)
                .stopAndAdd(
                        new ParallelAction(
                            leftFirecracker.closeLaunch(),
                            rightFirecracker.closeLaunch(),
                            inhaler1.intakeOn(),
                            inhaler2.intakeOn()
                        )
                )
                .strafeToLinearHeading(new Vector2d(-16,15), Math.toRadians(134))
                .stopAndAdd(
                        new SequentialAction(
                            leftLeftRight(),
                            hammer.right()
                        )
                )
                .splineToSplineHeading(new Pose2d(-8,22, Math.toRadians(90)), Math.toRadians(90))
                .afterDisp(7, ()->
                        Actions.runBlocking(
                                new ParallelAction(
                                        hammer.left()
                                )
                        ))
                .lineToYLinearHeading(43, Math.toRadians(90), new TranslationalVelConstraint(10.0))
                .splineToSplineHeading(launchPose, Math.toRadians(270))
                .stopAndAdd(
                        new SequentialAction(
                                leftLeftRight(),
                                hammer.right()
                        )
                )
                .splineToSplineHeading(new Pose2d(21,17.4, Math.toRadians(90)), Math.toRadians(90))
                .afterDisp(4.4, ()->
                        Actions.runBlocking(
                                new ParallelAction(
                                        hammer.left()
                                )
                        ))
                .lineToYLinearHeading(43, Math.toRadians(90), new TranslationalVelConstraint(10.0))
                .lineToYLinearHeading(30, Math.toRadians(90))
                .splineToSplineHeading(launchPose, Math.toRadians(200))
                .stopAndAdd(leftRightLeft())
                .strafeToLinearHeading(new Vector2d(0,20), Math.toRadians(134))
                .build();


        waitForStart();


            Actions.runBlocking(
                    fullCycleTest
            );


    }


    public Action firstCycleLaunch(){
        if(patternNumber == 21){
            return rightLeftLeft();
        }else if(patternNumber == 22){
            return leftRightLeft();
        }else{
            return leftLeftRight();
        }
    }
    public Action secondCycleLaunch(){
        if(patternNumber == 21){
            return leftRightLeft();
        }else if(patternNumber == 22){
            return leftLeftRight();
        }else{
            return leftRightLeft();
        }
    }
    public Action thirdCycleLaunch(){
        if(patternNumber == 21){
            return leftRightLeft();
        }else if(patternNumber == 22){
            return rightLeftLeft();
        }else{
            return leftRightLeft();
        }
    }
    public Action leftLeftRight(){
        return new SequentialAction(
                leftFeeder.feed(),
                new SleepAction(1),
                leftFeeder.rest(),
                leftFeeder.feed(),
                new SleepAction(1),
                leftFeeder.rest(),
                rightFeeder.feed(),
                new SleepAction(1),
                rightFeeder.rest()
        );
    }
    public Action leftRightLeft(){
        return new SequentialAction(
                leftFeeder.feed(),
                new SleepAction(1),
                leftFeeder.rest(),
                rightFeeder.feed(),
                new SleepAction(1),
                rightFeeder.rest(),
                leftFeeder.feed(),
                new SleepAction(1),
                leftFeeder.rest()
        );
    }
    public Action rightLeftLeft(){
        return new SequentialAction(
                rightFeeder.feed(),
                new SleepAction(0.9),
                rightFeeder.rest(),
                leftFeeder.feed(),
                new SleepAction(0.9),
                leftFeeder.rest(),
                leftFeeder.feed(),
                new SleepAction(0.9),
                leftFeeder.rest()
        );
    }

    public Action readAprilTag(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                timer.reset();
                while(patternNumber != 21 &&
                        patternNumber != 22 &&
                        patternNumber != 23 &&
                        timer.seconds() < 5)
                {
                    patternNumber = camera.getPattern();
                }
                return false;
            }
        };
    }
}


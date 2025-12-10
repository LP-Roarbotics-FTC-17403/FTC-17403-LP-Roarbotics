package org.firstinspires.ftc.teamcode.DecodeFiles;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.CameraSystem;
import org.firstinspires.ftc.teamcode.SubSystems.ColorSensorCode;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Firecracker;
import org.firstinspires.ftc.teamcode.SubSystems.Hammer;
import org.firstinspires.ftc.teamcode.SubSystems.Inhaler;
import org.firstinspires.ftc.teamcode.SubSystems.LED;
import org.firstinspires.ftc.teamcode.SubSystems.MotorClass;

@Autonomous
public final class KirbyRedAutoMeet2CTEST extends LinearOpMode {

    private Pose2d beginPose;
    private MecanumDrive drive;
    private CameraSystem camera;
    private Firecracker rightFirecracker;
    private Firecracker leftFirecracker;
    private Inhaler inhaler;
    private Feeder leftFeeder;
    private Feeder rightFeeder;
    private LED leftLight;
    private LED middleLight;
    private LED rightLight;
    private ColorSensorCode leftColor;
    private ColorSensorCode rightColor;
    private MotorClass vroom;

    private Hammer hammer;

    final double FEED_TIME_SECONDS = 0.80; //The feeder servos run this long when a shot is requested.
    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;

    final double LAUNCHER_CLOSE_TARGET_VELOCITY = 1200; //in ticks/second for the close goal.
    final double LAUNCHER_CLOSE_MIN_VELOCITY = 1175; //minimum required to start a shot for close goal.

    final double LAUNCHER_FAR_TARGET_VELOCITY = 1350; //Target velocity for far goal
    final double LAUNCHER_FAR_MIN_VELOCITY = 1325; //minimum required to start a shot for far goal.

    double launcherTarget = 1130; //These variables allow
    double launcherMin = LAUNCHER_CLOSE_MIN_VELOCITY;

    final double LEFT_POSITION = 0.2962; //the left and right position for the diverter servo
    final double RIGHT_POSITION = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        beginPose                = new Pose2d(0, 0, 0);
        drive              = new MecanumDrive(hardwareMap, beginPose);
        camera             = new CameraSystem(hardwareMap);
        rightFirecracker    =  new Firecracker(hardwareMap, "right_launcher");
        leftFirecracker     =  new Firecracker(hardwareMap, "left_launcher");
        inhaler                 = new Inhaler(hardwareMap, "intake");
        leftFeeder               = new Feeder(hardwareMap, "left_feeder");
        rightFeeder              = new Feeder(hardwareMap, "right_feeder");
        leftLight                   = new LED(hardwareMap, "left_light");
        middleLight                 = new LED(hardwareMap, "middle_light");
        rightLight                  = new LED(hardwareMap, "right_light");
        leftColor       = new ColorSensorCode(hardwareMap, "left_color_sensor");
        rightColor      = new ColorSensorCode(hardwareMap, "right_color_sensor");
        vroom            = new MotorClass(hardwareMap);


        boolean reverse = true;
        boolean notReverse = false;
        int detectedID;

        //adjust reverse if needed
        leftFeeder.initialize(reverse);
        rightFeeder.initialize(notReverse);
        leftFirecracker.initialize(notReverse);
        rightFirecracker.initialize(reverse);
        inhaler.initialize(reverse);
        vroom.initialize(true);
        //camera.cameraOn();


        waitForStart();

        if(opModeIsActive()) {

            leftFirecracker.setTargetVelocity(1150);
            rightFirecracker.setTargetVelocity(1150);

            vroom.motorTest(-0.4);
            sleep(2000);
            vroom.stopMotor();



            //first fire cycle

            launchCycle(leftFeeder, leftFirecracker);

            //second fire cycle
            launchCycle(rightFeeder, rightFirecracker);

            //third fire cycle
            launchCycle(leftFeeder, leftFirecracker);

            launchCycle(rightFeeder, rightFirecracker);

            launchCycle(leftFeeder, leftFirecracker);

            leftFirecracker.ceaseFire();
            rightFirecracker.ceaseFire();

            vroom.powerStrafe(true, 0.5);
            sleep(750);
            vroom.stopMotor();
        }
    }

    void launchCycle(Feeder feed, Firecracker launcher){
        leftFirecracker.crackDaFire();
        rightFirecracker.crackDaFire();

        while(opModeIsActive() && launcher.getCurrentVelocity()<launcherTarget){
            telemetry.addData("Current velocity: ", launcher.getCurrentVelocity());
            telemetry.update();
        }
        inhaler.inhale_on();
        feed.feed_on();
        sleep(1000);
        feed.feed_off();
        inhaler.inhale_off();
    }
}


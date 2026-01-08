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

@Autonomous(group = "MSI")
public final class MSI_FarSimpleScoring extends LinearOpMode {

    private Pose2d beginPose;
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

    @Override
    public void runOpMode() throws InterruptedException {

        beginPose                = new Pose2d(0, 0, 0);
        drive              = new MecanumDrive(hardwareMap, beginPose);
        camera             = new CameraSystem(hardwareMap);
        rightFirecracker    =  new Firecracker(hardwareMap, "right_launcher");
        leftFirecracker     =  new Firecracker(hardwareMap, "left_launcher");
        inhaler1                 = new Inhaler(hardwareMap, "intake");
        inhaler2                 = new Inhaler(hardwareMap, "transfer");
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
        inhaler1.initialize(reverse);
        inhaler2.initialize(notReverse);
        vroom.initialize(true);
        //camera.cameraOn();


        waitForStart();

        if(opModeIsActive()) {
            int patternNumber = 0;
            while(opModeIsActive() && patternNumber == 0){
                patternNumber = camera.getPattern();

            }
            if(patternNumber == 21){  //Green Purple Purple
                //repeats each steps twice in case it failed to launch on the first attempt
                singleIntakeLaunchCycle(leftFeeder, leftFirecracker);
                singleIntakeLaunchCycle(leftFeeder, leftFirecracker);
                singleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                singleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                doubleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                doubleIntakeLaunchCycle(rightFeeder, rightFirecracker);


            }else if(patternNumber == 22){ //Purple Green Purple
                singleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                singleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                singleIntakeLaunchCycle(leftFeeder, leftFirecracker);
                singleIntakeLaunchCycle(leftFeeder, leftFirecracker);
                doubleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                doubleIntakeLaunchCycle(rightFeeder, rightFirecracker);
            }else{                  //Purple Purple Green
                singleIntakeLaunchCycle(leftFeeder, leftFirecracker);
                singleIntakeLaunchCycle(leftFeeder, leftFirecracker);
                doubleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                doubleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                singleIntakeLaunchCycle(rightFeeder, rightFirecracker);
                singleIntakeLaunchCycle(rightFeeder, rightFirecracker);
            }


        //Utilizing the camera to determine the game's Pattern through apriltag



            //first fire cycle



            leftFirecracker.ceaseFire();
            rightFirecracker.ceaseFire();

            vroom.motorTest(0.4);
            sleep(600);
            vroom.stopMotor();
        }
    }

    // A method which only activates the second intake and delievers
    void singleIntakeLaunchCycle(Feeder feed, Firecracker launcher){
        leftFirecracker.crackBigBoyFire();
        rightFirecracker.crackBigBoyFire();
        launcherTarget = LAUNCHER_FAR_TARGET_VELOCITY;
        while(opModeIsActive() && launcher.getCurrentVelocity()<launcherTarget){
            telemetry.addData("Current velocity: ", launcher.getCurrentVelocity());
            telemetry.update();
        }

        inhaler2.inhale_on();
        feed.feed_on();
        sleep(1000);
        feed.feed_off();

        inhaler2.inhale_off();
    }

    //A method that runs both intake systems and deliever
    void doubleIntakeLaunchCycle(Feeder feed, Firecracker launcher){
        leftFirecracker.crackBigBoyFire();
        rightFirecracker.crackBigBoyFire();
        launcherTarget = LAUNCHER_FAR_TARGET_VELOCITY;
        while(opModeIsActive() && launcher.getCurrentVelocity()<launcherTarget){
            telemetry.addData("Current velocity: ", launcher.getCurrentVelocity());
            telemetry.update();
        }
        inhaler1.inhale_on();
        inhaler2.inhale_on();
        feed.feed_on();
        sleep(1000);
        feed.feed_off();
        inhaler1.inhale_off();
        inhaler2.inhale_off();
    }
}


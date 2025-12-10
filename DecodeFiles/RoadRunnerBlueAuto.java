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
public final class RoadRunnerBlueAuto extends LinearOpMode {

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

        beginPose                = new Pose2d(-56, -44, Math.toRadians(233.5));
        launchPose              = new Pose2d(-16,-15, Math.toRadians(227));
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
        //camera.cameraOn();

        Action firstCycle = drive.actionBuilder(beginPose)
                .strafeToLinearHeading(new Vector2d(-16,-15), Math.toRadians(227))
                .build();

        Action secondCycle = drive.actionBuilder(launchPose)
                .splineToSplineHeading(new Pose2d(-6,-22, Math.toRadians(270)), Math.toRadians(270))
                .afterDisp(7, ()->
                        Actions.runBlocking(
                                new ParallelAction(
                                        hammer.left()
                                )
                        ))
                .lineToYLinearHeading(-43, Math.toRadians(270), new TranslationalVelConstraint(10.0))
                .splineToSplineHeading(launchPose, Math.toRadians(90))
                .build();

        Action thirdCycle = drive.actionBuilder(launchPose)
                .splineToSplineHeading(new Pose2d(22,-18, Math.toRadians(270)), Math.toRadians(270))
                .afterDisp(0.1, ()->
                        Actions.runBlocking(
                                new ParallelAction(
                                        hammer.left()
                                )
                        ))
                .lineToYLinearHeading(-34, Math.toRadians(270), new TranslationalVelConstraint(10.0))
                .splineToSplineHeading(launchPose, Math.toRadians(200))
                .build();

        Action fourthCycle = drive.actionBuilder(launchPose)
                .splineToSplineHeading(new Pose2d(48,-25, Math.toRadians(270)), Math.toRadians(270))
                .afterDisp(4, ()->
                        Actions.runBlocking(
                                new ParallelAction(
                                        hammer.left()
                                )
                        ))
                .lineToYLinearHeading(-40, Math.toRadians(270), new TranslationalVelConstraint(10.0))
                .splineToSplineHeading(launchPose, Math.toRadians(140))
                .build();




        waitForStart();

            Actions.runBlocking(
                    new SequentialAction(
                            new ParallelAction(
                                    leftFirecracker.closeLaunch(),
                                    rightFirecracker.closeLaunch(),
                                    inhaler1.intakeOn(),
                                    inhaler2.intakeOn()
                            ),
                            firstCycle,
                            testLaunchCycle(),
                            hammer.right(),
                            secondCycle,
                            testLaunchCycle(),
                            hammer.right(),
                            thirdCycle,
                            testLaunchCycle(),
                            hammer.right(),
                            fourthCycle,
                            testLaunchCycle(),
                            hammer.right()
                    )
            );



    }

    void spitfire(){
        launchCycle(leftFeeder, leftFirecracker);

        //second fire cycle
        launchCycle(rightFeeder, rightFirecracker);

        //third fire cycle
        launchCycle(leftFeeder, leftFirecracker);
    }
    void launchCycle(Feeder feed, Firecracker launcher){
        int state = 1;
        if(opModeIsActive() && launcher.getCurrentVelocity()<launcherTarget){
            telemetry.addData("Current velocity: ", launcher.getCurrentVelocity());
            telemetry.update();
        }
        feed.feed_on();
        Actions.runBlocking(new SleepAction(0.4));
        feed.feed_off();
    }

    public Action callLaunchCycle(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                spitfire();
                return false;
            }
        };
    }

    public class LaunchAction implements Action {
        private final Feeder feed;
        private final Firecracker launcher;
        private int stage = 0;

        public LaunchAction(Feeder feed, Firecracker launcher) {
            this.feed = feed;
            this.launcher = launcher;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            // Stage 0: wait until launcher velocity >= launcherTarget
            if (stage == 0) {
                double vel = launcher.getCurrentVelocity();
                telemetryPacket.put("launch vel", vel);
                // still waiting
                if (vel < launcherTarget) {
                    return false; // not finished
                } else {
                    // ready to feed
                    feed.feed_on();
                    Actions.runBlocking(new SleepAction(0.4));
                    feed.feed_off();
                    return true;
                }
            }

            // fallback
            return true;
        }
    }
    public Action callLaunchCycle2() {
        return new SequentialAction(
                new LaunchAction(leftFeeder, leftFirecracker),
                new LaunchAction(rightFeeder, rightFirecracker),
                new LaunchAction(leftFeeder, leftFirecracker)
        );
    }

    public Action testLaunchCycle(){
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
}


package org.firstinspires.ftc.teamcode.DecodeFiles;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.CameraSystem;
import org.firstinspires.ftc.teamcode.SubSystems.ColorSensorCode;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Firecracker;
import org.firstinspires.ftc.teamcode.SubSystems.Inhaler;
import org.firstinspires.ftc.teamcode.SubSystems.LED;
import org.firstinspires.ftc.teamcode.SubSystems.MotorClass;

@TeleOp
public final class FunctionalityTest extends LinearOpMode {

    private Pose2d beginPose                = new Pose2d(0, 0, 0);
    private MecanumDrive drive              = new MecanumDrive(hardwareMap, beginPose);
    private CameraSystem camera             = new CameraSystem(hardwareMap);
    private Firecracker rightFirecracker    =  new Firecracker(hardwareMap, "right_launcher");
    private Firecracker leftFirecracker     =  new Firecracker(hardwareMap, "left_launcher");
    private Inhaler inhaler                 = new Inhaler(hardwareMap, "intake");
    private Feeder leftFeeder               = new Feeder(hardwareMap, "left_feeder");
    private Feeder rightFeeder              = new Feeder(hardwareMap, "right_feeder");
    private LED leftLight                   = new LED(hardwareMap, "left_light");
    private LED middleLight                 = new LED(hardwareMap, "middle_light");
    private LED rightLight                  = new LED(hardwareMap, "right_light");
    private ColorSensorCode leftColor       = new ColorSensorCode(hardwareMap, "left_color_sensor");
    private ColorSensorCode rightColor      = new ColorSensorCode(hardwareMap, "right_color_sensor");
    private MotorClass test1            = new MotorClass(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        /*
        Pose2d beginPose                = new Pose2d(0, 0, 0);
        MecanumDrive drive              = new MecanumDrive(hardwareMap, beginPose);
        CameraSystem camera             = new CameraSystem(hardwareMap);
        Firecracker rightFirecracker    =  new Firecracker(hardwareMap, "right_launcher");
        Firecracker leftFirecracker     =  new Firecracker(hardwareMap, "left_launcher");
        Inhaler inhaler                 = new Inhaler(hardwareMap, "intake");
        Feeder leftFeeder               = new Feeder(hardwareMap, "left_feeder");
        Feeder rightFeeder              = new Feeder(hardwareMap, "right_feeder");
        LED leftLight                   = new LED(hardwareMap, "left_light");
        LED middleLight                 = new LED(hardwareMap, "middle_light");
        LED rightLight                  = new LED(hardwareMap, "right_light");
        ColorSensorCode leftColor       = new ColorSensorCode(hardwareMap, "left_color_sensor");
        ColorSensorCode rightColor      = new ColorSensorCode(hardwareMap, "right_color_sensor");
        DriveMotorTest test1            = new DriveMotorTest(hardwareMap);

         */
        boolean reverse = true;
        boolean notReverse = false;
        int detectedID;

        //adjust reverse if needed
        leftFeeder.initialize(reverse);
        rightFeeder.initialize(notReverse);
        leftFirecracker.initialize(reverse);
        rightFirecracker.initialize(notReverse);
        inhaler.initialize(notReverse);
        test1.initialize(true);
        camera.cameraOn();

        waitForStart();

        while(opModeIsActive()){
            //id 21 GPP
            //id 23 PPG
            //id 22 PGP
            // press a to start motors
            if(gamepad1.a) {
                test1.motorTest(0.5);
            }
            //press b to stop motors
            if(gamepad1.b){
                test1.stopMotor();
            }
            if(gamepad1.x){

            }
            //right bumper to start detecting april tag
            while(gamepad1.right_trigger > 0){
                detectedID = camera.getPattern();
                telemetry.addData("aprilTag Id: ", detectedID);
                telemetry.update();
                if(detectedID == 21){
                    leftLight.setGreen();
                    middleLight.setPurple();
                    rightLight.setPurple();
                }
                if(detectedID == 22){
                    leftLight.setPurple();
                    middleLight.setGreen();
                    rightLight.setPurple();
                }
                if(detectedID == 23) {
                    leftLight.setPurple();
                    middleLight.setPurple();
                    rightLight.setGreen();
                }
            }
            //left bumper to turn off lights
            if(gamepad1.left_trigger>0){
                leftLight.lightOff();
                middleLight.lightOff();
                rightLight.lightOff();
            }

            if(gamepad1.left_bumper){
                leftColor.updateColor();
                telemetry.addData("Value:", rightColor.getValues());
                telemetry.update();
                if(leftColor.green()){
                    leftLight.setGreen();
                }else if(leftColor.purple()){
                    leftLight.setPurple();
                }else{
                    leftLight.lightOff();
                }
            }

            if(gamepad1.right_bumper){
                rightColor.updateColor();
                telemetry.addData("Value:", rightColor.getValues());
                telemetry.update();
                if(rightColor.green()){
                    rightLight.setGreen();
                }else if(rightColor.purple()){
                    rightLight.setPurple();
                }else{
                    rightLight.lightOff();
                }
            }
        }
        /*if (opModeIsActive()) {
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineToLinearHeading(new Pose2d(11, 20, Math.toRadians(135)), Math.toRadians(135))
                            .strafeToConstantHeading(new Vector2d(50,-10))
                            .build());

        }*/
    }

}


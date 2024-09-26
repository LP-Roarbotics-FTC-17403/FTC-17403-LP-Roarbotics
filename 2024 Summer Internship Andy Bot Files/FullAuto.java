package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.List;

@Autonomous
public class FullAuto extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private DcMotor m0, m1, m2, m3;
    private Servo clawServo;

    @Override
    public void runOpMode() {

        m0 = hardwareMap.get(DcMotor.class, "m0");
        m1 = hardwareMap.get(DcMotor.class, "m1");
        m2 = hardwareMap.get(DcMotor.class, "m2");
        m3 = hardwareMap.get(DcMotor.class, "m3");
        clawServo = hardwareMap.get(Servo.class, "clawServo");

        initAprilTag();

        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetryAprilTag();

                telemetry.update();

                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }
                sleep(20);
            }
        }

        visionPortal.close();
    }

    private void initAprilTag() {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }
    }

    private void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        for (AprilTagDetection detection : currentDetections) {
            telemetry.addData("Tag ID", detection.id);

            if (detection.metadata != null) {
                telemetry.addLine(String.format("(ID %d)", detection.id));
            } else {
                telemetry.addLine(String.format("(ID %d) Unknown", detection.id));
                telemetry.addData("Center", String.format("%6.0f %6.0f (pixels)", detection.center.x, detection.center.y));
            }

            if (detection.id == 1) {
                performAction1();
            } else if (detection.id == 2) {
                performAction2();
            } else if (detection.id == 3) {
                performAction3();
            }

            telemetry.update();
            sleep(20);
        }
    }

    private void performAction1() {
        clawServo.setPosition(0.95);
        sleep(250);
      m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (200);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower(-0.25);
     m0.setPower(-0.25);
    m2.setPower(-0.25);
    m1.setPower(-0.25); 
    sleep (300);
    
    m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
    
    m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (2800);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower(-0.25);
     m0.setPower(-0.25);
    m2.setPower(-0.25);
    m1.setPower(-0.25); 
    sleep (275);
    
    m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
    
    m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (1500);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500); 
      
      m3.setPower (0.25);
      m0.setPower (0.25);
      m2.setPower (0.25);
      m1.setPower (0.25);
      sleep (450);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (250);
      
      clawServo.setPosition(.87);
      sleep(100);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (-0.25);
      sleep (200);
      
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (-0.25);
      m0.setPower (-0.25);
      m2.setPower (-0.25);
      m1.setPower (-0.25);
      sleep (450);
      
      m3.setPower (-0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (0.25);
      sleep (700);
      
      m3.setPower (0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (-0.25);
      sleep (1550);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (-0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (0.25);
      sleep (1350);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (-0.25);
      sleep (1100);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
    }

    private void performAction2() {
        clawServo.setPosition(0.95);
              sleep(250);
      m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (200);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower(-0.25);
     m0.setPower(-0.25);
    m2.setPower(-0.25);
    m1.setPower(-0.25); 
    sleep (300);
    
    m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
    
    m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (2800);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower(-0.25);
     m0.setPower(-0.25);
    m2.setPower(-0.25);
    m1.setPower(-0.25); 
    sleep (300);
    
    m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
    
    m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (1600);
      
      clawServo.setPosition(.87);
      sleep(100);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (-0.25);
      sleep (1850);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (-0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (0.25);
      sleep (1350);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
    }

    private void performAction3() {
        clawServo.setPosition(0.95);
        sleep(250);
        m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (200);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower(-0.25);
     m0.setPower(-0.25);
    m2.setPower(-0.25);
    m1.setPower(-0.25); 
    sleep (300);
    
    m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
    
    m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (2800);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower(-0.25);
     m0.setPower(-0.25);
    m2.setPower(-0.25);
    m1.setPower(-0.25); 
    sleep (300);
    
    m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
    
    m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (1500);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500); 
      
      m3.setPower (-0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (0.25);
      sleep (625);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (100);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      clawServo.setPosition(.87);
      sleep(100);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (-0.25);
      sleep (350);
      
      m3.setPower (0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (-0.25);
      sleep (625);
      
      m3.setPower (0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (-0.25);
      sleep (1200);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (-0.25);
      m0.setPower (-0.25);
      m2.setPower (0.25);
      m1.setPower (0.25);
      sleep (1350);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
      
      m3.setPower (-0.25);
      m0.setPower (0.25);
      m2.setPower (-0.25);
      m1.setPower (0.25);
      sleep (1100);
      
      m3.setPower (0);
      m0.setPower (0);
      m2.setPower (0);
      m1.setPower (0);
      sleep (500);
    }
}

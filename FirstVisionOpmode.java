
package org.firstinspires.ftc.teamcode.opmodes;
import org.firstinspires.ftc.teamcode.HardwarePushbot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.processors.FirstVisionProcessor;

import org.firstinspires.ftc.vision.VisionPortal;

//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
//import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous()

public class FirstVisionOpmode extends OpMode  {


HardwarePushbot robot = new HardwarePushbot();
private ElapsedTime runtime = new ElapsedTime();
private FirstVisionProcessor visionProcessor;

public VisionPortal visionPortal;




static final double COUNTS_PER_MOTOR_REV = 384;
static final double DRIVE_GEAR_REDUCTION = 1.0;
static final double WHEEL_DIAMETER_INCHES = 4.0;
static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
  
static final double DRIVE_SPEED = 0.5;
static final double TURN_SPEED = 0.5;




@Override

public void init() {

visionProcessor = new FirstVisionProcessor();

visionPortal = VisionPortal.easyCreateWithDefaults(

hardwareMap.get(WebcamName.class, "Webcam 1"), visionProcessor);

robot.init(hardwareMap);
  
  telemetry.addData("Status", "Reseting Encoders");
  telemetry.update();
  
  robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
  robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
  robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
  robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        
  robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
  telemetry.addData("Path0", "Starting at %7d :%7d :%7d:%7d", 
      robot.frontLeft.getCurrentPosition(),
      robot.frontRight.getCurrentPosition(),
      robot.backLeft.getCurrentPosition(),
      robot.backRight.getCurrentPosition());   
   telemetry.update();

}


@Override

public void init_loop() {

}


 @Override

public void start() {

//visionPortal.stopStreaming();

}


@Override

public void loop() {


telemetry.addData("Identified", visionProcessor.getSelection());

switch (visionProcessor.getSelection()) {

case LEFT:

telemetry.addData("Randomization Detected", "LEFT");
//telemetry.addData("Average saturation", satRectLeft);

          telemetry.update();

        break;

case NONE:

telemetry.addData("Randomization Detected", "NONE");

          telemetry.update();

break;

case MIDDLE:

telemetry.addData("Randomization Detected", "MIDDLE");

          telemetry.update();
          encoderDrive(DRIVE_SPEED, 30,30,30,30, 10.0);


break;

case RIGHT:

telemetry.addData("Randomization Detected", "RIGHT");

          telemetry.update();

break;

}

}
 public void encoderDrive(double speed,
                          double frontLeftInches, double frontRightInches,double backLeftInches, double backRightInches,
                          double timeoutS) {
  int newfrontLeftTarget;
  int newfrontRightTarget;
  int newbackLeftTarget;
  int newbackRightTarget;
  

    // Determine new positions ans pass to motor controller
    newfrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int)(frontLeftInches * COUNTS_PER_INCH);
    newbackLeftTarget = robot.backLeft.getCurrentPosition() + (int)(backLeftInches * COUNTS_PER_INCH);
    newfrontRightTarget = robot.frontRight.getCurrentPosition() + (int)(frontRightInches * COUNTS_PER_INCH);
    newbackRightTarget = robot.backRight.getCurrentPosition() + (int)(backRightInches * COUNTS_PER_INCH);
    
  
    robot.frontLeft.setTargetPosition(newfrontLeftTarget);
    robot.backLeft.setTargetPosition(newbackLeftTarget);
    robot.frontRight.setTargetPosition(newfrontRightTarget);
    robot.backRight.setTargetPosition(newbackRightTarget);
    
    //Turn on to Run_to_position
    robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    //reset time and start motion
    runtime.reset();
    robot.frontLeft.setPower(Math.abs(speed));
    robot.frontRight.setPower(Math.abs(speed));
    robot.backLeft.setPower(Math.abs(speed));
    robot.backRight.setPower(Math.abs(speed));
    
    while (
    (runtime.seconds() < timeoutS) &&
    (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy())) {
    
    
   telemetry.addData("Path1", "Running to %7d :%7d :%7d:%7d", newfrontLeftTarget, newbackLeftTarget, newfrontRightTarget, newbackRightTarget);
   telemetry.addData("Path2", "Running at %7d : %7d : %7d: %7d", robot.frontLeft.getCurrentPosition(), robot.backLeft.getCurrentPosition(), robot.frontRight.getCurrentPosition(), robot.backRight.getCurrentPosition());
   telemetry.update();
    
}
  
  
  robot.frontLeft.setPower(0);
  robot.backLeft.setPower(0);
  robot.frontRight.setPower(0);
  robot.backRight.setPower(0);
  
  //Turn off
  robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  
  //sleep(100);
  
                           

                          }

}


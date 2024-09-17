// package org.firstinspires.ftc.teamcode;
// 
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
// import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
// import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
// import com.qualcomm.robotcore.util.Range;
// import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
// import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
// import org.firstinspires.ftc.vision.VisionProcessor;
// import com.qualcomm.robotcore.hardware.DcMotorEx;
// import com.qualcomm.robotcore.util.ElapsedTime;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.hardware.CRServo;
// import com.qualcomm.robotcore.hardware.Servo;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.OpMode;
// 
// import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
// import org.firstinspires.ftc.teamcode.processors.FirstVisionProcessor;
// import org.firstinspires.ftc.vision.VisionPortal;
// import org.firstinspires.ftc.vision.VisionPortal.Builder;
// import android.util.Size;
// 
// 
// import com.qualcomm.robotcore.eventloop.opmode.Disabled;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.util.Range;
// import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
// import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
// import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
// import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
// import org.firstinspires.ftc.vision.VisionPortal;
// import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
// import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
// 
// import java.util.List;
// import java.util.concurrent.TimeUnit;
// 
// 
// 
// @Autonomous()
// public class TestApril extends LinearOpMode {
// HardwarePushbot robot = new HardwarePushbot();
// private ElapsedTime runtime = new ElapsedTime();
// 
//     final double DESIRED_DISTANCE = 2.0; //  this is how close the camera should get to the target (inches)
// 
//     //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
//     //  applied to the drive motors to correct the error.
//     //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
//     final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
//     final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
//     final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
// 
//     final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
//     final double MAX_AUTO_STRAFE= 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
//     final double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value (adjust for your robot)
// 
//     private static final boolean USE_WEBCAM = true;  // Set true to use a webcam, or false for a phone camera
//     private static final int DESIRED_TAG_ID = -1;     // Choose the tag you want to approach or set to -1 for ANY tag.
//     private VisionPortal visionPortalA;               // Used to manage the video source.
//     private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
//     private AprilTagDetection desiredTag = null;
// 
// 
// static final double COUNTS_PER_MOTOR_REV = 384;
// static final double DRIVE_GEAR_REDUCTION = 1.0;
// static final double WHEEL_DIAMETER_INCHES = 3.5;
// static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
//  boolean middleDetected = false;
// boolean leftDetected = false;
// boolean rightDetected = false;
// boolean randomizationExecuted = false;
//  
// static final double DRIVE_SPEED = 0.5;
// static final double TURN_SPEED = 0.5;
//     private FirstVisionProcessor visionProcessor;
//     private VisionPortal visionPortal;
//     
//      int i;
// 
//     @Override
//     public void runOpMode() {
// 
// boolean targetFound     = false;    // Set to true when an AprilTag target is detected
//         double  drive           = 0;        // Desired forward power/speed (-1 to +1)
//         double  strafe          = 0;        // Desired strafe power/speed (-1 to +1)
//         double  turn            = 0;  
// 
//         robot.init(hardwareMap);
//   robot.servo3.setPosition(0.55);
//         visionProcessor = new FirstVisionProcessor();
// 
//         // Create a VisionPortal.Builder instance
//         VisionPortal.Builder visionPortalBuilder = new VisionPortal.Builder();
//         
//         //Set camera from hw map
//         visionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
//         
//         //Adding the processor
//         visionPortalBuilder.addProcessor(visionProcessor);
//         
//         // Set camera resolution to 4K
//         visionPortalBuilder.setCameraResolution(new Size(2304, 1536)); // Set resolution to 4K (3840x2160)
// 
//         // Initialize VisionPortal with configured parameters
//         visionPortal = visionPortalBuilder.build(/*
//             hardwareMap.get(WebcamName.class, "Webcam 1"), 
//             visionProcessor*/
//         );
//         /*
//         visionPortal = VisionPortal.easyCreateWithDefaults(
//                 hardwareMap.get(WebcamName.class, "Webcam 1"), visionProcessor);*/
// DcMotor shooter = hardwareMap.dcMotor.get("d3");
//  DcMotor frontLeft = hardwareMap.dcMotor.get("m0");
//     DcMotor backLeft = hardwareMap.dcMotor.get("m1");
//     DcMotor backRight = hardwareMap.dcMotor.get("m2");
//     DcMotor frontRight = hardwareMap.dcMotor.get("m3");
// 
//      DcMotor armLeft = hardwareMap.dcMotor.get("Arm1");
//    DcMotor armRight = hardwareMap.dcMotor.get("Arm2");
//     
//     CRServo servo0 = hardwareMap.get(CRServo.class, "cr0");
//     CRServo servo1 = hardwareMap.get(CRServo.class, "cr1");
//     Servo servo2 = hardwareMap.get(Servo.class, "s2");
//     Servo servo3 = hardwareMap.get(Servo.class, "s3");
//     Servo servo5 = hardwareMap.get(Servo.class, "s5"); //control hub blue side
//      Servo servo6 = hardwareMap.get(Servo.class, "s6"); //expansion hub blue side
//     
//     telemetry.addData("Status", "Reseting Encoders");   
//   telemetry.update();
//   
//   robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//   robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//   robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//   robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//   robot.armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         waitForStart();
// 
//      
// 
//         while (opModeIsActive()) {
//              robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//             telemetry.addData("Identified", visionProcessor.getSelection());
//             
//             
//             switch ((visionProcessor.getSelection())) {
//                case LEFT:
//                  telemetry.addData("Randomization Detected", "LEFT");
//                  telemetry.update();
//                 
//                  encoderDrive(DRIVE_SPEED, 11, 11, -11, -11, 10.0); //turn left
//                 
//                     encoderDrive(DRIVE_SPEED,- 32,-32, -32,-32, 10.0);// move forward
//                      
//                  encoderDrive(DRIVE_SPEED, -1, -1, 1, 1, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED, 5,5, 5,5, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, 5, 5, 5, 5, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED, -30,30, -30,30, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -34,-34, -34,-34, 10.0);// BLUE SIDE STRAFING RIGHT
//                     encoderDrive(DRIVE_SPEED, 34,-34,34,-34, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, 5.5,5.5, -5.5,-5.5, 10.0);// move forward
//                    // encoderDrive(DRIVE_SPEED, 1,1,1,1, 10.0);// move forward
//                     sleep(300);
//                     //encoderDrive(DRIVE_SPEED, -10,10, -10,-10, 10.0);// move forward
//                     //encoderDrive(DRIVE_SPEED, -1,-1, 1,1, 10.0);// BLUE SIDE STRAFING RIGHT
//                     sleep(500);
//                     //encoderDrive(DRIVE_SPEED, -59,-59, 59,59, 10.0);// BLUE SIDE STRAFING RIGHT
//                     robot.servo6.setPosition(0.4);
//                     sleep(3500);
//                     robot.servo6.setPosition(0);
//                     sleep(200);
//                     sleep(30000);
//                     
//             sleep(30000);
//                     break;
// 
//                 case MIDDLE:
//                     telemetry.addData("Randomization Detected", "MIDDLE");
//                     telemetry.update();
//                     
//                     encoderDrive(DRIVE_SPEED,- 35,-35, -35,-35, 10.0);// move forward
//                     sleep(100);
//                     encoderDrive(DRIVE_SPEED, 14,14, 14,14, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -5,-5, -5,-5, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -30,30, -30,30, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -45,-45, -45,-45, 10.0);// BLUE SIDE STRAFING RIGHT
//                     encoderDrive(DRIVE_SPEED, 31,-31, 31,-31, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, 7,7, -7,-7, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -1,-1,-1,-1, 10.0);// move forward
//                    // encoderDrive(DRIVE_SPEED, 0,0, 10,0, 10.0);// move forward
//                     sleep(300);
//                     //encoderDrive(DRIVE_SPEED, -10,10, -10,-10, 10.0);// move forward
//                     //encoderDrive(DRIVE_SPEED, -1,-1, 1,1, 10.0);// BLUE SIDE STRAFING RIGHT
//                     sleep(500);
//                     //encoderDrive(DRIVE_SPEED, -59,-59, 59,59, 10.0);// BLUE SIDE STRAFING RIGHT
//                     robot.servo6.setPosition(0.4);
//                     sleep(4000);
//                     robot.servo6.setPosition(0);
//                     sleep(200);
//                     sleep(30000);
//                     break;
// 
//                 case RIGHT:
//                     telemetry.addData("Randomization Detected", "RIGHT");
//                     telemetry.update();
//                   encoderDrive(DRIVE_SPEED,- 30,-30, -30,-30, 10.0);// move backward
//                     encoderDrive(DRIVE_SPEED, 5, -5, 5, -5, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED,- 2,-2, -2,-2, 10.0);// move backward
//                     encoderDrive(DRIVE_SPEED, 5, -5, 5, -5, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED,- 2,-2, -2,-2, 10.0);// move backward
//                     encoderDrive(DRIVE_SPEED, 5, -5, 5, -5, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED,- 2,-2, -2,-2, 10.0);// move backward
//                     encoderDrive(DRIVE_SPEED, 5, -5, 5, -5, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED,- 2,-2, -2,-2, 10.0);// move backward
//                     encoderDrive(DRIVE_SPEED, 5, -5, 5, -5, 10.0); //turn left
//                    
//                     encoderDrive(DRIVE_SPEED,- 3,-3, -3,-3, 10.0);// move backward
//                     encoderDrive(DRIVE_SPEED, 8, -8, 8, -8, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED, 5, 5, 5,5, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, 45, 45, 45, 45, 10.0); //turn left
//                     
//                     encoderDrive(DRIVE_SPEED, -32, 32, -32, 32, 10.0); //strafe right
// 
// aprilTag = new AprilTagProcessor.Builder().build();
// aprilTag.setDecimation(2);
// 
// visionPortalA = new VisionPortal.Builder().setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                     .addProcessor(aprilTag)
//                     .build();
// setManualExposure(6, 250);
// telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
//         telemetry.addData(">", "Touch Play to start OpMode");
//         telemetry.update();
// targetFound = false;
//  desiredTag  = null;
// 
// // Step through the list of detected tags and look for a matching tag
//             List<AprilTagDetection> currentDetections = aprilTag.getDetections();
//             for (AprilTagDetection detection : currentDetections) {
//                 // Look to see if we have size info on this tag.
//                 if (detection.metadata != null) {
//                     //  Check to see if we want to track towards this tag.
//                     if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
//                         // Yes, we want to use this tag.
//                         targetFound = true;
//                         desiredTag = detection;
//                         break;  // don't look any further.
//                     } else {
//                         // This tag is in the library, but we do not want to track it right now.
//                         telemetry.addData("Skipping", "Tag ID %d is not desired", detection.id);
//                     }
//                 } else {
//                     // This tag is NOT in the library, so we don't have enough information to track to it.
//                     telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
//                 }
//             }
// 
//             // Tell the driver what we see, and what to do.
//             if (targetFound) {
//                 telemetry.addData("\n>","HOLD Left-Bumper to Drive to Target\n");
//                 telemetry.addData("Found", "ID %d (%s)", desiredTag.id, desiredTag.metadata.name);
//                 telemetry.addData("Range",  "%5.1f inches", desiredTag.ftcPose.range);
//                 telemetry.addData("Bearing","%3.0f degrees", desiredTag.ftcPose.bearing);
//                 telemetry.addData("Yaw","%3.0f degrees", desiredTag.ftcPose.yaw);
//             } else {
//                 telemetry.addData("\n>","Drive using joysticks to find valid target\n");
//             
// 
// // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
//                 double  rangeError      = (desiredTag.ftcPose.range - DESIRED_DISTANCE);
//                 double  headingError    = desiredTag.ftcPose.bearing;
//                 double  yawError        = desiredTag.ftcPose.yaw;
// 
//                 // Use the speed and turn "gains" to calculate how we want the robot to move.
//                 drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
//                 turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
//                 strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
// 
//                 telemetry.addData("Auto","Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);
// telemetry.update();
// moveRobot(drive, strafe, turn);
// }
// 
// 
// 
// //EXPOSURE METHOD
// 
// 
// 
// 
// 
// /*
//                      encoderDrive(DRIVE_SPEED, 5,5, -5,-5, 10.0);//move forward
//                     encoderDrive(DRIVE_SPEED, -5,-5, -5,-5, 10.0);*/
// 
//                     robot.servo6.setPosition(0.4);
//                     sleep(3500);
//                     robot.servo6.setPosition(0);
//                     sleep(200);
//             
//                     sleep(30000);
//                 
//         
//         default:
//         telemetry.addData("Randomization Detected", "default");
//                     telemetry.update();
//             }
//         }
//     }
//     
//     
// //MOVE ROBOT METHOD
// public void moveRobot(double x, double y, double yaw) {
//         // Calculate wheel powers.
//         double leftFrontPower    =  x -y -yaw;
//         double rightFrontPower   =  x +y +yaw;
//         double leftBackPower     =  x +y -yaw;
//         double rightBackPower    =  x -y +yaw;
// 
//         // Normalize wheel powers to be less than 1.0
//         double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
//         max = Math.max(max, Math.abs(leftBackPower));
//         max = Math.max(max, Math.abs(rightBackPower));
// 
//         if (max > 1.0) {
//             leftFrontPower /= max;
//             rightFrontPower /= max;
//             leftBackPower /= max;
//             rightBackPower /= max;
//         }
// 
//         // Send powers to the wheels.
//         robot.frontLeft.setPower(leftFrontPower);
//         robot.frontRight.setPower(rightFrontPower);
//         robot.backLeft.setPower(leftBackPower);
//         robot.backRight.setPower(rightBackPower);
//     }
//     private void    setManualExposure(int exposureMS, int gain) {
//         // Wait for the camera to be open, then use the controls
// 
//         if (visionPortalA == null) {
//             return;
//         }
// 
//         // Make sure camera is streaming before we try to set the exposure controls
//         if (visionPortalA.getCameraState() != VisionPortal.CameraState.STREAMING) {
//             telemetry.addData("Camera", "Waiting");
//             telemetry.update();
//             while (!isStopRequested() && (visionPortalA.getCameraState() != VisionPortal.CameraState.STREAMING)) {
//                 sleep(20);
//             }
//             telemetry.addData("Camera", "Ready");
//             telemetry.update();
//         }
// 
//         // Set camera controls unless we are stopping.
//         if (!isStopRequested())
//         {
//             ExposureControl exposureControl = visionPortalA.getCameraControl(ExposureControl.class);
//             if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
//                 exposureControl.setMode(ExposureControl.Mode.Manual);
//                 sleep(50);
//             }
//             exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
//             sleep(20);
//             GainControl gainControl = visionPortalA.getCameraControl(GainControl.class);
//             gainControl.setGain(gain);
//             sleep(20);
//         }
//     }
//             public void encoderDrive(double speed,
//                           double frontLeftInches, double frontRightInches,double backLeftInches, double backRightInches,
//                           double timeoutS) {
//   int newfrontLeftTarget;
//   int newfrontRightTarget;
//   int newbackLeftTarget;
//   int newbackRightTarget;
//   
// if (opModeIsActive()){
//     // Determine new positions ans pass to motor controller
//     newfrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int)(frontLeftInches * COUNTS_PER_INCH);
//     newbackLeftTarget = robot.backLeft.getCurrentPosition() + (int)(backLeftInches * COUNTS_PER_INCH);
//     newfrontRightTarget = robot.frontRight.getCurrentPosition() + (int)(frontRightInches * COUNTS_PER_INCH);
//     newbackRightTarget = robot.backRight.getCurrentPosition() + (int)(backRightInches * COUNTS_PER_INCH);
//     
//   
//     robot.frontLeft.setTargetPosition(newfrontLeftTarget);
//     robot.backLeft.setTargetPosition(newbackLeftTarget);
//     robot.frontRight.setTargetPosition(newfrontRightTarget);
//     robot.backRight.setTargetPosition(newbackRightTarget);
//     
//     //Turn on to Run_to_position
//     robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//     robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//     robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//     robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//     
//     //reset time and start motion
//     runtime.reset();
//     robot.frontLeft.setPower(Math.abs(speed));
//     robot.frontRight.setPower(Math.abs(speed));
//     robot.backLeft.setPower(Math.abs(speed));
//     robot.backRight.setPower(Math.abs(speed));
//     
//     while (opModeIsActive()  &&
//     (runtime.seconds() < timeoutS) &&
//     (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy())) {
//     
//     
//    telemetry.addData("Path1", "Running to %7d :%7d :%7d:%7d", newfrontLeftTarget, newbackLeftTarget, newfrontRightTarget, newbackRightTarget);
//    telemetry.addData("Path2", "Running at %7d : %7d : %7d: %7d", robot.frontLeft.getCurrentPosition(), robot.backLeft.getCurrentPosition(), robot.frontRight.getCurrentPosition(), robot.backRight.getCurrentPosition());
//    telemetry.update();
//     
// }
//   
//   
//   robot.frontLeft.setPower(0);
//   robot.backLeft.setPower(0);
//   robot.frontRight.setPower(0);
//   robot.backRight.setPower(0);
//   
//   //Turn off
//   robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   
//   //sleep(100);
//   
//                            }
// 
//                           }
//     
// }
// 
// 
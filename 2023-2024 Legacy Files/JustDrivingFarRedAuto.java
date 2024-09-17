// package org.firstinspires.ftc.teamcode;
// 
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
// import org.firstinspires.ftc.vision.VisionPortal;
// import com.qualcomm.robotcore.hardware.DcMotorEx;
// import com.qualcomm.robotcore.util.ElapsedTime;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.OpMode;
// 
// 
// 
// 
// @Autonomous()
// public class JustDrivingFarRedAuto extends LinearOpMode {
// HardwarePushbot robot = new HardwarePushbot();
// //private ElapsedTime runtime = new ElapsedTime();
// 
// static final double COUNTS_PER_MOTOR_REV = 384;
// static final double DRIVE_GEAR_REDUCTION = 1.0;
// static final double WHEEL_DIAMETER_INCHES = 3.5;
// static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
//  /*boolean middleDetected = false;
// boolean leftDetected = false;
// boolean rightDetected = false;
// boolean randomizationExecuted = false;*/
//  
// static final double DRIVE_SPEED = 0.5;
// static final double TURN_SPEED = 0.5;
// //Servo servo6 = hardwareMap.get(Servo.class, "s6");
//    
//     @Override
//     public void runOpMode() {
//         robot.init(hardwareMap);
//   /*robot.servo3.setPosition(0.55);
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
//             
// //DcMotor shooter = hardwareMap.dcMotor.get("d3");
//  DcMotor frontLeft = hardwareMap.dcMotor.get("m0");
//     DcMotor backLeft = hardwareMap.dcMotor.get("m1");
//     DcMotor backRight = hardwareMap.dcMotor.get("m2");
//     DcMotor frontRight = hardwareMap.dcMotor.get("m3");
// 
//      //DcMotor armLeft = hardwareMap.dcMotor.get("Arm1");
//    //DcMotor armRight = hardwareMap.dcMotor.get("Arm2");
//     
//     /*CRServo servo0 = hardwareMap.get(CRServo.class, "cr0");
//     CRServo servo1 = hardwareMap.get(CRServo.class, "cr1");
//     Servo servo2 = hardwareMap.get(Servo.class, "s2");
//     Servo servo3 = hardwareMap.get(Servo.class, "s3");
//     Servo servo5 = hardwareMap.get(Servo.class, "s5"); //control hub blue side
//     Servo servo6 = hardwareMap.get(Servo.class, "s6"); //expansion hub blue side
//     
//     telemetry.addData("Status", "Reseting Encoders");   
//   telemetry.update();*/
//   
//   robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//   robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//   robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//   robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//   //robot.armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         waitForStart();
// 
//      
// 
//         while (opModeIsActive()) {
//              robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//   //robot.armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//             //telemetry.addData("Identified", visionProcessor.getSelection());
//             
//             
//             if (opModeIsActive()){
//                     telemetry.addData("Randomization Detected", "RIGHT");
//                     telemetry.update();
//                     sleep(3000);
//                     encoderDrive(DRIVE_SPEED, 2, 2, -2, -2, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED, -2, -2, -2, -2, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED, -1, 1, -1, 1, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED, -12, -12, 12, 12, 10.0); //turn left
//                     //encoderDrive(DRIVE_SPEED, 5,5, 5,5, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -32,-32, -32,-32, 10.0);// move forward
//                     
//                     encoderDrive(DRIVE_SPEED, 28,28,28,28, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, 32,-32, 32,-32, 10.0);// move forward
//                     //encoderDrive(DRIVE_SPEED, -40,-40, -40,-40, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -90,-90, -90,-90, 10.0);// BLUE SIDE STRAFING RIGHT
//                     encoderDrive(DRIVE_SPEED,10,10,-10,-10, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED,-30,30,-30,30, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-34,-34, -34, -34, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-40,-40, 40, 40, 10.0);// move forward
//        
//                    
//                    //encoderDrive(DRIVE_SPEED,-8,-8, 8, 8, 10.0);// move forward
//                     sleep(300);
//                     //encoderDrive(DRIVE_SPEED, -10,10, -10,-10, 10.0);// move forward
//                     //encoderDrive(DRIVE_SPEED, -1,-1, 1,1, 10.0);// BLUE SIDE STRAFING RIGHT
//                     sleep(500);
//                     //encoderDrive(DRIVE_SPEED, -59,-59, 59,59, 10.0);// BLUE SIDE STRAFING RIGHT
//                     //robot.servo5.setPosition(0.4);
//                     sleep(4000);
//                     //robot.servo5.setPosition(0);
//                     sleep(200);
//                     sleep(30000);
//                     break;
// 
//                 
//                     /*telemetry.addData("Randomization Detected", "MIDDLE");
//                     telemetry.update();
//                     sleep(3000);
//                     
//                     encoderDrive(DRIVE_SPEED,- 35,-35, -35,-35, 10.0);// move forward
//                     sleep(100);
//                     //encoderDrive(DRIVE_SPEED, 5,5, 5,5, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, 31,31, 31,31, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, 31,-31, 31,-31, 10.0);// move forward
//                     //encoderDrive(DRIVE_SPEED, -40,-40, -40,-40, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -90,-90, -90,-90, 10.0);// BLUE SIDE STRAFING RIGHT
//                     encoderDrive(DRIVE_SPEED,10,10,-10,-10, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED,-31,31,-31,31, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-25,-25, -25, -25, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-30,-30, 30, 30, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-3,-3, 3, 3, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,3,3, 3, 3, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-3,-3, 3, 3, 10.0);// move forward
//                     sleep(300);
//                     //encoderDrive(DRIVE_SPEED, -10,10, -10,-10, 10.0);// move forward
//                     //encoderDrive(DRIVE_SPEED, -1,-1, 1,1, 10.0);// BLUE SIDE STRAFING RIGHT
//                     sleep(500);
//                     //encoderDrive(DRIVE_SPEED, -59,-59, 59,59, 10.0);// BLUE SIDE STRAFING RIGHT
//                     //robot.servo5.setPosition(0.4);
//                     sleep(4000);
//                     //robot.servo5.setPosition(0);
//                     sleep(200);
//                     sleep(30000);
//                     break;
//                     
//                 
//                 telemetry.addData("Randomization Detected", "LEFT");
//                  telemetry.update();
//                  sleep(3000);
//                  encoderDrive(DRIVE_SPEED,- 30,-30, -30,-30, 10.0);// move backward
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
//                     encoderDrive(DRIVE_SPEED,- 5,-5, -5,-5, 10.0);// move backward
//                     encoderDrive(DRIVE_SPEED, 8, -8, 8, -8, 10.0); //turn left
//                     encoderDrive(DRIVE_SPEED, 5, 5, 5,5, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, 10, 10, 10,10, 10.0);// move forward
//                  
//                     encoderDrive(DRIVE_SPEED, -41, -41, 41,41, 10.0);// move forward
//                    sleep(500);
//                     //encoderDrive(DRIVE_SPEED, -32,32, -32,32, 10.0);// move forward
//                     //encoderDrive(DRIVE_SPEED, -40,-40, -40,-40, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED, -90,-90, -90,-90, 10.0);// BLUE SIDE STRAFING RIGHT
//                     encoderDrive(DRIVE_SPEED,10,10,-10,-10, 10.0);// move forward
//                     encoderDrive(DRIVE_SPEED,-30,30,-30,30, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-12,-12, -12, -12, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-40,-40, 40, 40, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-3,3, -3, 3, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,1.5, 1.5, 1.5, 1.5, 10.0);// move forward
//                    encoderDrive(DRIVE_SPEED,-2, -2, 2, 2, 10.0);// move forward
//                     sleep(300);
//                     //robot.servo5.setPosition(0.4);
//                     sleep(3500);
//                     //robot.servo5.setPosition(0);
//                     sleep(200);
//                     
//             sleep(30000);
//                     break;
//                     
//         
//         default:
//         telemetry.addData("Randomization Detected", "default");
//                     telemetry.update();
//             }
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
//   //sleep(100);*/
//   
//                            }
// 
//                           }
//     
// }
// }
// 
//        
// 
// 
// 

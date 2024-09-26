// 
// package org.firstinspires.ftc.robotcontroller.external.samples;
// 
// import com.qualcomm.robotcore.eventloop.opmode.Disabled;
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
// import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
// import org.firstinspires.ftc.vision.VisionPortal;
// import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
// import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.util.ElapsedTime;
// import com.qualcomm.robotcore.hardware.CRServo;
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// import com.qualcomm.robotcore.hardware.Servo;
// 
// import java.util.List;
// 
// 
// @Autonomous(name = "Concept: AprilTag Easy", group = "Concept")
// 
// public class Test extends LinearOpMode {
// 
//     private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
// 
//     /**
//      * The variable to store our instance of the AprilTag processor.
//      */
//     private AprilTagProcessor aprilTag;
// 
//     /**
//      * The variable to store our instance of the vision portal.
//      */
//     private VisionPortal visionPortal;
// 
//     @Override
//     public void runOpMode() {
// 
//         initAprilTag();
// 
//         // Wait for the DS start button to be touched.
//         telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
//         telemetry.addData(">", "Touch Play to start OpMode");
//         telemetry.update();
//         waitForStart();
// 
//         if (opModeIsActive()) {
//             while (opModeIsActive()) {
// 
//                 telemetryAprilTag();
// 
//                 // Push telemetry to the Driver Station.
//                 telemetry.update();
// 
//                 // Save CPU resources; can resume streaming when needed.
//                 if (gamepad1.dpad_down) {
//                     visionPortal.stopStreaming();
//                 } else if (gamepad1.dpad_up) {
//                     visionPortal.resumeStreaming();
//                 }
// 
//                 // Share the CPU.
//                 sleep(20);
//             }
//         }
// 
//         // Save more CPU resources when camera is no longer needed.
//         visionPortal.close();
// 
//     }   // end method runOpMode()
// 
//     /**
//      * Initialize the AprilTag processor.
//      */
//     private void initAprilTag() {
// 
//         // Create the AprilTag processor the easy way.
//         aprilTag = AprilTagProcessor.easyCreateWithDefaults();
// 
//         // Create the vision portal the easy way.
//         if (USE_WEBCAM) {
//             visionPortal = VisionPortal.easyCreateWithDefaults(
//                 hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
//         } else {
//             visionPortal = VisionPortal.easyCreateWithDefaults(
//                 BuiltinCameraDirection.BACK, aprilTag);
//         }
// 
//     }   // end method initAprilTag()
// 
//     /**
//      * Add telemetry about AprilTag detections.
//      */
//     private void telemetryAprilTag() {
// 
//         List<AprilTagDetection> currentDetections = aprilTag.getDetections();
//         telemetry.addData("# AprilTags Detected", currentDetections.size());
// 
//         // Step through the list of detections and display info for each one.
//         for (AprilTagDetection detection : currentDetections) {
//             if (detection.metadata != null) {
//                 telemetry.addLine(String.format("\n (ID %d) ", detection.id));
//                 
//             } else {
//                 telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
//                 telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
//             }
//         }   // end for() loop
// 
// if (detection.id != null) {
//             if (detection.id == 1) {
//                  // left code
//             } else if (detection.id == 2) {
//                 // middle code
//             } else if (detection.id == 3) {
//                 // right code
//             }
//         }
// 
//     }   // end method telemetryAprilTag()
// 
// }   // end class
// 
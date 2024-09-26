// package org.firstinspires.ftc.teamcode;
// 
// import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// import com.qualcomm.robotcore.eventloop.opmode.OpMode;
// import com.qualcomm.robotcore.hardware.IMU;
// import com.qualcomm.robotcore.hardware.HardwareMap;
// 
// import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
// import org.firstinspires.ftc.teamcode.MecanumDrive;
// 
// @TeleOp()
//   public class FieldOrientedMecanumDrive extends OpMode {
//     MecanumDrive drive = new MecanumDrive();
//     IMU imu;
// 
//     @Override
//     public void init() {
//         drive.init(hardwareMap);
//      
//     imu = hardwareMap.get(IMU.class, "imu");
//      RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, 
//      RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
//      
//      imu.initialize(new IMU.Parameters(RevOrientation));
//      
//     }
//     
//     private void driveFieldRelative(double forward, double right, double rotate) {
//         double robotAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
//         
//         double theta = Math.atan2(forward, right);
//         double r = Math.hypot(forward, right);
//         
//         theta = AngleUnit.normalizeRadians(theta - robotAngle);
//         
//         double newForward = r * Math.sin(theta);
//         double newRight = r * Math.cos(theta);
//         
//         drive.drive(newForward, newRight, rotate);
//     }
//      
//      @Override
//      public void loop() {
//          double forward = gamepad1.left_stick_y;
//          double right = -gamepad1.left_stick_x;
//          double rotate = -gamepad1.right_stick_x;
//          
//          driveFieldRelative(forward, right, rotate);
//      }
//     }
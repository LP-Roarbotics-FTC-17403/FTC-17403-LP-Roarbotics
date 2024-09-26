package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "TelemetryTest")
public class TelemetryTest extends LinearOpMode {
  private DcMotor m0;
  private DcMotor m1;
  private DcMotor m2;
  private DcMotor m3;
  private IMU imu;
  

  @Override
  public void runOpMode() {
    m0 = hardwareMap.get(DcMotor.class, "m0");
    m1 = hardwareMap.get(DcMotor.class, "m1");
    m2 = hardwareMap.get(DcMotor.class, "m2");
    m3 = hardwareMap.get(DcMotor.class, "m3");
    imu = hardwareMap.get(IMU.class,"imu");
  
  IMU.Parameters myIMUparameters;
  myIMUparameters = new IMU.Parameters(
        new RevHubOrientationOnRobot(
          RevHubOrientationOnRobot.LogoFacingDirection.UP,
          RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
          )
    );
  imu.initialize(myIMUparameters);
  YawPitchRollAngles robotOrientation;
  robotOrientation = imu.getRobotYawPitchRollAngles();
  double Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
  double Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
  double Roll = robotOrientation.getRoll(AngleUnit.DEGREES);

    
  waitForStart();

  /*while (opModeIsActive()) {
  robotOrientation = imu.getRobotYawPitchRollAngles();
  Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
  Roll = robotOrientation.getRoll(AngleUnit.DEGREES);
  Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
  telemetry.addData("Yaw", Yaw);
  telemetry.addData("Roll", Roll);
  telemetry.addData("Pitch", Pitch);
  telemetry.update();
  sleep(100);  */
  while (opModeIsActive()) {
       robotOrientation = imu.getRobotYawPitchRollAngles();
  Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
  Roll = robotOrientation.getRoll(AngleUnit.DEGREES);
  Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
  telemetry.addData("Yaw", Yaw);
  telemetry.addData("Roll", Roll);
  telemetry.addData("Pitch", Pitch);
  telemetry.update();
    m0.setPower(0.4);
    m1.setPower(-0.4);
    m2.setPower(-0.4);
    m3.setPower(0.4);
    sleep(1000);
    robotOrientation = imu.getRobotYawPitchRollAngles();
  Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
  Roll = robotOrientation.getRoll(AngleUnit.DEGREES);
  Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
  telemetry.addData("Yaw", Yaw);
  telemetry.addData("Roll", Roll);
  telemetry.addData("Pitch", Pitch);
  telemetry.update();
  sleep(100);  
    m0.setPower(0.4);
    m1.setPower(0.4);
    m2.setPower(-0.4);
    m3.setPower(-0.4);
       robotOrientation = imu.getRobotYawPitchRollAngles();
  Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
  Roll = robotOrientation.getRoll(AngleUnit.DEGREES);
  Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
  telemetry.addData("Yaw", Yaw);
  telemetry.addData("Roll", Roll);
  telemetry.addData("Pitch", Pitch);
  telemetry.update();
    sleep(1000);
    m0.setPower(-0.4);
    m1.setPower(0.4);
    m2.setPower(0.4);
    m3.setPower(-0.4);
       robotOrientation = imu.getRobotYawPitchRollAngles();
  Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
  Roll = robotOrientation.getRoll(AngleUnit.DEGREES);
  Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
  telemetry.addData("Yaw", Yaw);
  telemetry.addData("Roll", Roll);
  telemetry.addData("Pitch", Pitch);
  telemetry.update();
    sleep(1000);
    m0.setPower(-0.4);
    m1.setPower(-0.4);
    m2.setPower(0.4);
    m3.setPower(0.4);
       robotOrientation = imu.getRobotYawPitchRollAngles();
  Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
  Roll = robotOrientation.getRoll(AngleUnit.DEGREES);
  Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
  telemetry.addData("Yaw", Yaw);
  telemetry.addData("Roll", Roll);
  telemetry.addData("Pitch", Pitch);
  telemetry.update();
    sleep(1000);
    m0.setPower(0);
    m1.setPower(0);
    m2.setPower(0);
    m3.setPower(0);
       robotOrientation = imu.getRobotYawPitchRollAngles();
  Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
  Roll = robotOrientation.getRoll(AngleUnit.DEGREES);
  Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
  telemetry.addData("Yaw", Yaw);
  telemetry.addData("Roll", Roll);
  telemetry.addData("Pitch", Pitch);
  telemetry.update();
    sleep(1000);
}
  }
  }


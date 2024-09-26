package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "imu_science")
public class imu_science extends LinearOpMode {

  private DcMotor m0;
  private DcMotor m1;
  private DcMotor m2;
  private DcMotor m3;
  private IMU imu;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    m0 = hardwareMap.get(DcMotor.class, "m0");
    m1 = hardwareMap.get(DcMotor.class, "m1");
    m2 = hardwareMap.get(DcMotor.class, "m2");
    m3 = hardwareMap.get(DcMotor.class, "m3");
    imu = hardwareMap.get(IMU.class, "imu");

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

    AngularVelocity myRobotAngularVelocity;
    myRobotAngularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);

    float zRotationRate = myRobotAngularVelocity.zRotationRate;
    float yRotationRate = myRobotAngularVelocity.yRotationRate;
    float xRotationRate = myRobotAngularVelocity.xRotationRate;

    // Put initialization blocks here.
    /*telemetry.addData("Yaw",robotOrientation.getYaw(AngleUnit.DEGREES));
    telemetry.addData("Pitch", robotOrientation.getPitch(AngleUnit.DEGREES));
    telemetry.addData("Roll",robotOrientation.getRoll(AngleUnit.DEGREES));
    telemetry.addData("zVelo",myRobotAngularVelocity.zRotationRate);*/
    telemetry.addData("yVelo",myRobotAngularVelocity.yRotationRate);
    telemetry.addData("xVelo",myRobotAngularVelocity.xRotationRate);
    telemetry.update();  
   waitForStart();
    
    if (opModeIsActive()) {
      // Put run blocks here.
      sleep(2500);
      m0.setPower(0.4);
      m1.setPower(0.4);
      m2.setPower(-0.4);
      m3.setPower(-0.4);
      sleep(1000);
      m0.setPower(0.2);
      m1.setPower(0.2);
      m2.setPower(-0.2);
      m3.setPower(-0.2);
      sleep(1000);
      m0.setPower(0.4);
      m1.setPower(-0.4);
      m2.setPower(-0.4);
      m3.setPower(0.4);
      telemetry.update();
      sleep(1000);
      m0.setPower(0.4);
      m1.setPower(0.4);
      m2.setPower(0.4);
      m3.setPower(0.4);
      sleep(1000);
      m0.setPower(0);
      m1.setPower(0);
      m2.setPower(0);
      m3.setPower(0);
      sleep(5000);
      }
     while (opModeIsActive()) {
      telemetry.addData("xVelo",myRobotAngularVelocity.xRotationRate);
      telemetry.addData("yVelo", myRobotAngularVelocity.yRotationRate);
      telemetry.update();
  }
}
}
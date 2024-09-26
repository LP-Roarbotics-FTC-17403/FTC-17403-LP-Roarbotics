package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "auto experiments")
public class autoexperiments extends LinearOpMode {

  private DcMotor m0;
  private DcMotor m1;
  private DcMotor m2;
  private DcMotor m3;
  private IMU imu;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */

  public void runOpMode() {
    m0 = hardwareMap.get(DcMotor.class, "m0");
    m1 = hardwareMap.get(DcMotor.class, "m1");
    m2 = hardwareMap.get(DcMotor.class, "m2");
    m3 = hardwareMap.get(DcMotor.class, "m3");
   imu = hardwareMap.get(IMU.class, "imu");

RevHubOrientationOnRobot RevOrientation = 
 new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
  RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD);
  
  imu.initialize(new IMU.Parameters(RevOrientation));
  
  /*public double getHeading  {
    return imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
  }*/
  
  waitForStart();
  if (opModeIsActive()) {
    m0.setPower(0.4);
    m1.setPower(0.4);
    m2.setPower(-0.4);
    m3.setPower(-0.4);
    sleep(1000);
    m0.setPower(0.4);
    m1.setPower(0.4);
    m2.setPower(0.4);
    m3.setPower(0.4);
    sleep(250);
  }
}


   
}
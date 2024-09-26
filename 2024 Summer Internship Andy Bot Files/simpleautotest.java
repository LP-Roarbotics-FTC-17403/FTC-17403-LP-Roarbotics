package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "simpleautotest (Blocks to Java)")
public class simpleautotest extends LinearOpMode {

  private DcMotor m0;
  private DcMotor m1;
  private DcMotor m2;
  private DcMotor m3;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    m0 = hardwareMap.get(DcMotor.class, "m0");
    m1 = hardwareMap.get(DcMotor.class, "m1");
    m2 = hardwareMap.get(DcMotor.class, "m2");
    m3 = hardwareMap.get(DcMotor.class, "m3");

    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      m0.setPower(0.4);
      m1.setPower(0.4);
      m2.setPower(0.4);
      m3.setPower(0.4);
      sleep(1000);
      m0.setPower(0);
      m1.setPower(0);
      m2.setPower(0);
      m3.setPower(0);
      sleep(1000);
    }
  }
}

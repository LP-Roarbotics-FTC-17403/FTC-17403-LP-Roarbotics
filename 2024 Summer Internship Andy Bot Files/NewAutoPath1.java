package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous

public class NewAutoPath1 extends LinearOpMode {

    private DcMotor m0;
    private DcMotor m1;
    private DcMotor m2;
    private DcMotor m3;
    private Servo clawServo;


  @Override
  public void runOpMode() {
    m0 = hardwareMap.get(DcMotor.class, "m0");
    m1 = hardwareMap.get(DcMotor.class, "m1");
    m2 = hardwareMap.get(DcMotor.class, "m2");
    m3 = hardwareMap.get(DcMotor.class, "m3");
    clawServo = hardwareMap.get(Servo.class, "clawServo");
    
     waitForStart();
    if (opModeIsActive()) {
      clawServo.setPosition(.95);
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
      m2.setPower (-0.25);
      m1.setPower (-0.25);
      sleep (700);
      
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
      sleep (300);
      
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
      
      
    }
  }
}

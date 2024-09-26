package org.firstinspires.ftc.testcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class Red_F2_Autos extends LinearOpMode{
  DcMotor motor0;
  DcMotor motor1;
  DcMotor motor2;
  DcMotor motor3;

@Override
public void runOpMode() {
    motor3 = hardwareMap.get(DcMotor.class, "m3");
    motor2 = hardwareMap.get(DcMotor.class, "m2");
    motor1 = hardwareMap.get(DcMotor.class, "m1");
    motor0 = hardwareMap.get(DcMotor.class, "m0");
    
    
waitForStart();
if (opModeIsActive()) {
    //move forward
        motor0.setPower(0.5);
        motor1.setPower(0.5);
        motor2.setPower(-0.5);
        motor3.setPower(-0.5);
        sleep(920);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
    //turn right
        motor0.setPower(-0.5);
        motor1.setPower(-0.5);
        motor2.setPower(-0.5);
        motor3.setPower(-0.5);
        sleep(450);
    
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
    //move straight
        motor0.setPower(0.5);
        motor1.setPower(0.5);
        motor2.setPower(-0.5);
        motor3.setPower(-0.5);
        sleep(1750);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
        }
    }
}

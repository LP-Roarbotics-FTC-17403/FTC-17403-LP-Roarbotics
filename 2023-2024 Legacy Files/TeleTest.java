package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.configuration.annotations.ServoType;
import java.util.Set;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TeleTest extends LinearOpMode {
   

   @Override
   public void runOpMode() throws InterruptedException 
   {
      DcMotor motor3 = hardwareMap.dcMotor.get("motor3");
      DcMotor motor2 = hardwareMap.dcMotor.get("motor2");
      DcMotor motor0 = hardwareMap.dcMotor.get("motor0");
      DcMotor motor1 = hardwareMap.dcMotor.get("motor1");




      motor0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


      
      double sped = 0.7;
      

      waitForStart();
      if (isStopRequested()) return;
      while (opModeIsActive()) 
      {
         double y = (-gamepad1.left_stick_y/2); 
         double x = (gamepad1.left_stick_x/2) * 1.125; 
         double rx = (gamepad1.right_stick_x/2);
    
         
         double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
         double motor0Power = (y - x - rx) / denominator;
         double motor1Power = (y + x - rx) / denominator;
         double motor2Power = -(y - x + rx) / denominator;
         double motor3Power = (y + x + rx) / denominator;
         if (gamepad2.dpad_left) //Dpad right and left Moves robot horizantaly on barrier 
         {
            motor0.setPower(0.5);
            motor1.setPower(-0.5);
            motor2.setPower(0.5);
            motor3.setPower(-0.5);
         }
         else if (gamepad2.dpad_right)
         {
            motor0.setPower(-0.5);
            motor1.setPower(0.5);
            motor2.setPower(-0.5);
            motor3.setPower(0.5);
         }
         else if (gamepad1.right_trigger>0.5&&y<0)
         {
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power/2);
            motor3.setPower(motor3Power/2);
         }
         else if (gamepad1.left_trigger>0.5&&y<0)
         {
            motor0.setPower(motor0Power/2);
            motor1.setPower(motor1Power/2);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         else if (gamepad1.left_trigger>0.5&&y>0)
         {
            motor0.setPower(motor0Power/2);
            motor1.setPower(motor1Power/2);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         else if (gamepad1.right_trigger>0.5&&y>0)
         {
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power/2);
            motor3.setPower(motor3Power/2);
         }
         else
         {
            motor0.setPower(motor0Power/1.3);
            motor1.setPower(motor1Power/1.3);
            motor2.setPower(motor2Power/1.3);
            motor3.setPower(motor3Power/1.3);
         }
         
         
         if (gamepad2.right_trigger>0)
         {
            sped = 1.0;
         }
         else if (gamepad2.right_bumper)
         {
            sped = 0.7;
         }
         else
         {
            sped = 0.4;
         }
         
         
         
         
         telemetry.addData("motor0", motor0.getPower());
         telemetry.addData("motor1", motor1.getPower());
         telemetry.addData("motor2", motor2.getPower());
         telemetry.addData("motor3", motor3.getPower());

         //telemetry.addData("getElevPosition", elevator);
         telemetry.update();
      }
   }
}

package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.configuration.annotations.ServoType;
import java.util.Set;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Driving2023 extends LinearOpMode {
   

   @Override
   public void runOpMode() throws InterruptedException 
   {
       
      DcMotor motor0 = hardwareMap.dcMotor.get("motor0");
      DcMotor motor1 = hardwareMap.dcMotor.get("motor1");
      DcMotor motor2 = hardwareMap.dcMotor.get("motor2");
      DcMotor motor3 = hardwareMap.dcMotor.get("motor3");
      DcMotor Elev1 = hardwareMap.dcMotor.get("Elev1");
      DcMotor Elev2 = hardwareMap.dcMotor.get("Elev2");

      Servo  GripRight = hardwareMap.servo.get("GripRight");
      Servo  GripLeft = hardwareMap.servo.get("GripLeft");
      
      


      //brakes
      motor0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      Elev1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      Elev2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

      GripRight.setPosition(0.06);
      GripLeft.setPosition(0.98);

      waitForStart();
      if (isStopRequested()) return;
      while (opModeIsActive()) 
      {
         double y = (-gamepad1.left_stick_y/2); 
         double x = (gamepad1.left_stick_x/2) * 1.125; 
         double rx = (gamepad1.right_stick_x/2);
         
         
          if (gamepad1.left_stick_button) // stick buttons are the new accelerators
         {
            y = (-gamepad1.left_stick_y); 
            x = (gamepad1.left_stick_x) * 1.125; 
         }
         else if (gamepad1.right_stick_button)
         {
            rx = (gamepad1.right_stick_x);
         }

        
         
         
         double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
         double motor0Power = (y - x - rx) / denominator;
         double motor1Power = (y + x - rx) / denominator;
         double motor2Power = -(y - x + rx) / denominator;
         double motor3Power = (y + x + rx) / denominator;
         if (gamepad1.dpad_left) //Dpad right and left Moves robot horizantaly on barrier 
         {
            motor0.setPower(0.5);
            motor1.setPower(-0.5);
            motor2.setPower(0.5);
            motor3.setPower(-0.5);
         }
         else if (gamepad1.dpad_right)
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
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         
         if (gamepad1.right_bumper)
         {
             Elev1.setPower(-0.7);
             Elev2.setPower(0.7);


         } 
         else if (gamepad1.left_bumper)
         {
             Elev1.setPower(0.7);
             Elev2.setPower(-0.7);

         }
         
         else {
             Elev1.setPower(0);
             Elev2.setPower(0);

         }
         
         if (gamepad1.left_trigger >0.1)
         { //holding x closes the grip
            GripRight.setPosition(0.5);
            GripLeft.setPosition(0.5);
            sleep(300);
            
         }
         else if(gamepad1.right_trigger>0.1)
         {
            GripRight.setPosition(0.06);
            GripLeft.setPosition(0.98);
         }
         
         
         
         
         
         
         

         double y2 = (-gamepad2.left_stick_y/2); 
         double x2 = (gamepad2.left_stick_x/2) * 1.125; 
         double rx2 = (gamepad2.right_stick_x/2);
         
         
          if (gamepad2.left_stick_button) // stick buttons are the new accelerators
         {
            y2 = (-gamepad2.left_stick_y); 
            x2 = (gamepad2.left_stick_x) * 1.125; 
         }
         else if (gamepad2.right_stick_button)
         {
            rx2 = (gamepad2.right_stick_x);
         }

        
         
         
         double denominator2 = Math.max(Math.abs(y2) + Math.abs(x2) + Math.abs(rx2), 1);
         double motor0Power2 = (y2 - x2 - rx2) / denominator2;
         double motor1Power2 = (y2 + x2 - rx2) / denominator2;
         double motor2Power2 = -(y2 - x2 + rx2) / denominator2;
         double motor3Power2 = (y2 + x2 + rx2) / denominator2;
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
         else if (gamepad2.right_trigger>0.5&&y<0)
         {
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power/2);
            motor3.setPower(motor3Power/2);
         }
         else if (gamepad2.left_trigger>0.5&&y<0)
         {
            motor0.setPower(motor0Power/2);
            motor1.setPower(motor1Power/2);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         else if (gamepad2.left_trigger>0.5&&y>0)
         {
            motor0.setPower(motor0Power/2);
            motor1.setPower(motor1Power/2);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         else if (gamepad2.right_trigger>0.5&&y>0)
         {
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power/2);
            motor3.setPower(motor3Power/2);
         }
         else
         {
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         
         if (gamepad2.right_bumper)
         {
             Elev1.setPower(-0.7);
             Elev2.setPower(0.7);


         } 
         else if (gamepad2.left_bumper)
         {
             Elev1.setPower(0.7);
             Elev2.setPower(-0.7);

         }
         
         else {
             Elev1.setPower(0);
             Elev2.setPower(0);

         }
         
         if (gamepad2.left_trigger >0.1)
         { //holding x closes the grip
            GripRight.setPosition(0.5);
            GripLeft.setPosition(0.5);
            sleep(300);
            
         }
         else if(gamepad2.right_trigger>0.1)
         {
            GripRight.setPosition(0.06);
            GripLeft.setPosition(0.98);
         }



         telemetry.addData("motor0", motor0.getPower());
         telemetry.addData("motor1", motor1.getPower());
         telemetry.addData("motor2", motor2.getPower());
         telemetry.addData("motor3", motor3.getPower());
         telemetry.addData("GripRight", GripRight.getPosition());
         telemetry.addData("GripLeft", GripLeft.getPosition());
      }
   }
}
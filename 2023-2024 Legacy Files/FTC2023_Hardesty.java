/*
Copyright 2023 FIRST Tech Challenge Team 17403

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp

public class FTC2023_Hardesty extends LinearOpMode {
 DcMotorEx Elev1;
    DcMotorEx Elev2;
    TouchSensor touch;

    @Override
    public void runOpMode() {
      telemetry.addData("Status", "Initialized");
        telemetry.update();
              DcMotor motor0 = hardwareMap.dcMotor.get("motor0");
      DcMotor motor1 = hardwareMap.dcMotor.get("motor1");
      DcMotor motor2 = hardwareMap.dcMotor.get("motor2");
      DcMotor motor3 = hardwareMap.dcMotor.get("motor3");
      DcMotor Elev3 = hardwareMap.dcMotor.get("leftEncoder");

      touch = hardwareMap.get(TouchSensor.class, "Touch");

      Elev1 = hardwareMap.get(DcMotorEx.class, "Elev1");
      Elev2 = hardwareMap.get(DcMotorEx.class, "Elev2");


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
      
      Elev1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      Elev2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

   
        // run until the end of the match (driver presses STOP)
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
         double y = (-gamepad1.left_stick_x/2); // Remember, this is reversed!
        double x = ((gamepad1.left_stick_y/2) * 1.125); // Counteract imperfect strafing
        double lx = (gamepad1.right_stick_y/2);
        
          if (gamepad1.left_stick_button) // stick buttons are the new accelerators
         {
            y = (-gamepad1.right_stick_y); 
            x = (gamepad1.right_stick_x) * 1.125; 
         }
         else if (gamepad1.left_stick_button)
         {
            lx = (gamepad1.left_stick_x);
         }
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
       double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(lx), 1);
        motor0.setPower((y - x - lx) / denominator); //frontRightPower
        motor1.setPower((y + x - lx) / denominator); //backRightPower
        motor2.setPower((-(y - x + lx)) / denominator); //backLeftPower
        motor3.setPower((-(y + x + lx)) / denominator); //frontLeftPower
        
        Elev1.setPower(0);
        Elev2.setPower(0);
        GripRight.setPosition(0.06); 
        GripLeft.setPosition(0.98);




        if (gamepad1.right_bumper){
         GripRight.setPosition(0.5);
         GripLeft.setPosition(0.5);
        }
        else if(gamepad1.left_bumper){
            Elev1.setTargetPosition(2950);
            Elev1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Elev1.setPower(0.8); 
            
            Elev2.setTargetPosition(-2950);
            Elev2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Elev2.setPower(-0.8);
        }

        else if(gamepad1.left_trigger>0.1){
            motor0.setPower(-1);
            motor1.setPower(-1);
            motor2.setPower(-1); //rotate left
            motor3.setPower(-1);
        }
        else if(gamepad1.right_trigger>0.1){
            motor0.setPower(1);
            motor1.setPower(1);
            motor2.setPower(1); //rotate right
            motor3.setPower(1);
        }

         telemetry.addData("motor0", motor0.getPower());
         telemetry.addData("motor1", motor1.getPower());
         telemetry.addData("motor2", motor2.getPower());
         telemetry.addData("motor3", motor3.getPower());
         telemetry.addData("GripRight", GripRight.getPosition());
         telemetry.addData("GripLeft", GripLeft.getPosition());
         telemetry.addData("Elev1 Position", Elev1.getCurrentPosition());
         telemetry.addData("Elev2 Position", Elev2.getCurrentPosition());
        }
    }
}

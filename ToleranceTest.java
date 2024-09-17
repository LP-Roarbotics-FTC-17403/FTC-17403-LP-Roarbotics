package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class ToleranceTest extends LinearOpMode {
    
    private boolean manualMode = false;
    private boolean ServoControlBoolean;
    private boolean ServoControlBoolean1;
    private boolean ServoControlBoolean3;
    private double armSetpoint = 0.0;
    private final int armHomePosition = 150;
    private final int armHangPosition = 1000;//test the variable
    private final int armIntakePosition = 0;
    private final int armScorePosition = 1600;
    private final int armShutdownThreshold = 20;
    private final double armManualDeadband = 0.1;
    private final double droneMode = 0.05;
    private ElapsedTime runtime = new ElapsedTime();
    private int touchRight;
    private int touchLeft;
    
    DcMotorEx armLeft;
    DcMotorEx armRight;
    
    
    @Override
    public void runOpMode() throws InterruptedException {
         ElapsedTime time = new ElapsedTime();
     double manualArmPower;
     ElapsedTime time1 = new ElapsedTime();
     ElapsedTime time3 = new ElapsedTime();
    // Declare our motors and make sure your ID's match your configuration
    // Arms CPR: 537

    
    DcMotor frontLeft = hardwareMap.dcMotor.get("m0");
    DcMotor backLeft = hardwareMap.dcMotor.get("m1");
    DcMotor backRight = hardwareMap.dcMotor.get("m2");
    DcMotor frontRight = hardwareMap.dcMotor.get("m3");

    armLeft = hardwareMap.get(DcMotorEx.class,"Arm1");
    armRight = hardwareMap.get(DcMotorEx.class,"Arm2");
    
    
    DcMotor shooter = hardwareMap.dcMotor.get("d3");
    CRServo servo0 = hardwareMap.get(CRServo.class, "cr0");
    CRServo servo1 = hardwareMap.get(CRServo.class, "cr1");
    Servo servo2 = hardwareMap.get(Servo.class, "s2");
    Servo servo3 = hardwareMap.get(Servo.class, "s3");
    Servo servo6 = hardwareMap.get(Servo.class, "s6");
    TouchSensor touchLeft = hardwareMap.get(TouchSensor.class, "t0");
    TouchSensor touchRight = hardwareMap.get(TouchSensor.class, "t1");

        
    armLeft.setDirection(DcMotor.Direction.REVERSE);
    armRight.setDirection(DcMotor.Direction.FORWARD);
    armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armLeft.setTargetPositionTolerance(1);
    armRight.setTargetPositionTolerance(1);


   


      servo0.setPower(0);
      servo1.setPower(0);
      
    //servo3.setPosition(0.55);
     //servo3.setPosition(0);
        


    telemetry.addData("Status", "Initialized");
         telemetry.addData("m0", frontRight.getPower());
         telemetry.addData("m1", backRight.getPower());
         telemetry.addData("m2", backLeft.getPower());
         telemetry.addData("m3", frontLeft.getPower());
         telemetry.addData("Arm1 Position", armLeft.getCurrentPosition());
         telemetry.addData("Arm2 Position", armRight.getCurrentPosition());
         telemetry.addData("servo0 Position", armLeft.getCurrentPosition());
         telemetry.addData("servo1 Position", armRight.getCurrentPosition());
         telemetry.addData("servo2 Position", armLeft.getCurrentPosition());
         telemetry.addData("servo3 Position", armRight.getCurrentPosition());
        
    telemetry.update();


        waitForStart();

        if (isStopRequested()) return;
        

        while (opModeIsActive()) {

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards, reverse the left side instead.
   // servo4.setPosition(0.2);
    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    //touch2 = 0;
        
        //can divide by 2 for lower input
    double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
    double x = (gamepad1.left_stick_x) * 1.125; // Counteract imperfect strafing
    double rx = (gamepad1.right_stick_x);
        
        manualArmPower = (gamepad2.right_trigger - gamepad2.left_trigger)/2;
        if (Math.abs(manualArmPower) > armManualDeadband) {
            if (!manualMode) {
                armLeft.setPower(0.0);
                armRight.setPower(0.0);
                armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                manualMode = true;
            }
            
            if(gamepad2.x){
             armLeft.setPower(manualArmPower*2);
            armRight.setPower(manualArmPower*2);
            }
            else{
             armLeft.setPower(manualArmPower);
            armRight.setPower(manualArmPower);
            }
            
        }
        else { 
            
            if (manualMode) {
                armLeft.setTargetPosition(armLeft.getCurrentPosition());
                armRight.setTargetPosition(armRight.getCurrentPosition());
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armLeft.setPower(0.05);
                armRight.setPower(0.05);

                manualMode = false;
            }
        }
        
            //preset buttons
            if (gamepad2.b) {
                armLeft.setTargetPosition(armHomePosition);
                armRight.setTargetPosition(armHomePosition);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armLeft.setPower(0.75);
                armRight.setPower(0.75);
             
            }
           
            else if (gamepad2.a) {
                armLeft.setTargetPosition(armIntakePosition);
                armRight.setTargetPosition(armIntakePosition);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armLeft.setPower(0.75);
                armRight.setPower(0.75);
                
              

            }
           
            else if (gamepad2.y) {
                armLeft.setTargetPosition(armScorePosition);
                armRight.setTargetPosition(armScorePosition);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armLeft.setPower(0.75);
                armRight.setPower(0.75);
                
                
            }
        
        //Re-zero encoder button
        if (gamepad2.start) {
            armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armLeft.setPower(0.0);
            armRight.setPower(0.0);
            manualMode = false;
        }
        
if (gamepad1.start && time3.seconds() > 1) {
        ServoControlBoolean3 = !ServoControlBoolean3;
        time3.reset();
    }

   /* if (ServoControlBoolean3) {
        servo4.setPosition(-0.55);
    } else {
        servo4.setPosition(0);
    }*/

        //Watchdog to shut down motor once the arm reaches the home position
        if (!manualMode &&
        armLeft.getMode() == DcMotor.RunMode.RUN_TO_POSITION &&
        armLeft.getTargetPosition() <= armShutdownThreshold &&
        armLeft.getCurrentPosition() <= armShutdownThreshold
        ) 
        
        {
            armLeft.setPower(0.0);
            armRight.setPower(0.0);
            armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
            
         
        if (gamepad1.left_stick_button) // stick buttons are accelerators
         {
            y = (-gamepad1.left_stick_y); 
            x = (gamepad1.left_stick_x) * 1.125; 
         }
        
        else if (gamepad1.right_stick_button)
         {
            rx = (gamepad1.right_stick_x)/2;
         }


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;
        


        if (gamepad1.left_bumper) {
        servo0.setPower(-1);
        servo1.setPower(1);
      }

      
      else if (gamepad1.right_bumper) {
        servo0.setPower(1);
        servo1.setPower(-1);
      }
      
      else{
          servo0.setPower(0);
          servo1.setPower(0);
      }


            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

           //servo buttons
      if (gamepad2.dpad_up) {
        servo0.setPower(-1);
        servo1.setPower(1);
      }
      else{}

      
      if (gamepad2.dpad_down) {
        servo0.setPower(1);
        servo1.setPower(-1);
      }
      else{}
    


if (gamepad2.left_bumper && time.seconds() > 1) {
        ServoControlBoolean = !ServoControlBoolean;
        time.reset();
    }

    if (ServoControlBoolean) {
        servo3.setPosition(0.55);
    } else {
        servo3.setPosition(0);
    }

    if (gamepad2.right_bumper && time.seconds() > 1) {
        ServoControlBoolean1 = !ServoControlBoolean1;
        time.reset();
    }

    if (ServoControlBoolean1) {
        servo2.setPosition(0.55);
    }
else{
servo2.setPosition(0);
}


if (gamepad1.x) {
                shooter.setPower(-1);
            }
    //limit switches reset arm encoder to 0
    /*if (touchLeft.isPressed()) {
            armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armLeft.setPower(0.0);
            manualMode = false;
        }
    if (touchRight.isPressed()) {
            armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armRight.setPower(0.0);
            manualMode = false;
        }*/





         telemetry.addData("m0", frontRight.getPower());
         telemetry.addData("m1", backRight.getPower());
         telemetry.addData("m2", backLeft.getPower());
         telemetry.addData("m3", frontLeft.getPower());
         telemetry.addData("Arm1 Position", armLeft.getCurrentPosition());
         telemetry.addData("Arm2 Position", armRight.getCurrentPosition());
        /* telemetry.addData("servo0 Position", servo0.getPosition());
         telemetry.addData("servo1 Position", servo1.getCurrentPosition());*/
         telemetry.addData("servo2 Position", servo2.getPosition());
         telemetry.addData("servo3 Position", servo3.getPosition());
          telemetry.addData("servo4 Position", servo3.getPosition());
         telemetry.update();

        
}  
}   
}


/*

*/



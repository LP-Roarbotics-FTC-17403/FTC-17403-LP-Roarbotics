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
public class FTC2023 extends LinearOpMode {
    
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
     ElapsedTime time6 = new ElapsedTime();
    // Declare our motors and make sure your ID's match your configuration
    // Arms CPR: 537

    
    DcMotor frontLeft = hardwareMap.dcMotor.get("m3");
    DcMotor backLeft = hardwareMap.dcMotor.get("m2");
    DcMotor backRight = hardwareMap.dcMotor.get("m0");
    DcMotor frontRight = hardwareMap.dcMotor.get("m1");
    
    frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


 


    telemetry.addData("Status", "Initialized");
         telemetry.addData("m1", frontRight.getPower());
         telemetry.addData("m0", backRight.getPower());
         telemetry.addData("m2", backLeft.getPower());
         telemetry.addData("m3", frontLeft.getPower());

        
    telemetry.update();


        waitForStart();

        if (isStopRequested()) return;
        

        while (opModeIsActive()) {

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards, reverse the left side instead.
   // servo4.setPosition(0.2);
    frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    //touch2 = 0;
        
        //can divide by 2 for lower input
    double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
    double x = (gamepad1.left_stick_x) * 1.125; // Counteract imperfect strafing
    double rx = (gamepad1.right_stick_x);
        
           
            
         
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
        


      


            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

          




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





         telemetry.addData("m1", frontRight.getPower());
         telemetry.addData("m0", backRight.getPower());
         telemetry.addData("m2", backLeft.getPower());
         telemetry.addData("m3", frontLeft.getPower());
         
         telemetry.update();

        
}  
}   
}


/*

*/



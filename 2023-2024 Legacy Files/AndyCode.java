package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class AndyCode extends LinearOpMode {
    /*private final int armHomePosition = 20;
    private final int armIntakePosition = 10;
     private final int armScorePosition = 1732;*/
    private boolean manualMode = false;
    private double armSetpoint = 0.0;
    private final int armHomePosition = 0;
    private final int armIntakePosition = 10;
    private final int armScorePosition = 537;
    private final int armShutdownThreshold = 20;
    private final double armManualDeadband = 0.03;
    
    @Override
    public void runOpMode() throws InterruptedException {
        
     double manualArmPower;
        // Declare our motors
        // Make sure your ID's match your configuration
        //PPR: 3895.9

    
 
        DcMotor frontLeft = hardwareMap.dcMotor.get("m3");
        DcMotor backLeft = hardwareMap.dcMotor.get("m2");
        DcMotor frontRight = hardwareMap.dcMotor.get("m0");
        DcMotor backRight = hardwareMap.dcMotor.get("m1");
        DcMotor armLeft = hardwareMap.dcMotor.get("Arm1");
        DcMotor armRight = hardwareMap.dcMotor.get("Arm2");
        
        armLeft.setDirection(DcMotor.Direction.FORWARD);
        armRight.setDirection(DcMotor.Direction.REVERSE);
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armLeft.setPower(0.0);
        armRight.setPower(0.0);
        
        armLeft.setTargetPosition(530);


        telemetry.addData("Status", "Initialized");
        
        telemetry.addData("Arm1 Position", armLeft.getCurrentPosition());
         telemetry.addData("Arm2 Position", armRight.getCurrentPosition());
         telemetry.update();


        waitForStart();

        if (isStopRequested()) return;
        

        while (opModeIsActive()) {

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        armRight.setDirection(DcMotorSimple.Direction.REVERSE);
        
            //can divide by 2 for lower input
            double y = -gamepad1.left_stick_y/2; // Remember, Y stick value is reversed
            double x = (gamepad1.left_stick_x/2) * 1.125; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x/2;
        
        manualArmPower = gamepad1.right_trigger - gamepad1.left_trigger;
        if (Math.abs(manualArmPower) > armManualDeadband) {
            if (!manualMode) {
                armLeft.setPower(0.0);
                armRight.setPower(0.0);
                armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                manualMode = true;
            }
            armLeft.setPower(manualArmPower);
            armRight.setPower(manualArmPower);
        }
        else {
        if (manualMode) {
                armLeft.setTargetPosition(armLeft.getCurrentPosition());
                armRight.setTargetPosition(armRight.getCurrentPosition());
                armLeft.setPower(1.0);
                armRight.setPower(1.0);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                manualMode = false;
            }
        }
        
                    //preset buttons
            if (gamepad1.a) {
                armLeft.setTargetPosition(armHomePosition);
                armRight.setTargetPosition(armHomePosition);
                armLeft.setPower(1.0);
                armRight.setPower(1.0);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            }
            else if (gamepad1.b) {
                armLeft.setTargetPosition(armIntakePosition);
                armRight.setTargetPosition(armIntakePosition);
                armLeft.setPower(1.0);
                armRight.setPower(1.0);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            }
            else if (gamepad1.y) {
                armLeft.setTargetPosition(armScorePosition);
                armRight.setTargetPosition(armScorePosition);
                armLeft.setPower(1.0);
                armRight.setPower(1.0);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                
            }
        
        
        //Re-zero encoder button
        if (gamepad1.start) {
            armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armLeft.setPower(0.0);
            armRight.setPower(0.0);
            manualMode = false;
        }
        
        //Watchdog to shut down motor once the arm reaches the home position
        if (!manualMode &&
        armLeft.getMode() == DcMotor.RunMode.RUN_TO_POSITION &&
        armLeft.getTargetPosition() <= armShutdownThreshold &&
        armLeft.getCurrentPosition() <= armShutdownThreshold
        ) {
            armLeft.setPower(0.0);
            armRight.setPower(0.0);
            armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
            
           


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            /*double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 0.1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower/2);
            backLeftMotor.setPower(backLeftPower/2);
            frontRightMotor.setPower(frontRightPower/2);
            backRightMotor.setPower(backRightPower/2);*/
             if (gamepad1.left_stick_button) // stick buttons are accelerators
         {
            y = (-gamepad1.left_stick_y); 
            x = (gamepad1.left_stick_x) * 1.125; 
         }
         else if (gamepad1.right_stick_button)
         {
            rx = (gamepad1.right_stick_x);
         }


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

         if (gamepad1.dpad_right) {
         
//moves robot right on X axis on slightly reduced power if both left stick or X button is pressed
             frontLeft.setPower(0.8);
             backLeft.setPower(-0.8);
             frontRight.setPower(-0.8); //reversed
             backRight.setPower(0.8); //reversed
             
         }

        else if (gamepad1.dpad_left) {
         
//moves robot left on X axis on slightly reduced power if both left stick or X button is pressed
             frontLeft.setPower(-0.8); // motor1
             backLeft.setPower(0.8); //motor0
             frontRight.setPower(0.8);//reversed //motor3
             backRight.setPower(-0.8);//reversed //motor2
             
         }

         if (gamepad1.right_trigger>0.5&& y<0) {
         
//runs slower if right trigger and stick is lightly pushed
             frontLeft.setPower(frontLeftPower);
             backLeft.setPower(backLeftPower);
             frontRight.setPower(frontRightPower*2);//reversed
             backRight.setPower(backRightPower*2);//reversed
             
         }
         else if (gamepad1.left_trigger>0.5&&y<0) {
         
//runs slower if left trigger and stick is lightly pushed
             frontLeft.setPower(frontLeftPower*2);
             backLeft.setPower(backLeftPower*2);
             frontRight.setPower(frontRightPower);//reversed
             backRight.setPower(backRightPower);//reversed
             
         }

         else if (gamepad1.right_trigger>0.5&&y>0) {
         
//runs slower if right trigger and stick is lightly pushed
             frontLeft.setPower(frontLeftPower);
             backLeft.setPower(backLeftPower);
             frontRight.setPower(frontRightPower*2);//reversed
             backRight.setPower(backRightPower*2);//reversed
             
         }
         else if (gamepad1.left_trigger>0.5&&y>0) {
         
//runs slower if left trigger and stick is lightly pushed
             frontLeft.setPower(frontLeftPower*2);
             backLeft.setPower(backLeftPower*2);
             frontRight.setPower(frontRightPower);//reversed
             backRight.setPower(backRightPower);//reversed
             
         }
         else {
         
//runs slower if left trigger and stick is lightly pushed
             frontLeft.setPower(frontLeftPower);
             backLeft.setPower(backLeftPower);
             frontRight.setPower(frontRightPower);//reversed
             backRight.setPower(backRightPower);//reversed

         }

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            
         /*     if (gamepad1.right_bumper)
         {

            Arm1.setTargetPosition(1732);
            Arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Arm1.setPower(0.8); 
            
            Arm2.setTargetPosition(-1732);
            Arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Arm2.setPower(-0.8);
            

         }
         
         else {
             Arm1.setPower(0);
             Arm2.setPower(0);

         }
         */
         
 //preset buttons
/*if (gamepad1.a) {
Arm1.setTargetPosition(armHomePosition);
Arm2.setTargetPosition(armHomePosition);
Arm1.setPower(1.0);
Arm2.setPower(1.0);
Arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
Arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

}
else if (gamepad1.b) {
Arm1.setTargetPosition(armIntakePosition);
Arm2.setTargetPosition(armIntakePosition);
Arm1.setPower(1.0);
Arm2.setPower(1.0);
Arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
Arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

}*/
if (gamepad1.y) {
armLeft.setTargetPosition(537);
armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
armLeft.setPower(1.0);

armRight.setTargetPosition(537);
armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
armRight.setPower(1.0);



}




         telemetry.addData("m0", frontRight.getPower());
         telemetry.addData("m1", backRight.getPower());
         telemetry.addData("m2", backLeft.getPower());
         telemetry.addData("m3", frontLeft.getPower());
         telemetry.addData("Arm1 Position", armLeft.getCurrentPosition());
         telemetry.addData("Arm2 Position", armRight.getCurrentPosition());
         telemetry.update();

        
}  
}     
}

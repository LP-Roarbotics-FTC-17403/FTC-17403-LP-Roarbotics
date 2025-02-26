package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class AUTOCORRECT extends LinearOpMode {

    SparkFunOTOS myOtos;
    public DcMotorEx bl, fl, fr, br;
    public DcMotorEx armMotor;
    public DcMotorEx liftMotor;
    public Servo claw;
    public Servo wrist; // The wrist servo - does this exist? We don't use it in the code...

    // This constant is the number of encoder ticks for each degree of rotation of the arm
    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
            * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
            * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
            * 1/360.0; // we want ticks per degree, not per rotation

    // Calculates lift ticks per mm using motor encoder
    final double LIFT_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;

    // Positions
    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    final double ARM_CLEAR_BARRIER         = 15  * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW   = 90  * ARM_TICKS_PER_DEGREE;
    final double ARM_HORIZONTAL            = 22  * ARM_TICKS_PER_DEGREE;
    final double ARM_CHOMP = 19 * ARM_TICKS_PER_DEGREE;
    final double CLAW_CLOSE    = 0.2;
    final double CLAW_OPEN    =  0.6;
    final double LIFT_COLLAPSED = 0 * LIFT_TICKS_PER_MM;
    final double LIFT_SCORING_IN_LOW_BASKET = 0 * LIFT_TICKS_PER_MM;
    final double LIFT_SCORING_IN_HIGH_BASKET = 670 * LIFT_TICKS_PER_MM;
    final double LIFT_LIMIT = 410 * LIFT_TICKS_PER_MM;
    final double LIFT_FIRST_SAMPLE = 375 * LIFT_TICKS_PER_MM;
    final double LIFT_SECOND_SAMPLE = 390 * LIFT_TICKS_PER_MM;

    double liftPosition = LIFT_COLLAPSED;
    double armPosition = (int)ARM_COLLAPSED_INTO_ROBOT;
    SparkFunOTOS.Pose2D currentPosition;

    @Override
    public void runOpMode() {
        myOtos = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");
        bl = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        fl = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        fr = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        br = hardwareMap.get(DcMotorEx.class, "backRightMotor");
        claw = hardwareMap.get(Servo.class, "intake");
        wrist = hardwareMap.get(Servo.class, "wrist");
        liftMotor = hardwareMap.get(DcMotorEx.class, "slideMotor"); //the lift motor aka slide
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");

        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armMotor.setCurrentAlert(5,CurrentUnit.AMPS);
        liftMotor.setCurrentAlert(5,CurrentUnit.AMPS);

        /* Before starting the armMotor. We'll make sure the TargetPosition is set to 0.
        Then we'll set the RunMode to RUN_TO_POSITION. And we'll ask it to stop and reset encoder.
        If you do not have the encoder plugged into this motor, it will not run in this code. */
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addLine("Robot Ready.");
        telemetry.update();

        ElapsedTime timer = new ElapsedTime();

        configureOtos();

        /* Wait for the game driver to press play */
        waitForStart();

        if (opModeIsActive()){
            currentPosition = myOtos.getPosition();

            claw.setPosition(CLAW_CLOSE);
            sleep(50);

            armMotorOnly(1500, ARM_CLEAR_BARRIER);

            moveToTargetUsingOTOS(14, 0);

            // rotateMotors(0.4, "left",  0);

            armMotorOnly(1500, ARM_SCORE_SAMPLE_IN_LOW);

            timer.reset();
            while(opModeIsActive() && liftMotor.getCurrentPosition() < LIFT_SCORING_IN_HIGH_BASKET-10 && timer.seconds() < 2){
                liftMotor(LIFT_SCORING_IN_HIGH_BASKET);
            }

            claw.setPosition(CLAW_OPEN);
            sleep(250);

            timer.reset();
            while(opModeIsActive() && liftMotor.getCurrentPosition() > 30 && timer.seconds() < 1.75){
                liftMotor(0);
            }

            armMotorOnly(1500, ARM_CLEAR_BARRIER);

            //breakPoint();
            currentPosition = myOtos.getPosition();
            timer.reset();
            while(opModeIsActive() && (Math.abs(currentPosition.h - -140) > 0.2) && timer.seconds() < 2){
                moveToTargetAngleUsingOTOS(-140);
            }
            stopMotion();

            //breakPoint();
            armMotorOnly(1500,ARM_CLEAR_BARRIER);
            //breakPoint();
            grabSample(LIFT_FIRST_SAMPLE);
            //breakPoint();
            chomp(ARM_CHOMP);
            //breakPoint();
            grabSample(0);
            //breakPoint();
            currentPosition = myOtos.getPosition();

            timer.reset();
            while(opModeIsActive() && (Math.abs(currentPosition.h - 0) > 0.2) && timer.seconds() < 2){
                moveToTargetAngleUsingOTOS(0);
            }
            stopMotion();

            armMotorOnly(1500, ARM_SCORE_SAMPLE_IN_LOW);

            //resetting timer before every while loop, then setting a time for emergency stops.
            timer.reset();
            while(opModeIsActive() && liftMotor.getCurrentPosition() < LIFT_SCORING_IN_HIGH_BASKET -10 && timer.seconds() < 2){
                liftMotor(LIFT_SCORING_IN_HIGH_BASKET);
            }

            claw.setPosition(CLAW_OPEN);

            sleep(250);

            timer.reset();
            while(opModeIsActive() && liftMotor.getCurrentPosition() > 30 && timer.seconds() < 2){
                liftMotor(0);
            }            

            armMotorOnly(1500, ARM_CLEAR_BARRIER);
            
            currentPosition = myOtos.getPosition();

            timer.reset();
            while(opModeIsActive() && (Math.abs(currentPosition.h - -118) > 0.2) && timer.seconds() < 2){
                moveToTargetAngleUsingOTOS(-118);
            }
            stopMotion();

            //breakPoint();
            armMotorOnly(1500,ARM_CLEAR_BARRIER);

            //breakPoint();
            grabSample(LIFT_SECOND_SAMPLE);

            //breakPoint();
            chomp(ARM_CHOMP);

            //breakPoint();
            grabSample(0);

            //breakPoint();
            currentPosition = myOtos.getPosition();

            timer.reset();
            while(opModeIsActive() && (Math.abs(currentPosition.h - 0) > 0.2) && timer.seconds() < 2){
                moveToTargetAngleUsingOTOS(0);
            }
            stopMotion();

            armMotorOnly(1500, ARM_SCORE_SAMPLE_IN_LOW);

            timer.reset();
            while(opModeIsActive() && liftMotor.getCurrentPosition() < LIFT_SCORING_IN_HIGH_BASKET-10 && timer.seconds() < 2){
                liftMotor(LIFT_SCORING_IN_HIGH_BASKET);
            }

            claw.setPosition(CLAW_OPEN);

            sleep(250);

            timer.reset();
            while(opModeIsActive() && liftMotor.getCurrentPosition() > 30 && timer.seconds() < 2){
                liftMotor(0);
            }

            armMotorOnly(1500, ARM_CLEAR_BARRIER);
        }
    }

    private void stopMotion(){
        fr.setPower(0);
        fl.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }

    private void configureOtos() {
        telemetry.addLine("Configuring OTOS...");
        telemetry.update();

        myOtos.setLinearUnit(DistanceUnit.INCH);
        myOtos.setAngularUnit(AngleUnit.DEGREES);

        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(0, 1.75, 0);
        myOtos.setOffset(offset);
        myOtos.setLinearScalar(1.0);
        myOtos.setAngularScalar(1.0);
        myOtos.calibrateImu();
        myOtos.resetTracking();
        SparkFunOTOS.Pose2D currentPosition = new SparkFunOTOS.Pose2D(0, 0, 0);
        myOtos.setPosition(currentPosition);

        // Get the hardware and firmware version
        SparkFunOTOS.Version hwVersion = new SparkFunOTOS.Version();
        SparkFunOTOS.Version fwVersion = new SparkFunOTOS.Version();
        myOtos.getVersionInfo(hwVersion, fwVersion);

        telemetry.addLine("OTOS configured!");
        telemetry.update();
    }

    private void moveToTargetUsingOTOS(double targetX, double targetY) {
        while (opModeIsActive() && (Math.abs(currentPosition.x - targetX) > 0.1 || Math.abs(currentPosition.y - targetY) > 0.1 )) {
            double xPower = (targetX - currentPosition.x) * 0.2; // Proportional control
            double yPower = (targetY - currentPosition.y) * 0.2;

            xPower = Math.max(-1, Math.min(1, xPower));
            yPower = Math.max(-1, Math.min(1, yPower));

            driveMotors(yPower, xPower);

            currentPosition = myOtos.getPosition();

            telemetry.addData("Current OTOS X", currentPosition.x);
            telemetry.addData("Current OTOS Y", currentPosition.y);
            telemetry.update();
        }

        stopMotion();
    }

    //a run to position for angles while in same spot
    private void moveToTargetAngleUsingOTOS(double targetH) {
        currentPosition = myOtos.getPosition();

        if (opModeIsActive() && (Math.abs(currentPosition.h - targetH) > 0.1)) {
            double hPower = (targetH - currentPosition.h) * 0.01;  // Proportional control

            hPower = clamp(hPower, -1, 1);

            angleDriveMotors(hPower);

            currentPosition = myOtos.getPosition();

            telemetry.addData("Current OTOS X", currentPosition.x);
            telemetry.addData("Current OTOS Y", currentPosition.y);
            telemetry.addData("Current OTOS H", currentPosition.h);
            telemetry.update();
        } else {
            stopMotion();
        }
    }

    private void driveMotors(double forwardPower, double strafePower) {
        double frontLeft = forwardPower + strafePower;
        double frontRight = forwardPower - strafePower;
        double backLeft = forwardPower - strafePower;
        double backRight = forwardPower + strafePower;

        bl.setPower(backLeft);
        fl.setPower(frontLeft);
        fr.setPower(frontRight);
        br.setPower(backRight);
    }

    private void angleDriveMotors(double rotatePower) {
        double frontLeft = -1*rotatePower;
        double frontRight = rotatePower;
        double backLeft = -1*rotatePower;
        double backRight = rotatePower;

        bl.setPower(backLeft);
        fl.setPower(frontLeft);
        fr.setPower(frontRight);
        br.setPower(backRight);
    }

    private void rotateMotors(double speed, String direction, double angle){
        speed = (Math.abs(angle) - Math.abs(currentPosition.y)) * 0.05;

        if(direction == "right"){
            while(opModeIsActive() && currentPosition.h>angle){
                fr.setPower(-1*speed);
                fl.setPower(speed);
                bl.setPower(speed);
                br.setPower(-1*speed);
            }
        }

        if(direction == "left"){
                while(opModeIsActive() && currentPosition.h<angle){
                    fr.setPower(speed);
                    fl.setPower(-1*speed);
                    bl.setPower(-1*speed);
                    br.setPower(speed);
                }
        }

        stopMotion();
    }

    private void liftMotorOnly(double speed, double location){
        liftMotor.setTargetPosition ((int)location);
        liftMotor.setVelocity(speed);

        while (liftMotor.isBusy() && opModeIsActive()) {
            if (armMotor.getCurrentPosition() < 45 * ARM_TICKS_PER_DEGREE){
                armPosition += (0.257 * liftPosition);
                armMotor.setTargetPosition ((int)armPosition);
                    armMotor.setVelocity(1500);
                if(liftPosition > LIFT_LIMIT){
                    liftPosition = LIFT_LIMIT;
                }
            }

            telemetry.addData("Lift Variable", armPosition);
            telemetry.addData("Lift Target Position: ", liftMotor.getTargetPosition());
            telemetry.addData("Lift Current Position: ", liftMotor.getCurrentPosition());
            telemetry.addData("LiftMotor Current:",liftMotor.getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }
    }

    private void armMotorOnly(double speed, double location){
        armMotor.setTargetPosition ((int)location);
        armMotor.setVelocity(speed);

        while (armMotor.isBusy() && opModeIsActive()) {
            telemetry.addData("Arm Variable", armPosition);
            telemetry.addData("Arm Target Position: ", armMotor.getTargetPosition());
            telemetry.addData("Arm Current Position: ", armMotor.getCurrentPosition());
            telemetry.addData("ArmMotor Current:",armMotor.getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }
    }

    private void breakPoint(){
        while (!gamepad1.x && opModeIsActive()){
            sleep(20);

            currentPosition= myOtos.getPosition();

            telemetry.addData("Current OTOS X", currentPosition.x);
            telemetry.addData("Current OTOS Y", currentPosition.y);
            telemetry.addData("Current OTOS H", currentPosition.h);
            telemetry.addData("Arm Current Position: ", armMotor.getCurrentPosition());
            telemetry.addData("Lift Current Position: ", liftMotor.getCurrentPosition());
            telemetry.update();
        }
    }

    private void armStretch(double target){
        liftMotor.setVelocity(0);
        armMotor.setVelocity(1500);
        liftMotor.setTargetPosition ((int)target);

        while(Math.abs(liftMotor.getCurrentPosition()-target) > 1 && opModeIsActive()){
            double liftPower = (Math.abs(liftMotor.getCurrentPosition()-target))*4;
            liftPower = clamp(liftPower, 0, 2500);
            liftMotor.setVelocity(liftPower);
            double armComp = liftMotor.getCurrentPosition()* 0.35;
            armMotor.setTargetPosition((int)(armPosition + armComp));
            armMotor.setVelocity(2000);
        }
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }  

    private void grabSample(double targetPosition){
        ElapsedTime timer2 = new ElapsedTime();

        armMotor.setTargetPosition((int)ARM_HORIZONTAL);
        liftMotor.setTargetPosition((int)targetPosition);
        armMotor.setVelocity(2000);
        timer2.reset();

        while(Math.abs(liftMotor.getCurrentPosition()-targetPosition) > 20 && opModeIsActive() && timer2.seconds() < 2){
            double liftPower = (Math.abs(liftMotor.getCurrentPosition()-targetPosition))*5;
            liftPower = clamp(liftPower, 0, 2500);
            liftMotor.setVelocity(liftPower);
        }
    }

    private void chomp(double targetArm){
        armMotor.setTargetPosition((int)targetArm);
        armMotor.setVelocity(1000);
        sleep(250);
        claw.setPosition(CLAW_CLOSE);
        sleep(300);
    }

    private void liftMotor(double targetEncoder){
        if(Math.abs(liftMotor.getCurrentPosition()-targetEncoder)>1){
            double speed = Math.abs(liftMotor.getCurrentPosition()-targetEncoder) * 2.5; //value can be adjustable based on how fast you want the arm.
            clamp(speed, 0, 2200); // ensuring the speed doesn't go past 2500
            liftMotor.setTargetPosition ((int)targetEncoder);
            liftMotor.setVelocity(speed);
        }

        telemetry.addData("Lift Variable", armPosition);
        telemetry.addData("Lift Target Position: ", liftMotor.getTargetPosition());
        telemetry.addData("Lift Current Position: ", liftMotor.getCurrentPosition());
        telemetry.addData("LiftMotor Current:",liftMotor.getCurrent(CurrentUnit.AMPS));
        telemetry.update();
    }

    private void armMotor(double targetEncoder){
        if(Math.abs(armMotor.getCurrentPosition()-targetEncoder)>1){
            double speed = Math.abs(armMotor.getCurrentPosition()-targetEncoder) * 1.5; //value can be adjustable based on how fast you want the arm.
            clamp(speed, 0, 2500); // ensuring the speed doesn't go past 2500
            armMotor.setTargetPosition ((int)targetEncoder);
            armMotor.setVelocity(speed);
        }

        telemetry.addData("Arm Variable", armPosition);
        telemetry.addData("Arm Target Position: ", armMotor.getTargetPosition());
        telemetry.addData("Arm Current Position: ", armMotor.getCurrentPosition());
        telemetry.addData("ArmMotor Current:",armMotor.getCurrent(CurrentUnit.AMPS));
        telemetry.update();
    }
}
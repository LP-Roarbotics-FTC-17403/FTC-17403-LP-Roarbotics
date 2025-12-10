package org.firstinspires.ftc.teamcode.DecodeFiles;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorClass {
    DcMotorEx leftFront, leftBack, rightBack, rightFront;

    double ticksPerInch = (4*28 * 19.2)
            /(104*Math.PI)
            * 25.4;
    double angleRatio;
    double strafeRatio = 0;
    public MotorClass(HardwareMap hardwareMap) {
        // Initialize arm motor
        leftFront = hardwareMap.get(DcMotorEx.class, "left_front_drive");
        leftBack = hardwareMap.get(DcMotorEx.class, "left_back_drive");
        rightBack = hardwareMap.get(DcMotorEx.class, "right_back_drive");
        rightFront = hardwareMap.get(DcMotorEx.class, "right_front_drive");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        /*
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
*/
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
/*
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
*/
    }

    public void initialize(boolean reverse){
        /*if(reverse) {
            rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
            rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        }

         */
    }

    public void backWard(){
        leftFront.setPower(-0.5);
        leftBack.setPower(-0.5);
        rightBack.setPower(-0.5);
        rightFront.setPower(-0.5);
    }
    public void motorTest(double power){
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightBack.setPower(power);
        rightFront.setPower(power);
    }

    public void powerStrafe(boolean right, double power){
        if(right) {
            leftFront.setPower(power);
            leftBack.setPower(-power);
            rightBack.setPower(power);
            rightFront.setPower(-power);
        }else{
            leftFront.setPower(-power);
            leftBack.setPower(power);
            rightBack.setPower(-power);
            rightFront.setPower(power);
        }
    }
    public void moveVertical(double distance, double speed) throws InterruptedException {
        int encoders = (int)(distance*ticksPerInch);
        setVerticalEncoderCount(encoders);
        setRunToPositionMode();
        setSpeed(speed);
        while(Math.abs(leftBack.getCurrentPosition()) < Math.abs(leftBack.getTargetPosition()) || Math.abs(leftFront.getCurrentPosition()) < Math.abs(leftFront.getTargetPosition()) || Math.abs(rightFront.getCurrentPosition()) < Math.abs(rightFront.getTargetPosition()) || Math.abs(rightBack.getCurrentPosition()) < Math.abs(rightBack.getTargetPosition())){
            setVerticalEncoderCount(encoders);
            setRunToPositionMode();
            setSpeed(speed);

        }
        setSpeed(0);
    }

    public void rotate(double angle, double speed, boolean right){
        int encoders = (int)(angle * angleRatio);
        setRotate(encoders, right);
        setRunToPositionMode();
        setSpeed(speed);
        while(Math.abs(leftBack.getCurrentPosition()) < Math.abs(leftBack.getTargetPosition()) || Math.abs(leftFront.getCurrentPosition()) < Math.abs(leftFront.getTargetPosition()) || Math.abs(rightFront.getCurrentPosition()) < Math.abs(rightFront.getTargetPosition()) || Math.abs(rightBack.getCurrentPosition()) < Math.abs(rightBack.getTargetPosition())){
            setRotate(encoders, right);
            setRunToPositionMode();
            setSpeed(speed);

        }
        setSpeed(0);
    }
    public void strafe(double distance, double speed, boolean right){
        int encoders = (int)(distance*ticksPerInch);
        setHorizontalEncoderCount(encoders, right);
        setRunToPositionMode();
        setSpeed(speed);
        while(Math.abs(leftBack.getCurrentPosition()) < Math.abs(leftBack.getTargetPosition()) || Math.abs(leftFront.getCurrentPosition()) < Math.abs(leftFront.getTargetPosition()) || Math.abs(rightFront.getCurrentPosition()) < Math.abs(rightFront.getTargetPosition()) || Math.abs(rightBack.getCurrentPosition()) < Math.abs(rightBack.getTargetPosition())){
            setHorizontalEncoderCount(encoders, right);
            setRunToPositionMode();
            setSpeed(speed);

        }
        setSpeed(0);
    }


    public void stopMotor(){
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void setSpeed(double speed){
        leftFront.setVelocity(speed);
        leftBack.setVelocity(speed);
        rightBack.setVelocity(speed);
        rightFront.setVelocity(speed);
    }

    public void setVerticalEncoderCount(int encoderCount){
        leftFront.setTargetPosition(encoderCount);
        leftBack.setTargetPosition(encoderCount);
        rightFront.setTargetPosition(encoderCount);
        rightBack.setTargetPosition(encoderCount);
    }

    public void setHorizontalEncoderCount(int encoderCount, boolean right){
        if(right){
            leftFront.setTargetPosition(encoderCount);
            leftBack.setTargetPosition(-encoderCount);
            rightFront.setTargetPosition(-encoderCount);
            rightBack.setTargetPosition(encoderCount);
        }else{
            leftFront.setTargetPosition(-encoderCount);
            leftBack.setTargetPosition(encoderCount);
            rightFront.setTargetPosition(encoderCount);
            rightBack.setTargetPosition(-encoderCount);
        }
    }



    public void setRotate(int encoderCount, boolean right){
        if(right){
            leftFront.setTargetPosition(encoderCount);
            leftBack.setTargetPosition(encoderCount);
            rightFront.setTargetPosition(-encoderCount);
            rightBack.setTargetPosition(-encoderCount);
        }else{
            leftFront.setTargetPosition(-encoderCount);
            leftBack.setTargetPosition(-encoderCount);
            rightFront.setTargetPosition(encoderCount);
            rightBack.setTargetPosition(encoderCount);
        }
    }


    public void setRunToPositionMode(){
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}

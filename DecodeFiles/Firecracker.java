package org.firstinspires.ftc.teamcode.DecodeFiles;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Firecracker {
    private DcMotorEx firecracker = null;
    double LAUNCHER_CLOSE_TARGET_VELOCITY = 1100; //in ticks/second for the close goal. //1200 original
    final double LAUNCHER_CLOSE_MIN_VELOCITY = 1175; //minimum required to start a shot for close goal.

    final double LAUNCHER_FAR_TARGET_VELOCITY = 1350; //Target velocity for far goal
    final double LAUNCHER_FAR_MIN_VELOCITY = 1325; //minimum required to start a shot for far goal.


    public Firecracker(HardwareMap hardwareMap, String deviceName) {
        // Initialize arm motor
        firecracker = hardwareMap.get(DcMotorEx.class, deviceName);

    }

    public void initialize(boolean reverse){
        if(reverse){
            firecracker.setDirection(DcMotorSimple.Direction.REVERSE); // Adjust if necessary
        }
        firecracker.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        firecracker.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        firecracker.setZeroPowerBehavior(BRAKE);
        firecracker.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));
        firecracker.setPower(0);

    }
    public void setTargetVelocity(double velocity){
        LAUNCHER_CLOSE_TARGET_VELOCITY = velocity;
    }
    public void crackDaFire(){
        firecracker.setVelocity(LAUNCHER_CLOSE_TARGET_VELOCITY);
    }

    public void crackBigBoyFire(){
        firecracker.setVelocity(LAUNCHER_FAR_TARGET_VELOCITY + 100);
    }

    public void ceaseFire(){
        firecracker.setVelocity(0);
    }

    public double getCurrentVelocity(){
        return firecracker.getVelocity();
    }

    public Action closeLaunch(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                crackDaFire();
                return false;
            }
        };
    }

    public Action farLaunch(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                crackBigBoyFire();
                return false;
            }
        };
    }



}

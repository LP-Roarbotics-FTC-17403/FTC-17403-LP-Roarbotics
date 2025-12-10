package org.firstinspires.ftc.teamcode.DecodeFiles;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
public class Feeder {
    private CRServo feeder = null;
    private ElapsedTime feederTimer = new ElapsedTime();
    final double FEED_TIME_SECONDS = 0.80;
    private double feedPower                = 1.0;

    public Feeder(HardwareMap hardwareMap, String deviceName){
        this.feeder = hardwareMap.get(CRServo.class, deviceName);
    }
    public void initialize(boolean reverse){
        feeder.setPower(0);
        if(reverse){
            feeder.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }
    public void setRotation(double speed){
        feedPower = speed;
    }
    public void feed_on(){
        feeder.setPower(feedPower);
    }
    public void feed_off(){
        feeder.setPower(0);
    }
    //Action commands to use with roadrunner
    public Action feed(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                feed_on();
                return false;
            }
        };
    }
    public Action rest(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                feed_off();
                return false;
            }
        };
    }


}

package org.firstinspires.ftc.teamcode.DecodeFiles;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {
    private DcMotorEx motor = null;

    public Motors(HardwareMap hardwareMap, String deviceName) {
        // Initialize arm motor
        motor = hardwareMap.get(DcMotorEx.class, deviceName);

    }

    public void initialize(){
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    public double getCurrentEncoderPosition(){
        return motor.getCurrentPosition();
    }

    public void setReverse(){
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

}

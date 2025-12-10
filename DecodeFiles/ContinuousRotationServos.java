package org.firstinspires.ftc.teamcode.DecodeFiles;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ContinuousRotationServos {
    private CRServo  crServo= null;
    public ContinuousRotationServos(HardwareMap hardwareMap, String deviceName){
        this.crServo = hardwareMap.get(CRServo.class, deviceName);
    }

    public void initialize(double power){
        crServo.setPower(power);
    }

    public double getCurrentPower(){
        return crServo.getPower();
    }

}

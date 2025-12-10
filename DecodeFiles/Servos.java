package org.firstinspires.ftc.teamcode.DecodeFiles;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    private Servo servo = null;

    public Servos(HardwareMap hardwareMap, String deviceName) {
        this.servo = hardwareMap.get(Servo.class, deviceName);
    }

    public void initialize(double position) {
        servo.setPosition(position);
    }

    public double getCurrentServoPosition() {
        return servo.getPosition();
    }

}

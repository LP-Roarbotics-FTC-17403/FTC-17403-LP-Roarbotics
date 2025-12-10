package org.firstinspires.ftc.teamcode.DecodeFiles;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorSensorCode {
    ColorSensor colorSensor;
    private float hsvValues[] = {0F, 0F, 0F};

    private final float[] values = hsvValues;

    // sometimes it helps to multiply the raw RGB values with a scale factor
    // to amplify/attentuate the measured values.
    private final double SCALE_FACTOR = 255;

    public ColorSensorCode(HardwareMap hardwareMap, String deviceName){
        colorSensor = hardwareMap.get(ColorSensor.class, deviceName);
        colorSensor.enableLed(true);
    }

    public void updateColor(){
        Color.RGBToHSV((int) (colorSensor.red() * SCALE_FACTOR),
                (int) (colorSensor.green() * SCALE_FACTOR),
                (int) (colorSensor.blue() * SCALE_FACTOR),
                hsvValues);
    }
    public boolean purple(){
        return 200 < hsvValues[0] && hsvValues[0] < 235;
    }
    public boolean green(){
        return 150 < hsvValues[0] && hsvValues[0] < 160;

    }
    public double getValues(){
        return hsvValues[0];
    }
}

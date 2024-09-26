package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CheckedMecanumDrive;

@TeleOp()

public class CheckedSimpleMecanumDriveOpMode extends OpMode {
    CheckedMecanumDrive drive = new CheckedMecanumDrive();

    @Override
    public void init() {
        drive.init(hardwareMap);
    }
    
    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;
        boolean strafeleft = gamepad1.dpad_left;
        boolean straferight = gamepad1.dpad_right;
        
        double m0Power, m1Power, m2Power, m3Power;
       
        if(gamepad1.dpad_left) {
        m3Power = (1);
        m0Power = (1);
        m2Power = (-1);
        m1Power = (-1);
    }
    else{
       m3Power = (0);
        m0Power = (0);
        m2Power = (0);
        m1Power = (0); 
    }
    if(gamepad1.dpad_right) {
        m3Power = (-1);
        m0Power = (-1);
        m2Power = (1);
        m1Power = (1);
    }
    else {
        m3Power = (0);
        m0Power = (0);
        m2Power = (0);
        m1Power = (0); 
    }
            drive.drive(forward, right, rotate, strafeleft, straferight);
    
}

}

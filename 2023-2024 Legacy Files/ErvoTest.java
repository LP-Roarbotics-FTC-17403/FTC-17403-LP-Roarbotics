
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp


public class ErvoTest extends LinearOpMode {
    
    private Servo servo1;


    @Override
    public void runOpMode() {
       
       
        servo1 = hardwareMap.get(Servo.class, "servo1");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
            
                if(gamepad1.x) {
                 servo1.setPosition(servo1.getPosition());
                 }
                double deter = gamepad1.right_trigger;
                if (deter == 1){
                    servo1.setPosition(0.60);
                }
                    else
                    {
                        servo1.setPosition(0.49);
                    }
                if (gamepad1.right_bumper) {
                    servo1.setPosition(0.41);
                }
    

        }
    }
}

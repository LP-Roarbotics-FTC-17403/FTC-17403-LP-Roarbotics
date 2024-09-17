package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous


public class TimedAutonSI extends LinearOpMode {

    private DcMotor motor0 = null;
    private DcMotor motor2 = null;
    private DcMotor motor1 = null;
    private DcMotor motor3 = null;
    private Servo servo0;

    @Override
    public void runOpMode() throws InterruptedException {

        motor0 = hardwareMap.get(DcMotor.class, "motor0");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        ElapsedTime timer = new ElapsedTime();
        servo0 = hardwareMap.get(Servo.class, "servo0");
        
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor3.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive())
        {
            timer.reset();

            if(timer.time() < 5){
                motor0.setPower(0.3);
                motor2.setPower(0.3);
                motor1.setPower(0.3);
                motor3.setPower(0.3);
                servo0.setPosition(0.41);

            }
            

        }

    }
}
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;


@TeleOp
public class MecanumTeleop2024_07_11 extends LinearOpMode {
    private Servo servo1;
    private Servo servo0;

    @Override
    public void runOpMode() {
       
        // Make sure ID's match your configuration
        DcMotor m0 = hardwareMap.dcMotor.get("m0");
        DcMotor m1 = hardwareMap.dcMotor.get("m1");
        DcMotor m2 = hardwareMap.dcMotor.get("m2");
        DcMotor m3 = hardwareMap.dcMotor.get("m3");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo0 = hardwareMap.get(Servo.class, "servo0");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        m3.setDirection(DcMotorSimple.Direction.REVERSE);
        m2.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            
            double deter = gamepad1.left_trigger;
            if (deter == 1){
                servo0.setPosition(0.55);
            }else{
            servo0.setPosition(0.48);
            }
            if(gamepad1.x) {
                 servo1.setPosition(servo1.getPosition());
                 }
                double det = gamepad1.right_trigger;
                if (det == 1){
                    servo1.setPosition(0.60);
                }
                    else
                    {
                        servo1.setPosition(0.49);
                    }
                if (gamepad1.right_bumper) {
                    servo1.setPosition(0.41);
                }
            
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator * 0.75;
            double backLeftPower = (y - x + rx) / denominator * 0.75;
            double frontRightPower = (y + x - rx) / denominator * 0.75;
            double backRightPower = (y - x - rx) / denominator * 0.75;

            m2.setPower(frontLeftPower);
            m3.setPower(backLeftPower);
            m0.setPower(frontRightPower);
            m1.setPower(backRightPower);
            
            // Push telemetry to the Driver Station.
            telemetry.update();

        }
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
}

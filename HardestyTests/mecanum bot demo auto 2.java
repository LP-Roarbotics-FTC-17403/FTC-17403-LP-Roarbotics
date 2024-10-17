package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Example OpMode. Demonstrates use of gyro, color sensor, encoders, and telemetry.
 *
 */
@Autonomous(name = "mecanum bot demo auto 2", group = "MecanumBot")
public class MecanumDemo_Auto_2 extends LinearOpMode {

    DcMotor m1, m2, m3, m4;
    SparkFunOTOS myOtos;
    SparkFunOTOS.Pose2D pos;


    public void runOpMode(){
        m1 = hardwareMap.dcMotor.get("back_left_motor");
        m2 = hardwareMap.dcMotor.get("front_left_motor");
        m3 = hardwareMap.dcMotor.get("front_right_motor");
        m4 = hardwareMap.dcMotor.get("back_right_motor");
        myOtos = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");

        m1.setDirection(DcMotor.Direction.REVERSE);
        m2.setDirection(DcMotor.Direction.REVERSE);

        pos = myOtos.getPosition();

        configureOtos();

        telemetry.addData("Press Start When Ready","");
        telemetry.update();

        waitForStart();

        pos = myOtos.getPosition();

        //Drive forward
        while (pos.x < 15.0) {
            pos = myOtos.getPosition();
            m1.setPower(1);
            m2.setPower(1);
            m3.setPower(1);
            m4.setPower(1);
            pos = myOtos.getPosition();

            updatePositionTelemetry();
        }
        stopMotion();
        pause();

        //Turn left
        while (pos.h < 87.0) {
            pos = myOtos.getPosition();
            m1.setPower(-1);
            m2.setPower(-1);
            m3.setPower(1);
            m4.setPower(1);
            pos = myOtos.getPosition();

            updatePositionTelemetry();
        }
        stopMotion();
        pause();

        //Drive forward
        while (pos.y < 40.0) {
            pos = myOtos.getPosition();
            m1.setPower(1);
            m2.setPower(1);
            m3.setPower(1);
            m4.setPower(1);
            pos = myOtos.getPosition();

            updatePositionTelemetry();
        }
        stopMotion();
        pause();


        //Turn left
        while (pos.h < 135.0) {
            pos = myOtos.getPosition();
            m1.setPower(-1);
            m2.setPower(-1);
            m3.setPower(1);
            m4.setPower(1);
            pos = myOtos.getPosition();

            updatePositionTelemetry();
        }
        stopMotion();
        pause();

        //Drive forward
        while (pos.x > 8.0) {
            pos = myOtos.getPosition();
            m1.setPower(1);
            m2.setPower(1);
            m3.setPower(1);
            m4.setPower(1);
            pos = myOtos.getPosition();

            updatePositionTelemetry();
        }
        stopMotion();
        pause();

        //requestOpModeStop();
    }

    private void pause() {
        sleep(1000);
    }

    private void updatePositionTelemetry() {
        // Log the position to the telemetry
        telemetry.addData("X coordinate", pos.x);
        telemetry.addData("Y coordinate", pos.y);
        telemetry.addData("Heading angle", pos.h);

        // Update the telemetry on the driver station
        telemetry.update();
    }

    private void stopMotion() {
        m1.setPower(0);
        m2.setPower(0);
        m3.setPower(0);
        m4.setPower(0);
    }

    private void configureOtos() {
        telemetry.addLine("Configuring OTOS...");
        telemetry.update();

        myOtos.setLinearUnit(DistanceUnit.INCH);

        myOtos.setAngularUnit(AngleUnit.DEGREES);

        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(0, 0, 0);
        myOtos.setOffset(offset);

        myOtos.setLinearScalar(1.0);
        myOtos.setAngularScalar(1.0);

        myOtos.calibrateImu();

        myOtos.resetTracking();

        SparkFunOTOS.Pose2D currentPosition = new SparkFunOTOS.Pose2D(0, 0, 0);
        myOtos.setPosition(currentPosition);

        SparkFunOTOS.Version hwVersion = new SparkFunOTOS.Version();
        SparkFunOTOS.Version fwVersion = new SparkFunOTOS.Version();
        myOtos.getVersionInfo(hwVersion, fwVersion);

        telemetry.addLine("OTOS configured! Press start to get position data!");
        telemetry.addLine();
        telemetry.addLine(String.format("OTOS Hardware Version: v%d.%d", hwVersion.major, hwVersion.minor));
        telemetry.addLine(String.format("OTOS Firmware Version: v%d.%d", fwVersion.major, fwVersion.minor));
        telemetry.update();
    }
}

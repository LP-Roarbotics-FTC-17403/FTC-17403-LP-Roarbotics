package org.firstinspires.ftc.teamcode.DecodeFiles;

import com.qualcomm.hardware.rev.Rev9AxisImuOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.ArrayList;

@TeleOp
public final class EncoderTelemetry extends LinearOpMode {

    //enter how many motors you have

    private int numberOfMotors = 4;

    //enter how many crservos you have

    private int numberOfCRServos = 0;

    //enter how many servos you have

    private int numberOfServos = 0;

    //name all the names based on their deviceName, add more if needed
    private final String[] MOTORNAMES = {"left_front_drive", "right_front_drive", "left_back_drive", "right_back_drive"};

    private final String[] CRSERVONAMES = {};

    private final String[] SERVONAMES = {};

    private final boolean[] reverse = {true, false, true, false};


    @Override
    public void runOpMode() throws InterruptedException {
        IMU imu = hardwareMap.get(IMU.class, "imu");

        ArrayList<Motors> dcMotorList = new ArrayList<Motors>();
        ArrayList<ContinuousRotationServos> crServoList = new ArrayList<ContinuousRotationServos>();
        ArrayList<Servos> servoList = new ArrayList<Servos>();

        //create numbers of Motors based on the amount of motors you have
        for(int i = 0; i<numberOfMotors; i++){
            dcMotorList.add(new Motors(hardwareMap, MOTORNAMES[i]));
        }

        for(int i = 0; i<numberOfCRServos; i++){
            crServoList.add(new ContinuousRotationServos(hardwareMap, CRSERVONAMES[i]));
        }

        for(int i = 0; i<numberOfServos; i++){
            servoList.add(new Servos(hardwareMap, SERVONAMES[i]));
        }

        for(int i = 0; i<numberOfMotors; i++){
            dcMotorList.get(i).initialize();
        }
        for(int i = 0; i<reverse.length; i++){
            if(reverse[i]) {
                dcMotorList.get(i).setReverse();
            }
        }

        IMU.Parameters parameters = new IMU.Parameters(
                new Rev9AxisImuOrientationOnRobot(Rev9AxisImuOrientationOnRobot.LogoFacingDirection.UP, Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection.BACKWARD));
        imu.initialize(parameters);

        waitForStart();

        while(opModeIsActive()) {
            //encoder values for motors
            for(int i = 0; i<dcMotorList.size(); i ++){
                telemetry.addData(MOTORNAMES[i] + " encoder ticks: ",dcMotorList.get(i).getCurrentEncoderPosition());

            }
            telemetry.addData("imu angular Velocity: ", imu.getRobotAngularVelocity(AngleUnit.DEGREES));
            telemetry.addData("imu angular ortientation: ", imu.getRobotYawPitchRollAngles());
            telemetry.update();
            //Power values of Continuous rotational Servos
            for(int i = 0; i<crServoList.size(); i++){
                telemetry.addData("crServo" + (i+1) + "power: ", crServoList.get(i).getCurrentPower());
                telemetry.update();
            }
            //Position values of every servos
            for(int i = 0; i<servoList.size(); i++){
                telemetry.addData("Servo " + (i+1) + " position: ", servoList.get(i).getCurrentServoPosition());
            }



            if(gamepad1.a){
                for(int i = 0; i<numberOfMotors; i++){
                    dcMotorList.get(i).initialize();
                }
            }


        }
    }

}


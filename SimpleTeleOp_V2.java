package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import java.util.Set;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@TeleOp
public class SimpleTeleOp_V2 extends LinearOpMode {

    public DcMotor motorRightFront;
    public DcMotor motorRightBack;
    public DcMotor motorLeftBack;
    public DcMotor motorLeftFront;
    public DcMotor  motorArm;
    public DcMotor motorLift;
    public CRServo servoIntake;
    public Servo servoWrist;
    public Servo servoLight;
    public ColorSensor sensorColor;

    final double LIFT_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;
    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation

    double ARM_COLLAPSED_INTO_ROBOT             = 0;
    double ARM_COLLECT                          = 0 * ARM_TICKS_PER_DEGREE;
    double ARM_CLEAR_BARRIER                    = 15 * ARM_TICKS_PER_DEGREE;
    double ARM_SCORE_SPECIMEN                   = 90 * ARM_TICKS_PER_DEGREE;
    double ARM_SCORE_SAMPLE_IN_HIGH             = 95 * ARM_TICKS_PER_DEGREE;
    double ARM_ATTACH_HANGING_HOOK              = 130 * ARM_TICKS_PER_DEGREE;
    double ARM_WINCH_ROBOT                      = 10  * ARM_TICKS_PER_DEGREE;
    double SLOW_MOTOR1                          =(90 * ARM_TICKS_PER_DEGREE);
    double SLOW_MOTOR2                          =(129 * ARM_TICKS_PER_DEGREE);
    double CONTROL_LIMIT                        =(45 * ARM_TICKS_PER_DEGREE);
    double ARM_FWD_LIMIT                        =100;
    double ARM_REV_LIMIT                        =100;
    
    double INTAKE_COLLECT                       = -1.0;
    double INTAKE_OFF                           =  0.0;
    double INTAKE_DEPOSIT                       =  0.5;
    double WRIST_FOLDED_IN                      = 0.2;
    double WRIST_FOLDED_OUT                     = 0.52;

    double LIFT_COLLAPSED                       = 0 * LIFT_TICKS_PER_MM;
    double LIFT_SCORING_IN_LOW_BASKET           = 0 * LIFT_TICKS_PER_MM;
    double LIFT_SCORING_IN_HIGH_BASKET          = 455 * LIFT_TICKS_PER_MM;
    double LIFT_BELOW45                         = 310*LIFT_TICKS_PER_MM;
    double LIFT_FWD_LIMIT                       = 400 * LIFT_TICK_PER_MM;
    double LIFT_REV_LIMIT                       = 0 * LIFT_TICK_PER_MM;
    
    final double COLOR_RED                      = 0.279;
    final double COLOR_GREEN                    = 0.330;
    final double COLOR_BLUE                     = 0.611;
    final double COLOR_OFF                      = 0.0;
    
    final int FORWARD                           = 1;
    final int BACKWARD                          = -1;
    final int STOP_SPIN                         = 0;

    // Motor setpoints
    double armCommand = (int)ARM_COLLAPSED_INTO_ROBOT;
    double liftCommand = LIFT_COLLAPSED;
    int motorArmVelocity = 2000;
    int motorLiftVelocity = 2500;
    double manualArm = 5 * ARM_TICKS_PER_DEGREE;

    // Timers
    double cycletime = 0;
    double looptime = 0;
    double oldtime = 0;

    @Override
    public void runOpMode() {

        motorLeftBack = hardwareMap.dcMotor.get("backLeftMotor");
        motorLeftFront = hardwareMap.dcMotor.get("frontLeftMotor");
        motorRightFront = hardwareMap.dcMotor.get("frontRightMotor");
        motorRightBack = hardwareMap.dcMotor.get("backRightMotor");
        motorArm = hardwareMap.dcMotor.get("armMotor");
        motorLift = hardwareMap.dcMotor.get("slideMotor");
        servoIntake = hardwareMap.get(CRServo.class, "intake");
        servoWrist = hardwareMap.get(Servo.class, "wrist");
        sensorColor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        servoLight = hardwareMap.get(Servo.class, "light");

        // Calling setPower with a positive value should rotate the wheel forward
        motorLeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        motorRightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLift.setDirection(DcMotorSimple.Direction.REVERSE);

        // Configure hardware
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ((DcMotorEx) motorArm).setCurrentAlert(5,CurrentUnit.AMPS);
        ((DcMotorEx) motorLift).setCurrentAlert(5,CurrentUnit.AMPS);

        // Initial positions
        motorArm.setTargetPosition(0);
        motorLift.setTargetPosition(0);
        servoIntake.setPower(INTAKE_OFF);
        servoWrist.setPosition(WRIST_FOLDED_OUT);

        motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        // Start position
        ((DcMotorEx) motorArm).setVelocity(1500);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armCommand = ARM_CLEAR_BARRIER;
        servoWrist.setPosition(WRIST_FOLDED_OUT);

        telemetry.addLine("Initialized");
        telemetry.update();

        while (opModeIsActive()) {
            processGamepad1();
            processGamepad2();
            processColorSensor();

            coercePositionCommands();

            checkOverCurrent(motorArm);
            checkOverCurrent(motorLift);

            moveMotor(motorArm, motorArmVelocity);
            moveMotor(motorLift, motorLiftVelocity);

            directDriveControl();

            looptime = getRuntime();
            cycletime = looptime-oldtime;
            oldtime = looptime;

            telemetry.update();
            sleep(20);  // Sleeping here allows the CPU to catch up with other tasks
        }
    }

    private void coercePositionCommands(){
        if (liftCommand < 0){
            liftCommand = 0;
        }
        //TODO missing arm comp
        // if (armPosition < CONTROL_LIMIT){
        //     armLiftComp = (0.25568 * liftPosition);
        //     if(liftPosition > LIFT_BELOW45){
        //         liftPosition = LIFT_BELOW45;
        //     }

        // }
        // else{
        //     armLiftComp = 0;
        // }
    }

    private void processGamepad1(){
        // Set the arm vertical to hook onto the LOW RUNG for hanging
        if (gamepad1.y) {
            armCommand = ARM_ATTACH_HANGING_HOOK;
            servoIntake.setPower(INTAKE_OFF);
            servoWrist.setPosition(WRIST_FOLDED_IN);
        }

        /* Turn off the intake, fold in the wrist, and move the arm
        back to folded inside the robot. This is also the starting
        configuration */
        if (gamepad1.x) {
            armCommand = ARM_COLLAPSED_INTO_ROBOT;
            servoIntake.setPower(INTAKE_OFF);
            servoWrist.setPosition(WRIST_FOLDED_IN);
        }

        // Move the arm down to lift the robot up once it has been hooked
        if (gamepad1.a) {
            armCommand = ARM_WINCH_ROBOT;
            servoIntake.setPower(INTAKE_OFF);
            servoWrist.setPosition(WRIST_FOLDED_IN);
        }
    }

    private void processGamepad2(){
        // Manual control of arm
        armCommand += manualArm * (gamepad2.right_trigger + (-gamepad2.left_trigger));

        // Reset arm/lift encoders
        if (gamepad2.start) {
            motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ARM_COLLAPSED_INTO_ROBOT  = 0;
            ARM_COLLECT               = (0 * ARM_TICKS_PER_DEGREE);
            ARM_CLEAR_BARRIER         = (15 * ARM_TICKS_PER_DEGREE);
            ARM_SCORE_SPECIMEN        = (90 * ARM_TICKS_PER_DEGREE);
            ARM_SCORE_SAMPLE_IN_HIGH   = (95 * ARM_TICKS_PER_DEGREE);
            ARM_ATTACH_HANGING_HOOK   = (130 * ARM_TICKS_PER_DEGREE);
            ARM_WINCH_ROBOT           = (10  * ARM_TICKS_PER_DEGREE);
            SLOW_MOTOR1               =(90 * ARM_TICKS_PER_DEGREE);
            SLOW_MOTOR2               =(129 * ARM_TICKS_PER_DEGREE);
            CONTROL_LIMIT             =(45 * ARM_TICKS_PER_DEGREE);
        }

        if (gamepad2.back) {
            motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        if (gamepad2.dpad_up) {
            servoIntake.setPower(INTAKE_COLLECT);
        } else if (gamepad2.dpad_down) {
            servoIntake.setPower(INTAKE_OFF);
        } else if (gamepad2.y) {
            servoIntake.setPower(INTAKE_DEPOSIT);
        }

        if(gamepad2.a){
            /* This is the intaking/collecting arm position */
            armCommand = ARM_COLLECT;
            servoWrist.setPosition(WRIST_FOLDED_OUT);
            servoIntake.setPower(INTAKE_COLLECT);
        } else if (gamepad2.b){
            /* This is about 20° up from the collecting position to clear the barrier
            Note here that we don't set the wrist position or the intake power when we
            select this "mode", this means that the intake and wrist will continue what
            they were doing before we clicked left bumper. */
            armCommand = ARM_CLEAR_BARRIER;
        } else if (gamepad2.x){
            armCommand = ARM_SCORE_SAMPLE_IN_HIGH;
        } else if (gamepad2.dpad_left) {
            servoWrist.setPosition(WRIST_FOLDED_IN);
        } else if (gamepad2.dpad_right) {
            servoWrist.setPosition(WRIST_FOLDED_OUT);
        }

        if (gamepad2.right_bumper){
            liftCommand += 2800 * cycletime;
        } else if (gamepad2.left_bumper){
            liftCommand -= 2800 * cycletime;
        }
    }

    private void processColorSensor(){
        if(sensorColor.red()>=350){
            servoLight.setPosition(COLOR_RED);
            if(sensorColor.green()>=800){
                servoLight.setPosition(COLOR_GREEN);
            }
        } else if(sensorColor.blue()>=400){
            servoLight.setPosition(COLOR_BLUE);
        } else {
            servoLight.setPosition(COLOR_OFF);
        }
    }

    private void checkOverCurrent(DcMotor motor){
        if (((DcMotorEx) motor).isOverCurrent()){
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
        }
    }

    private void directDriveControl() {
        directDriveControl(1.0);
    }

    private void moveMotor(DcMotor motor, int velocity){
        motor.setTargetPosition((int) armCommand);
        ((DcMotorEx) motor).setVelocity(velocity);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void directDriveControl(double speedMultiplier) {
        double max;

        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double r = gamepad1.right_stick_x;

        // Apply exponential scaling to the joystick values
        y = Math.signum(y) * Math.pow(y, 2.0);
        x = Math.signum(x) * Math.pow(x, 2.0);

        double powerRightFront = -x + y - r;
        double powerRightBack  =  x + y - r;
        double powerLeftBack   = -x + y + r;
        double powerLeftFront  =  x + y + r;

        max = Math.max(Math.max(Math.abs(powerLeftFront), Math.abs(powerRightBack)),
                Math.max(Math.abs(powerLeftBack), Math.abs(powerLeftFront)));

        // If any individual motor power is greater than 1.0, scale all values to fit in the range [-1.0, 1.0]
        if (max > 1.0) {
            powerRightFront  /= max;
            powerRightBack   /= max;
            powerLeftBack    /= max;
            powerLeftFront   /= max;
        }

        motorRightFront.setPower(powerRightFront * speedMultiplier);
        motorRightBack.setPower(powerRightBack * speedMultiplier);
        motorLeftBack.setPower(powerLeftBack * speedMultiplier);
        motorLeftFront.setPower(powerLeftFront * speedMultiplier);
    }
    
    private void spin(Servo motor, int direction){
        motor.setPower(direction);
    }
    
    private void move(DcMotor motor, int position, int speed, int fwdLimit, int revLimit){
        position = clamp(position, revLimit, fwdLimit);
        motor.setTargetPosition(position);
        motor.setVelocity(speed);
    }
    
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }  
}

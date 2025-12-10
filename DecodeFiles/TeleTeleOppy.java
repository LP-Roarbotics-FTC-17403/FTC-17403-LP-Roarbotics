/*   MIT License
 *   Copyright (c) [2025] [Base 10 Assets, LLC]
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:

 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.

 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */


package org.firstinspires.ftc.teamcode.DecodeFiles;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This file includes a teleop (driver-controlled) file for the goBILDA® Robot in 3 Days for the
 * 2025-2026 FIRST® Tech Challenge season DECODE™!
 */

@TeleOp(name = "BlueTeleOp", group = "TestCode")
//@Disabled
public class TeleTeleOppy extends OpMode {
    final double FEED_TIME_SECONDS = 2.80; //The feeder servos run this long when a shot is requested.
    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;

    final double LAUNCHER_CLOSE_TARGET_VELOCITY = 1200; //in ticks/second for the close goal.
    final double LAUNCHER_CLOSE_MIN_VELOCITY = 1175; //minimum required to start a shot for close goal.

    final double LAUNCHER_FAR_TARGET_VELOCITY = 1400; //Target velocity for far goal
    final double LAUNCHER_FAR_MIN_VELOCITY = 1375; //minimum required to start a shot for far goal.

    double launcherTarget = LAUNCHER_CLOSE_TARGET_VELOCITY; //These variables allow
    double launcherMin = LAUNCHER_CLOSE_MIN_VELOCITY;

    final double LEFT_POSITION = 0.3162; //the left and right position for the diverter servo
    final double RIGHT_POSITION = 0.035;

    // Declare OpMode members.
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotorEx leftLauncher = null;
    private DcMotorEx rightLauncher = null;
    private DcMotor intake = null;
    private CRServo leftFeeder = null;
    private CRServo rightFeeder = null;
    private Servo diverter = null;

    ElapsedTime leftFeederTimer = new ElapsedTime();
    ElapsedTime rightFeederTimer = new ElapsedTime();


    private enum LaunchState {
        IDLE,
        SPIN_UP,
        LAUNCH,
        LAUNCHING,
    }
    private LaunchState leftLaunchState;
    private LaunchState rightLaunchState;

    private enum DiverterDirection {
        LEFT,
        RIGHT;
    }
    private DiverterDirection diverterDirection = DiverterDirection.LEFT;

    private enum IntakeState {
        ON,
        OFF;
    }

    private enum LeftFeederState {
        ON,
        OFF;
    }

    private enum RightFeederState {
        ON,
        OFF;
    }

    private IntakeState intakeState = IntakeState.OFF;
    private LeftFeederState leftFeederState = LeftFeederState.OFF;
    private RightFeederState rightFeederState = RightFeederState.OFF;

    private enum LauncherDistance {
        CLOSE,
        FAR;
    }

    private LauncherDistance launcherDistance = LauncherDistance.CLOSE;

    // Setup a variable for each drive wheel to save power level for telemetry
    double leftFrontPower;
    double rightFrontPower;
    double leftBackPower;
    double rightBackPower;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        leftLaunchState = LaunchState.IDLE;
        rightLaunchState = LaunchState.IDLE;

        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        leftLauncher = hardwareMap.get(DcMotorEx.class, "left_launcher");
        rightLauncher = hardwareMap.get(DcMotorEx.class, "right_launcher");
        intake = hardwareMap.get(DcMotor.class, "intake");
        leftFeeder = hardwareMap.get(CRServo.class, "left_feeder");
        rightFeeder = hardwareMap.get(CRServo.class, "right_feeder");
        diverter = hardwareMap.get(Servo.class, "diverter");

        /*
         * To drive forward, most robots need the motor on one side to be reversed,
         * because the axles point in opposite directions. Pushing the left stick forward
         * MUST make robot go forward. So adjust these two lines based on your first test drive.
         * Note: The settings here assume direct drive on left and right wheels. Gear
         * Reduction or 90 Deg drives may require direction flips
         */
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        rightLauncher.setDirection(DcMotorSimple.Direction.REVERSE);

        leftLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*
         * Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to
         * slow down much faster when it is coasting. This creates a much more controllable
         * drivetrain. As the robot stops much quicker.
         */
        leftFrontDrive.setZeroPowerBehavior(BRAKE);
        rightFrontDrive.setZeroPowerBehavior(BRAKE);
        leftBackDrive.setZeroPowerBehavior(BRAKE);
        rightBackDrive.setZeroPowerBehavior(BRAKE);
        leftLauncher.setZeroPowerBehavior(BRAKE);
        rightLauncher.setZeroPowerBehavior(BRAKE);

        /*
         * set Feeders to an initial value to initialize the servo controller
         */
        leftFeeder.setPower(STOP_SPEED);
        rightFeeder.setPower(STOP_SPEED);

        leftLauncher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));
        rightLauncher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));

        /*
         * Much like our drivetrain motors, we set the left feeder servo to reverse so that they
         * both work to feed the ball into the robot.
         */
        leftFeeder.setDirection(DcMotorSimple.Direction.REVERSE);

        /*
         * Tell the driver that initialization is complete.
         */
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {

        mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        /*
         * Here we give the user control of the speed of the launcher motor without automatically
         * queuing a shot.
         */
        if (gamepad2.y) {
            leftLauncher.setVelocity(launcherTarget);
            rightLauncher.setVelocity(launcherTarget);
        } else if (gamepad2.b) { // stop flywheel
            leftLauncher.setVelocity(STOP_SPEED);
            rightLauncher.setVelocity(STOP_SPEED);
        }

        if (gamepad2.dpadDownWasPressed()) {
            switch (diverterDirection){
                case LEFT:
                    diverterDirection = DiverterDirection.RIGHT;
                    diverter.setPosition(RIGHT_POSITION);
                    break;
                case RIGHT:
                    diverterDirection = DiverterDirection.LEFT;
                    diverter.setPosition(LEFT_POSITION);
                    break;
            }
        }

        if (gamepad2.dpadLeftWasPressed()) {
            switch (leftFeederState) {
                case ON:
                    leftFeeder.setDirection(CRServo.Direction.FORWARD);
                    leftFeederState = LeftFeederState.OFF;
                    leftFeeder.setPower(FULL_SPEED);
                    leftFeederTimer.reset();
                case OFF:
                    if (leftFeederTimer.seconds() > FEED_TIME_SECONDS) {
                        leftFeeder.setDirection(CRServo.Direction.REVERSE);
                        leftFeederState = LeftFeederState.ON;
                        leftFeeder.setPower(STOP_SPEED);
                    }
            }
        }

        if (gamepad2.dpadRightWasPressed()) {
            switch (rightFeederState) {
                case ON:
                    rightFeeder.setDirection(CRServo.Direction.REVERSE);
                    rightFeederState = RightFeederState.OFF;
                    rightFeeder.setPower(FULL_SPEED);
                    rightFeederTimer.reset();
                case OFF:
                    if (rightFeederTimer.seconds() > FEED_TIME_SECONDS) {
                        rightFeeder.setDirection(CRServo.Direction.FORWARD);
                        rightFeederState = RightFeederState.ON;
                        rightFeeder.setPower(STOP_SPEED);
                    }
            }
        }

        if (gamepad1.aWasPressed()){
            switch (intakeState){
                case ON:
                    intake.setDirection(DcMotorSimple.Direction.FORWARD);
                    intakeState = IntakeState.OFF;
                    intake.setPower(0);
                    break;
                case OFF:
                    intake.setDirection(DcMotorSimple.Direction.REVERSE);
                    intakeState = IntakeState.ON;
                    intake.setPower(1);
                    break;
            }
        }

        if (gamepad1.bWasPressed()){
            switch (intakeState){
                case ON:
                    intake.setDirection(DcMotorSimple.Direction.REVERSE);
                    intakeState = IntakeState.OFF;
                    intake.setPower(0);
                    break;
                case OFF:
                    intake.setDirection(DcMotorSimple.Direction.FORWARD);
                    intakeState = IntakeState.ON;
                    intake.setPower(1);
                    break;
            }
        }

        if (gamepad2.x) {
            leftFeeder.setPower(0.01);
            rightFeeder.setPower(0.01);
        }

        if (gamepad2.dpadUpWasPressed()) {
            switch (launcherDistance) {
                case CLOSE:
                    launcherDistance = LauncherDistance.FAR;
                    launcherTarget = LAUNCHER_FAR_TARGET_VELOCITY;
                    launcherMin = LAUNCHER_FAR_MIN_VELOCITY;
                    break;
                case FAR:
                    launcherDistance = LauncherDistance.CLOSE;
                    launcherTarget = LAUNCHER_CLOSE_TARGET_VELOCITY;
                    launcherMin = LAUNCHER_CLOSE_MIN_VELOCITY;
                    break;
            }
        }

        if(gamepad1.left_stick_button) {
            leftFrontDrive.setPower(leftFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightFrontDrive.setPower(rightFrontPower);
            rightBackDrive.setPower(rightBackPower);
        }

        /*
         * Now we call our "Launch" function.
         */
        launchLeft(gamepad2.leftBumperWasPressed());
        launchRight(gamepad2.rightBumperWasPressed());

        /*
         * Show the state and motor powers
         */
        telemetry.addData("State", leftLaunchState);
        telemetry.addData("launch distance", launcherDistance);
        telemetry.addData("Left Launcher Velocity", leftLauncher.getVelocity());
        telemetry.addData("Right Launcher Velocity", rightLauncher.getVelocity());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    void mecanumDrive(double y, double x, double rx){

        //double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        //double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        //double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        //rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftFrontPower = (y + x + rx) / denominator;
        double leftBackPower = (y - x + rx) / denominator;
        double rightFrontPower = (y - x - rx) / denominator;
        double rightBackPower = (y + x - rx) / denominator;

        leftFrontDrive.setPower(leftFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightFrontDrive.setPower(rightFrontPower);
        rightBackDrive.setPower(rightBackPower);


    }

    void launchLeft(boolean shotRequested) {
        switch (leftLaunchState) {
            case IDLE:
                if (shotRequested) {
                    leftLaunchState = LaunchState.SPIN_UP;
                }
                break;
            case SPIN_UP:
                leftLauncher.setVelocity(launcherTarget);
                rightLauncher.setVelocity(launcherTarget);
                if (leftLauncher.getVelocity() > launcherMin) {
                    leftLaunchState = LaunchState.LAUNCH;
                }
                break;
            case LAUNCH:
                leftFeeder.setPower(FULL_SPEED);
                leftFeederTimer.reset();
                leftLaunchState = LaunchState.LAUNCHING;
                break;
            case LAUNCHING:
                if (leftFeederTimer.seconds() > FEED_TIME_SECONDS) {
                    leftLaunchState = LaunchState.IDLE;
                    leftFeeder.setPower(STOP_SPEED);
                }
                break;
        }
    }

    void launchRight(boolean shotRequested) {
        switch (rightLaunchState) {
            case IDLE:
                if (shotRequested) {
                    rightLaunchState = LaunchState.SPIN_UP;
                }
                break;
            case SPIN_UP:
                leftLauncher.setVelocity(launcherTarget);
                rightLauncher.setVelocity(launcherTarget);
                if (leftLauncher.getVelocity() > launcherMin) {
                    rightLaunchState = LaunchState.LAUNCH;
                }
                break;
            case LAUNCH:
                rightFeeder.setPower(FULL_SPEED);
                rightFeederTimer.reset();
                rightLaunchState = LaunchState.LAUNCHING;
                break;
            case LAUNCHING:
                if (rightFeederTimer.seconds() > FEED_TIME_SECONDS) {
                    rightLaunchState = LaunchState.IDLE;
                    rightFeeder.setPower(STOP_SPEED);
                }
                break;
        }
    }
}
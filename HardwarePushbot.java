


package org.firstinspires.ftc.teamcode;

//import org.firstinspires.ftc.teamcode.HardwarePushbot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class HardwarePushbot {

    /* Declare OpMode members. */
    //private LinearOpMode myOpMode = null;   // gain access to methods in the calling OpMode.

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    public DcMotor frontLeft   = null;
    
    public DcMotor backLeft  = null;
    public DcMotor frontRight = null;
    public DcMotor backRight = null;
    public CRServo servo0 = null;
    public CRServo servo1 = null;
    public Servo servo2 = null;
    public Servo servo3 = null;
    

    // Define Drive constants.  Make them public so they CAN be used by the calling OpMode
    /*public static final double MID_SERVO       =  0.5 ;
    public static final double HAND_SPEED      =  0.02 ;  // sets rate to move servo
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;*/
HardwareMap hwMap = null;
private ElapsedTime period = new ElapsedTime();
    // Define a constructor that allows the OpMode to pass a reference to itself.
    public HardwarePushbot () {
        
    }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * <p>
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init(HardwareMap ahwMap)    {
        
        hwMap=ahwMap;
        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        frontLeft  = hwMap.get(DcMotor.class, "m0");
        backLeft = hwMap.get(DcMotor.class, "m1");
        frontRight  = hwMap.get(DcMotor.class, "m2");
        backRight = hwMap.get(DcMotor.class, "m3");
        servo0 = hwMap.get(CRServo.class, "cr0");
        servo1 = hwMap.get(CRServo.class, "cr1");
        servo2 = hwMap.get(Servo.class, "s2");
        servo3 = hwMap.get(Servo.class, "s3");
        

        
        //reverse the motor
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        
        //set zeropower behavior
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        //servo.setPosition(1);
        
    }
}

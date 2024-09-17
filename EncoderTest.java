package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Autonomous
public class EncoderTest extends LinearOpMode
{
    
    DcMotorEx motor0;
    DcMotorEx motor1;
    DcMotorEx motor2;
    DcMotorEx motor3;
    DcMotor Elev1;


  @Override
  public void runOpMode() {
    
    
      motor3 = hardwareMap.get(DcMotorEx.class, "motor3");

      motor2 = hardwareMap.get(DcMotorEx.class, "motor2");
      motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
      motor0 = hardwareMap.get(DcMotorEx.class, "motor0");
      
      DcMotor Elev1 = hardwareMap.dcMotor.get("Elev1");
      DcMotor Elev2 = hardwareMap.dcMotor.get("Elev2");

      Servo  GripRight = hardwareMap.servo.get("GripRight");
      Servo  GripLeft = hardwareMap.servo.get("GripLeft");
      
//tick 538
    //ticks 538
      motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

      motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
      motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
      motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
      
      motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      

      motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      
      //brakes
      motor0.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
      motor1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
      motor2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
      motor3.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
      Elev1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
      Elev2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

//open
      GripRight.setPosition(0.06);
      GripLeft.setPosition(0.98);
      

    waitForStart();
    if (opModeIsActive()){
        
          motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //move fowards
      
      motor3.setTargetPosition(4000);

      
      motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
      
      motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);


      motor3.setPower(0.1);

      sleep(4000);
      
      
      motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      sleep(1500);

    }



while (opModeIsActive()) {
            telemetry.update();
            telemetry.addData("velocity", motor0.getVelocity());
            telemetry.addData("position", motor0.getCurrentPosition());
            telemetry.addData("is at target", !motor0.isBusy());
            telemetry.addData("velocity", motor1.getVelocity());
            telemetry.addData("position", motor1.getCurrentPosition());
            telemetry.addData("is at target", !motor1.isBusy());
            telemetry.addData("velocity", motor2.getVelocity());
            telemetry.addData("position", motor2.getCurrentPosition());
            telemetry.addData("is at target", !motor2.isBusy());
            telemetry.addData("velocity", motor3.getVelocity());
            telemetry.addData("position", motor3.getCurrentPosition());
            telemetry.addData("is at target", !motor3.isBusy());
      }
    }
  }
 
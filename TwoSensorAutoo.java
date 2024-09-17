package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous
public class TwoSensorAutoo extends LinearOpMode
{

  DcMotorEx motor0;
  DcMotorEx motor1;
  DcMotorEx motor2;
  DcMotorEx motor3;
  ColorSensor color2;
  
  @Override
  public void runOpMode() {
    
    color2 = hardwareMap.get(ColorSensor.class, "ColorSensor2");
    
    motor3 = hardwareMap.get(DcMotorEx.class, "motor3");
    motor2 = hardwareMap.get(DcMotorEx.class, "motor2");
    motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
    motor0 = hardwareMap.get(DcMotorEx.class, "motor0");
    
    
      
    //Initialization
    
    //ticks 538
    // motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    // motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    // motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    // motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
      motor0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    waitForStart();
    if (opModeIsActive()) {
        //move fowards
        motor0.setPower(0.5);
        motor1.setPower(0.5);
        motor2.setPower(-0.5);
        motor3.setPower(0.5);
        sleep(610);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
//strafe to the right
        motor0.setPower(-0.5);
        motor1.setPower(0.5);
        //two is reversed
        motor2.setPower(0.5);
        motor3.setPower(0.5);
        sleep(300);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
        
      
    if(color2.red()> color2.blue() && color2.red()> color2.green())
    {
        //strafe to left
        motor0.setPower(0.5);
        motor1.setPower(-0.5);
        //two is reversed
        motor2.setPower(-0.5);
        motor3.setPower(-0.5);
        sleep(350);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);

//little forward
        motor0.setPower(0.5);
        motor1.setPower(0.5);
        motor2.setPower(-0.5);
        motor3.setPower(0.5);
        sleep(370);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
        
        //strafe left to pos 1
        motor0.setPower(0.5);
        motor1.setPower(-0.5);
        //two is reversed
        motor2.setPower(-0.5);
        motor3.setPower(-0.5);
        sleep(1350);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
        
        
        
    }
    else if(color2.blue()>color2.red() && color2.blue()>color2.green())
    {

        //strafe to the left 
        motor0.setPower(0.5);
        motor1.setPower(-0.5);
        //two is reversed
        motor2.setPower(-0.5);
        motor3.setPower(-0.5);
        sleep(350);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);

//little forward
        motor0.setPower(0.5);
        motor1.setPower(0.5);
        motor2.setPower(-0.5);
        motor3.setPower(0.5);
        sleep(370);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
        
        //strafe right to pos 3
        motor0.setPower(-0.5);
        motor1.setPower(0.5);
        //two is reversed
        motor2.setPower(0.5);
        motor3.setPower(0.5);
        sleep(1350);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);
    }
    else if(color2.green()> color2.red() && color2.green()> color2.blue())
    {
        //fowards
        //strafe to the left
        motor0.setPower(0.5);
        motor1.setPower(-0.5);
        //two is reversed
        motor2.setPower(-0.5);
        motor3.setPower(-0.5);
        sleep(350);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);

//little forward
        motor0.setPower(0.5);
        motor1.setPower(0.5);
        motor2.setPower(-0.5);
        motor3.setPower(0.5);
        sleep(370);
        
        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        sleep(1000);

    }
    else{

    }
}




while (opModeIsActive()) {
            telemetry.addData("Red", color2.red());
            telemetry.addData("Green", color2.green());
            telemetry.addData("Blue", color2.blue());
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
            telemetry.update();
      }
    }
  }
 
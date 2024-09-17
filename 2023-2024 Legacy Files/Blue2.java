package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Autonomous
public class Blue2 extends LinearOpMode
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
			//close
		GripRight.setPosition(0.5);
		GripLeft.setPosition(0.5);
		sleep(700);
		

		//up a bit
		Elev1.setPower(-0.7);
		Elev2.setPower(0.7);
		sleep(1300);
		
		
		Elev1.setPower(0);
		Elev2.setPower(0);
		sleep(100);

		//move fowards
	  
	  motor3.setTargetPosition(-2200);
	  //motor3 was positive
	  motor2.setTargetPosition(-2200);
	  motor0.setTargetPosition(2200);
	  motor1.setTargetPosition(2200);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
	  
	  motor2.setPower(0.7);
	  motor0.setPower(0.7);
	  motor1.setPower(0.7);
	  motor3.setPower(0.7);
	  //was at 0.1

	  sleep(3300);
	  

	  
	  motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  sleep(1500);
	  
				//up a bit
		Elev1.setPower(-0.7);
		Elev2.setPower(0.7);
		sleep(1760);
		//reduce the time?
		
		
		Elev1.setPower(0.3);
		Elev2.setPower(0.3);
		//sleep(100);
		//reduce time?
	  
	  
		//Strafe Right to deliver cone
	  
	  motor3.setTargetPosition(-640);
	  //motor3 was positive
	  motor2.setTargetPosition(640);
	  motor0.setTargetPosition(-640);
	  motor1.setTargetPosition(640);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
	  
	  motor2.setPower(-0.5);
	  motor0.setPower(0.5);
	  motor1.setPower(-0.5);
	  motor3.setPower(0.5);
	  //was at 0.1

	  sleep(2000);
	  

	  
	  motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  sleep(700);


	  
	  
		//move fowards
	  
	  motor3.setTargetPosition(-260);
	  //motor3 was positive
	  motor2.setTargetPosition(-260);
	  motor0.setTargetPosition(260);
	  motor1.setTargetPosition(260);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
	  
	  motor2.setPower(0.5);
	  motor0.setPower(0.5);
	  motor1.setPower(0.5);
	  motor3.setPower(0.5);
	  //was at 0.1

	  sleep(1500);
	  

	  
	  motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  sleep(700);
	  

	
//Drop the cone

	  GripRight.setPosition(0.06);
	  GripLeft.setPosition(0.98);
	  sleep(300);
	  
	  
		

//Exact opposite

 
		//move backwards
	  
	  motor3.setTargetPosition(260);
	  //motor3 was positive
	  motor2.setTargetPosition(260);
	  motor0.setTargetPosition(-260);
	  motor1.setTargetPosition(-260);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
	  
	  motor2.setPower(0.5);
	  motor0.setPower(-0.5);
	  motor1.setPower(-0.5);
	  motor3.setPower(0.5);
	  //was at 0.1

	  sleep(1500);
	  

	  
	  motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  sleep(700);
		


		//Strafe Left to deliver park
	  
	  motor3.setTargetPosition(640);
	  //motor3 was positive
	  motor2.setTargetPosition(-640);
	  motor0.setTargetPosition(640);
	  motor1.setTargetPosition(-640);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
	  
	  motor2.setPower(0.5);
	  motor0.setPower(-0.5);
	  motor1.setPower(0.5);
	  motor3.setPower(-0.5);
	  //was at 0.1

	  sleep(2000);
	  

	  
	  motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  sleep(700);
	  
//move backwards
	  
	  motor3.setTargetPosition(1000);
	  //motor3 was positive
	  motor2.setTargetPosition(1000);
	  motor0.setTargetPosition(-1000);
	  motor1.setTargetPosition(-1000);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
	  
	  motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	  motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
	  
	  motor2.setPower(0.7);
	  motor0.setPower(-0.7);
	  motor1.setPower(-0.7);
	  motor3.setPower(0.7);
	  //was at 0.1

	  sleep(2500);
	  

	  
	  motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	  sleep(800);
	  


	  


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
 

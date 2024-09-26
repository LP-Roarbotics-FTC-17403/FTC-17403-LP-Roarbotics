package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CheckedMecanumDrive {
  private DcMotor m3;
  private DcMotor m0;
  private DcMotor m2;
  private DcMotor m1;
  //m0 - frontRight , m1 - rearRight, m2 - rearLeft , m3 - frontLeft
  
  public void init(HardwareMap hardwareMap) {
    m3 = hardwareMap.dcMotor.get("m3");
    m0 = hardwareMap.dcMotor.get("m0");
    m2 = hardwareMap.dcMotor.get("m2");
    m1 = hardwareMap.dcMotor.get("m1");
      
      m2.setDirection(DcMotor.Direction.REVERSE);
      m3.setDirection(DcMotor.Direction.REVERSE);
    
    m3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    m0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    m2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    m1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }
  
    private void setPowers(double m3Power, double m0Power, double m2Power, double m1Power) {
      double maxSpeed = 1.0;
      maxSpeed = Math.max(maxSpeed, Math.abs(m3Power));
      maxSpeed = Math.max(maxSpeed, Math.abs(m0Power));
      maxSpeed = Math.max(maxSpeed, Math.abs(m2Power));
      maxSpeed = Math.max(maxSpeed, Math.abs(m1Power));
      
      m3Power /= maxSpeed;
      m0Power /= maxSpeed;
      m2Power /= maxSpeed;
      m1Power /= maxSpeed;

      m3.setPower(m3Power);
      m0.setPower(m0Power);
      m2.setPower(m2Power);
      m1.setPower(m1Power);
    }
    
    public void drive(double forward, double right, double rotate, boolean strafeleft, boolean straferight) {
        double m3Power = forward + right + rotate;
        double m0Power = forward - right - rotate;
        double m2Power = forward - right + rotate;
        double m1Power = forward + right - rotate;
        
      setPowers(m3Power, m0Power, m2Power, m1Power);
    }
}

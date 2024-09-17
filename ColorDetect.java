package org.firstinspires.ftc.teamcode;
 
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 
@TeleOp
public class ColorDetect extends LinearOpMode {
    ColorSensor color;
    
    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        color = hardwareMap.get(ColorSensor.class, "ColorSensor");
        
        // Wait for the Play button to be pressed
        waitForStart();
 
        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {


        
        if (color.red() < color.blue() && color.green() < color.blue() && color.blue() > 100){
            
            //This is Blue 
            telemetry.addLine("Blue is detected");
            
        }else {
            
            telemetry.addLine("Blue is NOOT detected ");
        }


            telemetry.addData("Red", color.red());
            telemetry.addData("Green", color.green());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("Luminosity", color.alpha());
            telemetry.addData("Combined Color Value", color.argb());

            telemetry.update();
        }
    }
}
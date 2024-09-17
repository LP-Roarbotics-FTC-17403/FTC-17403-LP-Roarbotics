//need to fix colors to white, green, purple, and yellow

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorSensorPractice extends LinearOpMode {
    private ColorSensor colorSensor;
    private RevBlinkinLedDriver blinkinLedDriver;

    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");

        waitForStart();

        while (opModeIsActive()) {
            // Step 2: Read color
            int red = colorSensor.red();
            int green = colorSensor.green();
            int blue = colorSensor.blue();

            // Step 3: Determine color
            String detectedColor = getColorName(red, green, blue);

            // Step 4: Control LEDs
            switch (detectedColor) {
                case "Red":
                    blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
                    break;
                case "Blue":
                    blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                    break;
                case "Green":
                    blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                    break;
                default:
                    blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
            }
        }
    }

    private String getColorName(int red, int green, int blue) {
        // Example simple color detection based on thresholds
        if (red > 200 && green < 100 && blue < 100)
            return "Red";
        else if (red < 100 && green > 200 && blue < 100)
            return "Green";
        else if (red < 100 && green < 100 && blue > 200)
            return "Blue";
        else
            return "Unknown";
    }
}

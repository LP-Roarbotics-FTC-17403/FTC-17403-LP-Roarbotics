package org.firstinspires.ftc.teamcode.DecodeFiles;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class CameraSystem {
    private OpenCvCamera camera;
    private AprilTagDetectionPipeline aprilTagDetectionPipeline;

    private static final double FEET_PER_METER = 3.28084;
    private static final double INCH_PER_METER = 39.37;

    private double fx = 965.463;
    private  double fy = 965.463;
    private double cx = 310.036;
    private double cy = 291.828;

    private double tagsize = 4 * 0.0254; //meters
    private int numFramesWithoutDetection = 0;
    private final float DECIMATION_HIGH = 3;
    private final float DECIMATION_LOW = 2;
    private final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
    private final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;

    private double x = 0;
    private double y = 0;

    private double z = 0;
    private double yaw = 0;
    private double pitch =0;
    private double roll = 0;



    public CameraSystem(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("CameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);

    }

    public void cameraOn(){
        camera.openCameraDevice();
        camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
    }

    public void updateDesiredDetection(int id){
        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();
        if (detections != null) {


            // If we don't see any tags
            if (detections.size() == 0) {
                numFramesWithoutDetection++;

                // If we haven't seen a tag for a few frames, lower the decimation
                // so we can hopefully pick one up if we're e.g. far back
                if (numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION) {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
                }
            }
            // We do see tags!
            else {
                numFramesWithoutDetection = 0;

                // If the target is within 1 meter, turn on high decimation to
                // increase the frame rate
                if (detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS) {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
                }

                for (AprilTagDetection detection : detections) {
                    Orientation rot = Orientation.getOrientation(detection.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
                    x = detection.pose.x * INCH_PER_METER;
                    y = detection.pose.y * INCH_PER_METER;
                    z = detection.pose.z * INCH_PER_METER;
                    pitch = rot.secondAngle;
                    yaw = rot.firstAngle;
                    roll = rot.thirdAngle;

                }
            }
        }
    }
    public int getPattern(){
        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();
        if (detections != null) {


            // If we don't see any tags
            if (detections.size() == 0) {
                numFramesWithoutDetection++;

                // If we haven't seen a tag for a few frames, lower the decimation
                // so we can hopefully pick one up if we're e.g. far back
                if (numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION) {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
                }
            }
            // We do see tags!
            else {
                numFramesWithoutDetection = 0;

                // If the target is within 1 meter, turn on high decimation to
                // increase the frame rate
                if (detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS) {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
                }

                for (AprilTagDetection detection : detections) {
                    if(detection.id == 21 || detection.id == 22 || detection.id == 23){
                        return detection.id;
                    }

                }
            }
        }
        return 0;
    }
    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getZ(){
        return z;
    }

    public double getYaw(){
        return yaw;
    }

    public double getPitch(){
        return pitch;
    }

    public double getRoll(){
        return roll;
    }

}

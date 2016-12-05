package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonomousTestEncoder", group="Linear OpMode")
public class AutonomousTestEncoder extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    static final double WHITE_THRESHOLD = 0.2;
    static final boolean bLedOn = true;

    ColorSensor color;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        DcMotor sweeper = null;

        DcMotor strafe = null;

/*        DcMotor scoringMotor = null;
*/
        LightSensor light;

        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        light = hardwareMap.lightSensor.get("light");
        color = hardwareMap.colorSensor.get("sensor_color");

        sweeper = hardwareMap.dcMotor.get("sweeper");

        strafe = hardwareMap.dcMotor.get("strafe");

/*
        scoringMotor = hardwareMap.dcMotor.get("scoring_motor");
*/

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        color.enableLed(true);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        light.enableLed(true);

        Color.RGBToHSV(color.red(), color.green(), color.blue(), hsvValues);
        encoderCalculations(.75, 58.0);
        strafe.setPower(-1);
        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD)) {
            telemetry.addData("Light Level",  light.getLightDetected());
            telemetry.update();
        }
        strafe.setPower(0);
        sleep(1000);
        encoderCalculations(.25, 5.0);
        if(color.blue() > color.green() + 25 && color.blue() > color.red() + 25) {
            encoderCalculations(.5, 50);
        }

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });

        sleep(5000);
        telemetry.addData("LED", bLedOn ? "On" : "Off");
        telemetry.addData("Clear", color.alpha());
        telemetry.addData("Red  ", color.red());
        telemetry.addData("Green", color.green());
        telemetry.addData("Blue ", color.blue());
        telemetry.addData("Hue", hsvValues[0]);
    }

    public void encoderCalculations(double speed, double inches) {
        final int leftFrontTarget;
        final int leftBackTarget;
        final int rightFrontTarget;
        final int rightBackTarget;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");


        if (opModeIsActive()) {

            leftFrontTarget = leftMotorFront.getCurrentPosition() + (int) (inches * 45);
            leftBackTarget = leftMotorBack.getCurrentPosition() + (int)(inches * 45);
            rightFrontTarget = rightMotorFront.getCurrentPosition() + (int)(inches * -45);
            rightBackTarget = rightMotorBack.getCurrentPosition() + (int)(inches * -45);

            leftMotorFront.setTargetPosition(leftFrontTarget);
            leftMotorBack.setTargetPosition(leftBackTarget);
            rightMotorFront.setTargetPosition(rightFrontTarget);
            rightMotorBack.setTargetPosition(rightBackTarget);

            leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorFront.setPower(speed);
            leftMotorBack.setPower(speed);
            rightMotorFront.setPower(-speed);
            rightMotorBack.setPower(-speed);

            while(opModeIsActive() && leftMotorFront.isBusy() && leftMotorBack.isBusy() && rightMotorFront.isBusy() && rightMotorBack.isBusy()) {
                telemetry.addData("Encoders active: ", leftMotorFront.isBusy());
                telemetry.update();
            }

            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);

            leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}

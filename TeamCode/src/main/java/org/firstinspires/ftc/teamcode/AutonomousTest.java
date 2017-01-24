package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BallnPark", group="Linear OpMode")
public class AutonomousTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    static final double WHITE_THRESHOLD = 0.2;

    @Override
    public void runOpMode() {

        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);

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
        ColorSensor colorSensor;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        light = hardwareMap.lightSensor.get("light");
        colorSensor = hardwareMap.colorSensor.get("color");

/*        sweeper = hardwareMap.dcMotor.get("sweeper");*/

        strafe = hardwareMap.dcMotor.get("strafe");

/*
        scoringMotor = hardwareMap.dcMotor.get("scoring_motor");
*/
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        strafe.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        strafe.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        light.enableLed(false);

        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        telemetry.addData("Clear", colorSensor.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue ", colorSensor.blue());
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.update();
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });

        //Move forward
        encoderCalculations(.25, 60.0);

        sleep(5000);
    }

    public void encoderCalculations(double speed, double inches) {
        int leftFrontTarget;
        int leftBackTarget;
        int rightFrontTarget;
        int rightBackTarget;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");


        if (opModeIsActive()) {

            leftFrontTarget = leftMotorFront.getCurrentPosition() + (int) (inches * 56);
            leftBackTarget = leftMotorBack.getCurrentPosition() + (int)(inches * 56);
            rightFrontTarget = rightMotorFront.getCurrentPosition() + (int)(inches * -56);
            rightBackTarget = rightMotorBack.getCurrentPosition() + (int)(inches * -56);

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

    public void strafeCalculations(double speed, double inches) {
        final int strafeTarget;

        DcMotor strafe = null;

        strafe = hardwareMap.dcMotor.get("strafe");

        if(opModeIsActive()) {
            strafeTarget = strafe.getCurrentPosition() + (int) (inches * 45);

            strafe.setTargetPosition(strafeTarget);

            strafe.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            strafe.setPower(speed);

            while(opModeIsActive() && strafe.isBusy()) {
                telemetry.addData("Encoder active: ", strafe.isBusy());
                telemetry.update();
            }
        }
        strafe.setPower(0);

        strafe.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turningCalculations(double speed, double degrees) {
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

            leftFrontTarget = leftMotorFront.getCurrentPosition() + (int) (degrees * 7);
            leftBackTarget = leftMotorBack.getCurrentPosition() + (int)(degrees * -7);
            rightFrontTarget = rightMotorFront.getCurrentPosition() + (int)(degrees * 7);
            rightBackTarget = rightMotorBack.getCurrentPosition() + (int)(degrees * -7);

            leftMotorFront.setTargetPosition(leftFrontTarget);
            leftMotorBack.setTargetPosition(leftBackTarget);
            rightMotorFront.setTargetPosition(rightFrontTarget);
            rightMotorBack.setTargetPosition(rightBackTarget);

            leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorFront.setPower(speed);
            leftMotorBack.setPower(-speed);
            rightMotorFront.setPower(speed);
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

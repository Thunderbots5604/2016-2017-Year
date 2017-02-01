package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="RedBeacons", group="Autonomous")
public class RedBeacons extends LinearOpMode {

    DcMotor leftMotorFront = null;
    DcMotor leftMotorBack = null;

    DcMotor rightMotorFront = null;
    DcMotor rightMotorBack = null;

    LightSensor light = null;
    LightSensor lightSensor2;
    ColorSensor color = null;
    UltrasonicSensor ultra = null;

    static final double     WHITE_THRESHOLD = 0.3;

    @Override
    public void runOpMode() {

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");
        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        light = hardwareMap.lightSensor.get("light");
        lightSensor2 = hardwareMap.lightSensor.get("light");
        color = hardwareMap.colorSensor.get("color");
        ultra = hardwareMap.ultrasonicSensor.get("ultra");

        light.enableLed(true);
        color.enableLed(false);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

/*        leftMotorFront.setPower(-.5);
        leftMotorBack.setPower(-.5);
        rightMotorFront.setPower(.5);
        rightMotorBack.setPower(.5);

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level",  light.getLightDetected());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);

        //Turning until line

        leftMotorFront.setPower(-.35);
        leftMotorBack.setPower(-.35);
        rightMotorFront.setPower(-.35);
        rightMotorBack.setPower(-.35);

        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD) && (lightSensor2.getLightDetected() < WHITE_THRESHOLD)) {
            telemetry.addLine("Aligning with line");
            telemetry.addData("Light Sensor 1: ", light.getLightDetected());
            telemetry.addData("Light Sensor 2: ", lightSensor2.getLightDetected());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(500);*/
        //Movee up to the Beacon
        while (opModeIsActive() && (ultra.getUltrasonicLevel() > 9.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(-.1);
            leftMotorBack.setPower(-.1);
            rightMotorFront.setPower(.1);
            rightMotorBack.setPower(.1);
            telemetry.addData("Ultrasanic level",  ultra.getUltrasonicLevel());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(2000);

        if (color.red() > color.blue() + 3) {
            telemetry.addLine("Right side is red");
            telemetry.update();
        }
        else if (color.red() < color.blue() + 3) {
            telemetry.addLine("Left side is red");
            telemetry.update();
        }
        else {
            telemetry.addLine("Rip nvm");
            telemetry.update();
        }

        sleep(5000);
/*
        //Moving Back
        while (opModeIsActive() && (ultra.getUltrasonicLevel() < 50.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(.1);
            leftMotorBack.setPower(.1);
            rightMotorFront.setPower(-.1);
            rightMotorBack.setPower(-.1);
            telemetry.addData("Ultrasanic level",  ultra.getUltrasonicLevel());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        //Turning Method

        sleep(500);

        //Move until line

        leftMotorFront.setPower(1);
        leftMotorBack.setPower(1);
        rightMotorFront.setPower(-1);
        rightMotorBack.setPower(-1);

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level",  light.getLightDetected());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);

        //Turning until white line

                leftMotorFront.setPower(-.35);
        leftMotorBack.setPower(-.35);
        rightMotorFront.setPower(-.35);
        rightMotorBack.setPower(-.35);

        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD) && (lightSensor2.getLightDetected() < WHITE_THRESHOLD)) {
            telemetry.addLine("Aligning with line");
            telemetry.addData("Light Sensor 1: ", light.getLightDetected());
            telemetry.addData("Light Sensor 2: ", lightSensor2.getLightDetected());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(500);

       //Movee up to the beacon
        while (opModeIsActive() && (ultra.getUltrasonicLevel() > 9.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(-.1);
            leftMotorBack.setPower(-.1);
            rightMotorFront.setPower(.1);
            rightMotorBack.setPower(.1);
            telemetry.addData("Ultrasanic level",  ultra.getUltrasonicLevel());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(2000);

        if (color.red() > color.blue() + 3) {
            telemetry.addLine("Right side is red");
            telemetry.update();
        }
        else if (color.red() < color.blue() + 3) {
            telemetry.addLine("Left side is red");
            telemetry.update();
        }
        else {
            telemetry.addLine("Rip nvm");
            telemetry.update();
        }*/
    }

    public void encoderDrive(double speed,
                             double inches) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftMotorFront.getCurrentPosition() + (int)(inches * 47);
            newRightTarget = rightMotorFront.getCurrentPosition() + (int)(inches * 47);
            leftMotorBack.setTargetPosition(newLeftTarget);
            rightMotorBack.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorBack.setPower(Math.abs(speed));
            rightMotorBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (leftMotorBack.isBusy() && rightMotorBack.isBusy())) {

                leftMotorFront.setPower(leftMotorBack.getPower());
                rightMotorFront.setPower(rightMotorBack.getPower());

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotorBack.getCurrentPosition(),
                        rightMotorBack.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftMotorBack.setPower(0);
            leftMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            rightMotorFront.setPower(0);

            //  sleep(250);   // optional pause after each move
        }
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BlueBeacons", group="Autonomous")
public class BlueBeacons extends LinearOpMode {

    private ElapsedTime     runtime = new ElapsedTime();

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
        lightSensor2 = hardwareMap.lightSensor.get("sensor_light");
        color = hardwareMap.colorSensor.get("color");
        ultra = hardwareMap.ultrasonicSensor.get("ultra");

        light.enableLed(true);
        lightSensor2.enableLed(true);
        color.enableLed(false);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        encoderDrive(.5, 25);
        sleep(5000);
        encoderDrive(-.5, -25);
        sleep(5000);
        turnDrive(.75, 90);
        sleep(5000);
        turnDrive(-.75, 90);

/*        leftMotorFront.setPower(-.2);
        leftMotorBack.setPower(-.2);
        rightMotorFront.setPower(.2);
        rightMotorBack.setPower(.2);

        // run until the white line is seen OR the driver presses STOP;*/
/*        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level",  light.getLightDetected());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(-.17);
        leftMotorBack.setPower(-.17);
        rightMotorFront.setPower(.17);
        rightMotorBack.setPower(.17);

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() && (lightSensor2.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level",  lightSensor2.getLightDetected());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);

        //Turning until line

*//*        leftMotorFront.setPower(-.65);
        leftMotorBack.setPower(-.65);
        rightMotorFront.setPower(-.65);
        rightMotorBack.setPower(-.65);

*//**//*        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD) && (lightSensor2.getLightDetected() < WHITE_THRESHOLD)) {
            telemetry.addLine("Aligning with line");
            telemetry.addData("Light Sensor 1: ", light.getLightDetected());
            telemetry.addData("Light Sensor 2: ", lightSensor2.getLightDetected());
            telemetry.update();
        }*//**//*
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .9)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);*//*

        turnDrive(.75, 45);

        sleep(500);
        //Movee up to the Beacon
        while (opModeIsActive() && (ultra.getUltrasonicLevel() > 11.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(-.3 * ultra.getUltrasonicLevel()/30);
            leftMotorBack.setPower(-.3 * ultra.getUltrasonicLevel()/30);
            rightMotorFront.setPower(.3 * ultra.getUltrasonicLevel()/30);
            rightMotorBack.setPower(.3 * ultra.getUltrasonicLevel()/30);
            telemetry.addData("Ultrasanic level",  ultra.getUltrasonicLevel());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);

        if (color.red() > color.blue() + 3) {
            telemetry.addLine("Right Red");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            turnDrive(.75, 15);
        }
        else if (color.red() < color.blue() + 3) {
            telemetry.addLine("Left Red");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            turnDrive(-.75, -15);
        }
        else {
            telemetry.addLine("Rip nvm");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
        }

        sleep(1000);

        //Moving Back
        while (opModeIsActive() && (ultra.getUltrasonicLevel() < 25.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(.2);
            leftMotorBack.setPower(.2);
            rightMotorFront.setPower(-.2);
            rightMotorBack.setPower(-.2);
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
        turnDrive(-.75, -90);

        leftMotorFront.setPower(-.2);
        leftMotorBack.setPower(-.2);
        rightMotorFront.setPower(.2);
        rightMotorBack.setPower(.2);

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level",  light.getLightDetected());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(-.17);
        leftMotorBack.setPower(-.17);
        rightMotorFront.setPower(.17);
        rightMotorBack.setPower(.17);

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() && (lightSensor2.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level",  lightSensor2.getLightDetected());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);

        //Turning until line

        turnDrive(.75,90);

        leftMotorFront.setPower(-.65);
        leftMotorBack.setPower(-.65);
        rightMotorFront.setPower(-.65);
        rightMotorBack.setPower(-.65);

        sleep(500);
        //Movee up to the Beacon
        while (opModeIsActive() && (ultra.getUltrasonicLevel() > 11.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(-.3 * ultra.getUltrasonicLevel()/30);
            leftMotorBack.setPower(-.3 * ultra.getUltrasonicLevel()/30);
            rightMotorFront.setPower(.3 * ultra.getUltrasonicLevel()/30);
            rightMotorBack.setPower(.3 * ultra.getUltrasonicLevel()/30);
            telemetry.addData("Ultrasanic level",  ultra.getUltrasonicLevel());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);

        if (color.red() > color.blue() + 3) {
            telemetry.addLine("Right Red");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            turnDrive(.75, 15);
        }
        else if (color.red() < color.blue() + 3) {
            telemetry.addLine("Left Red");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            turnDrive(-.75, -15);
        }
        else {
            telemetry.addLine("Rip nvm");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
        }

        while (opModeIsActive() && (ultra.getUltrasonicLevel() < 25.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(.2);
            leftMotorBack.setPower(.2);
            rightMotorFront.setPower(-.2);
            rightMotorBack.setPower(-.2);
            telemetry.addData("Ultrasanic level",  ultra.getUltrasonicLevel());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        turnDrive(.75, 135);

        encoderDrive(.5, 55);*/
    }

    public void encoderDrive(double speed,
                             double inches) {
        int newLeftTarget;
        int newRightTarget;

        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(opModeIsActive()) {
            newLeftTarget = leftMotorBack.getCurrentPosition() + (int)(inches * 45);
            newRightTarget = rightMotorBack.getCurrentPosition() - (int)(inches * 45);

            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorBack.setTargetPosition(newLeftTarget);
            rightMotorBack.setTargetPosition(newRightTarget);

            leftMotorBack.setPower(Math.abs(speed));
            rightMotorBack.setPower(Math.abs(speed));

            while (leftMotorBack.isBusy() && rightMotorBack.isBusy()) {
                leftMotorFront.setPower(leftMotorBack.getPower());
                rightMotorFront.setPower(rightMotorBack.getPower());

                telemetry.addData("Left Mode: ", leftMotorBack.getMode());
                telemetry.addData("Right Mode: ", rightMotorBack.getMode());

                telemetry.addData("Left Moving to: ", leftMotorBack.getTargetPosition());
                telemetry.addData("Right Moving to: ", rightMotorBack.getTargetPosition());

                telemetry.addData("Left at: ", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right at: ", rightMotorBack.getCurrentPosition());
                telemetry.update();
            }

            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);

            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }
    public void turnDrive(double speed,
                             double degrees) {
        int newLeftTarget;
        int newRightTarget;

        boolean ran = false;

        newLeftTarget = leftMotorBack.getCurrentPosition() + (int)(degrees * 3);
        newRightTarget = rightMotorBack.getCurrentPosition() + (int)(degrees * -3);


        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller

            if (ran ==false && opModeIsActive() && leftMotorBack.getCurrentPosition() < newLeftTarget && rightMotorBack.getCurrentPosition() < newRightTarget) {
                leftMotorBack.setPower(speed);
                leftMotorFront.setPower(speed);

                rightMotorBack.setPower(speed);
                rightMotorFront.setPower(speed);

                while(leftMotorBack.getCurrentPosition() != newLeftTarget && rightMotorBack.getCurrentPosition() != newRightTarget) {
                    telemetry.addData("Left Moving to: ", leftMotorBack.getTargetPosition());
                    telemetry.addData("Right Moving to: ", rightMotorBack.getTargetPosition());

                    telemetry.addData("Left at: ", leftMotorBack.getCurrentPosition());
                    telemetry.addData("Right at: ", rightMotorBack.getCurrentPosition());

                    telemetry.addData("Left front: ", leftMotorFront.getCurrentPosition());
                    telemetry.update();
                }

                leftMotorBack.setPower(0);
                leftMotorFront.setPower(0);

                rightMotorBack.setPower(0);
                rightMotorFront.setPower(0);

                ran = true;
            }

            else if (ran ==false && opModeIsActive() && leftMotorBack.getCurrentPosition() < newLeftTarget && rightMotorBack.getCurrentPosition() > newRightTarget) {
                leftMotorBack.setPower(speed);
                leftMotorFront.setPower(speed);

                rightMotorBack.setPower(-speed);
                rightMotorFront.setPower(-speed);

                while(leftMotorBack.getCurrentPosition() != newLeftTarget && rightMotorBack.getCurrentPosition() != newRightTarget) {
                    telemetry.addData("Left Moving to: ", leftMotorBack.getTargetPosition());
                    telemetry.addData("Right Moving to: ", rightMotorBack.getTargetPosition());

                    telemetry.addData("Left at: ", leftMotorBack.getCurrentPosition());
                    telemetry.addData("Right at: ", rightMotorBack.getCurrentPosition());

                    telemetry.addData("Left front: ", leftMotorFront.getCurrentPosition());
                    telemetry.update();
                }

                leftMotorBack.setPower(0);
                leftMotorFront.setPower(0);

                rightMotorBack.setPower(0);
                rightMotorFront.setPower(0);

                ran = true;
            }

            else if (ran ==false && opModeIsActive() && leftMotorBack.getCurrentPosition() > newLeftTarget && rightMotorBack.getCurrentPosition() < newRightTarget) {
                leftMotorBack.setPower(-speed);
                leftMotorFront.setPower(-speed);

                rightMotorBack.setPower(speed);
                rightMotorFront.setPower(speed);

                while (leftMotorBack.getCurrentPosition() != newLeftTarget && rightMotorBack.getCurrentPosition() != newRightTarget) {
                    telemetry.addData("Left Moving to: ", leftMotorBack.getTargetPosition());
                    telemetry.addData("Right Moving to: ", rightMotorBack.getTargetPosition());

                    telemetry.addData("Left at: ", leftMotorBack.getCurrentPosition());
                    telemetry.addData("Right at: ", rightMotorBack.getCurrentPosition());
                    telemetry.addData("Left front: ", leftMotorFront.getCurrentPosition());
                    telemetry.update();
                }

                leftMotorBack.setPower(0);
                leftMotorFront.setPower(0);

                rightMotorBack.setPower(0);
                rightMotorFront.setPower(0);

                ran = true;
            }

            else if (ran ==false && opModeIsActive() && leftMotorBack.getCurrentPosition() > newLeftTarget && rightMotorBack.getCurrentPosition() > newRightTarget) {
                leftMotorBack.setPower(-speed);
                leftMotorFront.setPower(-speed);

                rightMotorBack.setPower(-speed);
                rightMotorFront.setPower(-speed);

                while(leftMotorBack.getCurrentPosition() != newLeftTarget && rightMotorBack.getCurrentPosition() != newRightTarget) {
                    telemetry.addData("Left Moving to: ", leftMotorBack.getTargetPosition());
                    telemetry.addData("Right Moving to: ", rightMotorBack.getTargetPosition());

                    telemetry.addData("Left at: ", leftMotorBack.getCurrentPosition());
                    telemetry.addData("Right at: ", rightMotorBack.getCurrentPosition());
                    telemetry.update();
                }

                leftMotorBack.setPower(0);
                leftMotorFront.setPower(0);

                rightMotorBack.setPower(0);
                rightMotorFront.setPower(0);

                ran = true;
            }
        }
    }
}

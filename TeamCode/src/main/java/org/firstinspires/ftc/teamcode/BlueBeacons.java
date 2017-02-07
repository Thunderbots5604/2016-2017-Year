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

        leftMotorFront.setPower(-.65);
        leftMotorBack.setPower(-.65);
        rightMotorFront.setPower(-.65);
        rightMotorBack.setPower(-.65);

/*        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD) && (lightSensor2.getLightDetected() < WHITE_THRESHOLD)) {
            telemetry.addLine("Aligning with line");
            telemetry.addData("Light Sensor 1: ", light.getLightDetected());
            telemetry.addData("Light Sensor 2: ", lightSensor2.getLightDetected());
            telemetry.update();
        }*/
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .9)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

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
            leftMotorFront.setPower(-.85);
            leftMotorBack.setPower(-.85);
            rightMotorFront.setPower(-.85);
            rightMotorBack.setPower(-.85);
            sleep(1000);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 0.55)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
        }
        else if (color.red() < color.blue() + 3) {
            telemetry.addLine("Left Red");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            leftMotorFront.setPower(.85);
            leftMotorBack.setPower(.85);
            rightMotorFront.setPower(.85);
            rightMotorBack.setPower(.85);
            sleep(1000);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 0.55)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
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

        leftMotorFront.setPower(.25);
        leftMotorBack.setPower(.25);
        rightMotorFront.setPower(-.25);
        rightMotorBack.setPower(-.25);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        //Turning Method
/*
        leftMotorFront.setPower(.65);
        leftMotorBack.setPower(.65);
        rightMotorFront.setPower(.65);
        rightMotorBack.setPower(.65);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.6)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);


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

        leftMotorFront.setPower(-.65);
        leftMotorBack.setPower(-.65);
        rightMotorFront.setPower(-.65);
        rightMotorBack.setPower(-.65);

*/
/*        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD) && (lightSensor2.getLightDetected() < WHITE_THRESHOLD)) {
            telemetry.addLine("Aligning with line");
            telemetry.addData("Light Sensor 1: ", light.getLightDetected());
            telemetry.addData("Light Sensor 2: ", lightSensor2.getLightDetected());
            telemetry.update();
        }*//*

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.65)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

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
            telemetry.addLine("Left Blue");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            leftMotorFront.setPower(-.85);
            leftMotorBack.setPower(-.85);
            rightMotorFront.setPower(-.85);
            rightMotorBack.setPower(-.85);
            sleep(1000);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 0.55)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
        }
        else if (color.red() < color.blue() + 3) {
            telemetry.addLine("Right Blue");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            leftMotorFront.setPower(.85);
            leftMotorBack.setPower(.85);
            rightMotorFront.setPower(.85);
            rightMotorBack.setPower(.85);
            sleep(1000);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 0.55)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
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

        leftMotorFront.setPower(.65);
        leftMotorBack.setPower(.65);
        rightMotorFront.setPower(.65);
        rightMotorBack.setPower(.65);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.6)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
*/
    }

    public void encoderDrive(double speed,
                             double inches) {
        final int newLeftTarget;
        final int newRightTarget;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");
        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftMotorFront.getCurrentPosition() + (int)(inches * 45);
            newRightTarget = rightMotorFront.getCurrentPosition() + (int)(inches * -45);
            leftMotorBack.setTargetPosition(newLeftTarget);
            rightMotorBack.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorBack.setPower(Math.abs(speed));
            rightMotorBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (leftMotorBack.isBusy() && rightMotorBack.isBusy()) {
                leftMotorFront.setPower(leftMotorBack.getPower());
                rightMotorFront.setPower(rightMotorBack.getPower());

                telemetry.addData("Left Moving to: ", leftMotorBack.getTargetPosition());
                telemetry.addData("Right Moving to: ", rightMotorBack.getTargetPosition());

                telemetry.addData("Left at: ", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right at: ", rightMotorBack.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftMotorBack.setPower(0);
            leftMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            rightMotorFront.setPower(0);

            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
    public void turnDrive(double speed,
                             double degrees) {
        final int newLeftTarget;
        final int newRightTarget;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");
        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftMotorFront.getCurrentPosition() + (int)(degrees * 9);
            newRightTarget = rightMotorFront.getCurrentPosition() + (int)(degrees * 9);

            leftMotorBack.setTargetPosition(newLeftTarget);
            rightMotorBack.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorBack.setPower(speed);
            rightMotorBack.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (leftMotorBack.isBusy() && rightMotorBack.isBusy()) {
                leftMotorFront.setPower(leftMotorBack.getPower());
                rightMotorFront.setPower(rightMotorBack.getPower());

                telemetry.addData("Left Moving to: ", leftMotorBack.getTargetPosition());
                telemetry.addData("Right Moving to: ", rightMotorBack.getTargetPosition());

                telemetry.addData("Left at: ", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right at: ", rightMotorBack.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftMotorBack.setPower(0);
            leftMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            rightMotorFront.setPower(0);

            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
}

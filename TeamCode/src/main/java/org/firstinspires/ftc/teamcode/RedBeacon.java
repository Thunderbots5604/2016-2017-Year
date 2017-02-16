package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="RedBeacons", group="Autonomous")
public class RedBeacon extends LinearOpMode {

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

        //move back after second light sensor hit white
        encoderDrive(-.6, 2);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        sleep(1000);
        //turnDrive(.5,45);

        //Turning until line

        leftMotorFront.setPower(.4);
        leftMotorBack.setPower(.4);
        rightMotorFront.setPower(.4);
        rightMotorBack.setPower(.4);

        while (opModeIsActive() && (light.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level",  light.getLightDetected());
            telemetry.addData("Left Motor: ", leftMotorBack.getPower());
            telemetry.addData("Right Motor: ", rightMotorBack.getPower());
            telemetry.update();
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        //Movee up to the Beacon
        while (opModeIsActive() && (ultra.getUltrasonicLevel() > 11.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(-.2);
            leftMotorBack.setPower(-.2);
            rightMotorFront.setPower(.2);
            rightMotorBack.setPower(.2);
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
            sleep(1000);
            turnDrive(.75, 15);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else if (color.red() < color.blue() + 3) {
            telemetry.addLine("Right Blue");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            sleep(1000);
            turnDrive(-.75, 15);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else {
            telemetry.addLine("Rip nvm");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            sleep(1000);
        }

        sleep(1000);

        //Moving Back
        while (opModeIsActive() && (ultra.getUltrasonicLevel() < 30.0)) {

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
        turnDrive(-.75, 90);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        encoderDrive(.4, 33);


        //Commented out code for the second beacon
        /*leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

        turnDrive(-.75,-90);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        sleep(1000);
        //Movee up to the Beacon
        while (opModeIsActive() && (ultra.getUltrasonicLevel() > 11.0)) {

            // Display the light level while we are looking for the line
            leftMotorFront.setPower(-.3 * ultra.getUltrasonicLevel()/20);
            leftMotorBack.setPower(-.3 * ultra.getUltrasonicLevel()/20);
            rightMotorFront.setPower(.3 * ultra.getUltrasonicLevel()/20);
            rightMotorBack.setPower(.3 * ultra.getUltrasonicLevel()/20);
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
            sleep(1000);
            turnDrive(-.75, -15);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else if (color.red() < color.blue() + 3) {
            telemetry.addLine("Left Red");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            sleep(1000);
            turnDrive(.75, 15);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else {
            telemetry.addLine("Rip nvm");
            telemetry.addData("Red: ", color.red());
            telemetry.addData("Blue: ", color.blue());
            telemetry.update();
            sleep(1000);
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

        turnDrive(-.75, -135);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        encoderDrive(-.5, -55);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);*/
    }

    public void encoderDrive(double speed, double inches) {

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            int newLeftFrontTarget = leftMotorFront.getCurrentPosition() + (int)(inches * 45);
            int newLeftBackTarget = leftMotorFront.getCurrentPosition() + (int)(inches * 45);
            int newRightFrontTarget = rightMotorFront.getCurrentPosition() + (int)(inches * -45);
            int newRightBackTarget = rightMotorFront.getCurrentPosition() + (int)(inches * -45);
            leftMotorFront.setTargetPosition(newLeftFrontTarget);
            leftMotorBack.setTargetPosition(newLeftBackTarget);
            rightMotorFront.setTargetPosition(newRightFrontTarget);
            rightMotorBack.setTargetPosition(newRightBackTarget);

            // Turn On RUN_TO_POSITION
            leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorFront.setPower(speed);
            leftMotorBack.setPower(speed);
            rightMotorFront.setPower(-speed);
            rightMotorBack.setPower(-speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && leftMotorBack.isBusy() && rightMotorBack.isBusy() && leftMotorFront.isBusy() && rightMotorFront.isBusy()) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftFrontTarget,  newRightBackTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotorBack.getCurrentPosition(),
                        rightMotorBack.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);

            // Turn off RUN_TO_POSITION

            leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void turnDrive(double speed, double degrees) {

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            int newLeftFrontTarget = leftMotorFront.getCurrentPosition() + (int)(degrees * 9);
            int newLeftBackTarget = leftMotorFront.getCurrentPosition() + (int)(degrees * 9);
            int newRightFrontTarget = rightMotorFront.getCurrentPosition() + (int)(degrees * 9);
            int newRightBackTarget = rightMotorFront.getCurrentPosition() + (int)(degrees * 9);
            leftMotorFront.setTargetPosition(newLeftFrontTarget);
            leftMotorBack.setTargetPosition(newLeftBackTarget);
            rightMotorFront.setTargetPosition(newRightFrontTarget);
            rightMotorBack.setTargetPosition(newRightBackTarget);

            // Turn On RUN_TO_POSITION
            leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorFront.setPower(speed);
            leftMotorBack.setPower(speed);
            rightMotorFront.setPower(speed);
            rightMotorBack.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && leftMotorBack.isBusy() && rightMotorBack.isBusy() && leftMotorFront.isBusy() && rightMotorFront.isBusy()) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftFrontTarget,  newRightBackTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotorBack.getCurrentPosition(),
                        rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(speed);
                leftMotorBack.setPower(speed);
                rightMotorFront.setPower(speed);
                rightMotorBack.setPower(speed);
            }

            // Stop all motion;
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);

            // Turn off RUN_TO_POSITION
            leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void pointTurnDrive(double speed, double degrees) {

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            int newLeftFrontTarget = leftMotorFront.getCurrentPosition() + (int)(degrees * 9);
            int newLeftBackTarget = leftMotorFront.getCurrentPosition() + (int)(degrees * 9);
            int newRightFrontTarget = rightMotorFront.getCurrentPosition() + (int)(degrees * 9);
            int newRightBackTarget = rightMotorFront.getCurrentPosition() + (int)(degrees * 9);
            leftMotorFront.setTargetPosition(newLeftFrontTarget);
            leftMotorBack.setTargetPosition(newLeftBackTarget);
            rightMotorFront.setTargetPosition(newRightFrontTarget);
            rightMotorBack.setTargetPosition(newRightBackTarget);

            // Turn On RUN_TO_POSITION
            leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorFront.setPower(speed);
            leftMotorBack.setPower(speed);
            rightMotorFront.setPower(-speed);
            rightMotorBack.setPower(-speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && leftMotorBack.isBusy() && rightMotorBack.isBusy() && leftMotorFront.isBusy() && rightMotorFront.isBusy()) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftFrontTarget,  newRightBackTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotorBack.getCurrentPosition(),
                        rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(speed);
                leftMotorBack.setPower(speed);
                rightMotorFront.setPower(-speed);
                rightMotorBack.setPower(-speed);
            }

            // Stop all motion;
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);

            // Turn off RUN_TO_POSITION
            leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void turnTime(double speed, double degrees) {
        if(opModeIsActive()) {

            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            runtime.reset();

            double timeToRun = (degrees * (1/120))/speed;

            leftMotorFront.setPower(speed);
            leftMotorBack.setPower(speed);
            rightMotorFront.setPower(speed);
            rightMotorBack.setPower(speed);

            while(getRuntime() < timeToRun) {
                telemetry.addLine("Turning");
                telemetry.update();
            }

            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
        }
    }
}

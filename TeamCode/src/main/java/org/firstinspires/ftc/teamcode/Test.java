package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name="BallnParkEncoder", group="CenterVortex")
public class Test extends LinearOpMode {

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

        telemetry.addData("Path0",  "Starting at %7d :%7d",
                leftMotorBack.getCurrentPosition(),
                rightMotorBack.getCurrentPosition());
        telemetry.update();

        encoderDrive(.55, 58);
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
        }
    }
    public void turningDrive(double speed, double degrees) {

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
            }

            // Stop all motion;
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);

            // Turn off RUN_TO_POSITION
        }
    }
}

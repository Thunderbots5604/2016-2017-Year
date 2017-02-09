package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AAAAHHH", group="Autonomous")
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

        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path0",  "Starting at %7d :%7d",
                leftMotorBack.getCurrentPosition(),
                rightMotorBack.getCurrentPosition());
        telemetry.update();

        encoderDrive(.75, 50);
        sleep(5000);
        encoderDrive(-.75, 50);
    }

    public void encoderDrive(double speed, double inches) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftMotorBack.getCurrentPosition() + (int)(inches * 45);
            newRightTarget = rightMotorBack.getCurrentPosition() + (int)(inches * 45);
            leftMotorBack.setTargetPosition(newLeftTarget);
            rightMotorBack.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotorBack.setPower(Math.abs(speed));
            rightMotorBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (leftMotorBack.isBusy() && rightMotorBack.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotorBack.getCurrentPosition(),
                        rightMotorBack.getCurrentPosition());
                telemetry.update();

                leftMotorFront.setPower(leftMotorBack.getPower());
                rightMotorFront.setPower(rightMotorBack.getPower());
            }

            // Stop all motion;
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);

            // Turn off RUN_TO_POSITION
            leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}

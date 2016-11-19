package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="AutonomousTestTime", group="Linear OpMode")
public class AutonomousTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

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

        DcMotor scoringMotor = null;

        Servo leftServo = null;
        Servo rightServo = null;

        Servo leftArm = null;
        Servo rightArm = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        sweeper = hardwareMap.dcMotor.get("sweeper");

        strafe = hardwareMap.dcMotor.get("strafe");

        scoringMotor = hardwareMap.dcMotor.get("scoring_motor");

        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        leftArm = hardwareMap.servo.get("left_arm");
        rightArm = hardwareMap.servo.get("right_arm");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        leftMotorFront.setPower(-.7);
        leftMotorBack.setPower(-.7);
        rightMotorFront.setPower(.7);
        rightMotorBack.setPower(.7);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.1)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    public void timeDrive(double speed, double inches) {
        double inchesPerSecond = speed;

        double time = inches * inchesPerSecond;

        while (opModeIsActive()) {

        }
    }
}

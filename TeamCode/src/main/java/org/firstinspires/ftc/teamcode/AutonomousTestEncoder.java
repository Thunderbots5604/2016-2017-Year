package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonomousTestEncoder", group="Linear OpMode")
public class AutonomousTestEncoder extends LinearOpMode {

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

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        int rightTargetDistance = rightMotorFront.getTargetPosition() - rightMotorFront.getCurrentPosition();
        int leftTargetDistance = leftMotorFront.getTargetPosition() - leftMotorFront.getCurrentPosition();
        telemetry.addData("Target Right Ticks: ", rightMotorFront.getCurrentPosition());
        telemetry.addData("Target Left Tisks: ", leftMotorFront.getCurrentPosition());
        telemetry.addData("Current Right Tisks: ", rightMotorBack.getCurrentPosition());
        telemetry.addData("Current Left Tisks: ", leftMotorBack.getCurrentPosition());
        telemetry.update();

        encoderCalulations(1.0, 58.0);
        sleep(5000);
    }

    public void encoderCalulations(double speed, double inches) {
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

        leftFrontTarget = leftMotorFront.getCurrentPosition() + (int) (inches * 45);
        leftBackTarget = leftMotorBack.getCurrentPosition() + (int)(inches * 45);
        rightFrontTarget = rightMotorFront.getCurrentPosition() + (int)(inches * -45);
        rightBackTarget = rightMotorBack.getCurrentPosition() + (int)(inches * -45);

        if (opModeIsActive()) {

/*            leftMotorFront.setTargetPosition(leftFrontTarget);
            leftMotorBack.setTargetPosition(leftBackTarget);
            rightMotorFront.setTargetPosition(rightFrontTarget);
            rightMotorBack.setTargetPosition(rightBackTarget);*/

/*            leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/

            if (leftMotorFront.getCurrentPosition() < leftFrontTarget && leftMotorBack.getCurrentPosition() < leftBackTarget && rightMotorFront.getCurrentPosition() < rightFrontTarget && rightMotorBack.getCurrentPosition() < rightBackTarget) {
                leftMotorFront.setPower(-speed);
                leftMotorBack.setPower(-speed);
                rightMotorFront.setPower(speed);
                rightMotorBack.setPower(speed);
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
/*
            leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
        }
    }
}

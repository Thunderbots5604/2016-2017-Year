package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOp4Kids", group="Linear Opmode")
public class KidsDrive extends LinearOpMode {

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
        DcMotor cap = null;
        DcMotor scoringMotor = null;

        Servo stopper = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        sweeper = hardwareMap.dcMotor.get("sweeper");
        cap = hardwareMap.dcMotor.get("cap");
        scoringMotor = hardwareMap.dcMotor.get("scoring_motor");
        stopper = hardwareMap.servo.get("stop");

/*        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);*/

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            //Stuff to display for Telemetry

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(-gamepad1.left_stick_y);
                leftMotorBack.setPower(-gamepad1.left_stick_y);

                rightMotorFront.setPower(gamepad1.left_stick_y);
                rightMotorBack.setPower(gamepad1.left_stick_y);
            }
            //Turning methods in TeleOp
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y == 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y == 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }

            //Swing turn methods
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y > 0) {
                leftMotorFront.setPower(-gamepad1.left_stick_y);
                leftMotorBack.setPower(-gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y > 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(gamepad1.left_stick_y);
                rightMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y < 0) {
                leftMotorFront.setPower(-gamepad1.left_stick_y);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(gamepad1.left_stick_y);
                rightMotorBack.setPower(gamepad1.left_stick_y);
            }

            //Sweeper Code
            if(gamepad2.right_trigger > 0) {
                sweeper.setPower(-gamepad2.right_trigger);
            }
            else if(gamepad2.left_trigger > 0) {
                sweeper.setPower(gamepad2.left_trigger);
            }
            else {
                sweeper.setPower(0);
            }

            //Scoring Arm Code
            if(gamepad2.dpad_up) {
                scoringMotor.setPower(1.0);
            }
            else if (gamepad2.dpad_down) {
                scoringMotor.setPower(-1.0);
            }
            else {
                scoringMotor.setPower(0);
            }

            //Caping Mechanism Code

            if(gamepad2.left_bumper) {
                cap.setPower(1);
            }
            else if(gamepad2.right_bumper) {
                cap.setPower(-1);
            }
            else {
                cap.setPower(0);
            }

            if(gamepad2.y) {
                stopper.setPosition(0);
            }
            else if(gamepad2.a) {
                stopper.setPosition(.4);
            }
        }
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOpTest", group="Linear Opmode")  // @Autonomous(...) is the other common choice
public class TeleOpTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        double speed;
        double turningSpeed;
        double rightSpeed;
        double leftSpeed;
        double strafeSpeed;
        boolean halfSpeed = false;
        int newScoringArm;

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

        double leftStarting = 0;
        double rightStarting = 1;

        leftServo.setPosition(leftStarting);
        rightServo.setPosition(rightStarting);

        leftArm.setPosition(rightStarting);
        rightArm.setPosition(leftStarting);

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            //Stuff to display for Telemetry
            rightSpeed = leftMotorFront.getPower();
            leftSpeed = rightMotorFront.getPower();

            strafeSpeed = strafe.getPower();

            double capturingPoint = scoringMotor.getPower();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
/*            telemetry.addData("Half Speed: ", halfSpeed);
            telemetry.addData("Flipper: ", capturingPoint);*/
            telemetry.addData("Right Front Tisks: ", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back Tisks: ", rightMotorBack.getCurrentPosition());
            telemetry.addData("Left Front Tisks: ", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Bac Tisks: ", leftMotorBack.getCurrentPosition());
            telemetry.update();

            //Half Speed Toggle Method
            if(gamepad1.a == true && halfSpeed == false) {
                halfSpeed = true;
            }
            else if (gamepad1.a == true && halfSpeed == true) {
                halfSpeed = false;
            }

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0 && halfSpeed == false) {
                leftMotorFront.setPower(-gamepad1.left_stick_y);
                leftMotorBack.setPower(-gamepad1.left_stick_y);

                rightMotorFront.setPower(gamepad1.left_stick_y);
                rightMotorBack.setPower(gamepad1.left_stick_y);
            }
            //Turning methods in TeleOp
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y == 0 && halfSpeed == false) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y == 0 && halfSpeed == false) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }

            //Swing turn methods
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y < 0 && halfSpeed == false) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y < 0 && halfSpeed == false) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y > 0 && halfSpeed == false) {
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y > 0 && halfSpeed == false) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(gamepad1.right_stick_x);
                rightMotorBack.setPower(gamepad1.right_stick_x);
            }

            //MOVEMENT METHODS FOR HALFSPEED
            else if(gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0 && halfSpeed == true) {
                leftMotorFront.setPower(-gamepad1.left_stick_y * .25);
                leftMotorBack.setPower(-gamepad1.left_stick_y * .25);

                rightMotorFront.setPower(gamepad1.left_stick_y * .25);
                rightMotorBack.setPower(gamepad1.left_stick_y * .25);
            }
            //Turning methods in TeleOp while in Half Speed
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y == 0 && halfSpeed == true) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * .25);
                leftMotorBack.setPower(-gamepad1.right_stick_x * .25);

                rightMotorFront.setPower(-gamepad1.right_stick_x * .25);
                rightMotorBack.setPower(-gamepad1.right_stick_x * .25);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y == 0 && halfSpeed == true) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * .25);
                leftMotorBack.setPower(-gamepad1.right_stick_x * .25);

                rightMotorFront.setPower(-gamepad1.right_stick_x * .25);
                rightMotorBack.setPower(-gamepad1.right_stick_x * .25);
            }
            //Swing turn methods while in Half Speed
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y < 0 && halfSpeed == true) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * .25);
                leftMotorBack.setPower(-gamepad1.right_stick_x * .25);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y < 0 && halfSpeed == true) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.right_stick_x * .25);
                rightMotorBack.setPower(-gamepad1.right_stick_x * .25);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y > 0 && halfSpeed == true) {
                leftMotorFront.setPower(gamepad1.right_stick_x * .25);
                leftMotorBack.setPower(gamepad1.right_stick_x * .25);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y > 0 && halfSpeed == true) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(gamepad1.right_stick_x * .25);
                rightMotorBack.setPower(gamepad1.right_stick_x * .25);
            }
            else  {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }


            //Sweeper Code
            if(gamepad1.right_trigger > 0) {
                sweeper.setPower(-gamepad1.right_trigger);
            }
            else if(gamepad1.left_trigger > 0) {
                sweeper.setPower(gamepad1.left_trigger);
            }
            else {
                sweeper.setPower(0);
            }

            //Strafe Motor Code
            if(gamepad1.right_bumper) {
                strafe.setPower(-1);
            }
            else if (gamepad1.left_bumper) {
                strafe.setPower(1);
            }
            else {
                strafe.setPower(0);
            }

            //Booper Code

            if(gamepad2.left_bumper) {
                leftServo.setPosition(.2);
            }
            if(gamepad2.right_bumper) {
                rightServo.setPosition(.2);
            }
            //Resets to the position the boopers were initialized at.
            else {
                leftServo.setPosition(leftStarting);
                rightServo.setPosition(rightStarting);
            }

            //Releases the holder
            if(gamepad2.y && leftArm.getPosition() != leftStarting && rightArm.getPosition() != rightStarting) {
                leftArm.setPosition(leftStarting);
                rightArm.setPosition(rightStarting);
            }

            if(gamepad2.y && leftArm.getPosition() == leftStarting && rightArm.getPosition() == rightStarting) {
                leftArm.setPosition(rightStarting);
                rightArm.setPosition(leftStarting);
            }

            //Sets the scoring arm to three rotations
/*            if(gamepad2.a) {
                newScoringArm = scoringMotor.getCurrentPosition() + 4320;
                scoringMotor.setTargetPosition(newScoringArm);
                scoringMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                scoringMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                scoringMotor.setPower(0);
            }*/

            //Scoring Arm power set
            if(gamepad2.dpad_up) {
                scoringMotor.setPower(1.0);
            }
            else if (gamepad2.dpad_down) {
                scoringMotor.setPower(-1.0);
            }
            else {
                scoringMotor.setPower(0);
            }
        }
    }
}
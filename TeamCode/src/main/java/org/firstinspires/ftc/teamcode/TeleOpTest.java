package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math.*;


@TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOpTest extends LinearOpMode {

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

        DcMotor cap1 = null;
        DcMotor cap2 = null;

        DcMotor scoringMotor = null;

        OpticalDistanceSensor ods;

        UltrasonicSensor ultra;

/*        CRServo rightSpin = null;
        CRServo leftSpin = null;*/

        Servo stopper1 = null;
        Servo stopper2 = null;

        TouchSensor touch;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        sweeper = hardwareMap.dcMotor.get("sweeper");

        cap1 = hardwareMap.dcMotor.get("cap1");
        cap2 = hardwareMap.dcMotor.get("cap2");

        scoringMotor = hardwareMap.dcMotor.get("scoring_motor");

        ods = hardwareMap.opticalDistanceSensor.get("ods");

/*        rightSpin = hardwareMap.crservo.get("right");
        leftSpin = hardwareMap.crservo.get("left");*/

        stopper1 = hardwareMap.servo.get("stop1");
        stopper2 = hardwareMap.servo.get("stop2");

        ultra = hardwareMap.ultrasonicSensor.get("ultra");

        touch = hardwareMap.touchSensor.get("touch");

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
            telemetry.addData("Ultrasonic: ", ultra.getUltrasonicLevel());
            telemetry.addData("Left Motor Back:  ", leftMotorBack.getPower());
            telemetry.addData("Left Motor Front: ", leftMotorFront.getPower());
            telemetry.addData("Right Motor Back: ", rightMotorBack.getPower());
            telemetry.addData("Right Motor Front: ", rightMotorFront.getPower());
/*            telemetry.addData("Half Speed: ", halfSpeed);*/
/*            telemetry.addData("Right Front Tisks: ", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back Tisks: ", rightMotorBack.getCurrentPosition());
            telemetry.addData("Left Front Tisks: ", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Bac Tisks: ", leftMotorBack.getCurrentPosition());*/
            telemetry.addData("Servo 1: ", stopper1.getPosition());
            telemetry.addData("Servo 2: ", stopper2.getPosition());
            telemetry.addData("Is flipper at the end: ", touch.isPressed());
            telemetry.update();

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(-gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y) * Math.abs(gamepad1.left_stick_y));
                leftMotorBack.setPower(-gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y) * Math.abs(gamepad1.left_stick_y));

                rightMotorFront.setPower(gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y) * Math.abs(gamepad1.left_stick_y));
                rightMotorBack.setPower(gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y) * Math.abs(gamepad1.left_stick_y));
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
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);

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
            else if (gamepad2.dpad_down && touch.isPressed() == false) {
/*                while (touch.isPressed() == false) {
                    scoringMotor.setPower(-1.0);
                }*/
                scoringMotor.setPower(-1.0);
            }
            else if(gamepad1.dpad_up) {
                scoringMotor.setPower(1.0);
            }
            else if (gamepad1.dpad_down && touch.isPressed() == false) {
/*                while (touch.isPressed() == false) {
                    scoringMotor.setPower(-1.0);
                }*/
                scoringMotor.setPower(-1.0);
            }
            else {
                scoringMotor.setPower(0);
            }

            //Experimental flipper encoder code
/*

            if(gamepad2.dpad_right) {
                final int fire;

                fire = scoringMotor.getCurrentPosition() - 2;

                scoringMotor.setTargetPosition(fire);

                scoringMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                scoringMotor.setPower(-1.0);

                while(opModeIsActive() && scoringMotor.isBusy()) {
                    telemetry.addData("Firing: ", scoringMotor.isBusy());
                    telemetry.update();
                }

                scoringMotor.setPower(0);

                scoringMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                sleep(1000);

                final int reload;

                reload = 0;

                scoringMotor.setTargetPosition(reload);

                scoringMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                scoringMotor.setPower(1.0);

                while(opModeIsActive() && scoringMotor.isBusy()) {
                    telemetry.addData("Reloading: ", scoringMotor.isBusy());
                    telemetry.update();
                }

                scoringMotor.setPower(0);

                scoringMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                scoringMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
*/

            //Caping Mechanism Code

            if(gamepad2.left_bumper) {
                cap1.setPower(1);
                cap2.setPower(-1);
            }
            else if(gamepad2.right_bumper) {
                cap1.setPower(-1);
                cap2.setPower(1);
            }
            else {
                cap1.setPower(0);
                cap2.setPower(0);
            }

            //CRServo Method

/*            if(gamepad2.right_bumper) {
                rightSpin.setPower(1);
                leftSpin.setPower(-1);
            }
            else if (gamepad2.left_bumper) {
                rightSpin.setPower(-1);
                leftSpin.setPower(1);
            }
            else {
                rightSpin.setPower(0);
                leftSpin.setPower(0);
            }*/

            //Servo

            //.015

            if(gamepad2.b) {
                stopper1.setPosition(.3);
            }
            else if(gamepad2.a) {
                stopper2.setPosition(.75);
            }
/*            else if(ods.getLightDetected() > .015) {
                stopper2.setPosition(.75);
                stopper1.setPosition(.3);
                sleep(100);
                stopper1.setPosition(.75);
            }*/
            else {
                stopper1.setPosition(.75);
                stopper2.setPosition(.3);
            }
        }
    }
}
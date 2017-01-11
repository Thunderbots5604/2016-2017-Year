package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOpTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        double rightSpeed;
        double leftSpeed;
        double strafeSpeed;
        boolean halfSpeed = false;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        DcMotor sweeper = null;

        DcMotor strafe = null;

/*        DcMotor capMechanism = null;*/

        DcMotor scoringMotor = null;

/*        CRServo rightSpin = null;
        CRServo leftSpin = null;*/

        Servo stopper = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        sweeper = hardwareMap.dcMotor.get("sweeper");

        strafe = hardwareMap.dcMotor.get("strafe");

/*        capMechanism = hardwareMap.dcMotor.get("cap");*/

        scoringMotor = hardwareMap.dcMotor.get("scoring_motor");

/*        rightSpin = hardwareMap.crservo.get("right");
        leftSpin = hardwareMap.crservo.get("left");*/

        stopper = hardwareMap.servo.get("stop");

        double leftStarting = 0;
        double rightStarting = 1;

/*        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);*/

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            //Stuff to display for Telemetry
            rightSpeed = leftMotorFront.getPower();
            leftSpeed = rightMotorFront.getPower();

            strafeSpeed = strafe.getPower();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Half Speed: ", halfSpeed);
/*            telemetry.addData("Right Front Tisks: ", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back Tisks: ", rightMotorBack.getCurrentPosition());
            telemetry.addData("Left Front Tisks: ", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Bac Tisks: ", leftMotorBack.getCurrentPosition());
            telemetry.addData("Strafe tisks: ", strafe.getCurrentPosition());*/
            telemetry.addData("Servo Position: ", stopper.getPosition());
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
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y < 0 && halfSpeed == false) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(gamepad1.right_stick_x);
                rightMotorBack.setPower(gamepad1.right_stick_x);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y > 0 && halfSpeed == false) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y > 0 && halfSpeed == false) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
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
                leftMotorFront.setPower(-gamepad1.right_stick_x * .5);
                leftMotorBack.setPower(-gamepad1.right_stick_x * .5);

                rightMotorFront.setPower(-gamepad1.right_stick_x * .5);
                rightMotorBack.setPower(-gamepad1.right_stick_x * .5);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y == 0 && halfSpeed == true) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * .5);
                leftMotorBack.setPower(-gamepad1.right_stick_x * .5);

                rightMotorFront.setPower(-gamepad1.right_stick_x * .5);
                rightMotorBack.setPower(-gamepad1.right_stick_x * .5);
            }
            //Swing turn methods while in Half Speed
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y < 0 && halfSpeed == true) {
                leftMotorFront.setPower(gamepad1.right_stick_x * .5);
                leftMotorBack.setPower(gamepad1.right_stick_x * .5);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y < 0 && halfSpeed == true) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(gamepad1.right_stick_x * .5);
                rightMotorBack.setPower(gamepad1.right_stick_x * .5);
            }
            else if(gamepad1.right_stick_x < 0 && gamepad1.left_stick_y > 0 && halfSpeed == true) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * .5);
                leftMotorBack.setPower(-gamepad1.right_stick_x * .5);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0 && gamepad1.left_stick_y > 0 && halfSpeed == true) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.right_stick_x * .5);
                rightMotorBack.setPower(-gamepad1.right_stick_x * .5);
            }
            else  {
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

            //Strafe Motor Code
            if (gamepad1.right_bumper) {
                strafe.setPower(-1);
            }
            else if (gamepad1.left_bumper) {
                strafe.setPower(1);
            }
            else if (gamepad1.left_stick_x < -.5) {
                strafe.setPower(1);
            }
            else if (gamepad1.left_stick_x > .5) {
                strafe.setPower(-1);
            }
            else {
                strafe.setPower(0);
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

/*            if(gamepad2.y) {
                capMechanism.setPower(1);
            }
            else if(gamepad2.a) {
                capMechanism.setPower(-1);
            }
            else {
                capMechanism.setPower(0);
            }*/

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

            if(gamepad2.b) {
                stopper.setPosition(0);
            }
            else if(gamepad2.a) {
                stopper.setPosition(.4);
            }
        }
    }
}
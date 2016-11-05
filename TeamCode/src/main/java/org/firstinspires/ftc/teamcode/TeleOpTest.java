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
        boolean halfSpeedTrigger;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

/*        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);

        rightMotorFront.setDirection(DcMotor.Direction.FORWARD);
        rightMotorBack.setDirection(DcMotor.Direction.FORWARD);*/

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            /*telemetry.addData("Status", "Run Time: " + runtime.toString());*/

            speed = 0;
            turningSpeed = 0;
            halfSpeedTrigger = false;

            telemetry.addData("Speed: ", speed);
            telemetry.addData("Turning Speed: ", turningSpeed);
            telemetry.update();

            //A button triggers Half Speed
            if(gamepad1.a && halfSpeedTrigger == false){
                halfSpeedTrigger = true;
            }
            if(gamepad1.a && halfSpeedTrigger) {
                halfSpeedTrigger = false;
            }
            if(halfSpeedTrigger == false) {
                speed = gamepad1.left_stick_y;
                turningSpeed = gamepad1.right_stick_x;
            }
            else if(halfSpeedTrigger) {
                speed = gamepad1.left_stick_y * .1;
                turningSpeed = gamepad1.right_stick_x * .1;
            }

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0) {
                leftMotorFront.setPower(-speed);
                leftMotorBack.setPower(-speed);

                rightMotorFront.setPower(speed);
                rightMotorBack.setPower(speed);
            }

            //Turning methods in TeleOp
            else if(gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(turningSpeed);
                leftMotorBack.setPower(turningSpeed);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if(gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(turningSpeed);
                rightMotorBack.setPower(turningSpeed);
            }
            else  {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
        }
    }
}
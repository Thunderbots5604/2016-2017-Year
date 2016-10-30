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
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0) {
                leftMotorFront.setPower(-gamepad1.left_stick_y);
                leftMotorBack.setPower(-gamepad1.left_stick_y);

                rightMotorFront.setPower(gamepad1.left_stick_y);
                rightMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if(gamepad1.left_stick_y != 0 && gamepad1.a) {
                leftMotorFront.setPower(-gamepad1.left_stick_y * .5);
                leftMotorBack.setPower(-gamepad1.left_stick_y * .5);

                rightMotorFront.setPower(gamepad1.left_stick_y * .5);
                rightMotorBack.setPower(gamepad1.left_stick_y * .5);
            }
            //Turning methods in TeleOp
            else if(gamepad1.right_stick_x != 0) {
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);

                rightMotorFront.setPower(gamepad1.right_stick_x);
                rightMotorBack.setPower(gamepad1.right_stick_x);
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

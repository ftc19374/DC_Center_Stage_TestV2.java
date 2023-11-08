package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp(name="DC_Center_Stage_TestV2")

public class DC_Center_Stage_TestV2 extends LinearOpMode{
    DcMotor Front_Right_Wheel = null;
    DcMotor Front_Left_Wheel  = null;
    DcMotor Back_Right_Wheel  = null;
    DcMotor Back_Left_Wheel   = null;
    DcMotor Intake_Motor_1    = null;
    float Power = 0;
    @Override
    public void runOpMode() {
        Front_Right_Wheel = hardwareMap.get(DcMotor.class, "Fr_motor");
        Front_Left_Wheel  = hardwareMap.get(DcMotor.class, "Fl_motor");
        Front_Left_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Back_Right_Wheel  = hardwareMap.get(DcMotor.class, "Br_motor");
        Back_Left_Wheel   = hardwareMap.get(DcMotor.class, "Bl_motor");
        Back_Left_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake_Motor_1    = hardwareMap.get(DcMotor.class, "Intake_Motor_1");
        waitForStart();
        while(opModeIsActive()) {
            double y = -gamepad1.left_stick_y; //reverses the y axis allowing for only forward and backwards movement
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;


            Front_Right_Wheel.setPower(-(y + x - rx)); //front right wheel always goes backwards on new driver control station when not inverted
            Front_Left_Wheel.setPower((y - x + rx));
            Back_Left_Wheel.setPower((y + x + rx));
            Back_Right_Wheel.setPower(-(y - x - rx));

            if (rx > 0 ||  rx < 0) {
            }

            //5th motor controls intake via the left and right bumpers (right pulls in, left goes out)
            if (gamepad1.right_bumper) {
              Power += 0.1;
                Intake_Motor_1.setPower(Power);
                sleep(250);
            }
            else if (gamepad1.left_bumper) {
                Power -= 0.1;
               Intake_Motor_1.setPower(Power);
                sleep(250);
            }
            //left bumper pushes pixels out to prevent control of too many pixels at once
        }
    }
}


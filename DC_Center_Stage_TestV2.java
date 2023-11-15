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
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="DC_Center_Stage_TestV2")

public class DC_Center_Stage_TestV2 extends LinearOpMode{
    DcMotor Front_Right_Wheel = null;
    DcMotor Front_Left_Wheel  = null;
    DcMotor Back_Right_Wheel  = null;
    DcMotor Back_Left_Wheel   = null;
    DcMotor Intake_Motor_1    = null;
    DcMotor Lift_Motor_1      = null;
    DcMotor Box_Motor_1       = null;
    DcMotor Drone_Motor_1     = null;
    CRServo   Drone_Servo_1     = null;
    float Power = 0;
    float LPower = 0;  //lift power variable
    float BPower = 0;  //Box power variable
    float DroneS = 0; //Servo for the Drone (Power)
    float DroneM = 0; //Motor for the drone (Power)
    @Override
    public void runOpMode() {
        Front_Right_Wheel = hardwareMap.get(DcMotor.class, "Fr_motor");
        Front_Left_Wheel  = hardwareMap.get(DcMotor.class, "Fl_motor");
        Front_Left_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Back_Right_Wheel  = hardwareMap.get(DcMotor.class, "Br_motor");
        Back_Left_Wheel   = hardwareMap.get(DcMotor.class, "Bl_motor");
        Back_Left_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake_Motor_1    = hardwareMap.get(DcMotor.class, "Intake_Motor_1");
        Lift_Motor_1      = hardwareMap.get(DcMotor.class, "Lift_1");
        Box_Motor_1       = hardwareMap.get(DcMotor.class, "Box_1");
        Drone_Motor_1     = hardwareMap.get(DcMotor.class, "Drone_Motor");
        Drone_Servo_1     = hardwareMap.get(CRServo.class, "Drone_Servo");

        waitForStart();
        while(opModeIsActive()) {
            double y = -gamepad1.left_stick_y; //reverses the y axis allowing for only forward and backwards movement
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;


            Front_Right_Wheel.setPower(-(y + x - rx)); //front right wheel always goes backwards on new driver control station when not inverted
            Front_Left_Wheel.setPower((y - x + rx));
            Back_Left_Wheel.setPower((y + x + rx));
            Back_Right_Wheel.setPower(-(y - x - rx));

            if (rx > 0 || rx < 0) {
            }

            //5th motor controls intake via the left and right bumpers
            // right bumper pulls in pixels
            if (gamepad1.right_bumper) {
                Power += 0.1;  //adds intake speed
                Intake_Motor_1.setPower(Power);
                sleep(250);
            }
            //left bumper pushes pixels out to prevent control of too many pixels at once
            else if (gamepad1.left_bumper) {
                Power -= 0.1;  //subtracts intake speed
                Intake_Motor_1.setPower(Power);
                sleep(250);
            }
            if (gamepad1.x) {
                Power = 0;  //stops intake
                Intake_Motor_1.setPower(Power);
                sleep(500);
            }
            //raise and lower the lift to be able to reach higher on the backdrop
            if (gamepad2.left_trigger >= 0.5) { //left trigger on gamepad 2 moves the lift up
                LPower = 1; //test speed then change appropriately
                Lift_Motor_1.setPower(LPower);
                sleep(250);
            } else if (gamepad2.right_trigger >= 0.5) { //right trigger on gamepad 2 moves lift down
            LPower = -1; //test speed then change appropriately
            Lift_Motor_1.setPower(LPower);
            sleep(250);
            }
            //Open and close the box function to drop pixels into backdrop
            if (gamepad2.left_bumper) {  //left bumper on gamepad 2 opens the box
                BPower = 0.5f;
                Box_Motor_1.setPower(BPower);
                sleep(250);
            }
            if (gamepad2.right_bumper) {  //right bumper on gamepad 2 closes the box
                BPower = -0.5f;
                Box_Motor_1.setPower(BPower);
                sleep(250);
            }
            //Launch the drone from the robot using a servo and a motor
            if (gamepad2.dpad_up) { //launches drone when pressed
                DroneS = 1;
                DroneM += 0.4;
                Drone_Motor_1.setPower(DroneM);
                Drone_Servo_1.setPower(DroneS);
            } else if (gamepad2.dpad_down); { //turns off/stops the launcher
                DroneS = 0;
                DroneM -= 0.4;
                Drone_Motor_1.setPower(DroneM);
                Drone_Servo_1.setPower(DroneS);

            }
            //Add code for the Hanging function for the robot
        }
    }
}

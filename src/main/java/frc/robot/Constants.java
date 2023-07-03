
package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public final class Constants {
    public static final class Contorller {
        public static final int joystickID = 0;
        public static final int xboxID = 1;
        public static final int JoystickVersionButtin = 2;

    }

    public static final class DriverConstant {
        public static final int left_front = 33;
        public static final int left_back = 34;
        public static final int right_front = 31;
        public static final int right_back = 32;
        public static final int pigeno_ID = 3;
    }

    public static final class ShooterConstant {
        public static final int left_shooter = 24; //
        public static final int right_shooter = 25;
        public static final int rotate = 15;
        public static final int elevation = 16;
        public static final int launch = 14;

        public static final int rotate_left_limit = 30;
        public static final int rotate_right_limit = -40;
        public static final int elevation_limit = 45;

        public static final int rotate_init_angle = -99;
        public static final int elevation_init_angle = 50;

        public static final int kSlotIdx = 0;
        public static final int kPIDLoopIdx = 0;
        public static final int kTimeoutMs = 30;
        public final static Gains kGains_Velocit = new Gains(0.1, 0.001, 5, 1023.0 / 20660.0, 300, 1.00);

    }

    public static final class DeliverConstant {
        public static final int intake = 11; //
        public static final int front = 12; //
        public static final int back = 13; //
    }

    public static final class controlConstant {
        public static final int PDP = 1; //
        public static final int PCM = 0; //
    }

    //
    public static final class PneumaticConstant {
        public static final int k_IntakeU = 0;
        public static final int k_IntakeD = 1;
    }

    //
    public static final class PneumaticStatues {
        public static final DoubleSolenoid.Value kIntakeUp = DoubleSolenoid.Value.kForward;//
        public static final DoubleSolenoid.Value kIntakeDown = DoubleSolenoid.Value.kReverse;//
    }

    //
    public static final class SensorConstant {
        public static final int rotate_digital = 2;
        public static final int pitch_digital = 1;
        public static final int shootExit_digital = 0;
        public static final int colorSensor_IIC = 0x52;
    }

    public static final class PhotonVisionConstant {
        public static final String Front_Cam = "FrontCam";
        public static final String Shooter_Cam = "ShooterCam";

        double camDiagFOV = 170.0; // degrees - assume wide-angle camera
        double camPitch = 45; // degrees
        double camHeightOffGround =0.5; // meters
        double maxLEDRange = 20; // meters
        int camResolutionWidth = 640; // pixels
        int camResolutionHeight = 480; // pixels
        double minTargetArea = 10; // square pixels

        public final static Gains kGains_rotateVersion = new Gains(0.1, 0, 0, 0, 0, 1.00);

    }

}

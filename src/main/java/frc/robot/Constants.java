// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  
  // Enabled Systems
  public static final boolean ENABLE_DRIVE = true;
  public static final boolean ENABLE_BALANCE = true;
  public static final boolean ENABLE_ARM = true;
  public static final boolean ENABLE_CLAW = false;
  public static final boolean ENABLE_PNEUMATIC_CLAW = false;
  
  public final class Logging {
    public static final boolean ENABLE_DRIVE = true;
    public static final boolean ENABLE_ARM = true;
    public static final boolean ENABLE_INTAKE = true;
    public static final boolean ENABLE_PDP = true;
    public static final boolean ENABLE_ROBOT_CONTROLLER = true;
    public static final boolean ENABLE_DRIVER_STATION = true;
    public static final double LOGGING_PERIOD = 0.02;
  }

  // Drive Config
  public static final double DRIVE_POWER_LIMIT = 0.9; // Hard limit on power
  public static final double DRIVE_TURN_POWER_LIMIT = 0.5; // Hard limit on turning power
  public static final double INTAKE_SPEED = 1.0;
  public static final double SLEW_LIMIT = 2.0;

  // TUNE FOR AUTONOMOUS
  public static final double GYRO_DELAY = 0.19;
  public static final double LEVEL_DEGREES = 5;
  public static final double ON_STATION_DEGREES = 10;


  
  public static final double TRACKWIDTH = Units.inchesToMeters(22.0);

  public static final int ARM_STALL_CURRENT = 50;
  public static final int ARM_CURRENT_LIMIT = 80;

  public static final double DRIVE_FINE_CONTROL_POWER = 0.18;
  public static final double TURN_FINE_CONTROL_POWER = 0.25;

  public static final double DRIVE_BASE_POWER = 0.1; // Motor power required to get the chassis moving
  public static final double DRIVE_BASE_TURN_POWER = 0.15; // Motor power required to get the chassis turning
  public static final double DRIVE_TRACK_WIDTH = 0.5588; // Meters
  public static final double WHEEL_RADIUS = 7.62 / 100.0; // Meters
  public static final double GEARBOX_RATIO = 1.0 / 8.45; // 10.71 for test chassis, 8.45 for main chassis
  public static final double DRIVE_METERS_PER_REVOLUTION = 2.0 * Math.PI * WHEEL_RADIUS * GEARBOX_RATIO;
  public static final double DRIVE_MOTOR_MAX_RPM = 3500.0 / 1.2;
  public static final double MAX_DRIVE_SPEED = (DRIVE_MOTOR_MAX_RPM / 60.0) * DRIVE_METERS_PER_REVOLUTION;

  public static final double DRIVE_VEL_KP = 0.0091849;
  public static final double DRIVE_POS_KP = 23.685;
  public static final double DRIVE_POS_KD = 0.56287;
  public static final double DRIVE_KS = 0.10063;
  public static final double DRIVE_KV = 0.030039;
  public static final double DRIVE_KA = 0.0071934;

  public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(DRIVE_TRACK_WIDTH);

  // Joystick Dead-zones
  public static final double TWIST_DEADZONE = 0.3; // Joystick deadzone for turning
  public static final double STRAIGHT_DEADZONE = 0.1; // Joystick deadzone for turning
  public static final double THROTTLE_DEADZONE = 0.1; // Joystick deadzone for arm lifting


  // Channels
  public static final int CAN_LEFT_DRIVE_1 = 13; // 10 for Main Chassis, 1 For Test Chassis
  public static final int CAN_LEFT_DRIVE_2 = 28; // 28 for Main Chassis, 2 For Test Chassis
  public static final int CAN_RIGHT_DRIVE_1 = 7; // 7 for Main Chassis, 3 For Test Chassis
  public static final int CAN_RIGHT_DRIVE_2 = 27; // 27 for Main Chassis, 4 For Test Chassis
  public static final int CAN_ARM_LIFT_1 = 5;
  public static final int CAN_ARM_LIFT_2 = 15;
  public static final int CAN_ARM_EXTEND = 24; // changed from 13 after burning 
  public static final int CAN_CLAW_GRAB = 100;
  public static final int CAN_INTAKE = 17;

  public static final int DIO_CLAW_GRAB_MICRO_SWITCH = 1;
  public static final int DIO_ARM_LIFT_ENCODER = 0;

  public static final int USB_DRIVE_JOYSTICK = 0;
  public static final int USB_UTILITY_JOYSTICK = 1;


  // Balance Config
  public static final double BALANCE_LEVEL_ANGLE_RANGE = 2.5; // Angle range required to declare leveled
  public static final double BALANCE_ANGLE_POWER_MULTIPLE = 4; // Motor power multiple based on current angle
  public static final double BALANCE_BASE_POWER = 0.15; // Base balancing speed


  double x = (1.79 / 1.63) * 36;

  // Arm Positioning
  public static final double ARM_MAX_LENGTH = 0.57 + 1.22; // Meters from pivot when fully extended
  public static final double ARM_PADDING_HEIGHT = 0.1;
  public static final double ARM_STARTING_LENGTH = 0.97; // Meters from pivot when fully retracted
  public static final double ARM_HEIGHT = 1.07; // Meters above ground from pivot
  public static final double ARM_LIFT_ENCODER_OFFSET = 330.0 + 90.0; // Offset so encoder reads 90 degrees when parallel to ground
  public static final double ARM_EXTEND_TICKS_PER_METER = Constants.ARM_MAX_EXTEND_TICKS / (Constants.ARM_MAX_LENGTH - Constants.ARM_STARTING_LENGTH);

  // Extension
  public static final double ARM_MAX_EXTEND_TICKS = 43.0 / 5.0 * 9.0; // Arm extend limit (measured in encoder ticks)
  public static final double ARM_EXTEND_PADDING = 0.01; // Padding to prevent overshooting limits (measured in meters)
  public static final double ARM_EXTEND_MAX_POWER = 1.0; // Fastest speed arm will extend (0 - 1)
  public static final double ARM_EXTEND_METERS_TOLERANCE = 0.04; // Meters of precision

  public static final double ARM_EXTEND_SPEED = 0.4; // m/s
  public static final double ARM_EXTEND_SPEED_FINE = 0.1; // m/s

  // Lifting
  public static final double ARM_LIFT_MAX_POWER = 0.3; // Max arm lifting power
  public static final double ARM_LIFT_MIN_ANGLE = 30.0; // Min arm angle (degrees)
  public static final double ARM_LIFT_MAX_ANGLE = 118; // Max arm angle (degrees)
  public static final double ARM_LIFT_ANGLE_TOLERANCE = 2; // Degrees of precision

  public static final double ARM_LIFT_SPEED = 10.0; // degrees/s
  public static final double ARM_LIFT_SPEED_FINE = 10.0; // degrees/s

  // PID & FF Config
  public static final double ARM_EXTEND_KP = 5.0;
  public static final double ARM_EXTEND_KI = 2.5;
  public static final double ARM_EXTEND_KD = 0;

  // PID
  public static final double ARM_LIFT_KP = 0.01;
  public static final double ARM_LIFT_KI = 0.003;
  public static final double ARM_LIFT_KD = 0;

  // FF
  public static final double ARM_LIFT_KS = 0;
  public static final double ARM_LIFT_KG = 0;
  public static final double ARM_LIFT_KV = 0;

  // Claw Config
  public static final double CLAW_GRAB_LIMIT = -200; // Claw grab limit (measured in encoder ticks)
  public static final double CLAW_GRAB_PADDING = 0.2; // Padding to prevent overshooting limits (measured in percent 0 - 1)
  public static final double CLAW_GRAB_MIN_POWER = 0.05; // Slowest speed claw will grab (0 - 1)
  public static final double CLAW_GRAB_MAX_POWER = 0.4; // Fastest speed claw will grab (0 - 1)

  // Autonomous Config
  public static final double AUTONOMOUS_SPEED = 2.0; // m / s
  public static final double AUTONOMOUS_ACCELERATION = 4; // m / s^2
  public static final double AUTONOMOUS_ANGULAR_SPEED = driveSpeedToRotationalSpeed(Constants.AUTONOMOUS_SPEED); // m / s
  public static final double AUTONOMOUS_ANGULAR_ACCELERATION = 3; // m / s^2
  public static final RamseteController AUTONOMOUS_PID = new RamseteController();

  // Limelight Config
  public static final String TOP_LIMELIGHT_NAME = "limelight-top";
  public static final String BOTTOM_LIMELIGHT_NAME = "limelight-bottom";

  // Align Config
  public static final double PICK_UP_HEIGHT = 0.15;
  public static final double NODE_TAPE_HEIGHT_MID = (22.125 + 2.0) * 0.0254;
  public static final double NODE_TAPE_HEIGHT_TOP = (41.125 + 2.0) * 0.0254;
  public static final double NODE_TIP_HEIGHT_MID = (22.125 + 4.0 + 8.0) * 0.0254;
  public static final double NODE_TIP_HEIGHT_TOP = (41.125 + 4.0 + (3.0 / 16.0)) * 0.0254;
  public static final double PLACING_OVER_NODE_HEIGHT = 0.25;
  public static final double TOP_LIMELIGHT_HEIGHT = 1.1811;
  public static final double BOTTOM_LIMELIGHT_HEIGHT = 0.1651;
  public static final double TOP_LIMELIGHT_VFOV = 49.7;

  public static double driveSpeedToRotationalSpeed(double speed) {
    return Math.atan(speed / (Constants.DRIVE_TRACK_WIDTH / 2.0));
  }

  public static double rotationalSpeedToDriveSpeed(double speed) {
    return Math.tan(speed) * (Constants.DRIVE_TRACK_WIDTH / 2.0);
  }

  public static double driveSpeedToPower(double speed) {
    if (Math.pow(1.22543, Math.abs(speed)) - 0.81804 < 0.12) {
      return 0.0;
    }
    return (Math.pow(1.22543, Math.abs(speed)) - 0.81804) * Math.signum(speed);
  }

  public static double mapPower(double power, double min, double max, double deadZone) {
    double sign = Math.signum(power);
    double absPower = Math.abs(power);

    if (absPower < deadZone) {
      return 0.0;
    } else {
      return mapNumber(absPower, deadZone, 1, min, max) * sign;
    }
  }

  public static double mapLimitedPower(int direction, double pos, double minPos, double maxPos, double minPower, double maxPower, double padding) {
    if (direction > 0) {
      if (pos > maxPos) {
        return 0.0;
      }
      if (pos > maxPos - padding) {
        return mapNumber(pos, maxPos - padding, maxPos, maxPower, minPower);
      } else {
        return maxPower;
      }
    } else if (direction < 0) {
      if (pos < minPos) {
        return 0.0;
      }
      if (pos < minPos + padding) {
        return -mapNumber(pos, minPos, minPos + padding, minPower, maxPower);
      } else {
        return -maxPower;
      }
    } else {
      return 0.0;
    }
  }

  public static double mapBothSides(double x, double a, double b, double c, double d) {
    if (x > 0.0) return mapNumber(x, a, b, c, d);
    if (x < 0.0) return mapNumber(x, -a, -b, -c, -d);
    return 0.0;
  }

  public static double mapNumber(double x, double a, double b, double c, double d) {
    if (x < a) {
      return c;
    }
    if (x > b) {
      return d;
    }
    return (x - a) / (b - a) * (d - c) + c;
  }
}

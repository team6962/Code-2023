// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.commands.*;
import frc.robot.Constants;

public class IMU extends SubsystemBase {

  AHRS IMU = new AHRS(SPI.Port.kMXP);
  private boolean onStation = false;
  private boolean transitioning = false;
  private boolean isBalanced = false;

  private double roll = 0.0;

  public IMU() {
    
  }

  @Override
  public void periodic() {
    isBalanced = Math.abs(IMU.getRoll()) < Constants.LEVEL_DEGREES || Math.signum(roll) != Math.signum(IMU.getRoll());
    boolean isOnStation = Math.abs(IMU.getRoll()) < Constants.ON_STATION_DEGREES;
    roll = IMU.getRoll();

    if (transitioning && !isOnStation && isBalanced) {
      transitioning = false;
      return;
    }

    if (transitioning && isBalanced) {
      onStation = !onStation;
      transitioning = false;
      return;
    }

    if (!isBalanced && !transitioning) {
      transitioning = true;
      return;
    }

    // System.out.println(IMU.getRoll());
    // System.out.println(IMU.isCalibrating());
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public double getRoll() {
    return IMU.getRoll();
  }

  public AHRS getIMU() {
    return IMU;
  }

  public Rotation2d getRotation2d() {
    return IMU.getRotation2d();
  }

  public boolean isOnStation() {
    return onStation || transitioning;
  }

  public boolean isCenteredOnStation() {
    return isBalanced && onStation;
  }

  public boolean isOffStation() {
    return !(onStation || transitioning);
  }

  public Command resetVariables() {
    return runOnce(() -> {
      onStation = false;
      transitioning = false;
      isBalanced = false;
    });
  }
}

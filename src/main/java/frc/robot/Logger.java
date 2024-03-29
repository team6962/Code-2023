package frc.robot;

import java.util.HashMap;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.hal.PowerDistributionFaults;
import edu.wpi.first.hal.PowerDistributionStickyFaults;
import edu.wpi.first.hal.PowerDistributionVersion;
import edu.wpi.first.hal.can.CANStatus;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.*;
import edu.wpi.first.networktables.Topic;
import edu.wpi.first.util.datalog.*;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.subsystems.*;

import frc.robot.Constants.*;

public class Logger {
    DataLog log = DataLogManager.getLog();;
    Map<String, Object> logEntries = new HashMap<String, Object>();

    // PowerDistribution PDP = new PowerDistribution(5, ModuleType.kRev);
    public int loopCount = 0;
    public Drive drive;
    public Arm arm;
    public Intake intake;
    public IMU IMU;
    
    public NetworkTableInstance instance = NetworkTableInstance.getDefault();

    public Logger(Drive drive, Arm arm, Intake intake, IMU IMU) {
        this.IMU = IMU;
        this.drive = drive;
        this.arm = arm;
        this.intake = intake;
        DataLogManager.start();
    }

    public void logAll() {
        if (Logging.ENABLE_DRIVE)
            logDrive("/drive", drive);
        // if (Logging.ENABLE_PDP)
        //     logPDP("/powerDistribution", PDP);
        if (Logging.ENABLE_ROBOT_CONTROLLER)
            logRobotController("/robotController");
        if (Logging.ENABLE_DRIVER_STATION)
            logDriverStation("/driverStation");
        if (Logging.ENABLE_ARM)
            logArm("/arm", arm);
        if (Logging.ENABLE_INTAKE)
            logIntake("/intake", intake);
    }

    public void logDrive(String path, Drive drive) {
        logSparkMax(path + "/leftMotor1", drive.leftBank1);
        logSparkMax(path + "/leftMotor2", drive.leftBank2);
        logSparkMax(path + "/rightMotor1", drive.rightBank1);
        logSparkMax(path + "/rightMotor2", drive.rightBank2);
        logNavX(path + "/gyro", IMU.getIMU());
        logStationStatus(path + "/stationStatus", IMU);
    }

    public void logStationStatus(String path, IMU imu) {
        logData(path + "/onStation", imu.isOnStation());
        logData(path + "/offStation", imu.isOffStation());
        logData(path + "/isCentered", imu.isCenteredOnStation());
    }

    public void logArm(String path, Arm arm) {
        logSparkMax(path + "/lift1Motor", arm.lift1);
        logSparkMax(path + "/lift2Motor", arm.lift2);
        logSparkMax(path + "/extendMotor", arm.extend);

        logData(path + "/extendMeters", arm.extendMeters);
        logData(path + "/liftAngle", arm.liftAngle);
        logData(path + "/nextExtendMeters", arm.nextExtendMeters);
        logData(path + "/nextLiftAngle", arm.nextLiftAngle);
        logData(path + "/targetExtendMeters", arm.targetExtendMeters);
        logData(path + "/targetLiftAngle", arm.targetLiftAngle);
    }

    public void logIntake(String path, Intake intake) {
        logPWMSparkMax(path + "/intakeMotor", intake.intakeMotor);
    }

    public void logDriverStation(String path) {
        DriverStation.startDataLog(log, true);
    }

    public void logPWMSparkMax(String path, PWMSparkMax sparkMax) {
        logData(path + "/power", sparkMax.get());
        logData(path + "/inverted", sparkMax.getInverted(), true);

    }

    public void logSparkMax(String path, CANSparkMax sparkMax) {
        logData(path + "/power", sparkMax.get());
        logData(path + "/appliedOutputDutyCycle", sparkMax.getAppliedOutput());
        logData(path + "/busVoltage", sparkMax.getBusVoltage());
        logData(path + "/motorTemperature", sparkMax.getMotorTemperature());
        logData(path + "/outputCurrent", sparkMax.getOutputCurrent());
        logData(path + "/faults", sparkMax.getFaults());
        logData(path + "/firmwareString", sparkMax.getFirmwareString(), true);
        logData(path + "/firmwareVersion", sparkMax.getFirmwareVersion(), true);
        logData(path + "/stickyFaults", sparkMax.getStickyFaults(), true);
        logRelativeEncoder(path + "/relativeEncoder", sparkMax.getEncoder());
    }

    public void logRelativeEncoder(String path, RelativeEncoder encoder) {
        logData(path + "/position", encoder.getPosition());
        logData(path + "/velocity", encoder.getVelocity());
    }

    public void logNavX(String path, AHRS navX) {
        logData(path + "/actualUpdateRate", navX.getActualUpdateRate(), true);
        logData(path + "/firmwareVersion", navX.getFirmwareVersion(), true);
        logData(path + "/altitude", navX.getAltitude());
        logData(path + "/angle", navX.getAngle());
        logData(path + "/angleAdjustment", navX.getAngleAdjustment());
        logData(path + "/compassHeading", navX.getCompassHeading());
        logData(path + "/displacementX", navX.getDisplacementX());
        logData(path + "/displacementY", navX.getDisplacementY());
        logData(path + "/displacementZ", navX.getDisplacementZ());
        logData(path + "/fusedHeading", navX.getFusedHeading());
        logData(path + "/pitch", navX.getPitch());
        logData(path + "/pressure", navX.getPressure());
        logData(path + "/roll", navX.getRoll());
        logData(path + "/yaw", navX.getYaw());
        logData(path + "/temperature", navX.getTempC());
        logData(path + "/velocityX", navX.getVelocityX());
        logData(path + "/velocityY", navX.getVelocityY());
        logData(path + "/velocityZ", navX.getVelocityZ());
        logData(path + "/accelerationX", navX.getRawAccelX());
        logData(path + "/accelerationY", navX.getRawAccelY());
        logData(path + "/accelerationZ", navX.getRawAccelZ());
    }

    public void logOdometer(String path, SwerveDriveOdometry odometer) {
        logData(path + "/odometer", new double[] { odometer.getPoseMeters().getX(), odometer.getPoseMeters().getY(), odometer.getPoseMeters().getRotation().getRadians() });
    }

    public void logPose(String path, Pose2d pose) {
        logData(path, new double[] { pose.getX(), pose.getY(), pose.getRotation().getRadians() });
    }

    public void logRobotController(String path) {
        logData(path + "/brownoutVoltage", RobotController.getBrownoutVoltage(), true);
        logData(path + "/batteryVoltage", RobotController.getBatteryVoltage());
        logData(path + "/batteryVoltage", RobotController.getBatteryVoltage());
        logData(path + "/inputCurrent", RobotController.getInputCurrent());
        logData(path + "/inputVoltage", RobotController.getInputVoltage());
        logData(path + "/3V3Line/current", RobotController.getCurrent3V3());
        logData(path + "/5VLine/current", RobotController.getCurrent5V());
        logData(path + "/6VLine/current", RobotController.getCurrent6V());
        logData(path + "/3V3Line/enabled", RobotController.getEnabled3V3());
        logData(path + "/5VLine/enabled", RobotController.getEnabled5V());
        logData(path + "/6VLine/enabled", RobotController.getEnabled6V());
        logData(path + "/3V3Line/faultCount", RobotController.getFaultCount3V3());
        logData(path + "/5VLine/faultCount", RobotController.getFaultCount5V());
        logData(path + "/6VLine/faultCount", RobotController.getFaultCount6V());
        logData(path + "/3V3Line/voltage", RobotController.getVoltage3V3());
        logData(path + "/5VLine/voltage", RobotController.getVoltage5V());
        logData(path + "/6VLine/voltage", RobotController.getVoltage6V());
        logCanStatus(path + "/canStatus", RobotController.getCANStatus());
    }

    public void logCanStatus(String path, CANStatus canStatus) {
        logData(path + "/busOffCount", canStatus.busOffCount);
        logData(path + "/percentBusUtilization", canStatus.percentBusUtilization);
        logData(path + "/receiveErrorCount", canStatus.receiveErrorCount);
        logData(path + "/transmitErrorCount", canStatus.transmitErrorCount);
        logData(path + "/txFullCount", canStatus.txFullCount);
    }

    public void logPDP(String path, PowerDistribution PDP) {
        logPDPFaults(path + "/faults", PDP.getFaults());

        logData(path + "/canId", PDP.getModule(), true);
        for (int i = 0; i <= 23; i++) {
            logData(path + "/channels/channel" + i + "Current", PDP.getCurrent(i));
        }
        logData(path + "/isSwitchableChannelOn", PDP.getSwitchableChannel(), true);
        logData(path + "/temperature", PDP.getTemperature());
        logData(path + "/totalCurrent", PDP.getTotalCurrent());
        logData(path + "/totalJoules", PDP.getTotalEnergy());
        logData(path + "/totalWatts", PDP.getTotalPower());
        logData(path + "/voltage", PDP.getVoltage());
    }

    public void logPDPFaults(String path, PowerDistributionFaults faults) {
        logData(path + "/brownout", faults.Brownout);
        logData(path + "/canWarning", faults.CanWarning);
        logData(path + "/channel0BreakerFault", faults.Channel0BreakerFault);
        logData(path + "/channel1BreakerFault", faults.Channel1BreakerFault);
        logData(path + "/channel2BreakerFault", faults.Channel2BreakerFault);
        logData(path + "/channel3BreakerFault", faults.Channel3BreakerFault);
        logData(path + "/channel4BreakerFault", faults.Channel4BreakerFault);
        logData(path + "/channel5BreakerFault", faults.Channel5BreakerFault);
        logData(path + "/channel6BreakerFault", faults.Channel6BreakerFault);
        logData(path + "/channel7BreakerFault", faults.Channel7BreakerFault);
        logData(path + "/channel8BreakerFault", faults.Channel8BreakerFault);
        logData(path + "/channel9BreakerFault", faults.Channel9BreakerFault);
        logData(path + "/channel10BreakerFault", faults.Channel10BreakerFault);
        logData(path + "/channel11BreakerFault", faults.Channel11BreakerFault);
        logData(path + "/channel12BreakerFault", faults.Channel12BreakerFault);
        logData(path + "/channel13BreakerFault", faults.Channel13BreakerFault);
        logData(path + "/channel14BreakerFault", faults.Channel14BreakerFault);
        logData(path + "/channel15BreakerFault", faults.Channel15BreakerFault);
        logData(path + "/channel16BreakerFault", faults.Channel16BreakerFault);
        logData(path + "/channel17BreakerFault", faults.Channel17BreakerFault);
        logData(path + "/channel18BreakerFault", faults.Channel18BreakerFault);
        logData(path + "/channel19BreakerFault", faults.Channel19BreakerFault);
        logData(path + "/channel20BreakerFault", faults.Channel20BreakerFault);
        logData(path + "/channel21BreakerFault", faults.Channel21BreakerFault);
        logData(path + "/channel22BreakerFault", faults.Channel22BreakerFault);
        logData(path + "/channel23BreakerFault", faults.Channel23BreakerFault);
        logData(path + "/hardwareFault", faults.HardwareFault);
    }

    public void logData(String key, Object value) {
        logData(key, value, false);
    }

    public void logData(String key, Object value, boolean once) {
        boolean loggedYet = logEntries.containsKey(key);

        if (!loggedYet) {
            if (value instanceof Boolean)
                logEntries.put(key, instance.getBooleanTopic(key).publish());
            else if (value instanceof Double)
                logEntries.put(key, instance.getDoubleTopic(key).publish());
            else if (value instanceof Short)
                logEntries.put(key, instance.getIntegerTopic(key).publish());
            else if (value instanceof Float)
                logEntries.put(key, instance.getFloatTopic(key).publish());
            else if (value instanceof Integer)
                logEntries.put(key, instance.getIntegerTopic(key).publish());
            else if (value instanceof double[])
                logEntries.put(key, instance.getDoubleArrayTopic(key).publish());
            else if (value instanceof String)
                logEntries.put(key, instance.getStringTopic(key).publish());
            else
                System.out.println("unknown logging data type: " + value.getClass().getSimpleName());
            }

        if (!once || !loggedYet) {
            if (value instanceof Boolean)
                ((BooleanPublisher) logEntries.get(key)).set((boolean) value);
            else if (value instanceof Double)
                ((DoublePublisher) logEntries.get(key)).set((double) value);
            else if (value instanceof Short)
                ((IntegerPublisher) logEntries.get(key)).set(((Short) value).intValue());
            else if (value instanceof Float)
                ((FloatPublisher) logEntries.get(key)).set((float) value);
            else if (value instanceof Integer)
                ((IntegerPublisher) logEntries.get(key)).set((int) value);
            else if (value instanceof double[])
                ((DoubleArrayPublisher) logEntries.get(key)).set((double[]) value);
            else if (value instanceof String)
                ((StringPublisher) logEntries.get(key)).set((String) value);
            else
                System.out.println("unknown logging data type: " + value.getClass().getSimpleName());
        }
    }
}

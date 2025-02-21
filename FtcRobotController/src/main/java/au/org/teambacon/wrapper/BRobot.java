package au.org.teambacon.wrapper;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.HashMap;
import java.util.Map;

import au.org.teambacon.actions.ActionHandler;
import au.org.teambacon.control.BMotor;
import au.org.teambacon.control.BMotorController;
import au.org.teambacon.control.BServo;

public abstract class BRobot extends OpMode {
    public static BMotor DriveLeft;
    public static BMotor DriveRight;
    public static BMotorController DriveController;

    public static BRobot Instance;

    public static ActionHandler actionHandler;

    protected int LoopCount = 0;

    protected ElapsedTime Runtime = new ElapsedTime();

    private static boolean Flush = false;

    protected Map<String, BMotor> Motors = new HashMap<String, BMotor>();
    protected Map<String, BServo> Servos = new HashMap<String, BServo>();

    public BRobot() {
        Instance = this;

        this.actionHandler = new ActionHandler(this);
    }

    public void addMotor(BMotor motor, String name) {
        this.Motors.put(name, motor);
    }
    public BMotor getMotor(String name) {
        return this.Motors.get(name);
    }
    public void addServo(BServo servo, String name) {
        this.Servos.put(name, servo);
    }
    public BServo getServo(String name) {
        return this.Servos.get(name);
    }

    public static void flush() {
        Flush = true;
    }

    public final void init() {
        this.Runtime.reset();

        this.binit();

        this.bdefineauto();
    }

    public final void loop() {
        this.LoopCount++;

        if (Flush) {
            Flush = false;
            return;
        }

        if (this.bauto())
            return;

        this.bloop();
    }

    public abstract void bdefine();

    public abstract void binit();

    public abstract void bdefineauto();

    public final boolean bauto() {
        if (actionHandler.ActionList.isEmpty())
            return false;

        actionHandler.handle();
        return true;
    }

    public abstract void bloop();
}

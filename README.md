# Vertices

A simple, easy-to-use command system for FTC.

[View our slideshow presentation](https://docs.google.com/presentation/d/1boNWFfqJhmMipA3C38eTGi0WhQw5I1PjR8GlLHR41hk/edit?usp=sharing)

## Quick Start

Commands are executed through `CommandRunner` instances. Every `Command` implements three methods:

- `void init()` - Runs once when the command starts
- `void loop()` - Runs continuously until finished
- `boolean isFinished()` - Returns true when complete

### Basic Structure

Build complex behaviors by nesting commands:

```java
Command autonomous = new Command(
    new SeriesCommand(
        new ParallelCommand(
            new MoveArm(Arm.Targets.HIGH),
            new OpenClaw()
        ),
        new DriveForward(24)
    )
);
```

This runs `MoveArm` and `OpenClaw` simultaneously, then executes `DriveForward` after both complete. NOTE: These are examples, **NOT** built in `Command`s

## Built-in Commands

### Scheduling Commands
- **`SeriesCommand`** - Runs commands one after another
- **`ParallelCommand`** - Runs multiple commands simultaneously

### Utility Commands
- **`SleepCommand`** - Waits for a specified duration
- **`AwaitCommand`** - Waits until a condition becomes true (with optional timeout)
- **`CustomCommand`** - Executes a `Runnable` once (supports lambda expressions)

### Control Commands
- **`SwitchCommand`** - Executes different commands based on a condition
- **`StoppableCommand`** - Wrapper that allows pausing/stopping commands during runtime
  - `stop()` - Ends the command immediately
  - `disable()` - Pauses execution (can be re-enabled)
  - `enable()` - Resumes execution

### Debug Commands
- **`TelemetryCommand`** - Creates telemetry displays with method chaining
  ```java
  telemetryCommand
      .addLine("Robot Status")
      .addData("Position", () -> robot.getPosition())
      .addDisabledTag();
  ```

## Required Subsystems

### Robot
Central container for all hardware and subsystems. Access via `Robot.getInstance()` - this class cannot be instantiated directly.

### TelemetryProfiles
Manages saved `TelemetryCommand` instances mapped to enums.
- `getProfile(Profile profile)` - Returns a copy of the specified telemetry profile

## Example Usage

```java
// Create a complex autonomous routine
Command auto = new SeriesCommand(
    new TelemetryCommand().addLine("Starting autonomous..."),
    new ParallelCommand(
        new MoveArm(Arm.Targets.PICKUP),
        new DriveToPosition(startPosition)
    ),
    new AwaitCommand(() -> sensor.isPressed(), 5.0), // Wait max 5 seconds
    new CustomCommand(() -> claw.close()),
    new SleepCommand(1000) // Wait 1 second
);
```

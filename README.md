# ChoreoExtended

A trajectory event library super charging choreo with reliable events.

## Setup

Paste this url when installing online libraries using vendordep manager in order to install.

```sh
https://raw.githubusercontent.com/ThePinkAlliance/choreo-extended/main/vendor.json
```

## Instant Action Example

This is an action that runs once called an `InstantAction`. The action below will execute sysout printing "Hello!!" in the console when the action named `action-name` is trigged by choreo.

```java
var action_one = new InstantAction(() -> {
  System.out.println("Hello!!");
}, "trajectory-event-name");
```

**NOTE**: The example above is creating a looping action which will print the text "Hello!!" once before exiting. And all of this will happen when the trajectory event called "trajectory-event-name" in Choreo UI is triggered.  

## Looping Action Example

A looping action runs a java runnable repeatly for a specified duration when the trajectory event is triggered. Keep in mind when using looping actions that any commands sent to the motors won't be canceled when the action exits.

```java
var looping_action = new LoopingAction(() -> {
  System.out.println("Looping Action!!");
}, 1, "trajectory-event-name");
```

**NOTE**: The example above is creating a looping action which will print the text "Looping Action!!" repeatly for 1 second before exiting. And all of this will happen when the trajectory event called "trajectory-event-name" in Choreo UI is triggered.  

## Action Cleanup

This is a looping action with a custom cleanup method. The `ExtendableLoopingAction` was created specificly in order to allow for custom cleanup functions.

```java
public class CustomAction extends ExtendableLoopingAction {
  public CustomAction() {
    // Connected to "custom-action" with duration of 1 second.
    super("custom-action", 1);
  }

  @Override
  public void execute() {
    // Execute motors and read sensors.
  }

  @Override
  public void exit() {
    // Stop motors and cleanup
  }
}
```

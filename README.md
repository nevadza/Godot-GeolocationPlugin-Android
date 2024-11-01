# Geolocation Plugin

A Godot Geolocation Plugin for Android. Compatible with Godot 4.3.

## Install plugin

### Android

 1. Navigate to <https://github.com/nevadzask/Godot-GeolocationPlugin-Android/releases/> and download the latest release.
 2. Extract the contents of the project zip file into the `res://android/plugins` folder in your project. If this folder does not exist, create it manually.
 3. Install Android Build Template (`Project > Install Android Build Template...`)
 4. Add Android Export (`Project > Export...`)
 5. Enable `Use Gradle Build` in `Gradle Build`-section
 6. Enable `Geolocation` in the `Plugins`-section
 7. Enable `Access Coarse Location` and `Access Fine Location` in the `Permissions`-section

<br/>

## Build plugin
The plugin is based on the official Android Godot plugin and uses Gradle to compile and build AAR:

`./gradlew clean build`

Built AARs are in `plugin/build/outputs/aar`. Godot plugin descriptor files are in `plugin/godot_descriptors` directory.

<br/>

## API

### Methods

#### Permissions

- `request_permission()` *[iOS only]* - requests authorization and will show the native OS dialog.
The `authorization_changed(int)` signal will be triggered when the user has made their decision.
*On Android use the native Godot function `OS.request_permissions()` and the Godot signal `on_request_permissions_result`*

#### Location

- `request_location()` - Requests a single location the set accuracy. The result will be delivered by the `location_update` signal. An error might be delivered by the `error` signal (`ERROR_DENIED`, `ERROR_NETWORK`)

- `start_updating_location()` - Start location updates with the set options. Results will be delivered by the `location_update` signal. An error might be delivered by the `error` signal (`ERROR_DENIED`, `ERROR_NETWORK`)

- `stop_updating_location()` - Stops the location updates.

#### Magnetic Heading (iOS only!)

- `start_updating_heading()` - Start heading updates. Results will be delivered by the `heading_update` signal. An error might be delivered by the `error` signal (`ERROR_HEADING_FAILURE`)

- `stop_updating_heading()` - Stops the heading updates.

#### Options

- `set_distance_filter(meters:float)` - Set the minimum distance in meters the location has to be different to be delivered by `location_update`. **Default: 0**

- `set_desired_accuracy(desired_accuracy_constant:int)` - Set the desired accuracy `int` being a constant from `GeolocationDesiredAccuracyConstants` enum. **Default: `ACCURACY_BEST`**

- `set_update_interval(seconds:int)` *[Android only]* - Set the update interval for new locations in seconds. **Default: 1**

- `set_max_wait_time(seconds:int)` *[Android only]* - Set the maximum time in seconds you are willing to wait for the `location_update` signal. **This is not a timeout**. It actually means that locations will be deferred and delivered in bunches every `timeInSeconds`. **Default: 1**

- `set_return_string_coordinates(active:bool)` - Set to `false` if you don't want latitude and longitude to be *additionally* included as strings in the `Location Data` Dictionary. **Default: true**

- `set_failure_timeout(seconds:int)` - Set the timeout in seconds before an error is reported, when no location could be found. **Default: 20**

- `set_debug_log_signal(send:bool)` - Set to `true` if you want to receive debug messages from the native plugin through the `log` signal. **Default: false**

- `set_auto_check_location_capability(auto:bool)` *[Android only]* - Set to `true` if the plugin should automatically check for location capability before requesting a location. **Default: false**

#### Status

- `authorization_status() -> int` - returns the current authorization status (Enum `GeolocationAuthorizationStatus`)

- `allows_full_accuracy() -> bool` - returns `true` if the user allowed full accuracy

- `can_request_permissions() -> bool` - returns `true` if it is possible to request location permissions. On iOS this is only the case as long the authorization status is `PERMISSION_STATUS_UNKNOWN`, on Android it is possible to request permissions for a second time (if the first time only coarse permissions were granted)

- `is_updating_location() -> bool` - returns true when the native location manager is active. *Might not always report correctly. Stop and Start locations updates if unsure*

- `is_updating_heading() -> bool` *[iOS only for now]* - returns true heading is actively updating *Might not always report correctly*

- `should_show_permission_requirement_explanation() -> bool` *[Android only]* - returns true `true` when permissions where rejected before and the user needs an explanation why the permissions are neccesary

- `request_location_capability()` - checks device capability for locartion services. Async result (`true`/`false`) is delivered by `location_capability_result` signal

- `should_check_location_capability() -> bool` - returns true `true` when you should manually check for location capability (`true` when `set_auto_check_location_capability` is `false` on Android, alsways `false` on iOS)

#### Option/Method Platform support

- `supports(methodName:string) -> bool` - returns `true` if the method "methodName" is supported

### Signals

- `log(message:string, number:float)` - returns a debug message from the native platform plugin
- `error(code:int)` - returns an error code (Enum `GeolocationErrorCodes`)
- `authorization_changed(authorization_status:int)` *[iOS only]* - returns an authorization status (Enum `GeolocationAuthorizationStatus`)
- `location_update(location:Dictionary)` - returns a Godot Dictionary with location data (see `Location Data`)
- `heading_update(heading:Dictionary)` *[iOS only]* - returns a Godot Dictionary with magentic heading data (see `Heading Data`)
- `location_capability_result(capable:bool)` - returns a boolean value. `true` means that location capability is available

### Wrappers for easier usage

<!-- #### C\# -->
<!--  -->
<!-- <https://github.com/nevadzask/Geolocation-Csharp-Wrapper> -->

#### GDScript

<https://github.com/nevadzask/Geolocation-GDScript-Wrapper>

<!-- ### Example Test App -->
<!--  -->
<!-- <https://github.com/nevadzask/GeolocationTestApp> -->

### Enums

#### GDScript Enums

```GDscript
enum geolocation_authorization_status {
    PERMISSION_STATUS_UNKNOWN = 1 << 0,
    PERMISSION_STATUS_DENIED = 1 << 1,
    PERMISSION_STATUS_ALLOWED = 1 << 2
}

enum geolocation_desired_accuracy_constants {
    ACCURACY_BEST_FOR_NAVIGATION = 1 << 0,
    ACCURACY_BEST = 1 << 1,
    ACCURACY_NEAREST_TEN_METERS = 1 << 2,
    ACCURACY_HUNDRED_METERS = 1 << 3,
    ACCURACY_KILOMETER = 1 << 4,
    ACCURACY_THREE_KILOMETER = 1 << 5,
    ACCURACY_REDUCED = 1 << 6
}

enum geolocation_error_codes {
    ERROR_DENIED = 1 << 0,
    ERROR_NETWORK = 1 << 1,
    ERROR_HEADING_FAILURE = 1 << 2
    ERROR_LOCATION_UNKNOWN = 1 << 3
    ERROR_TIMEOUT = 1 << 4
    ERROR_UNSUPPORTED = 1 << 5
    ERROR_LOCATION_DISABLED = 1 << 6
    ERROR_UNKNOWN = 1 << 7
}
```

#### C\# Enums

```csharp
    public enum GeolocationErrorCodes
    {
        ERROR_DENIED = 1 << 0,
        ERROR_NETWORK = 1 << 1,
        ERROR_HEADING_FAILURE = 1 << 2,
        ERROR_LOCATION_UNKNOWN = 1 << 3,
        ERROR_TIMEOUT = 1 << 4,
        ERROR_UNSUPPORTED = 1 << 5,
        ERROR_LOCATION_DISABLED = 1 << 6,
        ERROR_UNKNOWN = 1 << 7
    };

    public enum GeolocationAuthorizationStatus
    {
        PERMISSION_STATUS_UNKNOWN = 1 << 0,
        PERMISSION_STATUS_DENIED = 1 << 1,
        PERMISSION_STATUS_ALLOWED = 1 << 2,
    }

    public enum GeolocationDesiredAccuracyConstants
    {
        ACCURACY_BEST_FOR_NAVIGATION = 1 << 0,
        ACCURACY_BEST = 1 << 1,
        ACCURACY_NEAREST_TEN_METERS = 1 << 2,
        ACCURACY_HUNDRED_METERS = 1 << 3,
        ACCURACY_KILOMETER = 1 << 4,
        ACCURACY_THREE_KILOMETER = 1 << 5,
        ACCURACY_REDUCED = 1 << 6,
    }
```

### Data Dictionaries

#### Location Data

- `["latitude"] float` - the current latitude. *precision is lost*
- `["longitude"] float` - the current latitude. *precision is lost*
- `["latitude_string"] string` (optional) - the current latitude. *convertable to double in C#*
- `["longitude_string"] string` (optional) - the current latitude. *convertable to double in C#*
- `["accuracy"] float` - the current horizontal accuracy in meters
- `["altitude"] float` - the current altitude in meters
- `["altitude_accuracy"] float` - the current vertical accuracy in meters. *(-1 when unavailable)*
- `["course"] float` - the current heading (in motion). *This is the direction the device is travelling in, not the magnetic/compass heading*
- `["course_accuracy"] float` - the course accuracy in degrees
- `["speed"] float` - the current speed in m/s
- `["speed_accuracy"] float` - the current speed accuracy in m/s. *(-1 when unavailable)*
- `["timestamp"] int` - the current time as the number of seconds since 1970-01-01

#### Heading Data (iOS only!)

- `["magnetic_heading"] float` - the magnetic heading (relative to **magnetic** noth pole)
- `["true_heading"] float` - the true heading (relative to **geographic** noth pole)
- `["heading_accuracy"] float` - the heading accuracy in degrees *(-1 when unavailable)*
- `["timestamp"] int` - the current time as the number of seconds since 1970-01-01

<br/>

## License

Copyright 2022 Andreas Ritter (www.wolfbeargames.de)

Copyright 2024 Gabriel Olekšák (www.nevadza.com)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

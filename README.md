# Arduinoremote
Some app for controlling the Telenet digicoder using an Arduino with IR Led.
Commands are sent over network using in my case a MT7681 wifimodule.
This module sends its IP address when "ip" is sent over UDP to port 7682,
During start of the app, "ip" is sent to all ip address in the range of the wifi connection.

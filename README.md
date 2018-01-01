# Arduinoremote
Some app for controlling the Telenet digicoder using an Arduino with IR Led.
Commands are sent over network using in my case a MT7681 wifimodule.
This module sends its IP address when "IP" is sent over UDP to port 7682,
this is the reason why it's scanning the network during start of the app.

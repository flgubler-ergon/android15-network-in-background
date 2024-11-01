# android15-network-in-background
Reproduction of the problem of accessing the network while in background

The problem is that network requests start failing once the app is in the background.
This app just sends a get-request to https://www.google.com every 5s for the purpose of reproduction.

To reproduce the problem, do the following
1. Build and run the app
2. Click the button "Start Polling"
3. Check the logcat to ensure that the requests succeed while the app is open
4. Return to the home-screen (home-button, gesture, etc.)
5. Keep checking the logcat: after 10-20s, the requests are going to start failing

# Android Studio Installation Guide

This guide provides detailed instructions for installing Android Studio on Windows, macOS, and Linux operating systems. Android Studio is the official Integrated Development Environment (IDE) for Android app development.

## Table of Contents

1. [System Requirements](#system-requirements)
2. [Windows Installation](#windows-installation)
3. [macOS Installation](#macos-installation)
4. [Linux Installation](#linux-installation)
5. [First-time Setup](#first-time-setup)
6. [Troubleshooting](#troubleshooting)

## System Requirements

Before installing Android Studio, ensure your system meets these minimum requirements:

### Windows
- 64-bit Microsoft® Windows® 8/10/11
- 8 GB RAM minimum, 16 GB RAM recommended
- 8 GB of available disk space minimum (IDE + Android SDK + Android Emulator)
- 1280 x 800 minimum screen resolution
- Intel processor with support for Hyper-V or AMD processor with support for AMD-V

### macOS
- macOS® 10.15 (Catalina) or higher
- 8 GB RAM minimum, 16 GB RAM recommended
- 8 GB of available disk space minimum
- 1280 x 800 minimum screen resolution

### Linux
- Any 64-bit Linux distribution that supports Gnome, KDE, or Unity DE
- GNU C Library (glibc) 2.31 or later
- 8 GB RAM minimum, 16 GB RAM recommended
- 8 GB of available disk space minimum
- 1280 x 800 minimum screen resolution

## Windows Installation

Follow these steps to install Android Studio on Windows:

1. **Download the installer**:
   - Visit the official Android Studio download page: [https://developer.android.com/studio](https://developer.android.com/studio)
   - Click on the "Download Android Studio" button
   - Accept the terms and conditions

2. **Run the installer**:
   - Locate the downloaded `.exe` file (typically `android-studio-ide-<version>-windows.exe`)
   - Right-click and select "Run as administrator" or simply double-click to start
   - If prompted by User Account Control, click "Yes" to allow the installation

3. **Follow the setup wizard**:
   - Click "Next" on the welcome screen
   - Choose the installation location or keep the default (recommended)
   - Select components to install:
     - Android Studio (required)
     - Android Virtual Device (recommended)
   - Click "Next"

4. **Choose Start Menu folder**:
   - Select the Start Menu folder or keep the default
   - Click "Next"

5. **Complete the installation**:
   - Click "Install" to begin installation
   - Wait for the installation to complete
   - When finished, ensure "Start Android Studio" is checked
   - Click "Finish"

6. **Hardware Acceleration (recommended)**:
   - For better emulator performance, enable Hyper-V:
     - Open Control Panel > Programs > Turn Windows Features On or Off
     - Check "Hyper-V" and "Windows Hypervisor Platform"
     - Click "OK" and restart your computer

## macOS Installation

Follow these steps to install Android Studio on macOS:

1. **Download the DMG file**:
   - Visit the official Android Studio download page: [https://developer.android.com/studio](https://developer.android.com/studio)
   - Click on the "Download Android Studio" button
   - Accept the terms and conditions

2. **Mount the DMG file**:
   - Locate the downloaded `.dmg` file (typically `android-studio-ide-<version>-mac.dmg`)
   - Double-click the file to mount it
   - A window showing Android Studio's icon and the Applications folder will appear

3. **Install Android Studio**:
   - Drag the Android Studio icon to the Applications folder
   - Wait for the copying process to complete

4. **First launch**:
   - Open the Applications folder and double-click Android Studio
   - If you see a warning about opening an application downloaded from the internet, click "Open"
   - If prompted about giving Android Studio access to your Documents folder or other locations, click "OK"

5. **macOS Security Settings**:
   - If you encounter a security message preventing the app from opening:
     - Open System Preferences > Security & Privacy
     - Click the "Open Anyway" button in the General tab
     - Confirm by clicking "Open" in the dialog that appears

6. **Hardware Acceleration (optional)**:
   - For M1/M2 Macs, the emulator is optimized for Apple Silicon
   - For Intel Macs, hardware acceleration is built into the installation

## Linux Installation

Follow these steps to install Android Studio on Linux:

1. **Download the package**:
   - Visit the official Android Studio download page: [https://developer.android.com/studio](https://developer.android.com/studio)
   - Click on the "Download Android Studio" button
   - Accept the terms and conditions
   - Download the `.tar.gz` file

2. **Extract the package**:
   - Open a terminal window
   - Navigate to the directory containing the downloaded file
   - Extract it using the following command:
     ```bash
     sudo tar -xzf android-studio-ide-<version>-linux.tar.gz -C /opt/
     ```

3. **Install required dependencies** (Ubuntu/Debian example):
   ```bash
   sudo apt-get install libc6:i386 libncurses5:i386 libstdc++6:i386 lib32z1 libbz2-1.0:i386
   ```

   For Fedora:
   ```bash
   sudo dnf install zlib.i686 ncurses-libs.i686 bzip2-libs.i686
   ```

4. **Launch Android Studio**:
   - Navigate to the installation directory:
     ```bash
     cd /opt/android-studio/bin
     ```
   - Run the studio script:
     ```bash
     ./studio.sh
     ```

5. **Create desktop entry** (optional):
   - During the first launch, Android Studio may ask if you want to create a desktop entry
   - Select "Yes" to create shortcuts

6. **Manual desktop entry creation** (if needed):
   - Create a file named `android-studio.desktop` in `~/.local/share/applications/` with the following content:
     ```
     [Desktop Entry]
     Version=1.0
     Type=Application
     Name=Android Studio
     Icon=/opt/android-studio/bin/studio.png
     Exec="/opt/android-studio/bin/studio.sh" %f
     Comment=Android Studio IDE
     Categories=Development;IDE;
     Terminal=false
     StartupWMClass=jetbrains-studio
     ```

## First-time Setup

After installing Android Studio, follow these steps to complete the setup:

1. **Setup Wizard**:
   - When Android Studio launches for the first time, a setup wizard appears
   - Choose "Standard" installation type (recommended for most users)
   - Click "Next"

2. **UI Theme**:
   - Choose between light or dark theme for the IDE
   - Click "Next"

3. **SDK Components Setup**:
   - Verify the settings for SDK components
   - Note the SDK installation location for future reference
   - Click "Next" or "Finish"

4. **SDK Download and Installation**:
   - Wait while Android Studio downloads and installs:
     - Android SDK
     - Android SDK Platform
     - Android Virtual Device
   - This may take some time depending on your internet connection

5. **Finish Setup**:
   - Click "Finish" when the installation is complete
   - Android Studio will start

## Troubleshooting

If you encounter issues during installation or setup, try these common solutions:

### Windows Issues

- **Installation fails**: Ensure you have administrator privileges and sufficient disk space
- **Emulator fails to start**: Verify that Hyper-V is enabled and virtualization is enabled in BIOS/UEFI
- **Performance issues**: Increase the IDE memory allocation in `studio64.exe.vmoptions`

### macOS Issues

- **"App is damaged" message**: Go to System Preferences > Security & Privacy and click "Open Anyway"
- **Slow emulator**: Ensure you have allocated sufficient RAM to the emulator in AVD settings
- **Permission issues**: Check that Android Studio has appropriate permissions in System Preferences > Security & Privacy > Privacy tab

### Linux Issues

- **Missing dependencies**: Install additional libraries as needed
- **Permission denied**: Ensure you have proper permissions for the installed directories
- **Display issues**: Install appropriate graphics drivers for your system

### General Troubleshooting

- **Log files**: Check the log files located in:
  - Windows: `%USERPROFILE%\.AndroidStudio<version>\system\log`
  - macOS: `~/Library/Logs/Google/AndroidStudio<version>`
  - Linux: `~/.AndroidStudio<version>/system/log`

- **Official Help**:
  - Visit the official troubleshooting guide: [https://developer.android.com/studio/troubleshoot](https://developer.android.com/studio/troubleshoot)
  - Search the Android developers community: [https://developer.android.com/support](https://developer.android.com/support)

---

For the latest information and updates, please refer to the official Android Studio documentation at [https://developer.android.com/studio/intro](https://developer.android.com/studio/intro).

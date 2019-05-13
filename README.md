# Staxel Mod Builder

A simple set of programs and scripts to enhance the experience of building Staxel Mods!

## Builder
The Builder is a suite of tools that go under the name "Staxel Mod Suite" and currently supports the importing of `.lang` as well as exporting in addition to editing the output `name` and `description` of the individual `.tile` files that are exported into the `.lang` file.

A Mod Builder will be part of it that allows you to define a `.tile` file as well as associate it to `.recipe` and `.reaction` files to ensure necessary variables are set the GUI will restrict the user as well as give hints on how a mod can be setup.

## Staxel Mod .lang Generator
This program is a simple console-based script written in Python that allows you to enter a folder-path and will then scan for `.tile` files within that folder and all its subfolders. It will use the `code` field of each `.tile` file to create a `.lang` file with `name` and `description` which is saved in the given folder under the folder's name followed by `.lang`.

### Usage
Download the latest release from [here](https://github.com/Dan6erbond/Staxel-Mod-Builder/releases) and launch the `.exe` file. Enter the folder-path and press enter. If you need to generate more files, the program will stay open until you press Ctrl + C so the process can be repeated. If any errors are thrown you should screenshot the output and submit it under the [Issues](https://github.com/Dan6erbond/Staxel-Mod-Builder/issues).

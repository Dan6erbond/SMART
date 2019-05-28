# Staxel Modding and Related Tools (SMART)

A simple set of programs and scripts to enhance the experience of building Staxel Mods!

## Mod Builder
The Mod Builder is a GUI application which currently supports the importing of `.lang` as well as exporting in addition to editing the output `name` and `description` of the individual `.tile` files that are exported into the `.lang` file with the GUI tools.

### Usage
Download the latest release from [here](https://github.com/Dan6erbond/Staxel-Mod-Builder/releases/latest) and launch the `.jar` file. If you want to (re-)create a Lang file, use the Lang Builder and if you want to generate a Mod, use the Mod Builder.

#### Generating a Lang File
In the Lang Builder you can select the root folder of your mod. In it it will search for all the `.tile` files as well as the `.lang` file in the root folder. If it does not find a `.lang` file it will create one when you press "SAVE". The `name` and `description` are dynamically loaded if they are present in the `.lang` file otherwise a generic one will be created. If the `.lang` file does not exist the program will save a new `.lang` file under the name scheme `FOLDERNAME.lang`.

## Staxel Mod .lang Generator
This program is a simple console-based script written in Python that allows you to enter a folder-path and will then scan for `.tile` files within that folder and all its subfolders. It will use the `code` field of each `.tile` file to create a `.lang` file with `name` and `description` which is saved in the given folder under the folder's name followed by `.lang`.

### Usage
Download the latest release from [here](https://github.com/Dan6erbond/Staxel-Mod-Suite/releases/tag/1.4) and launch the `.exe` file. Enter the folder-path and press enter. If you need to generate more files, the program will stay open until you press Ctrl + C so the process can be repeated. If any errors are thrown you should screenshot the output and submit it under the [Issues](https://github.com/Dan6erbond/Staxel-Mod-Builder/issues).

## Roadmap
  - [ ] A fully-fledged Mod Builder that allows you to create a Tile from scratch **or** load one and save (as).

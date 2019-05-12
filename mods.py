import os
import json
import time

def get_lang(tile_file):
    file_name = os.path.basename(tile_file)
    file_name = os.path.splitext(file_name)[0]
    with open(tile_file) as f:
        tile = json.loads(f.read())
        code = tile["code"]
        
        name = code + ".name=\"{}\"".format(file_name)
        description = code + ".description=\"{}\"".format(file_name)

    return name + "\n" + description

def get_tiles(folder):
    pass

def get_sub_folders(head_path, collected_folders=list()):
    head_path += "/"
    folders = [head_path + name for name in os.listdir(head_path) if os.path.isdir(head_path + name)]
    collected_folders.extend(folders)
    for folder in folders:
        collected_folders = get_sub_folders(folder, collected_folders)
    return collected_folders

def export_lang():
    mod_folder = input("What folder are your mods in? ")
    mod_name = os.path.basename(mod_folder)
    langs = list()

    folders = get_sub_folders(mod_folder)
    for folder in folders:
        for file in os.listdir(folder):
            if file.endswith(".tile"):
                tile_file = folder + "/" + file
                langs.append(get_lang(tile_file))

    lang_file = mod_folder + "/" + mod_name + ".lang"
    with open(lang_file, "w+") as f:
        f.write("language.code=en-GB\nlanguage=English\n\n" + "\n\n".join(langs))

while True:
    try:
        export_lang()
    except KeyboardInterrupt:
        exit()
    except Exception as e:
        print(e)
        time.sleep(10)
        exit()

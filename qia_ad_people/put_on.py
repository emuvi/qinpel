import os
import shutil
from enum import Enum

import builder
import packer


class Mode(Enum):
    TEST = 0
    PROD = 1


class Kind(Enum):
    APP = 0
    PUB = 1


def install(name: str, mode: Mode, kind: Kind):
    qin_root = os.environ['QIN_ROOT']
    destiny_root = f"{qin_root}/{'Prod' if mode == Mode.PROD else 'Test'}"
    builder.generate_and_build()
    packer.pack_browser("production" if mode == Mode.PROD else "development")
    print("Publishing...")
    if os.path.isdir("public"):
        destiny = f"{destiny_root}/{'pub' if kind == Kind.PUB else 'app'}/{name}"
        if os.path.isdir(destiny):
            shutil.rmtree(destiny)
        shutil.copytree("./public", destiny)
        print(f"Published public folder to {destiny}")
    else:
        print("No public folder found to be published.")
    if os.path.isdir("giz"):
        destiny = f"{destiny_root}/giz/{name}"
        if os.path.isdir(destiny):
            shutil.rmtree(destiny)
        shutil.copytree("./giz", destiny)
        print(f"Published giz folder to {destiny}")
    else:
        print("No giz folder found to be published.")

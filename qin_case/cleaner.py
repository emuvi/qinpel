import os
import shutil


def clean():
    if os.path.isdir("build"):
        shutil.rmtree("build")
    if os.path.isdir("types"):
        shutil.rmtree("types")
    if os.path.isdir("node_modules"):
        shutil.rmtree("node_modules")
    if os.path.isfile("package-lock.json"):
        os.remove("package-lock.json")


if __name__ == "__main__":
    clean()
    print("Cleaned!")

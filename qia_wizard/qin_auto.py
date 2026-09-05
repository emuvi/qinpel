import shutil
import urllib.request


def self_automagic():
    print("Updating the magic...")
    download("https://github.com/emuvi/qin_soul/raw/refs/heads/master/qin_magic.py", "qin_magic.py")
    with open("qin_magic.py") as file:
        exec(file.read(), globals())


def download(origin: str, destiny: str):
    with urllib.request.urlopen(origin) as resp, open(destiny, 'wb') as file:
        shutil.copyfileobj(resp, file)


if __name__ == "__main__":
    self_automagic()

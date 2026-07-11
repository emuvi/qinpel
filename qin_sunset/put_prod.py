import os
import shutil

qin_root = "../bin"
os.system("mvn clean install")
shutil.copytree("./dist", f"{qin_root}/prod", dirs_exist_ok=True)
shutil.copy("./setup-prod.json", f"{qin_root}/prod/setup.json")
shutil.copy("./start.bat", f"{qin_root}/prod/start.bat")
shutil.copy("./start.sh", f"{qin_root}/prod/start.sh")

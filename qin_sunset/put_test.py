import os
import shutil

qin_root = "../bin"
os.system("mvn clean install")
shutil.copytree("./dist", f"{qin_root}/Test", dirs_exist_ok=True)
shutil.copy("./setup-test.json", f"{qin_root}/Test/setup.json")
shutil.copy("./start.bat", f"{qin_root}/Test/start.bat")
shutil.copy("./start.sh", f"{qin_root}/Test/start.sh")

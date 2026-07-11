import os
import shutil

qin_root = "../bin"
os.system("mvn clean install")
shutil.copytree("./dist", f"{qin_root}/test", dirs_exist_ok=True)
shutil.copy("./setup-test.json", f"{qin_root}/test/setup.json")
shutil.copy("./start.bat", f"{qin_root}/test/start.bat")
shutil.copy("./start.sh", f"{qin_root}/test/start.sh")

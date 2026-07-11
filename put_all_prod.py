import os
import subprocess
import sys

def main():
    root_dir = os.path.dirname(os.path.abspath(__file__))
    
    for item in os.listdir(root_dir):
        item_path = os.path.join(root_dir, item)
        if os.path.isdir(item_path):
            magic_file = os.path.join(item_path, "put_prod.py")
            if os.path.exists(magic_file):
                print(f"==================================================")
                print(f"Executing put_prod.py in folder: {item}")
                print(f"==================================================")
                try:
                    subprocess.run([sys.executable, "put_prod.py"], cwd=item_path, check=True)
                except subprocess.CalledProcessError as e:
                    print(f"Error: Command failed with exit code {e.returncode} in folder {item}")
                except Exception as e:
                    print(f"Unexpected error in folder {item}: {e}")
                print("\n")

if __name__ == "__main__":
    main()

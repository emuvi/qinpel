import subprocess
import time
import os
import sys

# Paths
ROOT_DIR = os.path.abspath(os.path.dirname(__file__))
BIN_PROD_DIR = os.path.join(ROOT_DIR, 'bin', 'prod')
PUT_ALL_PROD_SCRIPT = os.path.join(ROOT_DIR, 'put_all_prod.py')

def get_current_commit():
    """Gets the current git commit hash."""
    try:
        return subprocess.check_output(['git', 'rev-parse', 'HEAD'], cwd=ROOT_DIR).decode('utf-8').strip()
    except subprocess.CalledProcessError as e:
        print(f"Error getting current commit: {e}")
        return None

def start_java_process():
    """Starts the java system similarly to how start.bat does it."""
    # Determine correct java path (since start.bat says jre\bin\java -jar qin_sunset.jar, 
    # but jre is one level up from prod in bin/jre)
    java_executable = 'java' # Fallback to whatever is in the system PATH
    possible_dirs = [
        os.path.join(ROOT_DIR, 'bin', 'jre', 'bin'),
        os.path.join(BIN_PROD_DIR, 'jre', 'bin')
    ]
    
    # Check if the java binary has or not the .exe extension
    for d in possible_dirs:
        if os.path.exists(os.path.join(d, 'java.exe')):
            java_executable = os.path.join(d, 'java.exe')
            break
        if os.path.exists(os.path.join(d, 'java')):
            java_executable = os.path.join(d, 'java')
            break

    cmd = [java_executable, '-jar', 'qin_sunset.jar']
    print(f"Starting process: {' '.join(cmd)} in {BIN_PROD_DIR}")
    
    # Use Popen so it runs in the background and we can track/kill it
    return subprocess.Popen(cmd, cwd=BIN_PROD_DIR)

def main():
    print("Starting auto update script...")
    java_process = start_java_process()

    while True:
        # Wait 1 hour (3600 seconds)
        print("Sleeping for 1 hour...")
        time.sleep(3600)
        
        print("\nChecking for updates...")
        old_commit = get_current_commit()
        
        try:
            # Perform git pull on main
            subprocess.run(['git', 'pull', 'origin', 'main'], cwd=ROOT_DIR, check=True)
        except subprocess.CalledProcessError as e:
            print(f"Git pull failed: {e}")
            continue
            
        new_commit = get_current_commit()
        
        # Check if commit has changed
        if old_commit and new_commit and old_commit != new_commit:
            print(f"Changes detected! Update from {old_commit[:7]} to {new_commit[:7]}")
            
            # Kill the running Java process
            print("Killing the prod java system execution...")
            java_process.terminate()
            
            # Wait for it to gracefully shutdown, fallback to kill
            try:
                java_process.wait(timeout=10)
            except subprocess.TimeoutExpired:
                java_process.kill()
                java_process.wait()
            
            # Run put_all_prod.py
            print("Running put_all_prod.py on the root...")
            try:
                subprocess.run([sys.executable, 'put_all_prod.py'], cwd=ROOT_DIR, check=True)
            except subprocess.CalledProcessError as e:
                print(f"Error while running put_all_prod.py: {e}")
            
            # Bring up the java system again
            print("Bringing up the prod java system again...")
            java_process = start_java_process()
        else:
            print("No changes on git pull. Java system continues to run.")

if __name__ == "__main__":
    main()

import re
import os

def read_version():
    file_path = "./kyupid.server.common/src/main/java/io/kyupid/v.properties"
    if os.path.isfile(file_path):
        with open(file_path, 'r') as file:
            content = file.read()
            version_match = re.search(r'VERSION\s*=\s*(\d+\.\d+\.\d+)', content)
            if version_match:
                version = version_match.group(1)
                return update_version(version)
            else:
                print("No version information found in the file.")
    else:
        print(f"File not found: {file_path}")
    return None

def update_version(version):
    major, minor, patch = map(int, version.split('.'))
    if patch == 9:
        if minor == 9:
            major += 1
            minor = 0
            patch = 0
        else:
            minor += 1
            patch = 0
    else:
        patch += 1
    return f"{major}.{minor}.{patch}"
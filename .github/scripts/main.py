import os
import sys
import re
import time
from create_notion_page import create_notion_page

def main():
    commits = sys.argv[1].split(',')
    for commit in commits:
        if commit:
            print(f"Processing commit: {commit}")

            commit_message = os.popen(f"git log -1 --pretty=%B {commit}").read()
            ticket_number = re.search(r'[A-Z]+-[0-9]+', commit_message)
            if ticket_number:
                ticket_number = ticket_number.group()
            module_names = re.findall(r'@[a-zA-Z]+', commit_message)
            module_names = [name.replace('@', '').upper() for name in module_names]
            module_names = ' '.join(module_names)

            commit_title = os.popen(f"git log -1 --pretty=%s {commit}").read().strip()
            commit_title = re.sub(r'([A-Z]+-[0-9]+|@[a-zA-Z]+)', '', commit_title).strip()
            commit_summary = os.popen(f"git log -1 --pretty=%b {commit}").read()

            version = os.popen("python .github/scripts/read_version.py").read().strip()

            if ticket_number or module_names:
                create_notion_page(ticket_number, module_names, commit_title, commit_summary, version)

            time.sleep(1)

if __name__ == "__main__":
    main()
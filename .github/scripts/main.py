import os
import sys
import re
import time
from create_notion_page import create_notion_page
from read_version import read_version

def main():
    commits = sys.argv[1].split(',')
    for commit in commits:
        if commit:
            print(f"Processing commit: {commit}")

            commit_title = os.popen(f"git log -1 --pretty=%s {commit}").read().strip()
            ticket_number = re.search(r'[A-Z]+-[0-9]+', commit_title)
            if ticket_number:
                ticket_number = ticket_number.group()
            module_names = re.findall(r'@[a-zA-Z]+', commit_title)
            module_names = [name.replace('@', '').upper() for name in module_names if name.upper() != '@BILLING']
            module_names = ' '.join(module_names)

            commit_title = re.sub(r'([A-Z]+-[0-9]+|@[a-zA-Z]+)', '', commit_title).strip()
            commit_summary = os.popen(f"git log -1 --pretty=%b {commit}").read()

            version = read_version()

            if ticket_number or module_names:
                create_notion_page(ticket_number, module_names, commit_title, commit_summary, version)

            time.sleep(1)

if __name__ == "__main__":
    main()

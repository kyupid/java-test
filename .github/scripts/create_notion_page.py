import os
import requests
import json
import csv

def create_notion_page(ticket_number, module_names, commit_title, commit_summary, version):
    notion_api_key = os.environ['NOTION_API_KEY']
    notion_database_id = os.environ['NOTION_DATABASE_ID']

    github_email = os.popen("git log -1 --pretty=format:'%ae'").read().strip()
    notion_user_id = None

    with open('.github/db/notion-user-id.csv', 'r') as file:
        csv_reader = csv.reader(file)
        for row in csv_reader:
            if len(row) >= 2 and row[1] == github_email:
                notion_user_id = row[0]
                break

    properties = {
        "Version": {
            "select": {
                "name": version
            }
        },
        "기능": {
            "title": [
                {
                    "text": {
                        "content": commit_title
                    }
                }
            ]
        },
        "요약": {
            "rich_text": [
                {
                    "text": {
                        "content": commit_summary
                    }
                }
            ]
        }
    }

    if notion_user_id:
        properties["담당자"] = {
            "people": [
                {
                    "object": "user",
                    "id": notion_user_id
                }
            ]
        }

    if module_names:
        properties["모듈"] = {
            "multi_select": [{"name": name} for name in module_names.split()]
        }

    if ticket_number:
        properties["Jira"] = {
            "rich_text": [
                {
                    "text": {
                        "content": ticket_number,
                        "link": {
                            "url": f"https://kyupid-labs.atlassian.net/browse/{ticket_number}"
                        }
                    }
                }
            ]
        }

    payload = {
        "parent": {
            "database_id": notion_database_id
        },
        "properties": properties
    }

    headers = {
        "Authorization": f"Bearer {notion_api_key}",
        "Content-Type": "application/json",
        "Notion-Version": "2022-06-28"
    }

    response = requests.post("https://api.notion.com/v1/pages", json=payload, headers=headers)

    print(json.dumps(properties, ensure_ascii=False, indent=2))

    if response.status_code == 200:
        print("Notion page created successfully!")
    else:
        print(f"Failed to create Notion page. Status code: {response.status_code}")
        print(response.text)
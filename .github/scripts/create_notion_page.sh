#!/bin/bash

ticket_number=$1
commit_title=$2
commit_summary=$3

curl -X POST https://api.notion.com/v1/pages \
  -H "Authorization: Bearer $NOTION_API_KEY" \
  -H "Content-Type: application/json" \
  -H "Notion-Version: 2021-05-13" \
  --data '{
    "parent": { "database_id": "'"$NOTION_DATABASE_ID"'" },
    "properties": {
      "Jira Link": { "url": "https://your-jira-domain.com/browse/'"$ticket_number"'" },
      "Title": { "title": [{ "text": { "content": "'"$commit_title"'" } }] },
      "Summary": { "rich_text": [{ "text": { "content": "'"$commit_summary"'" } }] }
    }
  }'

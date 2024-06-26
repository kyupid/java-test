#!/bin/bash
#!/bin/bash

ticket_number=$1
module_names=$2
commit_title=$3
commit_summary=$4
version=$5

# 깃헙 이메일 기준으로 notion user id 읽기
github_email=$(git log -1 --pretty=format:'%ae')
notion_user_id=$(grep "$github_email" .github/db/notion-user-id.csv | cut -d',' -f1)

# Prepare the JSON payload
json_payload=$(cat <<EOF
{
  "parent": {
    "database_id": "$NOTION_DATABASE_ID"
  },
  "properties": {
    $(if [ -n "$notion_user_id" ]; then
      echo "\"담당자\": {"
      echo "  \"people\": ["
      echo "    {"
      echo "      \"object\": \"user\","
      echo "      \"id\": \"$notion_user_id\""
      echo "    }"
      echo "  ]"
      echo "},"
    fi)
    "Version": {
      "select": {
        "name": "$version"
      }
    },
    $(if [ -n "$module_names" ]; then
      echo "\"모듈\": {"
      echo "  \"multi_select\": ["
      echo "    $(echo $module_names | sed 's/\([^ ]*\)/{"name":"\1"}/g' | sed 's/} {/}, {/g' | sed 's/}, {$/}/')"
      echo "  ]"
      echo "},"
    fi)
    $(if [ -n "$ticket_number" ]; then
      echo "\"Jira\": {"
      echo "  \"rich_text\": ["
      echo "    {"
      echo "      \"text\": {"
      echo "        \"content\": \"$ticket_number\","
      echo "        \"link\": {"
      echo "          \"url\": \"https://kyupid-labs.atlassian.net/browse/$ticket_number\""
      echo "        }"
      echo "      }"
      echo "    }"
      echo "  ]"
      echo "},"
    fi)
    "기능": {
      "title": [
        {
          "text": {
            "content": "$commit_title"
          }
        }
      ]
    },
    "요약": {
      "rich_text": [
        {
          "text": {
            "content": "$(echo "$commit_summary" | sed 's/"/\\"/g' | sed -e ':a' -e 'N' -e '$!ba' -e 's/\n/\\n/g')"
          }
        }
      ]
    }
  }
}
EOF
)

echo "JSON Payload:"
echo "$json_payload"
echo ""

curl -X POST https://api.notion.com/v1/pages \
  -H "Authorization: Bearer $NOTION_API_KEY" \
  -H "Content-Type: application/json" \
  -H "Notion-Version: 2022-06-28" \
  --data "$json_payload"
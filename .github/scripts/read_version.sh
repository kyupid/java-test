#!/bin/bash

version=$(grep '^VERSION' ./kyupid.server.common/src/main/java/io/kyupid/v.properties | cut -d'=' -f2 | xargs)
IFS='.' read -ra version_parts <<< "$version"
major=${version_parts[0]}
minor=${version_parts[1]}
patch=${version_parts[2]}
if [ $patch -eq 9 ]; then
  if [ $minor -eq 9 ]; then
    major=$((major + 1))
    minor=0
    patch=0
  else
    minor=$((minor + 1))
    patch=0
  fi
else
  patch=$((patch + 1))
fi

updated_version="$major.$minor.$patch"
echo "$updated_version"
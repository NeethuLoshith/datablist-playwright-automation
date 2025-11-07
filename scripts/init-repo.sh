#!/bin/bash
echo "🚀 Initializing GitHub repo..."
git init
git add .
git commit -m "Initial commit - Datablist Playwright Automation"
if command -v gh &> /dev/null; then
  gh repo create datablist-playwright-automation --public --source=. --push
else
  echo "⚠️ GitHub CLI not installed. Push manually using:"
  echo "git remote add origin <your_repo_url>"
  echo "git push -u origin main"
fi

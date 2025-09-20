#!/bin/bash
# start-claude.sh
# Load project-specific env & start Claude

# Load env vars
if [ -f ".env" ]; then
    export $(grep -v '^#' .env | xargs)
fi

# Masuk working directory
cd "$CLAUDE_WORKDIR"

# Start Claude Code CLI
claude

#!/bin/bash
# File: track_claude_prompts.sh
# Deskripsi: Hitung jumlah prompt user di project Claude Code CLI + tampilkan tanggal file

CLAUDE_PROJECTS_DIR="$HOME/.claude/projects"
OUTPUT_FILE="prompt-usage/data.txt"

# Header
echo "Claude Code CLI - User Prompt Usage" > "$OUTPUT_FILE"
echo "Scan date: $(date)" >> "$OUTPUT_FILE"
echo "-----------------------------------" >> "$OUTPUT_FILE"
echo "File | Created Date | User Prompts" >> "$OUTPUT_FILE"
echo "-----------------------------------" >> "$OUTPUT_FILE"

# Loop semua .jsonl file
find "$CLAUDE_PROJECTS_DIR" -type f -name "*.jsonl" | while read file; do
    # Hitung jumlah prompt user
    count=$(grep -h '"type":"user"' "$file" 2>/dev/null | wc -l)
    # Ambil creation date (fallback ke modification date jika creation date tidak tersedia)
    created=$(stat -c %w "$file" 2>/dev/null)
    if [ "$created" = "-" ] || [ -z "$created" ]; then
        created=$(stat -c %y "$file")
    fi
    # Tulis ke output
    echo "$(basename "$file") | $created | $count" >> "$OUTPUT_FILE"
done

# Total semua prompt
total=$(grep -r '"type":"user"' "$CLAUDE_PROJECTS_DIR" 2>/dev/null | wc -l)
echo "-----------------------------------" >> "$OUTPUT_FILE"
echo "Total user prompts: $total" >> "$OUTPUT_FILE"

echo "Done! Results saved to $OUTPUT_FILE"

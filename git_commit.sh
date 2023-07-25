
while true; do
    # Read command from user
    read -p "Enter a commit comment (or 'exit' to quit): " comment

    # Check if the user wants to exit
    if [[ "$comment" == "exit" ]]; then
        echo "Exiting shell..."

        break
    fi

    # Execute the command
    eval "git add -A && git commit -a -m '$comment' && git push"
done



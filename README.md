Hello! Welcome to my project for data storage!

Clone the repo to your machine, then:

cp help-center-data-load/src/tools/2017-04-29-help-center-update-script.scala /bin

Edit your crontab to include this:

MAILTO="senterfeitrd@gmail.com" # my email for right now as a placeholder
0 20 * * * /bin/2017-04-29-help-center-update-script.scala > /bin/null



Answers to the theoretical questions in the assignment:
- How would you structure the schema to make it easily accessible to other internal systems?

- How would you best improve matches and eliminate false positives?
  - - I would probably do a comparison on what's in the database vs what's coming in from the JSON data. To eliminate
  errors from extra spacing or characters, I'd strip out all spaces and characters from both strings, lower case them,
  then compare the two. So "Clinic Name 1" in the database and "Clinic  Name  1" in the JSON would both convert to
  "clinicname1" for comparison. If the name string(s) matched, the address, zip, phone, and lat/long would be similarly
  compared, and if all of that data matched, the database wouldn't be updated with that clinic. If any data was found
  to be different, that data would be updated.
  - - Matches would be stored in a "duplicates" table, available for review later. There could even be a cron job which
  checked for data in that table, found the duplicate in the main table, then sent an email to a team for human review.

-
# Meetings
this is a living document containing the notes of all the meetings for the OOP Project

## Meeting 1 (By `Jochem Pouwels`)
### Agenda OOPP Group 48 Meeting 21/02

per the agenda template presented in OOPP materials

- Location: Drebbelweg IZ1
- Date: Feb 21st 2023
- Time: 13:45-14:30
- Attendees:
	- Rareș Toader (Scrum Master)
	- Vlad Graure
	- Olaf Herman (Chair 🪑)
	- Jesse Klijnsma
	- Lovro Mlikotić
	- Andrei Petre
	- Jochem Pouwels (Notetaker)

#### Talking points

- [13:45] Opening by Chair.
- [13:45-13:50] Approval of agenda: Does anyone have any last-minute additions?
- [13:50-14:00] Kickstarting the Code of Conduct-creation process.
	- Where? (Google Docs, Dropbox, etc.)
	- Who? (Communal or drafted up by one person based on discussed team mindset)
	- Views on what our Code of Conduct should look like as a team?
- [14:00-14:05] Clarifying team mindset on tech stack self-study: do we want to be familiar with our tech stack before we start coding? When do we want to be sure we're ready as a team?
- [14:05-14:25] Start drafting up project Backlog.
- [14:25-14:30] Passing the Chair and Notetaking for next meeting.
- [14:30] Closure by Chair.

### Code of Conduct
- made in Google drive
- One person makes a draft

- Share google drives emails

#### Core values
- Respect
- Honesty
#### Assignment description
- needs to be in the 
#### Goal
- we strife for a 9
#### What should we deliver
- Code and intermediate assignments in gitlab, and COC in Google Drive.
#### Standards
- meet the assignments
- quality over quantity

#### Things to do
- check up on each other to see wether they are done
- checkups two days before the check-up
#### How do we treat each other
- respectfully
#### Disagreements
- we should all be alive at the end
- talk out, even after meeting untill we are done.
- perhaps a coin flip, when in a deadlock
- we can ask TA for tie-breaker (should ask cute)

#### What to do when someone is late
- text when you are late
- start meeting, no recap

### Team familiarity with tech-stack
- everyone should familiarize themselves with each part of our tech-stack (JFX, springboot, git, e.t.c.)
- make discord server
	- list everything we need to do
	- send links on minimun needed knowledge for each part of the code.
	- Jochem sets up discord

### Backlog
- make a backlog with project-requirements (deadline friday)
- we get official backlog, for when we missed something on our own one.
- backlog should consist mostly of user stories.

### Chair and Notetaker for next meeting
#### Week 3
`chair`: `Andrei Petre`
`note taker`: `Jesse Klijnsma`
#### Week 4
`chair`: `Vlad Graure`
`notetaker`: `Lovro Mlikotić`
#### Week 6
`chair`: `Lovro Mlikotić`

### TA Announcements
- we get the repo ASAP
- in the repo we should post all deliverables (agenda, notes, e.t.c)

### Closing Time
- some people to stay to discuss over `COC`



## Meeting 2 (By `Jesse Klijsma`)
### Agenda OOPP Group 48 Meeting 28/02
*per the agenda template presented in OOPP materials*

* Location: DW PC1 cubicle 11
* Date: Tu 28th 2023
* Time: 13:45-14:30
* Attendees:
  * Rareș Toader (Scrum Master)
  * Vlad Graure
  * Olaf Herman
  * Jesse Klijnsma (Notetaker)
  * Lovro Mlikotić
  * Andrei Petre (Chair 🪑)
  * Jochem Pouwels 

#### Talking points
- [13:45] Opening by Chair.
- [13:45-13:50] Approval of agenda: Does anyone have any last-minute additions?
- [13:50-14:00] Reviewing and approving the final backlog submission.
- [14:00-14:05] Making sure that everybody’s git is working as intended.
- [14:05-14:30] Discussing the assignment of tasks to each member of the team.
- [14:30] Closure by Chair

### Additions to the agenda
- Fixing the Code of Conduct

### Backlog
- Changes made during the meeting
  - Added more administrative functions
  - Added password to advanced 

### Git
- **Everyone has a fully working git setup**

### Changes to Code of Conduct
- Added 5th evaluation criterium
  - Added quality of feedback
- Change commitment
  - Quality of work
    - Done in time
    - By the norm of evaluation criteria
  - Quality of notes
    - Readable and easy to understand
    - Explains all points

### Task Assignments

| Task | Who is responsible | Deadline |
|------|--------------------|----------|
| Add Mockups | Lovro | 03-03-2023 |
| Refactor Epics | Everyone | 28-02-2023 |
| Create EER Diagram | Everyone | 28-02-2023 |
| Finish Backlog | Everyone | 28-02-2023 |

### Closing Time
- Everyone decided to stay over to finish some of the tasks



## Meeting 3 (by `Lovro Mlikotić`)
### Agenda OOPP Group 48 Meeting 07/03
*per the agenda template presented in OOPP materials*

* Location: DW PC1 cubicle 11
* Date: March 7th 2023
* Time: 13:45-14:30
* Attendees:
    * Rareș Toader (Scrum Master)
    * Vlad Graure (Chair 🪑)
    * Olaf Herman
    * Jesse Klijnsma
    * Lovro Mlikotić (Notetaker)
    * Andrei Petre
    * Jochem Pouwels

#### Talking points
- [13:45] Opening by Chair.
- [13:45-13:48] Check in: how is everyone doing so far?
- [13:48-13:50] Does anyone have any additions to the agenda?
- [13:55-14:05] Forming our CheckStyle and adding it to the team repository.
- [14:05-14:15] Discussing the official Backlog and identifying differences to our own.
- [14:15-14:25] Allocating tasks to each team member and creating issues on GitLab.
- [14:25-14:30] Final questions.
- [14:30] Closure by Chair.

#### Showcasing our work so far to the TA
- no additional feedback recieved

#### CHECKSTYLE
Checkstyle rules that are agreed upon:
    
    1) cyclomatic complexity limit - 8
    2) method length - 50 lines
    3) max length of line - 100 char
    4) javadoc enforced on public methods
    5) consecutive capital letters in identifiers allowed
    6) left curly braces in the same line as method name
    7) there should be no star imports except 
        java.io, java.net, java.lang.Math

Other CheckStyle modules used:
    
    - Indentation
    - NoWhitespaceAfter
    - NoWhitespaceBefore
    - MissingOverride
    - ConstantName
    - PackageName
    - EmptyBlock


#### Misc talking points
- all references to quizzzz (template project name) should be replaced by talio (current porject name)

#### BACKLOG
- password protection will be done last (as per the backlog)

### Task Assignments
- CRUD operations - next meeting
- controllers for each view
- add workspace class

ISSUES CREATED ON GITLAB

Server-side
- create Task controller
- create TaskList controller
- create Board controller

Client-side
- add board list on the main scene
- add BoardView controller

Commons
- create scenes for CRUD opertions for every class

#### Closing Time
- Everyone decided to stay over to finish some of the tasks



## Meeting 4 (by `Petre Andrei`)
### Agenda OOPP Group 48 Meeting 21/03
*per the agenda template presented in OOPP materials*

* Location: DW PC1 cubicle 11
* Date: March 21st 2023
* Time: 13:45-14:30
* Attendees:
    * Rareș Toader (Scrum Master)
    * Vlad Graure
    * Olaf Herman
    * Jesse Klijnsma
    * Lovro Mlikotić (Chair 🪑)
    * Andrei Petre (Notetaker)
    * Jochem Pouwels

#### Talking points
- [13:45] Opening by Chair.
- [13:45-13:48] Check in: how is everyone doing?
- [13:48-13:50] Does anyone have any additions to the agenda?
- [13:50-13:55] Showcasing the current state of our project to the TA
- [13:55-14:00] Making sure all team members understand the HUE task.
- [14:00-14:15] Hueristic Usability Evaluation:
  - going over our GUI.
  - discussion on how to approach the evaluation and what needs to be done.
  - division of tasks.
- [14:15-14:25] Product analysis:
  - Are we on track so far, what was done well and what needs to be changed?
  - What else needs to be implemented and how?
- [14:25-14:30] Each member of the team recieves a task and a deadline.
- [14:30] Closure by Chair.

#### Showcasing our work so far to the TA
- finishing the basic requierments.

#### Feedback on team management from the TA
- improving on commit descriptions,
- equalizing our work on the project.
- improving our task assignment on gitlab.
- connections don't need to be tested.
- setting up milestones and labels.

#### Hueristic Usability Evaluation Assignment
- creating an interactive Power Point project for the prototype.
- the TA will chose the other team for our HUE assignment.

### Tasks for the week
- creating prototype for HUE.
- holdong a meeting for the HUE report.
- creating git issues.
- tasks will be assigned after the meeting.

#### Closing Time
- Everyone decided to stay over to assign tasks to each member and create issues based on the backlog.


## Meeting 5 (by `Vlad Graure`)
### Agenda OOPP Group 48 Meeting 28/03
*per the agenda template presented in OOPP materials*

* Location: DW PC1 cubicle 11
* Date: March 28th 2023
* Time: 13:45-14:30
* Attendees:
    * Rareș Toader (Scrum Master)
    * Vlad Graure (Notetaker)
    * Olaf Herman
    * Jesse Klijnsma
    * Lovro Mlikotić
    * Andrei Petre
    * Jochem Pouwels (Chair 🪑)

#### Talking points
- [13:45] Open by Chair.
- [13:45-13:48] Check-in: how is everyone doing?
- [13:48-13:50] Approval of the agenda: does anyone have any additions to the agenda?
- [13:50-13:55] Product progress: showcasing the current state of our project to the TA.
- [13:55-14:00] HUE: Making sure all team members understand where we are. And what we still need to finish before sending the evaluation.
- [14:00-14:15] Next steps
    - Decide what we still need to do and what to focus on.
    - Divide tasks.
- [14:15-14:25] Product analysis
    - Are we on track so far, what was done well and what needs to be changed?
    - What else need to be implemented and how?
- [14:25-14:30] Task assignments: each member of the team receives a task and a deadline.
- [14:30] Closure by Chair.

#### Additions to the Agenda
- Product Pitch assignment - discussion

#### Showcasing our work so far to the TA
- the server selection feature fulfills the Backlog

#### HUE
- we received the Prototype from our partner team(it can be found in Google Drive)
- we will evaluate the other team's prototype by Thursday(30.03)
- each of us will evaluate the prototype individually and write the results in a Google Drive word document

#### Misc talking points
- merge websockets as soon as possible, so we can fix eventual bugs
- first merge Lovro's branch so Olaf can incorporate the changes into his one
- agreed on removing the index from the TaskList class as it is redundant

#### Task Assignments
The most important tasks for the next sprint are:
  1) Tag - create the class, a REST controller and the front end
  2) Task description - implement the description and the subtask part
  3) Complete websockets

#### Product Pitch
- we will do the final video together
- we will do a script, so the video will be cursive

#### Feedback from the TA
- looks are important for the project grade
- it is more important to implement a feature very good than to poorly implement a lot of the features
- the technology part is very good
- consider how important the features are

#### Announcements by the TA
- we are going to get feedback for the features
- the video should be encoded, so it has at most 100MB but the quality should still be decent(720p)
- the last 2 meetings will be shorter (25 minutes), but the location and the starting time will remain the same
- everyone should have at least 800 lines of code by the end of the project

#### Closing Time
- Some team members decided to stay over to merge some MRs and discuss future steps


## Meeting 6 (by `Olaf Herman`)
### Agenda OOPP Group 48 Meeting 28/03
*per the agenda template presented in OOPP materials*

* Location: DW PC1 cubicle 11
* Date: April 4th 2023
* Time: 13:45-14:10
* Attendees:
    * Rareș Toader (Scrum Master)
    * Vlad Graure
    * Olaf Herman (Notetaker)
    * Jesse Klijnsma (Chair 🪑)
    * Lovro Mlikotić
    * Andrei Petre
    * Jochem Pouwels

#### Talking points
- [13:45] Open by Chair.
- [13:45-13:46] Check-in: how is everyone doing?
- [13:46-13:47] Approval of the agenda: does anyone have any additions to the agenda?
- [13:47-13:50] Product progress: showcasing the current state of our project to the TA.
- [13:50-13:56] HUE:
    - Settle on changes we want to make to the app.
    - Assign tasks for completing the report.
- [13:56-14:03] Contribution deadline - Final merge requests:
    - Set deadline for opening merge requests.
    - Set deadline for merging merge requests.
    - Discuss what final features and fixes we'll implement.
- [14:03-14:08] Discuss Product Pitch
    - Agree upon date and time to record video.
    - Create a script or list of bullet points.
- [14:08-14:10] Task assignments: each member of the team receives a task and a deadline.
- [14:10] Closure by Chair.

#### Additions to the Agenda
- None, it's a really dense agenda already :)

#### Showcasing our work so far to the TA
- The tags feature works, and we have working long-polling.

#### HUE
- We received the feedback from the other team. What changes will we make?:
  - We already renamed "remove" to "leave" for boards.
  - We will add customization to boards and tasks.
  - We will add functionality to file / edit / view buttons in the taskbar.
  - We already added missing tags functionality and their input.
  - Constrain task(list) names as not to overflow their labels.

#### Contribution deadline
- There might be a deadline extension from the course for the project code.
- The deadline for opening merge requests will be Wednesday April 5th at 18:00
- There is no merging deadline: Merge asap
- As there will most probably be a deadline extension, we will not finally settle on which features to or not to include.

#### Discuss Product Pitch
- We will create a list of bullet points to talk about, everyone will record their own part and will be edited into a final video.

#### Task Assignments
- We will try to have all code finished by friday, and use the weekend to create the product pitch. (It was just announced that the deadline *will* be extended to next Tuesday.) We have not settled on exactly who does what.

#### Feedback from the TA
- All code related contributions are looking fine.

#### Closing Time
- Some team members decided to stay over to merge some MRs and discuss future steps

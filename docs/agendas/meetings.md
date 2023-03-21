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

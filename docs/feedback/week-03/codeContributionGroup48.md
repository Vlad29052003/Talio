# Code Contributions and Code Reviews

#### Focused Commits

Grade: Good!

Feedback: The repository obtained a good amount of commits in the past week, however it seemed that each person had around 2 commits, which is still a bit far from what should be the norm. The commits only affect a few files, and they mostly constitute a coherent change to the system. Most commit's messages concretely summarise the change. Your source code does not have a lot of commented code. My main advice is here, please keep the commit messages to be serious and to just summarise the change (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/commit/8cf46ef80c878b3d9f417368746ad623fe4a0621), and please try to make small changes per commit, and that the changes do not affect a lot of files(https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/commit/8cf46ef80c878b3d9f417368746ad623fe4a0621).


#### Isolation

Grade: Very good!

Feedback: The group used branches to isolate different features that were getting developed, and the repository contains a good amount of merge requests. Most merge requests had a focus on a certain feature, which is a great thing. I would recommend you shy away from really small merge requests that could have been just a commit instead. (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/merge_requests/18/diffs and https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/merge_requests/14/diffs).

#### Reviewability

Grade: Sufficient!

Feedback: Most merge requests have an eloquent title, that clearly describes the merge request, but the descriptions of some merge requests are too short or inexistent. Please make sure to also have a clear description of the MR. The MR mostly cover a small number of commits, and the changes are coherent and are connected with eachother (good thing). The branch of the MR's is recently created (also a good thing). Some MR's mostly contain formatting changes such as deletion of unused imports, code reformatting (tabs, spaces) such as https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/merge_requests/18/diffs, whereas these type of changes should be just made in simple commits. You should restrict yourself to use merge requests for features. The deviation to main is very small, as most branches have been merged to main.

#### Code Reviews

Grade: Insufficient

Feedback: I cannot quite give you feedback on this rubric section, as you did not review eachother's merge requests. I mentioned it in the meeting, please take the time to review the other's merge requests, by asking questions, giving feedback, suggesting changes. Whatever is said, must be constructive, of course. Remember to try to review MR in a timely manner. By doing these reviews, you can spot bugs early before merging a feature, you can actually learn by looking at how your teammates implement certain details, and this will lead to an iterative improvement of the codebase.


#### Build Server

Grade: Good!

Feedback: The team selected a very good amount of checkstyle rules. The team also committed and pushed quite frequently. The build duration is reasonable. Builds sometimes failed on the main branch or other branches. I suggest solving these problems locally first, so you will not clutter the codebase with commits such as "Fix pipeline" or "Fix checkstyle". You can do the same steps as the CI/CD pipeline locally, and also things like seeing if checkstyle passes for you. 

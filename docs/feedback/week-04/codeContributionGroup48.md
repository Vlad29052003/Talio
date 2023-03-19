# Code Contributions and Code Reviews


#### Focused Commits

Grade: Very good!

Feedback: The repository has a good amount of commits. Most commits only affect a small number of files and constitute a coherent change to the system. The commit messages are concise and describe the change clearly. The size of some commits is still quite large(https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/commit/1a81e5a1550f2b1f200a7e8e9f76bd32a7756db5).


#### Isolation

Grade: Very good!

Feedback: The group uses feature branches/merge requests to isolate individual features during development, which is great! The degree of focus on each merge request is quite high, I managed to find one very large merge request (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/merge_requests/28/diffs), but the content was mostly an isolated feature. 


#### Reviewability

Grade: Good!

Feedback: Most merge requests have a clear focus that becomes clear from the title and/or description. Some merge requests don't contain a description at all, and they should have (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/merge_requests/34 and https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/merge_requests/30). Merge requests contain code changes and only a low number of formatting changes. I recommend not creating merge requests for deleting code, you could do that in a single commit directly (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/merge_requests/31/diffs).  Merge requests cover a small amount of commits, this is great! The changes is MR's are coherent and relate to eachother. Merges happen usually not after a long time after the creation of the branch.


#### Code Reviews

Grade: Good!

Feedback: Code reviews on gitlab actually happened as a back and forth discussion, and this lead to iterative improvements of the code. However, I see that only a few of you actively participate in code reviews, and I want to reiterate that everyone should be active in code reviews! Just accepting a merge request directly is not enough. Comments in the MRs are constructive and goal oriented. I want to also mention that a larger MR should receive more attention during review, than a small one. For a small merge request it is probably fine to not have a thorough review, but for a large MR such as the following, having only a couple of comments are not enough (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-48/-/merge_requests/28).

#### Build Server

Grade: Excellent!

Feedback: The team has selected enough checkstyle rules. Build times are reasonable. Failing builds are fixed directly after them failing, which is great! Builds don't fail on main, well done! The team also has commited and pushed frequently.
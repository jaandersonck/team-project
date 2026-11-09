
## Team Information  
### Team Members  
- Jack Anderson  
- Jason Luo  
- Taha Tahir  
- Yusuke Ichiba  
---  
## Project Description  
GitHub repo analyzer allows members across a software development team to quickly visualize important metrics about project repositories.
### Domain  
[Project description]  
---  
## User Stories
### User Story 1
*As a developer on a software team, I want to sign in with my GitHub personal access token so that the app can read the repositories (including private ones) that I have access to.*
- **Use case:** UC1 · **Owner:** 
- **Acceptance criteria:**
	- A valid token takes me to repository selection and shows my GitHub username.
	- An empty, invalid, or expired token shows a clear error and nothing is saved.
	- If I tick "remember me", the next launch skips the login screen.

### User Story 2
*As a developer who works on several projects, I want to choose one of my repositories from a list (or type `owner/name`) so that I don't have to look up its exact name every time.*
- **Use case:** UC2 · **Owner:** 
- **Acceptance criteria:**
	- The list shows every repository my token can see.
	- Choosing a repository opens its dashboard (ownership, commit graph, pull requests).
	- A repository that doesn't exist or that I can't access shows "Repository not found".

### User Story 3
*As a new contributor, I want to see who has contributed most to each directory so that I know who to ask about, or request a review from, for that part of the code.*
- **Use case:** UC3 · **Owner:** 
- **Acceptance criteria:**
	- The repository is shown as an expandable directory tree.
	- Each directory shows its top contributor and that contributor's share of commits.
	- Directories with no commit history are labelled "no owner".

### User Story 4
*As a team lead, I want to see the repository's commit history as a graph so that I can tell how active the project is and who is contributing.*
- **Use case:** UC4 · **Owner:** 
- **Acceptance criteria:**
	- Commits on the default branch are shown in chronological order, linked to their parents.
	- Each commit is identified by author, and shows its message, author, and date on selection.
	- An empty repository shows a message rather than an empty graph.

### User Story 5
*As a team lead overseeing a sprint, I want to limit the commit graph to a date range so that I only see the work done during that time period.*
- **Use case:** UC5 · **Owner:** 
- **Acceptance criteria:**
	- I can enter a start and end date, or pick a preset (last 7 / 30 / 90 days).
	- Only commits inside the range are shown.
	- A start date after the end date is rejected with an error.

### User Story 6
*As a code reviewer, I want to see all open pull requests and how long each has been open so that I can prioritise the stale ones.*
- **Use case:** UC6 · **Owner:** 
- **Acceptance criteria:**
	- Each open PR shows its number, title, author, age in days, and draft status.
	- PRs are sorted oldest first.
	- A repository with no open PRs shows "No open pull requests".

### User Story 7
*As a user on a shared computer, I want to log out so that my token is not left on the machine.*
- **Use case:** UC7 · **Owner:** 
- **Acceptance criteria:**
	- Logging out returns me to the login screen.
	- The saved token is deleted, so the next launch asks for a token again.

---
## Use Cases
### Use Case 1: Log in with a GitHub token
- **Owner:**  · **Story:** US1
### Use Case 2: Select a repository
- **Owner:**  · **Story:** US2
### Use Case 3: View directory ownership
- **Owner:** · **Story:** US3
### Use Case 4: View the commit graph
- **Owner:** · **Story:** US4
### Use Case 5: Filter the commit graph by a time period
- **Owner:**  · **Story:** US5
### Use Case 6: View open pull requests
- **Owner:**  · **Story:** US6
### Use Case 7: Log out
- **Owner:**  · **Story:** US7
---
## Minimal Viable Product (MVP)
| Team Member | MVP Use Case                    |
| ----------- | ------------------------------- |
|             | UC1: Log in with a GitHub token |
|             | UC2: Select a repository        |
|             | UC3: View directory ownership   |
|             | UC4: View the commit graph      |

**After MVP:** UC5, UC6, UC7.

---
## Entities  
### Entity 1

### Entity 2  
(Add additional entities as needed.)  
---  
## API Usage  
**API Name:**  GitHub API
**Documentation:** [Link]  
**How it is used in the program:**  
---  
## Design Diagrams  
### UML Class Diagram  
### Sequence Diagrams  
---  
## How to Run  
[Instructions for running the program.]  
---  
## Notes  
[Any additional project information.]

## Annotations

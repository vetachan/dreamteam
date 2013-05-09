Dream Team
==========

- Download and install Eclipse for Java EE Developers (http://www.eclipse.org/downloads/)

- Open Eclipse and go to Help > Install New Software and add the following locations:

- http://dist.springsource.com/release/TOOLS/gradle and install Gradle IDE and Eclipse Integration Commons.
- http://download.eclipse.org/egit/updates only in case EGit is not installed (should be).
- http://download.eclipse.org/egit/github/updates and install Eclipse EGit Mylyn GitHub Feature.

- Go to Window > Open Perspective > Other and open the Git Repository Exploring perspective.

- Click on clone a repository from GitHub and search for "dreamteam". Select "vetachan/dreamteam" once it is listed.

- Right click on the new repository and select Import project as a General Project.

- Go now to Window > Open Perspective > Other and select the Java perspective.

- Right click on the new project and go to Configure > Convert to Gradle Project.

- Now click on Run and select Gradle Build.

- A new window will appear to configure the tasks to be executed. Select only the task "run" and click on Run.

That's all!

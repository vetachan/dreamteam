Dream Team
==========

#### Install Eclipse and plugins for GitHub and Gradle

1. Download and install [Eclipse for Java EE Developers](http://www.eclipse.org/downloads/)

2. Open Eclipse and go to **Help > Install New Software** and add the following locations:
  * http://dist.springsource.com/release/TOOLS/gradle and install *Gradle IDE* and *Eclipse Integration Commons*.
  * http://download.eclipse.org/egit/updates only in case *EGit* is not installed (should be).
  * http://download.eclipse.org/egit/github/updates and install *Eclipse EGit Mylyn GitHub Feature*.

#### Import project from GitHub

1. Go to **Window > Open Perspective > Other** and open the *Git Repository Exploring* perspective.

2. Click on clone a repository from GitHub and search for "dreamteam". Select "vetachan/dreamteam" once it is listed.

3. Right click on the new repository and select *Import project* as a *General Project*.

#### Configure and execute the project

1. Go now to **Window > Open Perspective > Other** and select the *Java perspective*.

2. Right click on the new project and go to **Configure > Convert to Gradle Project**.

3. Now click on *Run* and select *Gradle Build*.

4. A new window will appear to configure the tasks to be executed. Select only the task "run" and click on *Run*.

That's all and good luck with your team! :)

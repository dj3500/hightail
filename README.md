## Hightail

### [Downloads](https://github.com/dj3500/hightail/releases)

Hightail is an automatic tester for programming contests such as [CodeForces](http://codeforces.com) rounds. It will parse the problem statement, extract sample test cases (inputs and outputs) from it, and verify the correctness of your program against them. It is built to provide maximum automation and to relieve the contestant as much as possible.

A list of Hightail's features:
* parsing problem statements
* parsing entire contests (autoloading all problems)
* scheduling contest parsing ahead of time
* ability to comfortably view, edit and add test cases
* handling of all verdicts: WA, TLE, RE, even AC
* customizable time limits
* ability to create in/out files in your working directory
* detection of floating point values (comparing 0.1 vs. 0.10 does not give WA)
* easy-to-use UI, keyboard shortcuts
* resilient: it is multi-threaded so it will not let your program hang it; it will withstand large amounts of output from your program
* support for all contest languages (C++, Java, Python, ...)
* written in Java and should run on any OS

It supports the following online judges:
* CodeForces (incl. Gym)
* CodeChef (experimental)
* AtCoder (now also live-running contests!)
* Jutge.org
* Open Kattis

Additionally, the [Competitive Companion](https://github.com/jmerle/competitive-companion) browser extension can be used to parse problems and contests from the browser. It is available for [Chrome](https://chrome.google.com/webstore/detail/competitive-companion/cjnmckjndlpiamhfimnnjmnckgghkjbl) and [Firefox](https://addons.mozilla.org/en-US/firefox/addon/competitive-companion/). It supports many more online judges than the above (Google Code Jam, Facebook Hacker Cup, ...). Just install the browser extension, run Hightail, go to a problem/contest, and click the green plus button in your browser - it should parse and load into Hightail.

We do not plan on supporting the TopCoder Arena: it is a different architecture, and there are a lot of good plugins available for it. Also, if you use Java for contests, we recommend to take a look at Egor Kulikov's [CHelper](http://codeforces.com/blog/entry/3273) for IntelliJ IDEA.

### Screenshots and screencasts

![Screenshot of Hightail](http://dj3500.webfactional.com/hightail1.png)
![Screenshot of Hightail](http://dj3500.webfactional.com/hightail2.png)

Short [video](https://www.youtube.com/watch?v=LnI4HuzWY18) of using Competitive Companion to parse tasks from the browser.

Hightail also appears in dj3500's screencasts. See [this one](https://www.youtube.com/watch?v=3yG7ivRRQW4&hd=1#t=265s) for an example.

### How do I get Hightail?

To get a fairly up-to-date version - in the form of a .jar file which is ready to run if you have Java (JRE) installed - visit [Downloads](https://github.com/dj3500/hightail/releases). Just save it somewhere on your computer and run it from there. It will create a config file in its directory.

~An alternative way, if you're on Linux and have [snap](https://snapcraft.io/) installed, is to run `sudo snap install --edge hightail`. Then Hightail should auto-update every day (to the latest code from the master branch).~ (currently this is broken)

You may also compile Hightail from source code using the instructions below (and then you will also be able to work on the code, e.g. to help in development), or by following http://codeforces.com/blog/entry/6941#comment-125761 (which might be a little easier - or not).

### How to download the code and work on Hightail?

Hightail is developed in NetBeans (the UI in particular) and it is highly advisable to use it.

1. Download and install NetBeans (also JDK if you don't have it; there's a bundled version available). I have used NetBeans 8.0 (and Windows) for this tutorial.
2. While it's installing, sign up on Github if you don't have an account. Go to https://github.com/dj3500/hightail and fork the repository (using the Fork button).
3. Fire up NetBeans and select Team -> Git -> Clone; Repository URL: https://github.com/YOURUSERNAME/hightail.git/, also enter your Github username and password and select Save password; Next, Next, Finish.
4. If it says "Hightail project was cloned. Do you want to open the project?", select Open Project. If it doesn't, or says something else (like prompting you to "Create a project"), cancel and open the imported project yourself (File -> Open Project).
5. Change stuff, test, build, run, etc.
6. Once you have a working change: Team -> Commit, enter a commit description, Commit. Keep in mind that with Git, commits are only local. You can continue to make new commits. When you want to push your tree (of commits) to Github, select Team -> Remote -> Push To Upstream. The changes will be now publicly visible in your GitHub repository.
7. When you want to submit your changes to dj3500 for testing and review (please do test them thoroughly beforehand, though), create a pull request (from the web interface at https://github.com/YOURUSERNAME/hightail). The pull request model is documented at https://help.github.com/articles/using-pull-requests.

It is better to use branches for different features, and read up on Git in general, but the above should be enough to get you started.

### Ways you can help

Hightail is a community project, developed by volunteers. We'd love for you to join in the effort.

* It couldn't hurt to make Hightail more popular. If you use it, consider putting a comment in the code of your solutions, like
`// tested by Hightail - https://github.com/dj3500/hightail`. Also, tell your friends.
* There is a list of known problems and possible enhancements that you can work on fixing here: https://github.com/dj3500/hightail/issues?state=open. Many of them will be easy or fast to solve, they just need a little attention :)
* You can also report new issues or give new ideas.
* The project does not currently need any donations.

There is also a discussion topic on CodeForces ([here](http://codeforces.com/blog/entry/13141)).

### Contributors

The project was started and is maintained by Jakub Tarnawski ([dj3500](http://codeforces.com/profile/dj3500)). A large amount of work was done by contributors, some of whom are listed [here](https://github.com/dj3500/hightail/graphs/contributors) and others include Piotr Szcześniak and Robert Rosołek - thank you so much!


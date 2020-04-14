![Open Source Society University (OSSU)](http://i.imgur.com/kYYCXtC.png)

<h3 align="center">Open Source Society University</h3>
<p align="center">
  Path to a free self-taught education in Computer Science!
</p>
<p align="center">
  <a href="https://github.com/sindresorhus/awesome">
    <img alt="Awesome" src="https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg">
  </a>
  <a href="https://github.com/ossu/computer-science">
	<img alt="Open Source Society University - Computer Science" src="https://img.shields.io/badge/OSSU-computer--science-blue.svg">
  </a>
</p>

# Contents

- [Summary](#summary)
- [Community](#community)
- [Curriculum](#curriculum)
- [Code of conduct](#code-of-conduct)
- [Team](#team)
- [References](#references)

# Summary

The OSSU curriculum is a **complete education in computer science** using online materials.
It's not merely for career training or professional development.
It's for those who want a proper, *well-rounded* grounding in concepts fundamental to all computing disciplines,
and for those who have the discipline, will, and (most importantly!) good habits to obtain this education largely on their own,
but with support from a worldwide community of fellow learners.

It is designed according to the degree requirements of undergraduate computer science majors, minus general education (non-CS) requirements,
as it is assumed most of the people following this curriculum are already educated outside the field of CS.
The courses themselves are among the very best in the world, often coming from Harvard, Princeton, MIT, etc.,
but specifically chosen to meet the following criteria.

**Courses must**:
- Be open for enrollment
- Run regularly (ideally in self-paced format, otherwise running at least once a month or so)
- Fulfill the [academic requirements](REQUIREMENTS.md) of OSSU
- Fit neatly into the progression of the curriculum with respect to topics and difficulty level
- Be of generally high quality in teaching materials and pedagogical principles

When no course meets the above criteria, the coursework is supplemented with a book.
When there are courses or books that don't fit into the curriculum but are otherwise of high quality,
they belong in [extras/courses](extras/courses.md) or [extras/readings](extras/readings.md).

**Organization**. The curriculum is designed as follows:
- *Intro CS*: for students to try out CS and see if it's right for them
- *Core CS*: corresponds roughly to the first three years of a computer science curriculum, taking classes that all majors would be required to take
- *Advanced CS*: corresponds roughly to the final year of a computer science curriculum, taking electives according to the student's interests
- *Final Project*: a project for students to validate, consolidate, and display their knowledge, to be evaluated by their peers worldwide


**Duration**. It is possible to finish Core CS within about 2 years if you plan carefully and devote roughly 18-22 hours/week to your studies.
Courses in Core CS should be taken linearly if possible, but since a perfectly linear progression is rarely possible,
each class's prerequisites are specified so that you can design a logical but non-linear progression
based on the class schedules and your own life plans.

**Cost**. All or nearly all course material is available for free. However, some courses may charge money for assignments/tests/projects to be graded.
Note that Coursera offers [financial aid](https://learner.coursera.help/hc/en-us/articles/209819033-Apply-for-Financial-Aid).

Decide how much or how little to spend based on your own time and budget;
just remember that you can't purchase success!

**Process**. Students can work through the curriculum alone or in groups, in order or out of order.
- For grouping up, please use the [cohorts repository](https://github.com/ossu/cohorts) to find or create a cohort suited to you.
- We recommend doing all courses in Core CS, only skipping a course when you are certain that you've already learned the material previously.
- For simplicity, we recommend working through courses (especially Core CS) in order from top to bottom, as they have already been [topologically sorted](https://en.wikipedia.org/wiki/Topological_sorting) by their prerequisites.
- Courses in Advanced CS are electives. Choose one subject (e.g. Advanced programming) you want to become an expert in and take all the courses under that heading. You can also create your own custom subject, but we recommend getting validation from the community on the subject you choose.

**Content policy**. If you plan on showing off some of your coursework publicly, you must share only files that you are allowed to.
*Do NOT disrespect the code of conduct* that you signed in the beginning of each course!

**[How to contribute](CONTRIBUTING.md)**

**[Getting help](HELP.md)** (Details about our FAQ and chatroom)

# Community

- We have a chat room! This should be your first stop to talk with other OSSU students. Why don't you introduce yourself right now? [Join the chat in Gitter](https://gitter.im/open-source-society/computer-science) [![Join the chat at https://gitter.im/open-source-society/computer-science](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/open-source-society/computer-science?utm_campaign=pr-badge&utm_content=badge&utm_medium=badge&utm_source=badge)
- You can also interact through [GitHub issues](https://github.com/ossu/computer-science/issues). If there is a problem with a course, or a change needs to be made to the curriculum, this is the place to start the conversation. Read more [here](CONTRIBUTING.md).
- Add **Open Source Society University** to your [Linkedin](https://www.linkedin.com/school/11272443/) profile!
- Note: There is an unmaintained and deprecated firebase app that you might find when searching OSSU. You can safely ignore it. Read more in the [FAQ](./FAQ.md#why-is-the-firebase-ossu-app-different-or-broken).

# Curriculum

**Curriculum version**: `8.0.0` (see [CHANGELOG](CHANGELOG.md))

- [Prerequisites](#prerequisites)
- [Intro CS](#intro-cs)
  - [Introduction to Programming](#introduction-to-programming)
  - [Introduction to Computer Science](#introduction-to-computer-science)
- [Core CS](#core-cs)
  - [Core programming](#core-programming)
  - [Core math](#core-math)
  - [Core systems](#core-systems)
  - [Core theory](#core-theory)
  - [Core applications](#core-applications)
  - [Core security](#core-security)
- [Advanced CS](#advanced-cs)
  - [Advanced programming](#advanced-programming)
  - [Advanced systems](#advanced-systems)
  - [Advanced theory](#advanced-theory)
  - [Advanced applications](#advanced-applications)
- [Final project](#final-project)

---

## Prerequisites

- [Core CS](#core-cs) assumes the student has already taken [high school math](https://www.khanacademy.org/math/high-school-math), including algebra, geometry, and pre-calculus.
- [Advanced CS](#advanced-cs) assumes the student has already taken the entirety of Core CS
and is knowledgeable enough now to decide which electives to take.
- Note that [Advanced systems](#advanced-systems) assumes the student has taken a basic physics course (e.g. AP Physics in high school).

## Intro CS

### Introduction to Programming

If you've never written a for-loop, or don't know what a string is in programming, start here. Choose one of the two course series below. Either one will give you an introduction to programming that assumes no prior knowledge.

Trying to decide between them?

_Python for Everyone_ will introduce you to a popular language and will quickly move to practical programming tasks - using web APIs and databases. This will give you a taste of what many professional developers do.

_Fundamentals of Computing_ will also start by introducing you to Python. It then moves on to give an introduction to academic Computer Science topics, like sorting and recursion. This will give you a taste of what the following courses will be like. (Students who complete _Fundamentals of Computing_ can skip Intro to Computer Science and begin Core CS.)

**Topics covered**:
`simple programs`
`simple data structures`

Courses | Effort | Prerequisites
:-- | :--: | :--:
[Python for Everyone](https://www.coursera.org/specializations/python) ([alt](https://www.py4e.com/)) | 58 hours | none
[Fundamentals of Computing](https://www.coursera.org/specializations/computer-fundamentals) | 138 hours | high school mathematics

### Introduction to Computer Science

This course will introduce you to the world of computer science. Students who have been introduced to programming, either from the courses above or through study elsewhere, should take this course for a flavor of the material to come. If you finish the course wanting more, Computer Science is likely for you!

**Topics covered**:
`computation`
`imperative programming`
`basic data structures and algorithms`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Introduction to Computer Science and Programming using Python](https://www.edx.org/course/introduction-computer-science-mitx-6-00-1x-10) ([alt](https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-0001-introduction-to-computer-science-and-programming-in-python-fall-2016/)) | 9 weeks | 15 hours/week | high school algebra

## Core CS

All coursework under Core CS is **required**, unless otherwise indicated.

### Core programming
**Topics covered**:
`functional programming`
`design for testing`
`program requirements`
`common design patterns`
`unit testing`
`object-oriented design`
`Java`
`static typing`
`dynamic typing`
`ML-family languages (via Standard ML)`
`Lisp-family languages (via Racket)`
`Ruby`
`and more`

The How to Code courses are based on the textbook [How to Design Programs](https://htdp.org/2003-09-26/). The First Edition is available for free online and includes problem sets and solutions. Students are encouraged to do these assignments.

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[How to Code - Simple Data](https://www.edx.org/course/how-code-simple-data-ubcx-htc1x) | 7 weeks | 8-10 hours/week | none
[How to Code - Complex Data](https://www.edx.org/course/how-code-complex-data-ubcx-htc2x) | 6 weeks | 8-10 hours/week | How to Code: Simple Data
[Programming Languages, Part A](https://www.coursera.org/learn/programming-languages) | 4 weeks | 8-16 hours/week | recommended: Java, C
[Programming Languages, Part B](https://www.coursera.org/learn/programming-languages-part-b) | 3 weeks | 8-16 hours/week | Programming Languages, Part A
[Programming Languages, Part C](https://www.coursera.org/learn/programming-languages-part-c) | 3 weeks | 8-16 hours/week | Programming Languages, Part B

#### Readings
- **Required** to learn about monads, laziness, purity: [Learn You a Haskell for a Great Good!](http://learnyouahaskell.com/)
  - **Note**: probably the best resource to learn Haskell: [Haskell Programming from First Principles](http://haskellbook.com/) `paid`
- **Required**, to learn about logic programming, backtracking, unification: [Learn Prolog Now!](http://lpn.swi-prolog.org/lpnpage.php?pageid=top)

### Math Electives
**Students must choose one of the following topics**: calculus, linear algebra, logic, or probability.

#### Calculus
Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Calculus 1A: Differentiation](https://www.edx.org/course/calculus-1a-differentiation) | 13 weeks | 6-10 hours/week | pre-calculus
[Calculus 1B: Integration](https://www.edx.org/course/calculus-1b-integration) | 13 weeks | 5-10 hours/week | Calculus 1A
[Calculus 1C: Coordinate Systems & Infinite Series](https://www.edx.org/course/calculus-1c-coordinate-systems-infinite-series) | 13 weeks | 5-10 hours/week | Calculus 1B

#### Linear Algebra
Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Essence of Linear Algebra](https://www.youtube.com/playlist?list=PLZHQObOWTQDPD3MizzM2xVFitgF8hE_ab) | - | - | pre-calculus
[Linear Algebra - Foundations to Frontiers](https://www.edx.org/course/linear-algebra-foundations-to-frontiers-0) ([alt](http://ulaff.net/)) | 15 weeks | 8 hours/week | Essence of Linear Algebra

#### Logic
Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Introduction to Logic](https://www.coursera.org/learn/logic-introduction) | 10 weeks | 4-8 hours/week | set theory

#### Probability
Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Introduction to Probability - The Science of Uncertainty](https://www.edx.org/course/introduction-probability-science-mitx-6-041x-2) | 18 weeks | 12 hours/week | Multivariable Calculus

### Core Math
In addition to their math elective, students must complete the following course on discrete mathematics.

**Topics covered**:
`discrete mathematics`
`mathematical proofs`
`basic statistics`
`O-notation`
`discrete probability`
`and more`

Courses | Duration | Effort | Notes | Prerequisites
:-- | :--: | :--: | :--: | :--:
[Mathematics for Computer Science](https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-042j-mathematics-for-computer-science-spring-2015/index.htm) | 13 weeks | 5 hours/week | An alternate version with solutions to the problem sets is [here](https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-042j-mathematics-for-computer-science-fall-2005/assignments/). Students struggling can consider the [Discrete Mathematics Specialization](https://www.coursera.org/specializations/discrete-mathematics) first. It is more interactive but less comprehensive, and costs money to unlock full interactivity. | Calculus 1C

### Core systems

**Topics covered**:
`procedural programming`
`manual memory management`
`boolean algebra`
`gate logic`
`memory`
`computer architecture`
`assembly`
`machine language`
`virtual machines`
`high-level languages`
`compilers`
`operating systems`
`network protocols`
`and more`

Courses | Duration | Effort | Additional Text / Assignments| Prerequisites
:-- | :--: | :--: | :--: | :--:
[Introduction to Computer Science - CS50](https://www.edx.org/course/introduction-computer-science-harvardx-cs50x#!) ([alt](https://cs50.harvard.edu/)) | 12 weeks | 10-20 hours/week | After the sections on C, skip to the next course. [Why?](FAQ.md#why-do-you-recommend-skipping-the-second-half-of-cs50) | introductory programming
[Build a Modern Computer from First Principles: From Nand to Tetris](https://www.coursera.org/learn/build-a-computer) ([alt](http://www.nand2tetris.org/)) | 6 weeks | 7-13 hours/week | - | C-like programming language
[Build a Modern Computer from First Principles: Nand to Tetris Part II ](https://www.coursera.org/learn/nand2tetris2) | 6 weeks | 12-18 hours/week | - | one of [these programming languages](https://user-images.githubusercontent.com/2046800/35426340-f6ce6358-026a-11e8-8bbb-4e95ac36b1d7.png), From Nand to Tetris Part I
[Introduction to Computer Networking](https://www.youtube.com/playlist?list=PLEAYkSg4uSQ2dr0XO_Nwa5OcdEcaaELSG)| 8 weeks | 4–12 hours/week | [Assignment 1](https://github.com/PrincetonUniversity/COS461-Public/tree/master/assignments/assignment1)<br>[Assignment 2](https://www.scs.stanford.edu/10au-cs144/lab/reliable/reliable.html)<br>[Assignment 3](https://nptel.ac.in/content/storage2/courses/106105080/pdf/M2L7.pdf)<br>[Assignment 4](http://www-net.cs.umass.edu/wireshark-labs/Wireshark_TCP_v7.0.pdf) | algebra, probability, basic CS
[ops-class.org - Hack the Kernel](https://www.ops-class.org/) | 15 weeks | 6 hours/week | Replace course textbook with [Operating Systems: Three Easy Pieces](http://pages.cs.wisc.edu/~remzi/OSTEP/) | algorithms

### Core theory

**Topics covered**:
`divide and conquer`
`sorting and searching`
`randomized algorithms`
`graph search`
`shortest paths`
`data structures`
`greedy algorithms`
`minimum spanning trees`
`dynamic programming`
`NP-completeness`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Divide and Conquer, Sorting and Searching, and Randomized Algorithms](https://www.coursera.org/learn/algorithms-divide-conquer) | 4 weeks | 4-8 hours/week | any programming language, Mathematics for Computer Science
[Graph Search, Shortest Paths, and Data Structures](https://www.coursera.org/learn/algorithms-graphs-data-structures) | 4 weeks | 4-8 hours/week | Divide and Conquer, Sorting and Searching, and Randomized Algorithms
[Greedy Algorithms, Minimum Spanning Trees, and Dynamic Programming](https://www.coursera.org/learn/algorithms-greedy) | 4 weeks | 4-8 hours/week | Graph Search, Shortest Paths, and Data Structures
[Shortest Paths Revisited, NP-Complete Problems and What To Do About Them](https://www.coursera.org/learn/algorithms-npcomplete) | 4 weeks | 4-8 hours/week | Greedy Algorithms, Minimum Spanning Trees, and Dynamic Programming

### Core Security
**Topics covered**
`Confidentiality, Integrity, Availability`
`Secure Design`
`Defensive Programming`
`Threats and Attacks`
`Network Security`
`Cryptography`
`and more`

Note: **_These courses are provisionally recommended_**. There is an open [Request For Comment](https://github.com/ossu/computer-science/issues/639) on security course selection. Contributors are encouraged to compare the various courses in the RFC and offer feedback.

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Information Security: Context and Introduction](https://www.coursera.org/learn/information-security-data) | 5 weeks | 3 hours/week | -
[Principles of Secure Coding](https://www.coursera.org/learn/secure-coding-principles)| 4 weeks | 4 hours/week | -
[Identifying Security Vulnerabilities](https://www.coursera.org/learn/identifying-security-vulnerabilities) | 4 weeks | 4 hours/week | -

Choose **one** of the following:
Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Identifying Security Vulnerabilities in C/C++Programming](https://www.coursera.org/learn/identifying-security-vulnerabilities-c-programming) | 4 weeks | 5 hours/week | -
[Exploiting and Securing Vulnerabilities in Java Applications](https://www.coursera.org/learn/exploiting-securing-vulnerabilities-java-applications) | 4 weeks | 5 hours/week | -

### Core applications

**Topics covered**:
`Agile methodology`
`REST`
`software specifications`
`refactoring`
`relational databases`
`transaction processing`
`data modeling`
`neural networks`
`supervised learning`
`unsupervised learning`
`OpenGL`
`raytracing`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Relational Database Systems](https://www.coursera.org/learn/relational-database)| 6 weeks | 3 hours/week | -
[Machine Learning](https://www.coursera.org/learn/machine-learning)| 11 weeks | 4-6 hours/week | linear algebra
[Computer Graphics](https://www.edx.org/course/computer-graphics-uc-san-diegox-cse167x)| 6 weeks | 12 hours/week | C++ or Java, linear algebra
[Software Engineering: Introduction](https://www.edx.org/course/software-engineering-introduction-ubcx-softeng1x) | 6 weeks | 8-10 hours/week | Software Construction - Object-Oriented Design
[Software Development Capstone Project](https://www.edx.org/course/software-development-capstone-project-ubcx-softengprjx) | 6-7 weeks | 8-10 hours/week | Software Engineering: Introduction

## Advanced CS

After completing **every required course** in Core CS, students should choose a subset of courses from Advanced CS based on interest.
Not every course from a subcategory needs to be taken.
But students should take *every* course that is relevant to the field they intend to go into.

The Advanced CS study should then end with one of the Specializations under [Advanced applications](#advanced-applications).
A Specialization's Capstone, if taken, may act as the [Final project](#final-project), if permitted by the Honor Code of the course.
If not, or if a student chooses not to take the Capstone, then a separate Final project will need to be done to complete this curriculum.

### Advanced programming

**Topics covered**:
`debugging theory and practice`
`goal-oriented programming`
`GPU programming`
`CUDA`
`parallel computing`
`object-oriented analysis and design`
`UML`
`large-scale software architecture and design`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Introduction to Parallel Programming](https://classroom.udacity.com/courses/cs344) ([alt](https://www.youtube.com/playlist?list=PLGvfHSgImk4aweyWlhBXNF6XISY3um82_)) ([HW](https://colab.research.google.com/github/depctg/udacity-cs344-colab))| 12 weeks | - | C, algorithms
[Compilers](https://www.edx.org/course/compilers) ([alt](https://www.youtube.com/playlist?list=PLDcmCgguL9rxPoVn2ykUFc8TOpLyDU5gx))| 9 weeks | 6-8 hours/week | none
[Software Debugging](https://www.udacity.com/course/software-debugging--cs259)| 8 weeks | 6 hours/week | Python, object-oriented programming
[Software Testing](https://www.udacity.com/course/software-testing--cs258) | 4 weeks | 6 hours/week | Python, programming experience
[LAFF - On Programming for Correctness](https://www.edx.org/course/laff-on-programming-for-correctness) | 7 weeks | 6 hours/week | linear algebra
[Software Architecture & Design](https://www.udacity.com/course/software-architecture-design--ud821)| 8 weeks | 6 hours/week | software engineering in Java

### Advanced systems

**Topics covered**:
`digital signaling`
`combinational logic`
`CMOS technologies`
`sequential logic`
`finite state machines`
`processor instruction sets`
`caches`
`pipelining`
`virtualization`
`parallel processing`
`virtual memory`
`synchronization primitives`
`system call interface`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Electricity and Magnetism, Part 1](https://www.edx.org/course/electricity-magnetism-part-1-ricex-phys102-1x-0)<sup>1</sup> | 7 weeks | 8-10 hours/week | calculus, basic mechanics
[Electricity and Magnetism, Part 2](https://www.edx.org/course/electricity-magnetism-part-2-ricex-phys102-2x-0) | 7 weeks | 8-10 hours/week | Electricity and Magnetism, Part 1
[Computation Structures 1: Digital Circuits](https://www.edx.org/course/computation-structures-part-1-digital-mitx-6-004-1x-0) | 10 weeks | 6 hours/week | electricity, magnetism
[Computation Structures 2: Computer Architecture](https://www.edx.org/course/computation-structures-2-computer-mitx-6-004-2x) | 10 weeks | 6 hours/week | Computation Structures 1
[Computation Structures 3: Computer Organization](https://www.edx.org/course/computation-structures-3-computer-mitx-6-004-3x-0) | 10 weeks | 6 hours/week | Computation Structures 2

**<sup>1</sup> Note**:
These courses assume knowledge of basic physics.
([Why?](FAQ.md#why-is-the-curriculum-missing-some-pre-requisites))
If you are struggling, you can find a physics MOOC or utilize the materials from Khan Academy:
[Khan Academy - Physics](https://www.khanacademy.org/science/physics)

### Advanced theory

**Topics covered**:
`formal languages`
`Turing machines`
`computability`
`event-driven concurrency`
`automata`
`distributed shared memory`
`consensus algorithms`
`state machine replication`
`computational geometry theory`
`propositional logic`
`relational logic`
`Herbrand logic`
`concept lattices`
`game trees`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Theory of Computation](http://aduni.org/courses/theory/index.php?view=cw) ([Lectures](https://www.youtube.com/playlist?list=PLTke5lHMAdSNmi57H0DOTzClHPK6UwSTN)) | 8 weeks | 10 hours/week | discrete mathematics, logic, algorithms
[Computational Geometry](https://www.edx.org/course/computational-geometry-tsinghuax-70240183x) | 16 weeks | 8 hours/week | algorithms, C++
[Introduction to Formal Concept Analysis](https://www.coursera.org/learn/formal-concept-analysis) | 6 weeks | 4-6 hours/week | logic, probability
[Game Theory](https://www.coursera.org/learn/game-theory-1) | 8 weeks | 3 hours/week | mathematical thinking, probability, calculus

### Advanced applications

These Coursera Specializations all end with a Capstone project.
Depending on the course, you may be able to utilize the Capstone as your Final Project for this Computer Science curriculum.
Note that doing a Specialization with the Capstone at the end always costs money.
So if you don't wish to spend money or use the Capstone as your Final, it may be possible to take the courses in the Specialization for free by manually searching for them, but not all allow this.

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Modern Robotics (Specialization)](https://www.coursera.org/specializations/modernrobotics) | 26 weeks | 2-5 hours/week | freshman-level physics, linear algebra, calculus, linear ordinary differential equations
[Data Mining (Specialization)](https://www.coursera.org/specializations/data-mining) | 30 weeks | 2-5 hours/week | machine learning
[Big Data (Specialization)](https://www.coursera.org/specializations/big-data) | 30 weeks | 3-5 hours/week | none
[Internet of Things (Specialization)](https://www.coursera.org/specializations/internet-of-things) | 30 weeks | 1-5 hours/week | strong programming
[Cloud Computing (Specialization)](https://www.coursera.org/specializations/cloud-computing) | 30 weeks | 2-6 hours/week | C++ programming
[Full Stack Web Development (Specialization)](https://www.coursera.org/specializations/full-stack) | 27 weeks | 2-6 hours/week | programming, databases
[Data Science (Specialization)](https://www.coursera.org/specializations/jhu-data-science) | 43 weeks | 1-6 hours/week | none
[Functional Programming in Scala (Specialization)](https://www.coursera.org/specializations/scala) | 29 weeks | 4-5 hours/week | One year programming experience
[Game Design and Development (Specialization)](https://www.coursera.org/specializations/game-development) | 6 months | 5 hours/week | programming, interactive design

## Final project

OSS University is **project-focused**.
You are encouraged to do the assignments and exams for each course, but what really matters is whether you can *use* your knowledge to solve a real-world problem.

After you've gotten through all of Core CS and the parts of Advanced CS relevant to you, you should think about a problem that you can solve using the knowledge you've acquired.
Not only does real project work look great on a resume, but the project will also *validate* and *consolidate* your knowledge.
You can create something entirely new, or you can find an existing project that needs help via websites like
[CodeTriage](https://www.codetriage.com/)
or
[First Timers Only](http://www.firsttimersonly.com/).

Another option is using the Capstone project from taking one of the Specializations in [Advanced applications](#advanced-applications);
whether or not this makes sense depends on the course, the project, and whether or not the course's Honor Code permits you to display your work publicly.
In some cases, it may not be permitted;
do **not** violate your course's Honor Code!

Put the OSSU-CS badge in the README of your repository!
[![Open Source Society University - Computer Science](https://img.shields.io/badge/OSSU-computer--science-blue.svg)](https://github.com/ossu/computer-science)

- Markdown: `[![Open Source Society University - Computer Science](https://img.shields.io/badge/OSSU-computer--science-blue.svg)](https://github.com/ossu/computer-science)`
- HTML: `<a href="https://github.com/ossu/computer-science"><img alt="Open Source Society University - Computer Science" src="https://img.shields.io/badge/OSSU-computer--science-blue.svg"></a>`

### Evaluation

Upon completing your final project, submit your project's information to [PROJECTS](PROJECTS.md)
via a pull request and use our [community](#community) channels to announce it to your fellow students.

Your peers and mentors from OSSU will then informally evaluate your project.
You will not be "graded" in the traditional sense — everyone has their own measurements for what they consider a success.
The purpose of the evaluation is to act as your first announcement to the world that you are a computer scientist
and to get experience listening to feedback — both positive and negative — and taking it in stride.

The final project evaluation has a second purpose: to evaluate whether OSSU,
through its community and curriculum, is successful in its mission to guide independent learners in obtaining a world-class computer science education.

### Cooperative work

You can create this project alone or with other students!
**We love cooperative work**!
Use our [channels](#community) to communicate with other fellows to combine and create new projects!

### Which programming languages should I use?

My friend, here is the best part of liberty!
You can use **any** language that you want to complete the final project.

The important thing is to **internalize** the core concepts and to be able to use them with whatever tool (programming language) that you wish.

## Congratulations

After completing the requirements of the curriculum above, you will have completed the equivalent of a full bachelor's degree in Computer Science. Congratulations!

What is next for you? The possibilities are boundless and overlapping:

- Look for a job as a developer!
- Check out the [readings](extras/readings.md) for classic books you can read that will sharpen your skills and expand your knowledge.
- Join a local developer meetup (e.g. via [meetup.com](https://www.meetup.com/)).
- Pay attention to emerging technologies in the world of software development:
  + Explore the **actor model** through [Elixir](http://elixir-lang.org/), a new functional programming language for the web based on the battle-tested Erlang Virtual Machine!
  + Explore **borrowing and lifetimes** through [Rust](https://www.rust-lang.org/), a systems language which achieves memory- and thread-safety without a garbage collector!
  + Explore **dependent type systems** through [Idris](https://www.idris-lang.org/), a new Haskell-inspired language with unprecedented support for type-driven development.

![keep learning](http://i.imgur.com/REQK0VU.jpg)

# Code of conduct
[OSSU's code of conduct](https://github.com/ossu/code-of-conduct).

## How to show your progress

1. Create an account in [Trello](https://trello.com/).
1. Copy [this](https://trello.com/b/7NIfi40X) board to your personal account.
See how to copy a board [here](https://help.trello.com/article/802-copying-cards-lists-or-boards).

Now that you have a copy of our official board, you just need to pass the cards to the `Doing` column or `Done` column as you progress in your study.

We also have **labels** to help you have more control through the process.
The meaning of each of these labels is:

- `Main Curriculum`: cards with that label represent courses that are listed in our curriculum.
- `Extra Resources`: cards with that label represent courses that were added by the student.
- `Doing`: cards with that label represent courses the student is current doing.
- `Done`: cards with that label represent courses finished by the student.
Those cards should also have the link for at least one project/article built with the knowledge acquired in such course.
- `Section`: cards with that label represent the section that we have in our curriculum.
Those cards with the `Section` label are only to help the organization of the Done column.
You should put the *Course's cards* below its respective *Section's card*.

The intention of this board is to provide our students a way to track their progress, and also the ability to show their progress through a public page for friends, family, employers, etc.
You can change the status of your board to be *public* or *private*.

# Team

* **[Eric Douglas](https://github.com/ericdouglas)**: founder of OSSU
* **[hanjiexi](https://github.com/hanjiexi)**: lead technical maintainer
* **[waciumawanjohi](https://github.com/waciumawanjohi)**: lead academic maintainer
* **[Contributors](https://github.com/ossu/computer-science/graphs/contributors)**

# References

- [Google - Guide for Technical Development](https://www.google.com/about/careers/students/guide-to-technical-development.html)
- [Coursera](https://www.coursera.org/)
- [edX](https://www.edx.org)
- [Udacity](https://www.udacity.com/)
- [Stanford University](https://lagunita.stanford.edu/)
- [Carnegie Mellon University: Computer Science Major Requirements](https://www.csd.cs.cmu.edu/academics/undergraduate/requirements)
- [MIT Open Courseware](http://ocw.mit.edu/courses/#electrical-engineering-and-computer-science)
- [Teach Yourself Computer Science](https://teachyourselfcs.com/)
- [Obtaining a Thorough CS Background Online](http://spin.atomicobject.com/2015/05/15/obtaining-thorough-cs-background-online/)

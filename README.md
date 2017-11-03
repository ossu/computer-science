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
  <a href="https://www.patreon.com/ossu">
	<img alt="Contribute with OSSU on Patreon" src="https://img.shields.io/badge/Patreon-contribute-yellow.svg">
  </a>
</p>

# Contents

- [Summary](#summary)
- [Curriculum](#curriculum)
  - [Prerequisites](#prerequisites)
  - [Introduction to Computer Science](#introduction-to-computer-science)
  - [Core CS](#core-cs)
  - [Advanced CS](#advanced-cs)
  - [Final project](#final-project)
  - [Pro CS](#pro-cs)
- [Code of conduct](#code-of-conduct)
- [Community](#community)
  - [How to show your progress](#how-to-show-your-progress)
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
- *Pro CS*: graduate-level specializations students can elect to take after completing the above curriculum if they want to maximize their chances of getting a good job

**Duration**. It is possible to finish Core CS within about 2 years if you plan carefully and devote roughly 18-22 hours/week to your studies.
Courses in Core CS should be taken linearly if possible, but since a perfectly linear progression is rarely possible,
each class's prerequisites is specified so that you can design a logical but non-linear progression
based on the class schedules and your own life plans.

**Cost**. All or nearly all course material prior to Pro CS is available for free,
however some courses may charge money for assignments/tests/projects to be graded.
Note that Coursera offers [financial aid](https://learner.coursera.help/hc/en-us/articles/209819033-Apply-for-Financial-Aid).
Decide how much or how little to spend based on your own time and budget;
just remember that you can't purchase success!

**Content policy**. If you plan on showing off some of your coursework publicly, you must share only files that you are allowed to.
*Do NOT disrespect the code of conduct* that you signed in the beginning of each course!

**How to contribute**. Please see [CONTRIBUTING](CONTRIBUTING.md).

**Getting help**. Please check our [Frequently Asked Questions](FAQ.md), and if you cannot find the answer, file an issue or talk to our [friendly community](#community)!

# Curriculum

**Curriculum version**: `8.0.0` (see [CHANGELOG](CHANGELOG.md))

- [Prerequisites](#prerequisites)
- [Introduction to Computer Science](#introduction-to-computer-science)
- [Core CS](#core-cs)
  - [Core programming](#core-programming)
  - [Core math](#core-math)
  - [Core systems](#core-systems)
  - [Core theory](#core-theory)
  - [Core applications](#core-applications)
- [Advanced CS](#advanced-cs)
  - [Advanced programming](#advanced-programming)
  - [Advanced math](#advanced-math)
  - [Advanced systems](#advanced-systems)
  - [Advanced theory](#advanced-theory)
  - [Advanced applications](#advanced-applications)
- [Final project](#final-project)
- [Pro CS](#pro-cs)

---

## Prerequisites

- [Core CS](#core-cs) assumes the student has already taken high school math and physics, including algebra, geometry, and pre-calculus.
Some high school graduates will have already taken AP Calculus, but this is usually only about 3/4 of a college calculus class, so the calculus courses in the curriculum are still recommended.
- [Advanced CS](#advanced-cs) assumes the student has already taken the entirety of Core CS
and is knowledgeable enough now to decide which electives to take.
- Note that [Advanced systems](#advanced-systems) assumes the student has taken a basic physics course (e.g. AP Physics in high school).

## Introduction to Computer Science

These courses will introduce you to the world of computer science.
Both are required, but feel free to skip straight to the second course when CS50 (the first course) moves away from C.
([Why?](FAQ.md#why-do-you-recommend-skipping-the-second-half-of-cs50))

**Topics covered**:
`imperative programming`
`procedural programming`
`C`
`manual memory management`
`basic data structures and algorithms`
`Python`
`SQL`
`basic HTML, CSS, JavaScript`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Introduction to Computer Science - CS50](https://www.edx.org/course/introduction-computer-science-harvardx-cs50x#!) ([alt](https://cs50.harvard.edu/)) | 12 weeks | 10-20 hours/week | none
[Introduction to Computer Science and Programming using Python](https://www.edx.org/course/introduction-computer-science-mitx-6-00-1x-10) | 9 weeks | 15 hours/week | high school algebra

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

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[How to Code - Simple Data](https://www.edx.org/course/how-code-simple-data-ubcx-htc1x) | 7 weeks | 8-10 hours/week | none
[How to Code - Complex Data](https://www.edx.org/course/how-code-complex-data-ubcx-htc2x) | 6 weeks | 8-10 hours/week | How to Code: Simple Data
[Software Construction - Data Abstraction](https://www.edx.org/course/software-construction-data-abstraction-ubcx-softconst1x) | 6 weeks | 8-10 hours/week | How to Code - Complex Data
[Software Construction - Object-Oriented Design](https://www.edx.org/course/software-construction-object-oriented-ubcx-softconst2x) | 6 weeks | 8-10 hours/week | Software Construction - Data Abstraction
[Programming Languages, Part A](https://www.coursera.org/learn/programming-languages) | 4 weeks | 8-16 hours/week | recommended: Java, C
[Programming Languages, Part B](https://www.coursera.org/learn/programming-languages-part-b) | 3 weeks | 8-16 hours/week | Programming Languages, Part A
[Programming Languages, Part C](https://www.coursera.org/learn/programming-languages-part-c) | 3 weeks | 8-16 hours/week | Programming Languages, Part B

#### Readings
- **Required** to learn about monads, laziness, purity: [Learn You a Haskell for a Great Good!](http://learnyouahaskell.com/)
  - **OBS**: probably the best resource to learn Haskell: [Haskell Programming from First Principles](http://haskellbook.com/) `paid`
- **Required**, to learn about logic programming, backtracking, unification: [Learn Prolog Now!](http://www.learnprolognow.org/)

### Core math

**Topics covered**:
`linear transformations`
`matrices`
`vectors`
`mathematical proofs`
`number theory`
`differential calculus`
`integral calculus`
`sequences and series`
`discrete mathematics`
`basic statistics`
`O-notation`
`graph theory`
`vector calculus`
`discrete probability`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Essence of Linear Algebra](https://www.youtube.com/playlist?list=PLZHQObOWTQDPD3MizzM2xVFitgF8hE_ab) | - | - | pre-calculus
[Linear Algebra - Foundations to Frontiers](https://www.edx.org/course/linear-algebra-foundations-frontiers-utaustinx-ut-5-04x#!) ([alt](http://ulaff.net/)) | 15 weeks | 8 hours/week | Essence of Linear Algebra
[Calculus One](https://www.coursera.org/learn/calculus1)<sup>*1*</sup> ([alt](https://mooculus.osu.edu/)) | 16 weeks | 8-10 hours/week | pre-calculus
[Calculus Two: Sequences and Series](https://www.coursera.org/learn/advanced-calculus)| 7 weeks | 9-10 hours/week | Calculus One
[Mathematics for Computer Science](https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-042j-mathematics-for-computer-science-spring-2015/index.htm) | 13 weeks | 5 hours/week | single variable calculus (Calculus Two)

**<sup>1</sup> Note**: When you are enrolled, please see this
[list of errors](https://www.coursera.org/learn/calculus1/discussions/forums/WcY9_8ayEeSWEiIAC0wC5g/threads/CgOJwV-jEeWncxKXIFxpFQ/replies/kH6u_2FPEeWukw4fFhIvKw)
and
[these recommendations](https://www.coursera.org/learn/calculus1/discussions/all/threads/W5P9mFY8EeWbVQrsfyQbuw/replies/XyyJflZDEeWBRg5dvElQww/comments/l-bON17nEeW9lgqcHapJBw)
for how to progress through the course.

### Core systems

**Topics covered**:
`boolean algebra`
`gate logic`
`memory`
`machine language`
`computer architecture`
`assembly`
`machine language`
`virtual machines`
`high-level languages`
`compilers`
`operating systems`
`network protocols`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Build a Modern Computer from First Principles: From Nand to Tetris](https://www.coursera.org/learn/build-a-computer) ([alt](http://www.nand2tetris.org/)) | 6 weeks | 7-13 hours/week | none
[Build a Modern Computer from First Principles: Nand to Tetris Part II ](https://www.coursera.org/learn/nand2tetris2) | 6 weeks | 12-18 hours/week | From Nand to Tetris Part I
[Introduction to Computer Networking](https://lagunita.stanford.edu/courses/Engineering/Networking-SP/SelfPaced/about)| 8 weeks | 4–12 hours/week | algebra, probability, basic CS
[ops-class.org - Hack the Kernel](https://www.ops-class.org/) | 15 weeks | 6 hours/week | algorithms

#### Readings
- **Recommended**: While Hack the Kernel recommends Modern Operating Systems as a textbook, we suggest using [Operating Systems: Three Easy Pieces](http://pages.cs.wisc.edu/~remzi/OSTEP/).

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
[Algorithms: Design and Analysis, Part I](https://lagunita.stanford.edu/courses/course-v1:Engineering+Algorithms1+SelfPaced/about) | 8 weeks | 4-8 hours/week | any programming language, Mathematics for Computer Science
[Algorithms: Design and Analysis, Part II](https://lagunita.stanford.edu/courses/course-v1:Engineering+Algorithms2+SelfPaced/about) | 8 weeks | 4-8 hours/week | Part I


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
`block ciphers`
`authentication`
`public key encryption`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Databases](https://lagunita.stanford.edu/courses/DB/2014/SelfPaced/about)| 12 weeks | 8-12 hours/week | some programming, basic CS
[Machine Learning](https://www.coursera.org/learn/machine-learning)| 11 weeks | 4-6 hours/week | linear algebra
[Computer Graphics](https://www.edx.org/course/computer-graphics-uc-san-diegox-cse167x)| 6 weeks | 12 hours/week | C++ or Java, linear algebra
[Cryptography I](https://www.coursera.org/course/crypto)| 6 weeks | 5-7 hours/week | linear algebra, probability
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
[Compilers](https://lagunita.stanford.edu/courses/Engineering/Compilers/Fall2014/about)| 9 weeks | 6-8 hours/week | none
[Software Debugging](https://www.udacity.com/course/software-debugging--cs259)| 8 weeks | 6 hours/week | Python, object-oriented programming
[Software Testing](https://www.udacity.com/course/software-testing--cs258) | 4 weeks | 6 hours/week | programming experience
[LAFF: Programming for Correctness](https://www.edx.org/course/laff-programming-correctness-utaustinx-ut-p4c-14-01x) | 7 weeks | 6 hours/week | linear algebra
[Introduction to Parallel Programming](https://www.udacity.com/course/intro-to-parallel-programming--cs344) | 12 weeks | - | C, algorithms
[Software Architecture & Design](https://www.udacity.com/course/software-architecture-design--ud821)| 8 weeks | 6 hours/week | software engineering in Java

### Advanced math

**Topics covered**:
`parametric equations`
`polar coordinate systems`
`multivariable integrals`
`multivariable differentials`
`probability theory`
`and more`

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Calculus: Parametric Equations and Polar Coordinates](https://ocw.mit.edu/courses/mathematics/18-01sc-single-variable-calculus-fall-2010/unit-4-techniques-of-integration/part-c-parametric-equations-and-polar-coordinates/) | - | - | single-variable calculus (Calculus Two)
[Multivariable Calculus](https://ocw.mit.edu/courses/mathematics/18-02sc-multivariable-calculus-fall-2010/index.htm) | 13 weeks | 12 hours/week | Parametric Equations and Polar Coordinates
[Introduction to Probability - The Science of Uncertainty](https://www.edx.org/course/introduction-probability-science-mitx-6-041x-2) | 18 weeks | 12 hours/week | Multivariable Calculus

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
[Introduction to Logic](https://www.coursera.org/learn/logic-introduction) | 10 weeks | 4-8 hours/week | set theory
[Automata Theory](https://lagunita.stanford.edu/courses/course-v1:ComputerScience+Automata+Fall2016/about) | 8 weeks | 10 hours/week | discrete mathematics, logic, algorithms
[Reliable Distributed Systems, Part 1](https://www.edx.org/course/reliable-distributed-algorithms-part-1-kthx-id2203-1x) | 5 weeks | 5 hours/week | Scala, intermediate CS
[Reliable Distributed Systems, Part 2](https://www.edx.org/course/reliable-distributed-algorithms-part-2-kthx-id2203-2x) | 5 weeks | 5 hours/week | Part 1
[Computational Geometry](https://www.edx.org/course/computational-geometry-tsinghuax-70240183x) | 16 weeks | 8 hours/week | algorithms, C++
[Introduction to Formal Concept Analysis](https://www.coursera.org/learn/formal-concept-analysis) | 6 weeks | 4-6 hours/week | logic, probability
[Game Theory](https://www.coursera.org/learn/game-theory-1) | 8 weeks | x hours/week | mathematical thinking, probability, calculus

### Advanced applications

These Coursera Specializations all end with a Capstone project.
Depending on the course, you may be able to utilize the Capstone as your Final Project for this Computer Science curriculum.
Note that doing a Specialization with the Capstone at the end always costs money.
So if you don't wish to spend money or use the Capstone as your Final, it may be possible to take the courses in the Specialization for free by manually searching for them, but not all allow this.

Courses | Duration | Effort | Prerequisites
:-- | :--: | :--: | :--:
[Robotics (Specialization)](https://www.coursera.org/specializations/robotics) | 26 weeks | 2-5 hours/week | linear algebra, calculus, programming, probability
[Data Mining (Specialization)](https://www.coursera.org/specializations/data-mining) | 30 weeks | 2-5 hours/week | machine learning
[Big Data (Specialization)](https://www.coursera.org/specializations/big-data) | 30 weeks | 3-5 hours/week | none
[Internet of Things (Specialization)](https://www.coursera.org/specializations/internet-of-things) | 30 weeks | 1-5 hours/week | strong programming
[Cloud Computing (Specialization)](https://www.coursera.org/specializations/cloud-computing) | 30 weeks | 2-6 hours/week | C++ programming
[Full Stack Web Development (Specialization)](https://www.coursera.org/specializations/full-stack) | 27 weeks | 2-6 hours/week | programming, databases
[Data Science (Specialization)](https://www.coursera.org/specializations/jhu-data-science) | 43 weeks | 1-6 hours/week | none
[Functional Programming in Scala (Specialization)](https://www.coursera.org/specializations/scala) | 29 weeks | 4-5 hours/weeks | One year programming experience

## Final project

OSS University is **project-focused**.
You are encouraged to do the assignments and exams for each course, but what really matters is whether you can *use* your knowledge to solve a real world problem.

After you've gotten through all of Core CS and the parts of Advanced CS relevant to you, you should think about a problem that you can solve using the knowledge you've acquired.
Not only does real project work look great on a resume, the project will *validate* and *consolidate* your knowledge.
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
The purpose of the evaluation is to act as your first announcement to the world that you are a computer scientist,
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

## Pro CS

After completing the requirements of the curriculum above, you will have completed the equivalent of a full bachelor's degree in Computer Science, or quite close to one.
You can stop in the Advanced CS section, but the next step to completing your studies is to develop skills and knowledge in a specific domain.
Many of these courses are graduate-level.

Choose one or more of the following **specializations**:
- [Mastering Software Development in R Specialization](https://www.coursera.org/specializations/r) by Johns Hopkins University
- [Artificial Intelligence Engineer Nanodegree](https://www.udacity.com/ai) by IBM, Amazon, and Didi
- [Machine Learning Engineer Nanodegree](https://www.udacity.com/course/machine-learning-engineer-nanodegree--nd009) by kaggle
- [Cybersecurity MicroMasters](https://www.edx.org/micromasters/ritx-cybersecurity) by the Rochester Institute of Technology
- [Android Developer Nanodegree](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801) by Google

These aren't the only specializations you can choose. Check the following websites for **more options**:
- edX: [xSeries](https://www.edx.org/xseries)
- Coursera: [Specializations](https://www.coursera.org/specializations)
- Udacity: [Nanodegree](https://www.udacity.com/nanodegree)

### Where to go next?

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

# Community

- Subscribe to our [newsletter](https://tinyletter.com/ossu).
- Use our [forum](https://github.com/ossu/forum) if you need some help.
- You can also interact through [GitHub issues](https://github.com/ossu/computer-science/issues).
- We also have a chat room! [![Join the chat at https://gitter.im/open-source-society/computer-science](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/open-source-society/computer-science?utm_campaign=pr-badge&utm_content=badge&utm_medium=badge&utm_source=badge)
- Add **Open Source Society University** to your [Linkedin](https://www.linkedin.com/school/11272443/) and [Facebook](https://www.facebook.com/ossuniversity) profile!

> **PS**: A forum is an ideal way to interact with other students as we do not lose important discussions, which usually occur in communication via chat apps.
**Please use our forum for important discussions**.

## How to show your progress

1. Create an account in [Trello](https://trello.com/).
1. Copy [this](https://trello.com/b/9DPXYv5f) board to your personal account.
See how to copy a board [here](http://blog.trello.com/you-can-copy-boards-now-finally/).

Now that you have a copy of our official board, you just need to pass the cards to the `Doing` column or `Done` column as you progress in your study.

We also have **labels** to help you have more control through the process.
The meaning of each of these labels is:

- `Main Curriculum`: cards with that label represent courses that are listed in our curriculum.
- `Extra Resources`: cards with that label represent courses that was added by the student.
- `Doing`: cards with that label represent courses the student is current doing.
- `Done`: cards with that label represent courses finished by the student.
Those cards should also have the link for at least one project/article built with the knowledge acquired in such course.
- `Section`: cards with that label represent the section that we have in our curriculum.
Those cards with the `Section` label are only to help the organization of the Done column.
You should put the *Course's cards* below its respective *Section's card*.

The intention of this board is to provide our students a way to track their progress, and also the ability to show their progress through a public page for friends, family, employers, etc.
You can change the status of your board to be *public* or *private*.

## Team

* **Curriculum Founders**: [Eric Douglas](https://github.com/ericdouglas)
* **Curriculum Maintainers**: [Eric Douglas](https://github.com/ericdouglas) and [hanjiexi](https://github.com/hanjiexi)
* **Contributors**: [contributors](https://github.com/ossu/computer-science/graphs/contributors)

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

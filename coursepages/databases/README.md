# CMU 15-445/645: Database Systems

CMU 15-445/645 is an upper-level course on the design and implementation of
database management systems. It covers relational models and SQL, storage,
buffer management, indexes, query execution and optimization, transactions,
concurrency control, recovery, and distributed databases. Its programming
projects build parts of the [BusTub](https://github.com/cmu-db/bustub) database
system.

The course is updated regularly, but a semester that is currently in progress
may not have released every lecture or assignment. This guide uses the complete
[Fall 2025 offering](https://15445.courses.cs.cmu.edu/fall2025/) as a stable
self-study path. Check the [current course site and archives](https://15445.courses.cs.cmu.edu/)
before starting if you want to use a newer complete offering.

## Before you start

The official prerequisite is CMU 15-213/513, a systems programming course. In
OSSU terms, complete Core Systems first and be comfortable with memory,
concurrency, debugging, command-line development, and low-level systems code.
The course also assumes C++ proficiency and does not teach the language.

Before beginning the database material:

1. Be comfortable writing and debugging C++ programs. If you know basic C++
   syntax but need the modern language features used by BusTub, complete the
   [15-445/645 C++ Bootcamp](https://github.com/cmu-db/15445-bootcamp).
   [LearnCpp](https://www.learncpp.com/) is a more complete introduction if C++
   is new to you.
2. Complete [Project 0: C++ Primer](https://15445.courses.cs.cmu.edu/fall2025/project0/)
   before continuing. Treat it as a readiness check for the remaining projects,
   not as a substitute for the systems prerequisite.
3. Prepare a supported development environment. BusTub recommends Ubuntu 24.04
   and explicitly does not support WSL. Windows users should install Ubuntu in a
   virtual machine; Canonical provides a [VirtualBox setup guide](https://ubuntu.com/tutorials/how-to-run-ubuntu-desktop-on-a-virtual-machine-using-virtualbox).

The projects are significantly more demanding than the lectures and homeworks.
If Project 0 requires extensive step-by-step help, spend more time practicing
C++ and debugging before moving on.

## Course links

- [Fall 2025 schedule](https://15445.courses.cs.cmu.edu/fall2025/schedule.html):
  lectures, slides, notes, videos, readings, and assignment release points
- [Fall 2025 assignments](https://15445.courses.cs.cmu.edu/fall2025/assignments.html):
  six homeworks with solutions and five programming projects
- [Fall 2025 syllabus](https://15445.courses.cs.cmu.edu/fall2025/syllabus.html):
  objectives, prerequisites, and policies
- [Fall 2025 FAQ](https://15445.courses.cs.cmu.edu/fall2025/faq.html):
  instructions for the public Gradescope autograder and community discussion
- [Fall 2025 video playlist](https://www.youtube.com/playlist?list=PLSE8ODhjZXjYMAgsGH-GtY5rJYZ6zjsd5)
- [Fall 2025 BusTub snapshot](https://github.com/cmu-db/bustub/tree/v20251119-2025fall)

## Study plan

Follow the [Fall 2025 schedule](https://15445.courses.cs.cmu.edu/fall2025/schedule.html)
in order. For each topic:

1. Complete the listed reading, watch the lecture, and review its slides or
   notes. The readings are part of the official course and are assessed in its
   exams. If you do not have the textbook, the public notes provide a useful
   guide, but this is an adapted self-study path rather than the complete CMU
   assessment.
2. Complete the associated homework before reading the published solution.
3. Start each project where it appears on the schedule. The projects are
   cumulative, so finish them in order: C++ Primer, Buffer Pool Manager,
   Database Index, Query Execution, and Concurrency Control.
4. Use the public Gradescope course described in [FAQ Q7](https://15445.courses.cs.cmu.edu/fall2025/faq.html#q7)
   after an assignment's autograder becomes available to non-CMU students.

Allow approximately one week per lecture pair and extra time around project
deadlines. The original semester fits lectures, readings, homeworks, projects,
and exams into 15 weeks. Without its campus support and fixed deadlines, a
20-24 week self-study schedule is more realistic. This estimate does not include
C++ or systems-prerequisite preparation.

The official midterm and final are not published, so the self-study path cannot
reproduce those assessments. Use the homeworks and projects as your primary
checks of understanding.

## Fall 2025 project source

BusTub's `master` branch changes for each semester. Use the
[`v20251119-2025fall` tag](https://github.com/cmu-db/bustub/tree/v20251119-2025fall)
with the Fall 2025 specifications. Follow BusTub's private-repository setup, but
create your working branch from that tag and do not merge later `master` changes
while completing this offering:

```console
git fetch public tag v20251119-2025fall
git switch --detach v20251119-2025fall
git switch -c fall2025-projects
```

## Project setup and academic integrity

Do not fork BusTub into a public repository and do not publish project
solutions. Follow BusTub's [private repository setup](https://github.com/cmu-db/bustub/tree/v20251119-2025fall#cloning-this-repository),
keep your implementation private, and submit only your own work. These
restrictions continue after you finish the course.

The public autograder is provided in exchange for respecting these rules. Run
the repository's `gradescope_sign.py` agreement before submitting, as described
in the BusTub documentation. Follow the [Fall 2025 collaboration policy](https://15445.courses.cs.cmu.edu/fall2025/syllabus.html#collaboration-policy)
and each assignment's rules: do not search for or view past solutions, copy
work, or rely on step-by-step collaboration.

## Scope

This course emphasizes database-system engineering and implementation. It goes
deeper into storage, indexing, query processing, concurrency, and recovery than
a data-modeling survey. To cover topics emphasized by the replaced sequence,
review the free *Database System Concepts* slides for [E-R modeling](https://www.db-book.com/slides-dir/PDF-dir/ch6.pdf),
[complex data types](https://www.db-book.com/slides-dir/PDF-dir/ch8.pdf),
[object-based databases](https://www.db-book.com/slides-dir/PDF-dir/ch29.pdf), and
[XML](https://www.db-book.com/slides-dir/PDF-dir/ch30.pdf).

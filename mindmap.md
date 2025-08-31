---
layout: page
title: Curriculum Mind Map
---

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/vis-timeline@7.7.0/styles/vis-timeline-graph2d.min.css">
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/vis-timeline@7.7.0/standalone/umd/vis-timeline-graph2d.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>

<style>
  #timeline {
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-top: 20px;
  }
  .vis-item {
    border-color: #4a90e2;
    background-color: #d8e6f8;
  }
</style>

<div id="timeline"></div>

<script type="text/javascript">
  document.addEventListener('DOMContentLoaded', function() {
    fetch('README.md')
      .then(response => response.text())
      .then(text => {
        const html = marked.parse(text);
        const container = document.createElement('div');
        container.innerHTML = html;

        const tables = container.querySelectorAll('table');
        let courses = [];
        let id = 1;

        tables.forEach(table => {
          const headers = [...table.querySelectorAll('th')].map(th => th.textContent.trim());
          if (headers.includes('Courses') && headers.includes('Duration')) {
            const rows = table.querySelectorAll('tbody tr');
            rows.forEach(row => {
              const cells = [...row.querySelectorAll('td')].map(td => td.textContent.trim());
              const courseData = {};
              headers.forEach((header, i) => {
                courseData[header] = cells[i];
              });
              courses.push(courseData);
            });
          }
        });

        const items = new vis.DataSet();
        let startDate = new Date();

        courses.forEach(course => {
          const durationInWeeks = parseInt(course.Duration.split(' ')[0]);
          if (!isNaN(durationInWeeks)) {
            const endDate = new Date(startDate.getTime());
            endDate.setDate(startDate.getDate() + durationInWeeks * 7);

            items.add({
              id: id++,
              content: course.Courses,
              start: startDate,
              end: endDate
            });

            startDate = endDate;
          }
        });

        const timelineContainer = document.getElementById('timeline');
        const options = {
          stack: false,
          start: new Date(new Date().getFullYear(), 0, 1),
          end: new Date(new Date().getFullYear() + 2, 0, 1),
        };
        const timeline = new vis.Timeline(timelineContainer, items, options);
      });
  });
</script>

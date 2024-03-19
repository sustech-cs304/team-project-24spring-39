# [CS304] Team Project - Report1

------

## Part I. Project Proposal

### Project Overview

The proposed system aims to streamline various aspects of student life within the educational institution, enhancing efficiency, convenience, and user experience. By providing features like seamless course selection, comprehensive course evaluation, collaborative forums, hassle-free room booking, and timely notifications, the system seeks to create a conducive environment for learning, interaction, and academic success. Ultimately, the goal is to empower students, teachers, and administrators with a robust platform that fosters communication, facilitates resource management, and enriches the overall educational experience.



### Preliminary Requirement Analysis

1. **Functional requirements**
   1. **Course Selection System**
      - Implement a bidding system for course enrollment, allowing students to allocate 100 points across desired courses.
      - Filter and display courses based on department, instructor, schedule, and whether schedule conflicts are ignored. Exclude previously taken courses.
      - Recommend courses based on user's major and grade level, prioritizing courses with a rating of 9.0 or above.
      - Feature a "Historical Course Selection" option to display a user's course enrollment history.
   2. **Teaching Evaluation System**
      - Allow star ratings (0 to 5 stars, corresponding to scores of 0 to 10) for courses and instructors.
      - Require evaluations on course difficulty, workload, grading leniency, and overall gain with low, medium, and high options.
      - Provide an optional text field for detailed feedback.
   3. **Forum System**
      - Display posts in reverse chronological order.
      - Enable post creation with predefined tags (by admin) related to major, course, and optionally, instructor. Support text, images, videos, and file uploads.
      - Offer filtering and search functionalities for browsing posts by tags or keywords (course, instructor, review).
      - Implement like/unlike functionality and comments for posts.
   4. **Study Room Reservation System**
      - Facilitate room reservations by area and specific rooms, displaying available times in color-coded segments. Limit reservations to four consecutive half-hour slots.
   5. **Notification System**
      - Aggregate notifications from all systems, including reminders for course selection deadlines, notification of unselected courses, forum activity related to the user, study room reservation reminders, and personal schedule reminders.
2. **Non-functional requirements**
   1. **Usability**: Intuitive UI/UX for easy navigation and interaction with all systems. Provide UI guidance for first-time users.
   2. **Performance**:
      1. Ensure system responsiveness by achieving a load time of less than 2 seconds for all operations under normal conditions.
      2. Optimize database queries and server-side processing to handle high-volume transactions efficiently.
      3. Incorporate caching mechanisms to reduce data retrieval times and server load.
   3. **Security**: 
      1. Implement authentication and login through institutional email verification to ensure users are part of the academic community.
      2. Enforce strict role-based access control (RBAC), distinguishing between student and administrator roles to prevent users from accessing functionalities beyond their permissions.
      3. Provides run log management to track historical system usage.
   4. **Scalability**: 
      1. Implement load balancing capabilities to facilitate scaling up with additional servers as needed to handle increased data volume without performance degradation.
      2. Designed with low coupling between system modules, ensuring that the introduction of new features or modules has minimal impact on existing functionalities.
   5. **Reliability**:  
      1. Implement redundant systems and databases to provide failover capabilities in case of hardware or software failure.
      2. Regularly back up data and provide a clear, tested disaster recovery plan to ensure data integrity and availability
3. **Data requirements**
   1. **Data required:**
      1. **Student Data Requirements**
         1. **Basic Information**
            1. Account ID and password.
            2. Major, grade level, name, student ID.

         2. **Forum Activity**
            1. Posts created, comments made.

         3. **Teaching Evaluation Activity**
            1. Courses evaluated and content of evaluations.

         4. **Course Selection Activity**
            1. Current and historical courses enrolled, including points allocated to each course during selection.

         5. **Study Room Reservation Activity**
            1. Study rooms reserved.

         6. **Notifications**
            1. Personal schedule reminders set by the student.

      2. **Administrator Data Requirements**
         1. Account ID and password.

         2. Courses published.

      3. **Course Data Requirements**
         1. Course name, department offering the course, instructor, schedule, capacity, rating.

         2. Course enrollment start and end dates.

         3. Students enrolled in the course.

      4. **Teaching Evaluation Data Requirements**
         1. Overall score, grading standards, course benefits, difficulty level, workload, textual comments.

      5. **Post Data Requirements**
         1. Timestamp, author, title, content, tags, likes, replies.

      6. **Comment Data Requirements**
         1. Timestamp, author, content, replies.

      7. **Study Room Data Requirements**
         1. Time slots, location, reservation status, reserving student.

      8. **Schedule Reminder Data Requirements**
         1. Schedule source, reminder content, reminder time.

   2. **How to get the data:**
      1. **Student information:** Registration will be through the school email, through the email account to pick up the student number and grade. Other relevant information (such as major) is provided by the student at the time of registration.
      2. **Course information:** Import according to the class schedule of SUSTech.
      3. **Study Room information:** Import according to the study room of SUSTech.
4. **Technical Requirements**
   1. **Operating Environment**: Web-based platform accessible on desktop and mobile devices.

   2. **Tech Stack**
      - **Frontend**: Vue3 + element-plus for dynamic, responsive UIs.
        
      - **Backend**: Node.js with Express for RESTful APIs or Python with Django for a robust backend solution.
        
      - **Database**: PostgreSQL or MySQL.
        
      - **Authentication**: OAuth2.0 or JWT for secure user authentication.
        
      - **Cloud Services**: Aliyun or Hicloud for hosting, storage, and scalability solutions.
        
      - **Version Control**: Git with GitHub for version control and collaboration.



## Part II. Task Decomposition & Planning

1. **snapshots of github project board view**

   ![image-20240319085726998](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\image-20240319085726998.png)

2. **snapshots of github project roadmap view**

   ![image-20240319085826863](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\image-20240319085826863.png)

## Part III. AI Usage (based on GPT-4)

1. **Have you used AI to propose features for the project?**

   No, we tried to use AI to propose features, but the features it provided were not very satisfactory based on coarse prompts. But if we take the time to design a detailed prompt, in fact, we have basically designed the feature during this process. However, I think AI can help us brainstorm to come up with some rough ideas (In this process, we haven't use it for brainstorm). But when it comes to concrete details, we still needs to do it ourselves.

2. **Have you used AI to conduct the preliminary requirement analysis (e.g., identify functional and non functional requirements)?**

   - **functional requirements:** Same as the answer about AI usage for features.
   - **non functional requirements:** Yes.
     - **prompt:** 我们即将开发一个软件工程项目，请帮助我完成我项目的Non-functional requirements analysis (e.g., usability, safety, security, performance, etc.) ，以下是我项目的构成：(接下来是中文的feature内容，内容过长这里省略)
     - **response:** 
       - **Non-Functional Requirements**
         - **Usability**: Intuitive UI/UX for easy navigation and interaction with all systems.
         - **Performance**: Ensure quick response times for user actions, even under high load during course selection periods.
         - **Security**: Protect user data with robust authentication and authorization. Ensure data transmission and storage are secure.
         - **Scalability**: Design for scalability to accommodate growing numbers of users and data without degradation in performance.
         - **Reliability**: Achieve high availability and minimal downtime through effective error handling and redundant systems where necessary.
     - **use of response:** We haven't use the response directly, since it is all about non-specific description of requirements. We searched some common non-functional requirements analysis on the internet for reference.

3. **Have you used AI to generate user stories?**

   Yes

   - **prompt:** Part II. Task Decomposition & Planning (5 points) You'll use the Github Projects board for project planning (see lab 2 for using Github Projects). First, break the high-level general features into a set of user stories: In the "Board" view, add a "Product Backlog" column to GitHub Project board. Add users stories to this column. For each user story, provide a brief description in the form of "As a type-of-user, I want to some-goal so that some-reason." as we introduced in the lecture. Set priority or labels for each user story Then, break each user story into a set of actionable issues (tasks) that will be tackled in the first sprint: In the same view, add a "Sprint Backlog" or "Todo" column to GitHub Project board. Add issues (tasks) to this column. For each issue, provide a brief description (e.g., "implement user registration"). Assign issues to group members Finally, set up dates and durations for each issue. The ending date for the current planning should be week 9 (i.e., the first sprint). In the "Roadmap" view, set start date and duration for each issue. Your team should use this board to track the progress as we described in the lecture. In addition to the "Product Backlog" (user stories) and "Sprint Backlog" (tasks for this sprint) columns described above, you should also have "In progress" and "Done" columns on the board once the first sprint starts. 请帮我的软件工程项目设计所有的user stories，以下是我的项目的构成：(接下来是中文的feature内容，内容过长这里省略)
   - **response:** 
     - **Product Backlog (User Stories)**
       1. **Course Selection System**
          - As a student, I want to allocate points to courses I wish to enroll in so that I can prioritize my preferred classes within a limited quota.
          - As a student, I want to filter and search courses by department, instructor, and schedule so that I can find courses that fit my academic and personal schedule.
          - As a student, I want the system to recommend courses based on my major and grade level so that I can discover courses that match my academic progression.
          - As a student, I want to view my course enrollment history so that I can track my academic journey.
       2. **Teaching Evaluation System**
          - As a student, I want to rate courses and instructors with stars so that I can express my level of satisfaction with the course.
          - As a student, I want to evaluate courses based on difficulty, workload, and overall gain so that I can provide detailed feedback for future students.
          - As a student, I want to write detailed reviews for courses so that I can share my experiences and advice with peers.
       3. **Forum System**
          - As a student, I want to create posts with tags related to courses so that I can discuss specific aspects of my academic experience.
          - As a student, I want to filter and search forum posts by tags and keywords so that I can find relevant discussions and materials.
          - As a student, I want to like and comment on posts so that I can engage with the academic community.
       4. **Study Room Reservation System**
          - As a student, I want to reserve study rooms based on location and availability so that I can have a dedicated space for studying.
       5. **Notification System**
          - As a student, I want to receive reminders about important academic deadlines and activities so that I can manage my academic responsibilities effectively.
   - **use of response:** We use it directly since we think these user stories are specific and covers enough scenarios.

4. **Have you used AI to generate tasks?**

   Yes

   - **prompt:** 对task的要求如下： Tasks are used to break down user stories even further.  Tasks are the smallest unit used in scrum to track work.  A task should be completed by one person on the team A task typically takes ~1 day Tasks should be small, manageable, and clear, so that everyone knows what need to be done. 请为每一条user story生成对应的tasks.

   - **response:** 

     1. **Course Selection System**

        1. **As a student, I want to allocate points to courses I wish to enroll in so that I can prioritize my preferred classes within a limited quota.**
           - Design the database schema for storing student points allocation.
           - Implement the backend logic for point allocation to courses.
           - Create a user interface for point allocation in the course selection module.
           - Write unit tests for the point allocation feature.
        2. **As a student, I want to filter and search courses by department, instructor, and schedule so that I can find courses that fit my academic and personal schedule.**
           - Develop the search functionality backend to query courses by department, instructor, and schedule.
           - Design and implement a filter and search user interface in the course selection module.
           - Create API endpoints for fetching filtered search results.
           - Conduct usability testing for the filter and search feature.
        3. **As a student, I want the system to recommend courses based on my major and grade level so that I can discover courses that match my academic progression.**
           - Design an algorithm for course recommendation based on major and grade level.
           - Implement the recommendation algorithm in the backend.
           - Develop a user interface section for displaying recommended courses.
           - Test the recommendation system with mock student profiles.

        - **As a student, I want to view my course enrollment history so that I can track my academic journey.**
          - Design the database schema for storing student course history.
          - Implement backend logic to retrieve a student's course enrollment history.
          - Create a user interface for displaying course history in the student profile.
          - Write integration tests for the course history feature.

     2. **Teaching Evaluation System**

        1. **As a student, I want to rate courses and instructors with stars so that I can express my level of satisfaction with the course.**
           - Design a star rating component for the user interface.
           - Implement the backend logic to store and retrieve star ratings.
           - Integrate the star rating component into the course evaluation page.
           - Test the star rating feature for accuracy and responsiveness.
        2. **As a student, I want to evaluate courses based on difficulty, workload, and overall gain so that I can provide detailed feedback for future students.**
           - Create a form for evaluating courses with predefined criteria (difficulty, workload, overall gain).
           - Develop backend logic to store and process evaluation criteria.
           - Implement a user interface for submitting detailed course evaluations.
           - Perform user testing on the evaluation form for clarity and ease of use.

        3. **As a student, I want to write detailed reviews for courses so that I can share my experiences and advice with peers.**
           - Implement a text input field for writing detailed reviews.
           - Design the backend to store and display text reviews.
           - Integrate the review feature into the course evaluation system.
           - Conduct usability tests on the review submission process.

     3. **Forum System**

        1. **As a student, I want to create posts with tags related to courses so that I can discuss specific aspects of my academic experience.**
           - Design a post creation form with tag functionality.
           - Implement backend logic for storing and retrieving posts with tags.
           - Create a user interface for post creation including tag selection.
           - Test the post creation feature for usability and functionality.
        2. **As a student, I want to filter and search forum posts by tags and keywords so that I can find relevant discussions and materials.**
           - Develop backend logic for filtering and searching posts by tags and keywords.
           - Implement a search and filter interface in the forum system.
           - Test the search and filter functionality with various user inputs.

        3. **As a student, I want to like and comment on posts so that I can engage with the academic community.**

           - Implement like and comment functionality for posts.

           - Design backend services for storing and retrieving likes and comments.

           - Create user interface components for liking and commenting on posts.

           - Perform functional testing on engagement features to ensure reliability.

     4. **Study Room Reservation System**
        1. As a student, I want to reserve study rooms based on location and availability so that I can have a dedicated space for studying.
           - Design a reservation system interface showing rooms by location and availability.
           - Implement backend logic to handle reservation requests and track room availability.
           - Create a user interface for making and viewing reservations.
           - Test the reservation system for accuracy and user satisfaction.
     5. **Notification System**
        1. As a student, I want to receive reminders about important academic deadlines and activities so that I can manage my academic responsibilities effectively.
           - Design a notification system to aggregate reminders from all subsystems.
           - Implement backend services to trigger notifications based on defined criteria.
           - Develop a user interface for viewing and managing notifications.
           - Test notification delivery and user customization options.

   - **use of response:** We haven't use the response directly, since these tasks are not good assignments of responsibility for each user story. In our group, the members responsible for the front-end and the members responsible for the back-end to write corresponding tasks for each user story respectively.

